package grossary.cyron.com.grossarybrch.productList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mindorks.placeholderview.ExpandablePlaceHolderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import grossary.cyron.com.grossarybrch.R;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ProductListFragment extends Fragment {

    private List<ProductlistModel> productList1;
    private ExpandablePlaceHolderView expandablePlaceHolderView;
    private Map<String,List<ProductlistModel>> categoryMap;


    public ProductListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        initView(view);
        productList1 = new ArrayList<>();
        categoryMap = new HashMap<>();

        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        callApi();
        expandablePlaceHolderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Toast.makeText(getContext(),"Clixcked", view.getId()).show();
            }
        });

    }

    private void callApi() {

        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);*/
        final Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES).readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(FileUploadService.BASE_URL_FOR_LOGIN).client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson)).build();
        FileUploadService api = retrofit.create(FileUploadService.class);

        JsonObject obj = new JsonObject();

        try {



            obj.addProperty("StoreId", 0);
            obj.addProperty("CategoryId", 0);



        } catch (Exception e) {
        }
        Log.d("Loginrequest", obj.toString());
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), obj.toString());
        final Call<ResponseBody> response = api.LoginAuthentication(body);

        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {

                    Log.d("apiversion",rawResponse.toString());


                    if (rawResponse.code() == 200) {



                        String res = rawResponse.body().string();


                     JSONObject dataListcoin = new JSONObject(res);
                        JSONArray js = new JSONArray(dataListcoin.getString("objProductDetailsList"));
                        Log.d("cdcscdscdsvdsdscvd", "objProductDetailsList " + js);
                        for (int i = 0; i < js.length(); i++) {
                            JSONObject jsObj = js.getJSONObject(i);

                            int  ProductId = jsObj.getInt("ProductId");
                            int  StoreId = jsObj.getInt("StoreId");
                            int  CategoryId = jsObj.getInt("CategoryId");
                            Double  MRPPrice = jsObj.getDouble("MRPPrice");
                            Double  SellingPrice = jsObj.getDouble("SellingPrice");
                            Double  ShippingCharge = jsObj.getDouble("ShippingCharge");
                            int  ProductDescId = jsObj.getInt("ProductDescId");
                            String CategoryName = jsObj.getString("CategoryName");
                            String ProductName = jsObj.getString("ProductName");
                            String ProductImage = jsObj.getString("ProductImage");
                            String StoreName = jsObj.getString("StoreName");
                            String SubProductQTY = jsObj.getString("SubProductQTY");
                            String SubProductDesc = jsObj.getString("SubProductDesc");

                            ProductlistModel productlistModel  = new ProductlistModel(ProductId,
                              StoreId,
                          CategoryId,
                              MRPPrice ,
                          SellingPrice,
                         ShippingCharge,
                              ProductDescId ,
                            CategoryName,
                           ProductName,
                             ProductImage ,
                             StoreName,
                             SubProductQTY,SubProductDesc);
                            productList1.add(productlistModel);
                        }
                        getHeaderAndChild(productList1);

                       /*    JSONObject dataListcoin1 = new JSONObject(dataListcoin.getString("Response"));
                        String status = dataListcoin1.getString("ResponseVal");
                        String resason = dataListcoin1.getString("Reason");
                        Log.d("loginrequestok", "ResponseVal: " + dataListcoin1.getString("ResponseVal"));

                        if (status.equalsIgnoreCase("false")) {
                            Log.d("MainActivivy", "test");
                            Toast.makeText(getContext(), resason, Toast.LENGTH_LONG).show();
                        } else {
                             String sessionid = dataListcoin.getString("SessionId");
                            JSONObject dataListcoin2 = new JSONObject(dataListcoin.getString("UserSessionDtls"));
                            Log.d("cvi11", "Response: " + dataListcoin2.getString("CenterCode"));
                            String CenterCode = dataListcoin2.getString("CenterCode");
                            String CityId = dataListcoin2.getString("CityId");
                            String CityCode = dataListcoin2.getString("CityCode");
                            String CounterNo = dataListcoin2.getString("CounterNo");
                            String CenterId = dataListcoin2.getString("CenterId");
                            String UserCode = dataListcoin2.getString("UserCode");
                            String EmployeeId = dataListcoin2.getString("EmployeeId");
                            String WalletType = dataListcoin2.getString("WalletType");
                            String CenterDetails = dataListcoin2.getString("CenterDetails");
                            String RemmitanceStatus = dataListcoin2.getString("RemmitanceStatus");



                        }*/
                    }


                } catch (Exception e) {

                    e.printStackTrace();


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

                }
        });

    }

    private void initView(View view) {
        expandablePlaceHolderView = view. findViewById(R.id.expandablePlaceHolder);


    }

    /*private void callApi() {

        String url = BASE_URL + "/Home/ProductPiceList";

        Log.e("URl", "*** " + url);


        String  storeId="0",
                catergoryId="0";

        Call<ProductlistModel.Productlist> call = RetrofitClient.getAPIInterface().productlistDetails(url, storeId,catergoryId);
        Request request = new RetrofitRequest<>(call, new ResponseListener<ProductlistModel.Productlist>() {
            @Override
            public void onResponse(int code, ProductlistModel.Productlist response, Headers headers) {


                   Log.d("cdcbjdcbdjkcd",""+response.CategoryName);





            }



            @Override
            public void onError(int error) {


            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e("respond", "failure ---->");

            }
        });
        request.enqueue();


    }*/

    private void getHeaderAndChild(List<ProductlistModel> movieList){
        Log.e("respond", "cdacdwfdscds"+movieList);

        for (ProductlistModel movie : movieList ){
            List<ProductlistModel> movieList1 = categoryMap.get(movie.getCategoryName());
            if(movieList1 == null){
                movieList1 = new ArrayList<>();
            }
            movieList1.add(movie);
            categoryMap.put(movie.getCategoryName(),movieList1);
        }

        Log.d("Map",categoryMap.toString());
        Iterator it = categoryMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Log.d("Key", pair.getKey().toString());
            expandablePlaceHolderView.addView(new HeaderView(getContext(), pair.getKey().toString()));
            List<ProductlistModel> movieList1 = (List<ProductlistModel>) pair.getValue();
            for (ProductlistModel movie : movieList1){
                expandablePlaceHolderView.addView(new ChildView(getContext(), movie));
            }
            it.remove();
        }
    }





}
