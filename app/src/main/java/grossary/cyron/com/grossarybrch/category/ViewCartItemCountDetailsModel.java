package grossary.cyron.com.grossarybrch.category;

import com.google.gson.annotations.SerializedName;

public class ViewCartItemCountDetailsModel {


    @SerializedName("Response")
    private ResponseEntity response;
    @SerializedName("TotalItemCount")
    private int totalitemcount;
    @SerializedName("GrandToal")
    private int grandtoal;

    public ResponseEntity getResponse() {
        return response;
    }

    public void setResponse(ResponseEntity response) {
        this.response = response;
    }

    public int getTotalitemcount() {
        return totalitemcount;
    }

    public void setTotalitemcount(int totalitemcount) {
        this.totalitemcount = totalitemcount;
    }

    public int getGrandtoal() {
        return grandtoal;
    }

    public void setGrandtoal(int grandtoal) {
        this.grandtoal = grandtoal;
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
