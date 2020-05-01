package grossary.cyron.com.grossarybrch.home;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeModel implements Parcelable {


    @SerializedName("Response")
    private ResponseEntity Response;

    @SerializedName("Status")
    private String Status;

    @SerializedName("objTotalCartItemCount")
    private ObjTotalCartItemCountEntity objTotalCartItemCount;

    @SerializedName("objOfferProdList")
    private List<ObjOfferProdListEntity> objOfferProdList;

    @SerializedName("objOfferImageList")
    private List<ObjOfferImageListEntity> objOfferImageList;

    @SerializedName("objOfferDetailsList")
    private List<ObjOfferDetailsListEntity> objOfferDetailsList;

    @SerializedName("objStoreDetailsList")
    private List<ObjStoreDetailsListEntity> objStoreDetailsList;

    @SerializedName("objCategoryList")
    private List<ObjCategoryListEntity> objCategoryList;

    public HomeModel(){

    }
    public HomeModel(Parcel in) {
        Status = in.readString();
        Response=in.readParcelable(ResponseEntity.class.getClassLoader());
        objTotalCartItemCount=in.readParcelable(ObjTotalCartItemCountEntity.class.getClassLoader());
        objOfferProdList=in.readParcelable(ObjOfferProdListEntity.class.getClassLoader());
        objOfferImageList=in.readParcelable(ObjOfferImageListEntity.class.getClassLoader());
        objOfferDetailsList=in.readParcelable(ObjOfferDetailsListEntity.class.getClassLoader());
        objStoreDetailsList=in.readParcelable(ObjStoreDetailsListEntity.class.getClassLoader());
        objCategoryList=in.readParcelable(ObjCategoryListEntity.class.getClassLoader());

    }

    public  final Creator<HomeModel> CREATOR = new Creator<HomeModel>() {
        @Override
        public HomeModel createFromParcel(Parcel in) {
            return new HomeModel(in);
        }

        @Override
        public HomeModel[] newArray(int size) {
            return new HomeModel[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Status);
        dest.writeParcelable(Response,flags);
        dest.writeParcelable(objTotalCartItemCount,flags);
        dest.writeTypedList( objOfferProdList);
        dest.writeTypedList(objOfferImageList);
        dest.writeTypedList(objOfferDetailsList);
        dest.writeTypedList(objStoreDetailsList);
        dest.writeTypedList(objCategoryList);

    }

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

    public ObjTotalCartItemCountEntity getObjTotalCartItemCount() {
        return objTotalCartItemCount;
    }

    public void setObjTotalCartItemCount(ObjTotalCartItemCountEntity objTotalCartItemCount) {
        this.objTotalCartItemCount = objTotalCartItemCount;
    }

    public List<ObjOfferProdListEntity> getObjOfferProdList() {
        return objOfferProdList;
    }

    public void setObjOfferProdList(List<ObjOfferProdListEntity> objOfferProdList) {
        this.objOfferProdList = objOfferProdList;
    }

    public List<ObjOfferImageListEntity> getObjOfferImageList() {
        return objOfferImageList;
    }

    public void setObjOfferImageList(List<ObjOfferImageListEntity> objOfferImageList) {
        this.objOfferImageList = objOfferImageList;
    }

    public List<HomeModel.ObjOfferDetailsListEntity> getObjOfferDetailsList() {
        return objOfferDetailsList;
    }

    public void setObjOfferDetailsList(List<ObjOfferDetailsListEntity> objOfferDetailsList) {
        this.objOfferDetailsList = objOfferDetailsList;
    }

    public List<ObjStoreDetailsListEntity> getObjStoreDetailsList() {
        return objStoreDetailsList;
    }

    public void setObjStoreDetailsList(List<ObjStoreDetailsListEntity> objStoreDetailsList) {
        this.objStoreDetailsList = objStoreDetailsList;
    }

    public List<ObjCategoryListEntity> getObjCategoryList() {
        return objCategoryList;
    }

    public void setObjCategoryList(List<ObjCategoryListEntity> objCategoryList) {
        this.objCategoryList = objCategoryList;
    }



    public  class ResponseEntity implements Parcelable {
        @SerializedName("Reason")
        private String Reason;
        @SerializedName("ResponseVal")
        private boolean ResponseVal;

        protected ResponseEntity(Parcel in) {
            Reason = in.readString();
            ResponseVal = in.readByte() != 0;
        }

        public  final Creator<ResponseEntity> CREATOR = new Creator<ResponseEntity>() {
            @Override
            public ResponseEntity createFromParcel(Parcel in) {
                return new ResponseEntity(in);
            }

            @Override
            public ResponseEntity[] newArray(int size) {
                return new ResponseEntity[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(Reason);
            dest.writeByte((byte) (ResponseVal ? 1 : 0));
        }
    }

    public  class ObjTotalCartItemCountEntity implements Parcelable {
        @SerializedName("TotalItemCount")
        private int TotalItemCount;

        protected ObjTotalCartItemCountEntity(Parcel in) {
            TotalItemCount = in.readInt();
        }

        public  final Creator<ObjTotalCartItemCountEntity> CREATOR = new Creator<ObjTotalCartItemCountEntity>() {
            @Override
            public ObjTotalCartItemCountEntity createFromParcel(Parcel in) {
                return new ObjTotalCartItemCountEntity(in);
            }

            @Override
            public ObjTotalCartItemCountEntity[] newArray(int size) {
                return new ObjTotalCartItemCountEntity[size];
            }
        };

        public int getTotalItemCount() {
            return TotalItemCount;
        }

        public void setTotalItemCount(int TotalItemCount) {
            this.TotalItemCount = TotalItemCount;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(TotalItemCount);
        }
    }

    public class ObjOfferProdListEntity implements Parcelable {
        @SerializedName("ShippingCharge")
        private String ShippingCharge;
        @SerializedName("SubProductDesc")
        private String SubProductDesc;
        @SerializedName("SubProductQTY")
        private String SubProductQTY;
        @SerializedName("SellingPrice")
        private String SellingPrice;
        @SerializedName("MRPPrice")
        private String MRPPrice;
        @SerializedName("StoreName")
        private String StoreName;
        @SerializedName("ProductImage")
        private String ProductImage;
        @SerializedName("ProductName")
        private String ProductName;
        @SerializedName("ProductDescId")
        private int ProductDescId;
        @SerializedName("StoreId")
        private int StoreId;
        @SerializedName("ProductId")
        private int ProductId;

        protected ObjOfferProdListEntity(Parcel in) {
            ShippingCharge = in.readString();
            SubProductDesc = in.readString();
            SubProductQTY = in.readString();
            SellingPrice = in.readString();
            MRPPrice = in.readString();
            StoreName = in.readString();
            ProductImage = in.readString();
            ProductName = in.readString();
            ProductDescId = in.readInt();
            StoreId = in.readInt();
            ProductId = in.readInt();
        }

        public final Creator<ObjOfferProdListEntity> CREATOR = new Creator<ObjOfferProdListEntity>() {
            @Override
            public ObjOfferProdListEntity createFromParcel(Parcel in) {
                return new ObjOfferProdListEntity(in);
            }

            @Override
            public ObjOfferProdListEntity[] newArray(int size) {
                return new ObjOfferProdListEntity[size];
            }
        };

        public String getShippingCharge() {
            return ShippingCharge;
        }

        public void setShippingCharge(String ShippingCharge) {
            this.ShippingCharge = ShippingCharge;
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

        public String getStoreName() {
            return StoreName;
        }

        public void setStoreName(String StoreName) {
            this.StoreName = StoreName;
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

        public int getProductDescId() {
            return ProductDescId;
        }

        public void setProductDescId(int ProductDescId) {
            this.ProductDescId = ProductDescId;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(ShippingCharge);
            dest.writeString(SubProductDesc);
            dest.writeString(SubProductQTY);
            dest.writeString(SellingPrice);
            dest.writeString(MRPPrice);
            dest.writeString(StoreName);
            dest.writeString(ProductImage);
            dest.writeString(ProductName);
            dest.writeInt(ProductDescId);
            dest.writeInt(StoreId);
            dest.writeInt(ProductId);
        }
    }

    public  class ObjOfferImageListEntity implements Parcelable{
        @SerializedName("OfferImage")
        private String OfferImage;
        @SerializedName("OfferId")
        private int OfferId;

        protected ObjOfferImageListEntity(Parcel in) {
            OfferImage = in.readString();
            OfferId = in.readInt();
        }

        public  final Creator<ObjOfferImageListEntity> CREATOR = new Creator<ObjOfferImageListEntity>() {
            @Override
            public ObjOfferImageListEntity createFromParcel(Parcel in) {
                return new ObjOfferImageListEntity(in);
            }

            @Override
            public ObjOfferImageListEntity[] newArray(int size) {
                return new ObjOfferImageListEntity[size];
            }
        };

        public String getOfferImage() {
            return OfferImage;
        }

        public void setOfferImage(String OfferImage) {
            this.OfferImage = OfferImage;
        }

        public int getOfferId() {
            return OfferId;
        }

        public void setOfferId(int OfferId) {
            this.OfferId = OfferId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(OfferImage);
            dest.writeInt(OfferId);
        }
    }

    public  class ObjOfferDetailsListEntity implements Parcelable {
        @SerializedName("ProductImage")
        private String ProductImage;
        @SerializedName("SellingPrice")
        private String SellingPrice;
        @SerializedName("MRPPrice")
        private String MRPPrice;
        @SerializedName("ProductName")
        private String ProductName;
        @SerializedName("ProductId")
        private int ProductId;
        @SerializedName("ProductDescId")
        private int ProductDescId;
        @SerializedName("StoreName")
        private String StoreName;

        protected ObjOfferDetailsListEntity(Parcel in) {
            ProductImage = in.readString();
            SellingPrice = in.readString();
            MRPPrice = in.readString();
            ProductName = in.readString();
            ProductId = in.readInt();
            ProductDescId = in.readInt();
            StoreName = in.readString();

        }

        public  final Creator<ObjOfferDetailsListEntity> CREATOR = new Creator<ObjOfferDetailsListEntity>() {
            @Override
            public ObjOfferDetailsListEntity createFromParcel(Parcel in) {
                return new ObjOfferDetailsListEntity(in);
            }

            @Override
            public ObjOfferDetailsListEntity[] newArray(int size) {
                return new ObjOfferDetailsListEntity[size];
            }
        };

        public String getProductImage() {
            return ProductImage;
        }

        public void setProductImage(String ProductImage) {
            this.ProductImage = ProductImage;
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

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String ProductName) {
            this.ProductName = ProductName;
        }

        public int getProductId() {
            return ProductId;
        }

        public void setProductId(int ProductId) {
            this.ProductId = ProductId;
        }

        public int getProductDescId() {
            return ProductDescId;
        }

        public void setProductDescId(int ProductDescId) {
            this.ProductDescId = ProductDescId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(ProductImage);
            dest.writeString(SellingPrice);
            dest.writeString(MRPPrice);
            dest.writeString(ProductName);
            dest.writeInt(ProductId);
            dest.writeInt(ProductDescId);
            dest.writeString(StoreName);

        }

        public String getStoreName() {
            return StoreName;
        }

        public void setStoreName(String storeName) {
            StoreName = storeName;
        }
    }

    public  class ObjStoreDetailsListEntity implements Parcelable {
        @SerializedName("StoreImage")
        private String StoreImage;
        @SerializedName("StoreName")
        private String StoreName;
        @SerializedName("StoreId")
        private int StoreId;

        protected ObjStoreDetailsListEntity(Parcel in) {
            StoreImage = in.readString();
            StoreName = in.readString();
            StoreId = in.readInt();
        }

        public  final Creator<ObjStoreDetailsListEntity> CREATOR = new Creator<ObjStoreDetailsListEntity>() {
            @Override
            public ObjStoreDetailsListEntity createFromParcel(Parcel in) {
                return new ObjStoreDetailsListEntity(in);
            }

            @Override
            public ObjStoreDetailsListEntity[] newArray(int size) {
                return new ObjStoreDetailsListEntity[size];
            }
        };

        public String getStoreImage() {
            return StoreImage;
        }

        public void setStoreImage(String StoreImage) {
            this.StoreImage = StoreImage;
        }

        public String getStoreName() {
            return StoreName;
        }

        public void setStoreName(String StoreName) {
            this.StoreName = StoreName;
        }

        public int getStoreId() {
            return StoreId;
        }

        public void setStoreId(int StoreId) {
            this.StoreId = StoreId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(StoreImage);
            dest.writeString(StoreName);
            dest.writeInt(StoreId);
        }
    }

    public  class ObjCategoryListEntity implements Parcelable{
        @SerializedName("CatergoryImage")
        private String CatergoryImage;
        @SerializedName("CatergoryName")
        private String CatergoryName;
        @SerializedName("CatergoryId")
        private int CatergoryId;

        protected ObjCategoryListEntity(Parcel in) {
            CatergoryImage = in.readString();
            CatergoryName = in.readString();
            CatergoryId = in.readInt();
        }

        public  final Creator<ObjCategoryListEntity> CREATOR = new Creator<ObjCategoryListEntity>() {
            @Override
            public ObjCategoryListEntity createFromParcel(Parcel in) {
                return new ObjCategoryListEntity(in);
            }

            @Override
            public ObjCategoryListEntity[] newArray(int size) {
                return new ObjCategoryListEntity[size];
            }
        };

        public String getCatergoryImage() {
            return CatergoryImage;
        }

        public void setCatergoryImage(String CatergoryImage) {
            this.CatergoryImage = CatergoryImage;
        }

        public String getCatergoryName() {
            return CatergoryName;
        }

        public void setCatergoryName(String CatergoryName) {
            this.CatergoryName = CatergoryName;
        }

        public int getCatergoryId() {
            return CatergoryId;
        }

        public void setCatergoryId(int CatergoryId) {
            this.CatergoryId = CatergoryId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(CatergoryImage);
            dest.writeString(CatergoryName);
            dest.writeInt(CatergoryId);
        }
    }
}
