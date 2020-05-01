package grossary.cyron.com.grossarybrch.order;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsModel {


    @SerializedName("Response")
    private ResponseEntity response;
    @SerializedName("orderDetail")
    private List<OrderdetailEntity> orderdetail;

    public ResponseEntity getResponse() {
        return response;
    }

    public void setResponse(ResponseEntity response) {
        this.response = response;
    }

    public List<OrderdetailEntity> getOrderdetail() {
        return orderdetail;
    }

    public void setOrderdetail(List<OrderdetailEntity> orderdetail) {
        this.orderdetail = orderdetail;
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

    public static class OrderdetailEntity {
        @SerializedName("StoreName")
        private String storename;
        @SerializedName("ProductImage")
        private String productimage;
        @SerializedName("TotalOrderPrice")
        private int totalorderprice;
        @SerializedName("OrderItemQty")
        private String orderitemqty;
        @SerializedName("ProductPrice")
        private String productprice;
        @SerializedName("CustomerName")
        private String customername;
        @SerializedName("SubProductName")
        private String subproductname;
        @SerializedName("ProductName")
        private String productname;
        @SerializedName("OrderId")
        private String orderid;
        @SerializedName("TranDate")
        private String trandate;
        @SerializedName("TranNo")
        private String tranno;

        public String getStorename() {
            return storename;
        }

        public void setStorename(String storename) {
            this.storename = storename;
        }

        public String getProductimage() {
            return productimage;
        }

        public void setProductimage(String productimage) {
            this.productimage = productimage;
        }

        public int getTotalorderprice() {
            return totalorderprice;
        }

        public void setTotalorderprice(int totalorderprice) {
            this.totalorderprice = totalorderprice;
        }

        public String getOrderitemqty() {
            return orderitemqty;
        }

        public void setOrderitemqty(String orderitemqty) {
            this.orderitemqty = orderitemqty;
        }

        public String getProductprice() {
            return productprice;
        }

        public void setProductprice(String productprice) {
            this.productprice = productprice;
        }

        public String getCustomername() {
            return customername;
        }

        public void setCustomername(String customername) {
            this.customername = customername;
        }

        public String getSubproductname() {
            return subproductname;
        }

        public void setSubproductname(String subproductname) {
            this.subproductname = subproductname;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getTrandate() {
            return trandate;
        }

        public void setTrandate(String trandate) {
            this.trandate = trandate;
        }

        public String getTranno() {
            return tranno;
        }

        public void setTranno(String tranno) {
            this.tranno = tranno;
        }
    }
}
