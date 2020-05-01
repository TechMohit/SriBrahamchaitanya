package grossary.cyron.com.grossarybrch.utility.retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {

    private static final int CACHE_SIZE = 5 * 1024 * 1024;//5 MB
    private static RetrofitClient sInstance;
    private Retrofit retrofit = null;


    public static void create(File cacheDir) {
        if (sInstance == null) {
            synchronized (RetrofitClient.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitClient(cacheDir);
                }
            }
        } else
            throw new IllegalStateException("RetrofitClient instance is already been created.");
    }

    private RetrofitClient() {
    }

    private RetrofitClient(File cacheDir) {
        this();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        httpClient.cache(new Cache(cacheDir, CACHE_SIZE));
        httpClient.addInterceptor(new ApplicationInterceptor());
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.vzons.com")
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Returns the instance of {@link Retrofit}.
     * This method must be called after {@link #create}.
     */
    public static Retrofit retrofit() {
        synchronized (RetrofitClient.class) {
            if (sInstance == null)
                throw new IllegalStateException("RetrofitClient instance is not created yet. Call RetrofitClient.create() before calling getInstance()");

        }
        return sInstance.retrofit;
    }

    /**
     * Returns the instance of {@link APIInterface}.
     * This method must be called after {@link #create}.
     */
    public static APIInterface getAPIInterface() {
        return retrofit().create(APIInterface.class);
    }

    /**
     * An interceptor is used to modify each request before it is performed and alters the request header.
     * The advantage is, that you don’t have to add @Header("Authorization") to each API method definition.
     */
    private class ApplicationInterceptor implements Interceptor {


        public ApplicationInterceptor() {
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();
//            String authToken = "";
//            requestBuilder.header(APIInterface.Header.AUTHORIZATION, authToken).build();
//            requestBuilder.header(APIInterface.Header.CONTENT_TYPE, "application/json").build();
            requestBuilder.header(APIInterface.Header.CONTENT_TYPE, "application/x-www-form-urlencoded").build();
//
            Request request = requestBuilder.build();
            Response response = chain.proceed(request);
            return response;
        }
    }
}
