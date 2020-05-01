package grossary.cyron.com.grossarybrch.payment;

import com.google.gson.annotations.SerializedName;

public class SubmitTransactionModel {


    @SerializedName("Response")
    private ResponseEntity response;
    @SerializedName("Paymode")
    private String paymode;
    @SerializedName("ShippingAddress")
    private String shippingaddress;
    @SerializedName("CustomerName")
    private String customername;
    @SerializedName("TotalAmount")
    private int totalamount;
    @SerializedName("TransactionDate")
    private String transactiondate;
    @SerializedName("TranNo")
    private String tranno;
    @SerializedName("PaymentTransactionNumber")
    private int paymenttransactionnumber;
    @SerializedName("Message")
    private String message;
    @SerializedName("Status")
    private String status;
    @SerializedName("StatusClass")
    private String statusclass;
    @SerializedName("NoInfoMessage")
    private String noinfomessage;

    public ResponseEntity getResponse() {
        return response;
    }

    public void setResponse(ResponseEntity response) {
        this.response = response;
    }

    public String getPaymode() {
        return paymode;
    }

    public void setPaymode(String paymode) {
        this.paymode = paymode;
    }

    public String getShippingaddress() {
        return shippingaddress;
    }

    public void setShippingaddress(String shippingaddress) {
        this.shippingaddress = shippingaddress;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public int getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(int totalamount) {
        this.totalamount = totalamount;
    }

    public String getTransactiondate() {
        return transactiondate;
    }

    public void setTransactiondate(String transactiondate) {
        this.transactiondate = transactiondate;
    }

    public String getTranno() {
        return tranno;
    }

    public void setTranno(String tranno) {
        this.tranno = tranno;
    }

    public int getPaymenttransactionnumber() {
        return paymenttransactionnumber;
    }

    public void setPaymenttransactionnumber(int paymenttransactionnumber) {
        this.paymenttransactionnumber = paymenttransactionnumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusclass() {
        return statusclass;
    }

    public void setStatusclass(String statusclass) {
        this.statusclass = statusclass;
    }

    public String getNoinfomessage() {
        return noinfomessage;
    }

    public void setNoinfomessage(String noinfomessage) {
        this.noinfomessage = noinfomessage;
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
