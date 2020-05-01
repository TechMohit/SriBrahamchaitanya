package grossary.cyron.com.grossarybrch.category;

import com.google.gson.annotations.SerializedName;

public class AddToCartDetailsModel {


    @SerializedName("Message")
    private String message;
    @SerializedName("TotalItemCount")
    private int totalitemcount;
    @SerializedName("Response")
    private ResponseEntity response;
    @SerializedName("Status")
    private String status;
    @SerializedName("StatusMessage")
    private String statusmessage;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotalitemcount() {
        return totalitemcount;
    }

    public void setTotalitemcount(int totalitemcount) {
        this.totalitemcount = totalitemcount;
    }

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
