package grossary.cyron.com.grossarybrch.brands;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrandsModel {


    @SerializedName("objCategoryList")
    public List<Objcategorylist> objcategorylist;
    @SerializedName("objStoreDetailsList")
    public List<Objstoredetailslist> objstoredetailslist;
    @SerializedName("objOfferDetailsList")
    public List<Objofferdetailslist> objofferdetailslist;
    @SerializedName("StatusMessage")
    public String statusmessage;
    @SerializedName("Status")
    public String status;
    @SerializedName("Response")
    public Response response;

    public static class Objcategorylist {
        @SerializedName("CatergoryId")
        public int catergoryid;
        @SerializedName("CatergoryName")
        public String catergoryname;
        @SerializedName("CatergoryImage")
        public String catergoryimage;
    }

    public static class Objstoredetailslist {
        @SerializedName("StoreId")
        public int storeid;
        @SerializedName("StoreName")
        public String storename;
        @SerializedName("StoreImage")
        public String storeimage;
    }

    public static class Objofferdetailslist {
        @SerializedName("ProductDescId")
        public int productdescid;
        @SerializedName("ProductId")
        public int productid;
        @SerializedName("ProductName")
        public String productname;
        @SerializedName("MRPPrice")
        public String mrpprice;
        @SerializedName("SellingPrice")
        public String sellingprice;
        @SerializedName("ProductImage")
        public String productimage;
    }

    public static class Response {
        @SerializedName("ResponseVal")
        public boolean responseval;
        @SerializedName("Reason")
        public String reason;
    }
}
