package grossary.cyron.com.grossarybrch.category;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CategoryModel {

    @SerializedName("objProductDetailsList")
    public List<Projectlist> projectlists;
    @SerializedName("StatusMessage")
    public String statusmessage;
    @SerializedName("Status")
    public String status;
    @SerializedName("Response")
    public Response response;

    public class Projectlist {
        @SerializedName("ProductId")
        public int productId;
        @SerializedName("StoreId")
        public int stroeId;
        @SerializedName("ProductDescId")
        public int productDescId;
        @SerializedName("ProductName")
        public String productName;
        @SerializedName("ProductImage")
        public String productImage;
        @SerializedName("StoreName")
        public String storeName;
        @SerializedName("MRPPrice")
        public String mRPPrice;
        @SerializedName("SellingPrice")
        public String sellingPrice;
        @SerializedName("SubProductQTY")
        public String subProductQTY;
        @SerializedName("SubProductDesc")
        public String subProductDesc;
        @SerializedName("ShippingCharge")
        public String shippingCharge;
    }

    public class Response {
        @SerializedName("ResponseVal")
        public boolean responseval;
        @SerializedName("Reason")
        public String reason;
    }


}
