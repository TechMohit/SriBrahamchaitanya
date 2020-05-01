package grossary.cyron.com.grossarybrch.cart;

import com.google.gson.annotations.SerializedName;

public class DeleteFromCartDetailsModel {

    @SerializedName("Response")
    private ResponseEntity response;
    @SerializedName("Status")
    private String status;
    @SerializedName("StatusMessage")
    private String statusmessage;

    public ResponseEntity getResponse() {
        return response;
    }

    public void setResponse(ResponseEntity response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusmessage() {
        return statusmessage;
    }

    public void setStatusmessage(String statusmessage) {
        this.statusmessage = statusmessage;
    }

    public static class ResponseEntity {
        @SerializedName("Reason")
        private String reason;
        @SerializedName("ResponseVal")
        private boolean responseval;

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public boolean getResponseval() {
            return responseval;
        }

        public void setResponseval(boolean responseval) {
            this.responseval = responseval;
        }
    }
}
