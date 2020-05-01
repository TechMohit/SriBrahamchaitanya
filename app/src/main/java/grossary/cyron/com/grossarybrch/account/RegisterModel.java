package grossary.cyron.com.grossarybrch.account;

import com.google.gson.annotations.SerializedName;

public class RegisterModel {


    @SerializedName("MobileNumber")
    public String mobilenumber;
    @SerializedName("OTP")
    public String otp;
    @SerializedName("Response")
    public Response response;

    public String getMobilenumber() {
        return mobilenumber;
    }

    public RegisterModel setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
        return this;
    }

    public String getOtp() {
        return otp;
    }

    public RegisterModel setOtp(String otp) {
        this.otp = otp;
        return this;
    }

    public Response getResponse() {
        return response;
    }

    public RegisterModel setResponse(Response response) {
        this.response = response;
        return this;
    }

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
}
