package grossary.cyron.com.grossarybrch.productList;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FileUploadService {

    //change in string.xml file also

    //baseurl for UAT
   // String BASE_URL_FOR_LOGIN = "http://demo.mobileapi.benakasoft.com/api/";
   // String BASE_URL_FOR_LOGIN = "http://mobileapi.benakasoft.com/api/";
    String BASE_URL_FOR_LOGIN = "http://mobileapi.sbcstores.in/api/";

    @Headers("Content-Type: application/json")
    @POST("Home/ProductPiceList")
    Call<ResponseBody> LoginAuthentication(@Body RequestBody requestdata);

    @Headers("Content-Type: application/json")
    @POST("ShoppingCart/ViewCartItemCountDetails")
    Call<ResponseBody> Cartcount(@Body RequestBody requestdata);



}


