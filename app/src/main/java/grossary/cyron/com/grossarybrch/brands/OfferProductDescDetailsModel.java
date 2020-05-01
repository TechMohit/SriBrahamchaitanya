package grossary.cyron.com.grossarybrch.brands;

import com.google.gson.annotations.SerializedName;

public class OfferProductDescDetailsModel {


    @SerializedName("Response")
    private ResponseEntity Response;
    @SerializedName("Status")
    private String Status;
    @SerializedName("SubProductDesc")
    private String SubProductDesc;
    @SerializedName("SubProductQTY")
    private String SubProductQTY;
    @SerializedName("SellingPrice")
    private String SellingPrice;
    @SerializedName("MRPPrice")
    private String MRPPrice;
    @SerializedName("ProductImage")
    private String ProductImage;
    @SerializedName("ProductName")
    private String ProductName;
    @SerializedName("StoreName")
    private String StoreName;
    @SerializedName("ShippingCharges")
    private int ShippingCharges;
    @SerializedName("StoreId")
    private int StoreId;
    @SerializedName("ProductId")
    private int ProductId;
    @SerializedName("UserId")
    private int UserId;
    @SerializedName("ProductDescId")
    private int ProductDescId;

    public ResponseEntity getResponse() {
        return Response;
    }

    public void setResponse(ResponseEntity Response) {
        this.Response = Response;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getSubProductDesc() {
        return SubProductDesc;
    }

    public void setSubProductDesc(String SubProductDesc) {
        this.SubProductDesc = SubProductDesc;
    }

    public String getSubProductQTY() {
        return SubProductQTY;
    }

    public void setSubProductQTY(String SubProductQTY) {
        this.SubProductQTY = SubProductQTY;
    }

    public String getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(String SellingPrice) {
        this.SellingPrice = SellingPrice;
    }

    public String getMRPPrice() {
        return MRPPrice;
    }

    public void setMRPPrice(String MRPPrice) {
        this.MRPPrice = MRPPrice;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String ProductImage) {
        this.ProductImage = ProductImage;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String StoreName) {
        this.StoreName = StoreName;
    }

    public int getShippingCharges() {
        return ShippingCharges;
    }

    public void setShippingCharges(int ShippingCharges) {
        this.ShippingCharges = ShippingCharges;
    }

    public int getStoreId() {
        return StoreId;
    }

    public void setStoreId(int StoreId) {
        this.StoreId = StoreId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int ProductId) {
        this.ProductId = ProductId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public int getProductDescId() {
        return ProductDescId;
    }

    public void setProductDescId(int ProductDescId) {
        this.ProductDescId = ProductDescId;
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
