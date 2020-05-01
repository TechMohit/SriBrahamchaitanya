package grossary.cyron.com.grossarybrch.utility.retrofit;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class RetrofitUtil {

    public static Errors parseError(Response<?> response) {
        Converter<ResponseBody, Errors> converter =
                RetrofitClient.retrofit()
                        .responseBodyConverter(Errors.class, new Annotation[0]);

        Errors error;
        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new Errors();
        }

        return error;
    }

    @NonNull
    public static RequestBody getRequestBody(String value) {
        return RequestBody.create(MultipartBody.FORM, value);
    }

    @NonNull
    public static MultipartBody.Part getImageMultipart(Context context, Uri imageUri, String key) {
        MultipartBody.Part imageMultipart = null;
        if (null == imageUri) {
            return null;
        } else {
            String urlPath;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                urlPath = Camera.getPath(context, imageUri);
            } else {
                urlPath = Camera.getRealPathFromURI(context, imageUri);
            }
            //urlpath can be null in some device because we are returning null inside the function getPath or getRealPathFromURI
            //not reproducible in device

            if (urlPath != null) {
                File imageFile = new File(urlPath);
                imageMultipart = MultipartBody.Part.createFormData(
                        key, imageFile.getName(), RequestBody.create(MediaType.parse("image/*"), imageFile));
            }
            return imageMultipart;
        }
    }

    @NonNull
    public static List<MultipartBody.Part> getImageMultipartStr(Context context, List<String> imageUri) {
        List<MultipartBody.Part> imageMultipart = new ArrayList<>();
        if (null == imageUri) {
            return null;
        }
        for (int i = 0; i < imageUri.size(); i++) {
            //urlpath can be null in some device because we are returning null inside the function getPath or getRealPathFromURI
            //not reproducible in device
            File imageFile = new File(imageUri.get(i));
            imageMultipart.add(MultipartBody.Part.createFormData(
                    "packagePhoto", imageFile.getName(), RequestBody.create(MediaType.parse("*/*"), imageFile)));

        }
        return imageMultipart;

    }
}
