package grossary.cyron.com.grossarybrch.category;

import android.app.Dialog;
import android.app.FragmentManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import grossary.cyron.com.grossarybrch.R;
import grossary.cyron.com.grossarybrch.account.LoginModel;
import grossary.cyron.com.grossarybrch.adress.AddressFragment;
import grossary.cyron.com.grossarybrch.cart.ViewCartFragment;
import grossary.cyron.com.grossarybrch.order.MyOrderDetailFragment;
import grossary.cyron.com.grossarybrch.order.MyOrdersFragment;
import grossary.cyron.com.grossarybrch.productList.FileUploadService;
import grossary.cyron.com.grossarybrch.search.ProductSearchDetailsAdapter;
import grossary.cyron.com.grossarybrch.search.ProductSearchDetailsModel;
import grossary.cyron.com.grossarybrch.utility.FragmentHelper;
import grossary.cyron.com.grossarybrch.utility.LoadingView;
import grossary.cyron.com.grossarybrch.utility.PreferenceManager;
import grossary.cyron.com.grossarybrch.utility.callback.OnItemClickListener;
import grossary.cyron.com.grossarybrch.utility.retrofit.RetrofitClient;
import grossary.cyron.com.grossarybrch.utility.retrofit.RetrofitRequest;
import grossary.cyron.com.grossarybrch.utility.retrofit.callbacks.Request;
import grossary.cyron.com.grossarybrch.utility.retrofit.callbacks.ResponseListener;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static grossary.cyron.com.grossarybrch.utility.Constant.CATEGORY.ADDRESS;
import static grossary.cyron.com.grossarybrch.utility.Constant.CATEGORY.LIST;
import static grossary.cyron.com.grossarybrch.utility.Constant.CATEGORY.LIST_DETAILS;
import static grossary.cyron.com.grossarybrch.utility.Constant.CATEGORY.ORDER;
import static grossary.cyron.com.grossarybrch.utility.Constant.CATEGORY.ORDER_DETAIL;
import static grossary.cyron.com.grossarybrch.utility.Constant.CATEGORY.VIEW_CART;
import static grossary.cyron.com.grossarybrch.utility.Constant.CONSTANT.CHECKOUT;
import static grossary.cyron.com.grossarybrch.utility.Constant.CONSTANT.MAKE_PAYMENT;
import static grossary.cyron.com.grossarybrch.utility.Constant.CONSTANT.PLACE_YOUR_ORDER;
import static grossary.cyron.com.grossarybrch.utility.Constant.CURRENT_STATE.ADDRESS_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.CURRENT_STATE.BRAND_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.CURRENT_STATE.HOME_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.CURRENT_STATE.MY_ORDER_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.CURRENT_STATE.OFFER_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.CURRENT_STATE.SEARCH_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.CURRENT_STATE.SELLER_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.CURRENT_STATE.VIEW_CART_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.KEY_NAME.ACT_HOME_PARAMETER;
import static grossary.cyron.com.grossarybrch.utility.Constant.KEY_NAME.CURRENT_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.KEY_NAME.FRAG_PARAMETER;
import static grossary.cyron.com.grossarybrch.utility.Constant.URL.BASE_URL;
import static grossary.cyron.com.grossarybrch.utility.Util.openKeyPad;

public class CategoryActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener,
        OnItemClickListener<ProductSearchDetailsModel.ObjproductsearchdetailsEntity> {

    private LoadingView load;
    private RelativeLayout revBottom;
    public TextView txtCheckout, tvTotal, tvCount;
    private Dialog dialog;
    private ImageView tvBack,img_cart;

    private RecyclerView recyclerView;
    private ProductSearchDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        txtCheckout = findViewById(R.id.txtCheckout);
        revBottom = findViewById(R.id.revBottom);
        tvTotal = findViewById(R.id.tvTotal);
        tvCount = findViewById(R.id.tvCount);
        tvBack=findViewById(R.id.tvBack);
        img_cart=findViewById(R.id.img_cart);

        String current = getIntent().getStringExtra(CURRENT_FRG);
        if (current.equalsIgnoreCase(HOME_FRG)) {
            selectFrag(LIST, getIntent().getStringExtra(ACT_HOME_PARAMETER), current);
        } else if (current.equalsIgnoreCase(OFFER_FRG)) {
            selectFrag(LIST_DETAILS, getIntent().getStringExtra(ACT_HOME_PARAMETER), current);
        } else if (current.equalsIgnoreCase(BRAND_FRG)) {
            selectFrag(LIST_DETAILS, getIntent().getStringExtra(ACT_HOME_PARAMETER), current);
        } else if (current.equalsIgnoreCase(SELLER_FRG)) {
            selectFrag(LIST, getIntent().getStringExtra(ACT_HOME_PARAMETER), current);
        } else if (current.equalsIgnoreCase(VIEW_CART_FRG)) {
            selectFrag(VIEW_CART, "1", VIEW_CART_FRG);
        } else if (current.equalsIgnoreCase(MY_ORDER_FRG)) {
            selectFrag(ORDER, "", MY_ORDER_FRG);
        } else if (current.equalsIgnoreCase(SEARCH_FRG)) {
            selectFrag(LIST_DETAILS, getIntent().getStringExtra(ACT_HOME_PARAMETER), current);

        }
        getFragmentManager().addOnBackStackChangedListener(this);

        txtCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtCheckout.getText().toString().equalsIgnoreCase(CHECKOUT))
                    selectFrag(VIEW_CART, "2", VIEW_CART_FRG);
                else if (txtCheckout.getText().toString().equalsIgnoreCase(PLACE_YOUR_ORDER)) {
                    String count = new PreferenceManager(CategoryActivity.this).getCount();
                    String GrandTotal = new PreferenceManager(CategoryActivity.this).getGrandtoal();
                    LoginModel res = new PreferenceManager(CategoryActivity.this).getLoginModel();

                    Float total = Float.parseFloat(GrandTotal);
                    String value1 = res.getMinimumOrderAmount();
                    Float valuecheck = Float.parseFloat(value1);



                    if (count.equals("0")) {
                        Toast.makeText(CategoryActivity.this, "Please Add few Items in cart", Toast.LENGTH_SHORT).show();

                    }
                    else if((total<valuecheck)&&(total>0.0)){
                        Toast.makeText(CategoryActivity.this, "Minimum Order value should be "+value1, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        selectFrag(ADDRESS, "", ADDRESS_FRG);
                    }

                }else if(txtCheckout.getText().toString().equalsIgnoreCase(MAKE_PAYMENT)) {

                        AddressFragment fragment = (AddressFragment) FragmentHelper.getFragment(CategoryActivity.this, ADDRESS);
                        if (fragment != null)
                            fragment.callApiSubmitTransaction();

                }
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtCheckout.getText().toString().equalsIgnoreCase(CHECKOUT))
                    selectFrag(VIEW_CART, "2", VIEW_CART_FRG);

            }
        });

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ImageView imgSearch=findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(CategoryActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_search);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                Window window = dialog.getWindow();
                lp.copyFrom(window.getAttributes());
                //This makes the dialog take up the full width
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                window.setAttributes(lp);
                dialog.setCancelable(true);
                TextView imgSearch = dialog.findViewById(R.id.imgSearch);
                ImageView imgBack = dialog.findViewById(R.id.imgBack);
                final EditText etSearch = dialog.findViewById(R.id.etSearch);

                recyclerView = dialog.findViewById(R.id.recycle_view);

                recyclerView.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
                setAdapter();

                openKeyPad(CategoryActivity.this,etSearch);

                imgBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                etSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                            adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                callApiProductSearchDetails();
                dialog.show();

            }
        });
    }

    private void setAdapter() {
        adapter = new ProductSearchDetailsAdapter(CategoryActivity.this, this);
        recyclerView.setAdapter(adapter);
    }

    private void callApiProductSearchDetails() {
        load = new LoadingView(CategoryActivity.this);
        load.setCancalabe(false);
        load.showLoading();
        String url = BASE_URL + "/Home/ProductSearchDetails";

        Log.e("URl", "*** " + url);

        Call<ProductSearchDetailsModel> call = RetrofitClient.getAPIInterface().productSearchDetails(url);
        Request request = new RetrofitRequest<>(call, new ResponseListener<ProductSearchDetailsModel>() {
            @Override
            public void onResponse(int code, ProductSearchDetailsModel response, Headers headers) {
                load.dismissLoading();
                if (response.getResponse().getResponseval()) {
                    adapter.setAdapterData(response.getObjproductsearchdetails());

                }else{
                    Toast.makeText(CategoryActivity.this, ""+response.getResponse().getReason() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int error) {
                load.dismissLoading();

            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e("respond", "failure ---->");
                load.dismissLoading();
            }
        });
        request.enqueue();

    }


    public void selectFrag(String tag, String response, String current) {

        revBottom.setVisibility(View.VISIBLE);
        Fragment fragment = null;
        Bundle arguments = null;
        switch (tag) {
            case LIST:

                fragment = new CategoryListFragment();
                arguments = new Bundle();
                arguments.putString(CURRENT_FRG, current);
                arguments.putString(FRAG_PARAMETER, response);
                fragment.setArguments(arguments);

                FragmentHelper.replaceFragment(this, R.id.container, fragment, false, tag);

                break;

            case LIST_DETAILS:

                fragment = new CategoryListDetailsFragment();
                arguments = new Bundle();
                arguments.putString(CURRENT_FRG, current);
                arguments.putString(FRAG_PARAMETER, response);
                fragment.setArguments(arguments);
                if (current.equalsIgnoreCase(OFFER_FRG)||current.equalsIgnoreCase(BRAND_FRG)||
                        current.equalsIgnoreCase(SEARCH_FRG)) {
                    FragmentHelper.replaceFragment(this, R.id.container, fragment, false, tag);

                } else {
                    FragmentHelper.replaceFragment(this, R.id.container, fragment, true, tag);
                }
                break;
            case VIEW_CART:

                fragment = new ViewCartFragment();
                if (response.equalsIgnoreCase("1")) {
                    FragmentHelper.replaceFragment(this, R.id.container, fragment, false, tag);

                } else {
                    FragmentHelper.replaceFragment(this, R.id.container, fragment, true, tag);
                }

                break;
            case ORDER:
                revBottom.setVisibility(View.GONE);
                fragment = new MyOrdersFragment();
                FragmentHelper.replaceFragment(this, R.id.container, fragment, false, tag);

                break;

            case ADDRESS:
                fragment = new AddressFragment();
                FragmentHelper.replaceFragment(this, R.id.container, fragment, true, tag);

                break;
            case ORDER_DETAIL:
                revBottom.setVisibility(View.GONE);
                fragment = new MyOrderDetailFragment();
                arguments = new Bundle();
                arguments.putString(CURRENT_FRG, current);
                arguments.putString(FRAG_PARAMETER, response);
                fragment.setArguments(arguments);
                FragmentHelper.replaceFragment(this, R.id.container, fragment, true, tag);

                break;
        }
    }

    public void callApiAddtoCart(String productDescId, String productId, String stroeId, String ShippingCharges, String qty) {

        load = new LoadingView(CategoryActivity.this);
        load.setCancalabe(false);
        load.showLoading();

        String url = BASE_URL + "/ShoppingCart/AddToCartDetails";

        Log.e("URl", "*** " + url);
        LoginModel res = new PreferenceManager(CategoryActivity.this).getLoginModel();


        Call<AddToCartDetailsModel> call = RetrofitClient.getAPIInterface().addToCartDetails(url, "" + res.getUserid(),
                "" + productDescId,
                "" + productId,
                "" + stroeId,
                "" + ShippingCharges, "" + qty);
        Request request = new RetrofitRequest<>(call, new ResponseListener<AddToCartDetailsModel>() {
            @Override
            public void onResponse(int code, AddToCartDetailsModel response, Headers headers) {
                load.dismissLoading();
                if (response.getResponse().getResponseval()) {
                    callApiCount();
                    Toast.makeText(CategoryActivity.this, "" + response.getResponse().getReason(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CategoryActivity.this, "" + response.getResponse().getReason(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int error) {
                load.dismissLoading();

            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e("respond", "failure ---->");
                load.dismissLoading();
            }
        });
        request.enqueue();
    }


    public void callApiCount() {

        load = new LoadingView(CategoryActivity.this);
        load.setCancalabe(false);
       load.showLoading();

        String url = BASE_URL + "/ShoppingCart/ViewCartItemCountDetails";

        Log.e("URl", "*** " + url);
        final LoginModel res = new PreferenceManager(CategoryActivity.this).getLoginModel();

        Log.d("2016test","userid"+res.getUserid());

        final Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES).readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(FileUploadService.BASE_URL_FOR_LOGIN).client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson)).build();
        FileUploadService api = retrofit.create(FileUploadService.class);

        JsonObject obj = new JsonObject();

        try {

            int username = res.getUserid();
            Log.d("LoginAct", "request" + username);

            obj.addProperty("UserId", res.getUserid());


        } catch (Exception e) {
        }
        Log.d("LoginAct", obj.toString());
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), obj.toString());
        final Call<ResponseBody> response = api.Cartcount(body);

        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {

                    if (rawResponse.code() == 200) {

                        String res = rawResponse.body().string();
                        JSONObject dataListcoin = new JSONObject(res);

                        JSONObject dataListcoin1 = new JSONObject(dataListcoin.getString("Response"));
                        String status = dataListcoin1.getString("ResponseVal");
                        String resason = dataListcoin1.getString("Reason");
                        Log.d("LoginAct", "ResponseVal: " + dataListcoin1.getString("ResponseVal"));
                            load.dismissLoading();
                            String GrandToal = dataListcoin.getString("GrandToal");
                            String TotalItemCount = dataListcoin.getString("TotalItemCount");
                            Log.d("LoginAct", "ResponseVal: " +GrandToal+":"+TotalItemCount);
                            tvTotal.setText("₹" + GrandToal);
                            tvCount.setText("" + TotalItemCount);
                            new PreferenceManager(CategoryActivity.this).setCount(""+TotalItemCount);
                            new PreferenceManager(CategoryActivity.this).setGrandtoal(""+GrandToal);

                        }


                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_LONG).show();
            }
        });

/*


        Call<ViewCartItemCountDetailsModel> call = RetrofitClient.getAPIInterface().viewCartItemCountDetails(url, "" + res.getUserid());
        Request request = new RetrofitRequest<>(call, new ResponseListener<ViewCartItemCountDetailsModel>() {
            @Override
            public void onResponse(int code, ViewCartItemCountDetailsModel response, Headers headers) {
//                load.dismissLoading();
                Log.d("2016test","callapicount"+response.getGrandtoal());
                if (response.getResponse().getResponseval()) {
                    tvTotal.setText("₹" + response.getGrandtoal());
                    tvCount.setText("" + response.getTotalitemcount());
                    new PreferenceManager(CategoryActivity.this).setCount(""+response.getTotalitemcount());
                    new PreferenceManager(CategoryActivity.this).setGrandtoal(""+response.getGrandtoal());

                } else {
                    Toast.makeText(CategoryActivity.this, "" + response.getResponse().getReason(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int error) {
//                load.dismissLoading();

            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e("respond", "failure ---->");
//                load.dismissLoading();
            }
        });
        request.enqueue();*/
    }

    @Override
    public void onBackStackChanged() {

    }


    @Override
    public void onItemClick(ProductSearchDetailsModel.ObjproductsearchdetailsEntity objproductsearchdetailsEntity, View view, int position, String type) {

        if(dialog!=null && dialog.isShowing())
            dialog.dismiss();
        selectFrag(LIST_DETAILS, new Gson().toJson(objproductsearchdetailsEntity), SEARCH_FRG);

    }
}
