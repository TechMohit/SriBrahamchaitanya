package grossary.cyron.com.grossarybrch.utility.retrofit;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import grossary.cyron.com.grossarybrch.utility.retrofit.callbacks.Request;
import grossary.cyron.com.grossarybrch.utility.retrofit.callbacks.ResponseListener;
import retrofit2.Call;
import retrofit2.Callback;

public final class RetrofitRequest<T> extends Request {

    private final ResponseListener responseListener;
    private Call<T> call;

    public RetrofitRequest(Call<T> call, ResponseListener<T> responseListener) {
        this.call = call;
        this.responseListener = responseListener;
    }

    @Override
    public void enqueue() {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, retrofit2.Response<T> response) {
//                if (response.isSuccessful())
                    responseListener.onResponse(response.code(),response.body(), response.headers());
//                else {
//                    switch (response.code()) {
//                        case HttpURLConnection.HTTP_UNAUTHORIZED:
//                            break;
//                        case 400:
//                            Errors errors = RetrofitUtil.parseError(response);
//                            responseListener.onError(response.code(), errors);
//                            break;
//                        case HttpURLConnection.HTTP_NOT_FOUND:
//                            errors = RetrofitUtil.parseError(response);
//                            responseListener.onError(response.code());
//                            break;
//                    }

//                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (call != null && call.isCanceled())// don't process if request is cancelled.
                    return;
                if (t instanceof UnknownHostException)
                    responseListener.onFailure(t);//network error
                else {
                    List<Errors.Error> errors = new ArrayList<>();
                    errors.add(new Errors.Error("Something went wrong", ""));
                    responseListener.onError(0);
                }
            }
        });
    }

    @Override
    public void cancel() {
        call.cancel();
    }

    @Override
    public void retry() {
        call = call.clone();
        enqueue();
    }

}
