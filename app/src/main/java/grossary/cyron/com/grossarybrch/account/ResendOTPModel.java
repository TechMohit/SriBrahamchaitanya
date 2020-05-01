package grossary.cyron.com.grossarybrch.account;

import com.google.gson.annotations.SerializedName;

/**
 * Created by subham_naik on 07-Oct-18.
 */

public class ResendOTPModel {

    @SerializedName("UserId")
    public int userid;
    @SerializedName("LoginId")
    public String loginid;
    @SerializedName("FullName")
    public String fullname;
    @SerializedName("Mobile")
    public String mobile;
    @SerializedName("Email")
    public String email;
    @SerializedName("Address")
    public String address;
    @SerializedName("City")
    public String city;
    @SerializedName("State")
    public String state;
    @SerializedName("ZipCode")
    public String zipcode;
    @SerializedName("StoreId")
    public String storeid;
    @SerializedName("GstNumber")
    public String gstnumber;
    @SerializedName("RoleCode")
    public String rolecode;
    @SerializedName("OTP")
    public String otp;
    @SerializedName("Status")
    public String status;
    @SerializedName("StatusMessage")
    public String statusmessage;
    @SerializedName("Response")
    public Response response;

    public static class Response {
        @SerializedName("ResponseVal")
        public boolean responseval;
        @SerializedName("Reason")
        public String reason;
    }
}
