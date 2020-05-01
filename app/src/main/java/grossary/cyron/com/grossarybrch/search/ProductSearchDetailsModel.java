package grossary.cyron.com.grossarybrch.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductSearchDetailsModel {


    @SerializedName("Response")
    private ResponseEntity response;
    @SerializedName("Status")
    private String status;
    @SerializedName("StatusMessage")
    private String statusmessage;
    @SerializedName("objProductSearchDetails")
    private List<ObjproductsearchdetailsEntity> objproductsearchdetails;

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

    public List<ObjproductsearchdetailsEntity> getObjproductsearchdetails() {
        return objproductsearchdetails;
    }

    public void setObjproductsearchdetails(List<ObjproductsearchdetailsEntity> objproductsearchdetails) {
        this.objproductsearchdetails = objproductsearchdetails;
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

    public static class ObjproductsearchdetailsEntity {
        @SerializedName("ProductImage")
        private String productimage;
        @SerializedName("CategoryName")
        private String categoryname;
        @SerializedName("ProductName")
        private String productname;
        @SerializedName("ProductDescId")
        private int productdescid;

        public String getProductimage() {
            return productimage;
        }

        public void setProductimage(String productimage) {
            this.productimage = productimage;
        }

        public String getCategoryname() {
            return categoryname;
        }

        public void setCategoryname(String categoryname) {
            this.categoryname = categoryname;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public int getProductdescid() {
            return productdescid;
        }

        public void setProductdescid(int productdescid) {
            this.productdescid = productdescid;
        }
    }
}
