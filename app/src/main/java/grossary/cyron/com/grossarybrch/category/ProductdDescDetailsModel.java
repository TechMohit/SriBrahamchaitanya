package grossary.cyron.com.grossarybrch.category;

import com.google.gson.annotations.SerializedName;

public class ProductdDescDetailsModel {


    @SerializedName("ProductDescId")
    public int productDescId;
    @SerializedName("UserId")
    public int userId;
    @SerializedName("ProductId")
    public int productId;
    @SerializedName("StoreId")
    public int storeId;
    @SerializedName("ShippingCharges")
    public int shippingCharges;
    @SerializedName("StoreName")
    public String storeName;
    @SerializedName("ProductName")
    public String productName;
    @SerializedName("ProductImage")
    public String productImage;
    @SerializedName("MRPPrice")
    public String mRPPrice;
    @SerializedName("SellingPrice")
    public String sellingPrice;
    @SerializedName("SubProductQTY")
    public String subProductQTY;
    @SerializedName("SubProductDesc")
    public String subProductDesc;
    @SerializedName("StatusMessage")
    public String statusmessage;
    @SerializedName("Status")
    public String status;
    @SerializedName("Response")
    public Response response;

    public class Response {
        @SerializedName("ResponseVal")
        public boolean responseval;
        @SerializedName("Reason")
        public String reason;
    }

}
