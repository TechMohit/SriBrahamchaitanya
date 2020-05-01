package grossary.cyron.com.grossarybrch.utility.retrofit;

import grossary.cyron.com.grossarybrch.account.LoginModel;
import grossary.cyron.com.grossarybrch.account.RegisterModel;
import grossary.cyron.com.grossarybrch.account.ResendOTPModel;
import grossary.cyron.com.grossarybrch.account.VerifyRegisterOTPModel;
import grossary.cyron.com.grossarybrch.brands.OfferProductDescDetailsModel;
import grossary.cyron.com.grossarybrch.cart.DeleteFromCartDetailsModel;
import grossary.cyron.com.grossarybrch.cart.UpdateFromCartDetailsModel;
import grossary.cyron.com.grossarybrch.cart.ViewAddtoCartDetailsModel;
import grossary.cyron.com.grossarybrch.category.AddToCartDetailsModel;
import grossary.cyron.com.grossarybrch.category.CategoryModel;
import grossary.cyron.com.grossarybrch.category.ProductdDescDetailsModel;
import grossary.cyron.com.grossarybrch.category.ViewCartItemCountDetailsModel;
import grossary.cyron.com.grossarybrch.home.HomeModel;
import grossary.cyron.com.grossarybrch.order.OrderDetailsModel;
import grossary.cyron.com.grossarybrch.order.ViewOrderListModel;
import grossary.cyron.com.grossarybrch.payment.PaymentGatewayRequestModel;
import grossary.cyron.com.grossarybrch.payment.SubmitTransactionModel;
import grossary.cyron.com.grossarybrch.profile.GetUserProfileModel;
import grossary.cyron.com.grossarybrch.profile.GetUserProfileUpdateModel;
import grossary.cyron.com.grossarybrch.search.ProductSearchDetailsModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface APIInterface {

    @POST()
    @FormUrlEncoded
    Call<LoginModel> authenticate(@Url String url, @Field("MobileNumber") String MobileNumber);

    @POST()
    @FormUrlEncoded
    Call<GetUserProfileUpdateModel> getUserProfileUpdate(@Url String url, @Field("userId") String UserId, @Field("LoginId") String LoginId
            , @Field("FullName") String FullName, @Field("MobileNo") String MobileNo, @Field("Email") String Email, @Field("Address") String Address
            , @Field("GSTNumber") String GSTNumber, @Field("ZipCode") String ZipCode);

    @POST()
    @FormUrlEncoded
    Call<GetUserProfileModel> getUserProfile(@Url String url, @Field("UserId") String UserId);

    @POST()
    @FormUrlEncoded
    Call<ViewOrderListModel> viewOrderList(@Url String url, @Field("UserId") String UserId);

    @POST()
    @FormUrlEncoded
    Call<ViewOrderListModel.CancelOrderEntity> cancelOrder(@Url String url, @Field("TranNo") String TranNo, @Field("UserId") String UserId);


    @POST()
    @FormUrlEncoded
    Call<DeleteFromCartDetailsModel> deleteFromCartDetails(@Url String url, @Field("OrderId") String OrderId);

    @POST()
    @FormUrlEncoded
    Call<UpdateFromCartDetailsModel> updateFromCartDetails(@Url String url, @Field("OrderId") String OrderId, @Field("Qty") String Qty);


    @POST()
    @FormUrlEncoded
    Call<ViewAddtoCartDetailsModel> viewAddtoCartDetails(@Url String url, @Field("UserId") String UserId);

    @POST()
    @FormUrlEncoded
    Call<ViewCartItemCountDetailsModel> viewCartItemCountDetails(@Url String url, @Field("UserId") String UserId);


    @POST()
    @FormUrlEncoded
    Call<SubmitTransactionModel> submitTransaction(@Url String url, @Field("FullName") String FullName, @Field("Address") String Address
            , @Field("City") String City, @Field("State") String State, @Field("ZipCode") String ZipCode, @Field("Phone") String Phone
            , @Field("UserId") String UserId, @Field("Paymode") String Paymode, @Field("TotalShippingCharges") String TotalShippingCharges
            , @Field("GrandToal") String GrandToal);

    @POST()
    @FormUrlEncoded
    Call<PaymentGatewayRequestModel> paymentGatewayRequest(@Url String url, @Field("FullName") String FullName, @Field("Address") String Address
            , @Field("City") String City, @Field("State") String State, @Field("ZipCode") String ZipCode, @Field("Phone") String Phone
            , @Field("UserId") String UserId, @Field("Paymode") String Paymode, @Field("TotalShippingCharges") String TotalShippingCharges
            , @Field("GrandToal") String GrandToal);


    @POST()
    @FormUrlEncoded
    Call<OrderDetailsModel> orderDetails(@Url String url, @Field("UserId") String UserId, @Field("TranNo") String TranNo);

    @POST()
    @FormUrlEncoded
    Call<ResendOTPModel> resendOTP(@Url String url, @Field("MobileNumber") String MobileNumber);

    @POST()
    @FormUrlEncoded
    Call<VerifyRegisterOTPModel> verifyRegisterOTP(@Url String url, @Field("MobileNumber") String MobileNumber, @Field("OTP") String otp);


    @POST()
    @FormUrlEncoded
    Call<ProductdDescDetailsModel> ProductdDescDetails(@Url String url, @Field("ProductDescId") String ProductDescId);

    @POST()
    @FormUrlEncoded
    Call<OfferProductDescDetailsModel> OfferProductdDescDetails(@Url String url, @Field("ProductDescId") String ProductDescId);

    @POST()
    @FormUrlEncoded
    Call<CategoryModel> productDetails(@Url String url, @Field("Storeid") String Storeid, @Field("CategoryId") String CategoryId);

    /*@POST()
    @FormUrlEncoded
    Call<ProductlistModel.Productlist> productlistDetails(@Url String url, @Field("Storeid") String Storeid, @Field("CategoryId") String CategoryId);
    */@POST()
    @FormUrlEncoded
    Call<CategoryModel> productSearch(@Url String url, @Field("SearchTag") String SearchTag);

    @POST()
    Call<ProductSearchDetailsModel> productSearchDetails(@Url String url);

    @POST()
    @FormUrlEncoded
    Call<AddToCartDetailsModel> addToCartDetails(@Url String url, @Field("UserId") String UserId, @Field("ProductDescId") String ProductDescId,
                                                 @Field("ProductId") String ProductId, @Field("StoreId") String StoreId,
                                                 @Field("ShippingCharges") String ShippingCharges, @Field("Qty") String Qty);

    @POST()
    @FormUrlEncoded
    Call<RegisterModel> register(@Url String url, @Field("PortalLoginName") String PortalLoginName, @Field("Password") String Password,
                                 @Field("Address") String Address, @Field("MobileNumber") String MobileNumber, @Field("EmailId") String EmailId,
                                 @Field("GSTNumber") String GSTNumber);

    @POST()
    @FormUrlEncoded
    Call<HomeModel> homeDetailsAPI(@Url String url, @Field("UserId") String UserId);

    interface Header {
        String AUTHORIZATION = "Authorization";
        String CONTENT_TYPE = "Content-Type";
    }

}
