package grossary.cyron.com.grossarybrch.productList;



public class ProductlistModel {
    int  ProductId ;
    int  StoreId ;
    int  CategoryId ;
    Double  MRPPrice ;
    Double  SellingPrice ;
    Double  ShippingCharge ;
    int  ProductDescId ;
    String CategoryName ;
    String ProductName ;
    String ProductImage ;
    String StoreName ;
    String SubProductQTY ;
    String SubProductDesc ;

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getStoreId() {
        return StoreId;
    }

    public void setStoreId(int storeId) {
        StoreId = storeId;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public Double getMRPPrice() {
        return MRPPrice;
    }

    public void setMRPPrice(Double MRPPrice) {
        this.MRPPrice = MRPPrice;
    }

    public Double getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public Double getShippingCharge() {
        return ShippingCharge;
    }

    public void setShippingCharge(Double shippingCharge) {
        ShippingCharge = shippingCharge;
    }

    public int getProductDescId() {
        return ProductDescId;
    }

    public void setProductDescId(int productDescId) {
        ProductDescId = productDescId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getSubProductQTY() {
        return SubProductQTY;
    }

    public void setSubProductQTY(String subProductQTY) {
        SubProductQTY = subProductQTY;
    }

    public String getSubProductDesc() {
        return SubProductDesc;
    }

    public void setSubProductDesc(String subProductDesc) {
        SubProductDesc = subProductDesc;
    }



    public ProductlistModel(int productId, int storeId, int categoryId, Double MRPPrice, Double sellingPrice, Double shippingCharge, int productDescId, String categoryName, String productName, String productImage, String storeName, String subProductQTY, String subProductDesc) {
        ProductId = productId;
        StoreId = storeId;
        CategoryId = categoryId;
        this.MRPPrice = MRPPrice;
        SellingPrice = sellingPrice;
        ShippingCharge = shippingCharge;
        ProductDescId = productDescId;
        CategoryName = categoryName;
        ProductName = productName;
        ProductImage = productImage;
        StoreName = storeName;
        SubProductQTY = subProductQTY;
        SubProductDesc = subProductDesc;
    }





}
