package grossary.cyron.com.grossarybrch.account;

import com.google.gson.annotations.SerializedName;

/**
 * Created by subham_naik on 07-Oct-18.
 */

public class VerifyRegisterOTPModel {

    @SerializedName("MobileNumber")
    public String mobilenumber;
    @SerializedName("OTP")
    public String otp;
    @SerializedName("Response")
    public Response response;

    public static class Response {
        @SerializedName("ResponseVal")
        public boolean responseval;
        @SerializedName("Reason")
        public String reason;

        public boolean isResponseval() {
            return responseval;
        }

        public Response setResponseval(boolean responseval) {
            this.responseval = responseval;
            return this;
        }

        public String getReason() {
            return reason;
        }

        public Response setReason(String reason) {
            this.reason = reason;
            return this;
        }
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public VerifyRegisterOTPModel setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
        return this;
    }

    public String getOtp() {
        return otp;
    }

    public VerifyRegisterOTPModel setOtp(String otp) {
        this.otp = otp;
        return this;
    }

    public Response getResponse() {
        return response;
    }

    public VerifyRegisterOTPModel setResponse(Response response) {
        this.response = response;
        return this;
    }
}
