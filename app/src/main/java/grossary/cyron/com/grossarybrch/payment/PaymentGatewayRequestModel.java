package grossary.cyron.com.grossarybrch.payment;

import com.google.gson.annotations.SerializedName;

public class PaymentGatewayRequestModel {


    @SerializedName("Response")
    private ResponseEntity Response;
    @SerializedName("GatewayURL")
    private String GatewayURL;

    public ResponseEntity getResponse() {
        return Response;
    }

    public void setResponse(ResponseEntity Response) {
        this.Response = Response;
    }

    public String getGatewayURL() {
        return GatewayURL;
    }

    public void setGatewayURL(String GatewayURL) {
        this.GatewayURL = GatewayURL;
    }

    public static class ResponseEntity {
        @SerializedName("Reason")
        private String Reason;
        @SerializedName("ResponseVal")
        private boolean ResponseVal;

        public String getReason() {
            return Reason;
        }

        public void setReason(String Reason) {
            this.Reason = Reason;
        }

        public boolean getResponseVal() {
            return ResponseVal;
        }

        public void setResponseVal(boolean ResponseVal) {
            this.ResponseVal = ResponseVal;
        }
    }
}
