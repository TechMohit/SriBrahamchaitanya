package grossary.cyron.com.grossarybrch.category;


import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.gson.Gson;

import grossary.cyron.com.grossarybrch.R;
import grossary.cyron.com.grossarybrch.brands.OfferProductDescDetailsModel;
import grossary.cyron.com.grossarybrch.home.HomeModel;
import grossary.cyron.com.grossarybrch.search.ProductSearchDetailsModel;
import grossary.cyron.com.grossarybrch.utility.GlideApp;
import grossary.cyron.com.grossarybrch.utility.LoadingView;
import grossary.cyron.com.grossarybrch.utility.retrofit.RetrofitClient;
import grossary.cyron.com.grossarybrch.utility.retrofit.RetrofitRequest;
import grossary.cyron.com.grossarybrch.utility.retrofit.callbacks.Request;
import grossary.cyron.com.grossarybrch.utility.retrofit.callbacks.ResponseListener;
import okhttp3.Headers;
import retrofit2.Call;

import static grossary.cyron.com.grossarybrch.utility.Constant.CONSTANT.CHECKOUT;
import static grossary.cyron.com.grossarybrch.utility.Constant.CURRENT_STATE.BRAND_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.CURRENT_STATE.CATG_LIST_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.CURRENT_STATE.OFFER_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.CURRENT_STATE.SEARCH_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.KEY_NAME.CURRENT_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.KEY_NAME.FRAG_PARAMETER;
import static grossary.cyron.com.grossarybrch.utility.Constant.URL.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryListDetailsFragment extends Fragment {


    private TextView tvProductName, tvDesc, tvSellingPrice, tvMrpPrice, txtCount,diffrence;
    private LoadingView load;
    private Context context;
    private int count = 1;
    private Button btnAddCart, btnMin, btnAdd;
    private ImageView imgProduct;
    private ProductdDescDetailsModel responseMain;
    private OfferProductDescDetailsModel responseMainBrand;
    private String current;
    public CategoryListDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list_details, container, false);
        initView(view);

        ((CategoryActivity)getActivity()).txtCheckout.setText(CHECKOUT);

        txtCount.setText("" + count);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                txtCount.setText("" + count);

            }
        });
        btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count >=2) {
                    count--;
                    txtCount.setText("" + count);
                }

            }
        });
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(current.equalsIgnoreCase(BRAND_FRG)){
                    ((CategoryActivity) getActivity()).callApiAddtoCart("" + responseMainBrand.getProductDescId(),
                            "" + responseMainBrand.getProductId(), "" + responseMainBrand.getStoreId(),
                            "" + responseMainBrand.getShippingCharges(), "" + count);
                }else {
                    ((CategoryActivity) getActivity()).callApiAddtoCart("" + responseMain.productDescId,
                            "" + responseMain.productId, "" + responseMain.storeId, "" + responseMain.shippingCharges, "" + count);
                }
            }
        });

        ((CategoryActivity)getActivity()).callApiCount();
        return view;
    }

    private void initView(View view) {
        tvProductName = view.findViewById(R.id.tvProductName);
        diffrence = view.findViewById(R.id.diffrence);
        tvDesc = view.findViewById(R.id.tvDesc);
        tvSellingPrice = view.findViewById(R.id.tvSellingPrice);
        tvMrpPrice = view.findViewById(R.id.tvMrpPrice);
        btnAddCart = view.findViewById(R.id.btnAddCart);
        btnMin = view.findViewById(R.id.btnMin);
        btnAdd = view.findViewById(R.id.btnAdd);
        txtCount = view.findViewById(R.id.txtCount);
        imgProduct = view.findViewById(R.id.imgProduct);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

         current = (getArguments().getString(CURRENT_FRG));
        if(current.equalsIgnoreCase(BRAND_FRG)){
            callApiBrand();
        }else {
            callApi();
        }
    }


    private void callApi() {
        load = new LoadingView(getActivity());
        load.setCancalabe(false);
        load.showLoading();
        String url = BASE_URL + "/Home/ProductdDescDetails";

        Log.e("URl", "*** " + url);

        String value = (getArguments().getString(FRAG_PARAMETER));

        String productDescId = "0";

        if (current.equalsIgnoreCase(CATG_LIST_FRG)) {
            CategoryModel.Projectlist product = new Gson().fromJson(value, CategoryModel.Projectlist.class);
            productDescId = "" + product.productDescId;
        } else if (current.equalsIgnoreCase(OFFER_FRG)) {
            HomeModel.ObjOfferDetailsListEntity product = new Gson().fromJson(value, HomeModel.ObjOfferDetailsListEntity.class);
            productDescId = "" + product.getProductDescId();
        } else if (current.equalsIgnoreCase(BRAND_FRG)) {
            HomeModel.ObjOfferProdListEntity product = new Gson().fromJson(value, HomeModel.ObjOfferProdListEntity.class);
            productDescId = "" + product.getProductDescId();
        }else if (current.equalsIgnoreCase(SEARCH_FRG)) {
            ProductSearchDetailsModel.ObjproductsearchdetailsEntity product = new Gson().fromJson(value, ProductSearchDetailsModel.ObjproductsearchdetailsEntity.class);
            productDescId = "" + product.getProductdescid();
        }

        Call<ProductdDescDetailsModel> call = RetrofitClient.getAPIInterface().ProductdDescDetails(url, "" + productDescId);
        Request request = new RetrofitRequest<>(call, new ResponseListener<ProductdDescDetailsModel>() {
            @Override
            public void onResponse(int code, ProductdDescDetailsModel response, Headers headers) {
                load.dismissLoading();
                responseMain = response;
                if (response.response.responseval) {

                    tvProductName.setText(String.format("%s", response.productName)+"("+response.storeName+")");
                    tvDesc.setText(String.format("%s", response.subProductQTY));
                    tvSellingPrice.setText("₹" + String.format("%s", response.sellingPrice));
                    tvMrpPrice.setText(String.format("%s", "( MRP: ₹" + response.mRPPrice + ")"));
                    String  sellingPrice =String.format("%s", response.sellingPrice);
                    String mRPPrice = String.format("%s", response.mRPPrice);
                    float difference2 = Float.parseFloat(sellingPrice);
                    float difference1 = Float.parseFloat(mRPPrice);
                    float difference =difference1-difference2;
                    diffrence.setText(String.format("%s", "(You can Save  ₹"+difference+ " on this item)"));
                   // tvMrpPrice.setPaintFlags(tvMrpPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    GlideApp.with(getActivity())
                            .load(response.productImage)
                            .centerInside()
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .placeholder(R.mipmap.logo_pink)
                            .error(R.drawable.ic_launcher_background)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imgProduct);


                } else {
                    Toast.makeText(getActivity(), "" + response.response.reason, Toast.LENGTH_SHORT).show();
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

    private void callApiBrand() {
        load = new LoadingView(getActivity());
        load.setCancalabe(false);
        load.showLoading();
        String url = BASE_URL + "/Home/OfferProductdDescDetails";

        Log.e("URl", "*** " + url);

        String value = (getArguments().getString(FRAG_PARAMETER));

        String productDescId = "0";
        HomeModel.ObjOfferProdListEntity product = new Gson().fromJson(value, HomeModel.ObjOfferProdListEntity.class);
        productDescId = "" + product.getProductDescId();

        Call<OfferProductDescDetailsModel> call = RetrofitClient.getAPIInterface().OfferProductdDescDetails(url, "" + productDescId);
        Request request = new RetrofitRequest<>(call, new ResponseListener<OfferProductDescDetailsModel>() {
            @Override
            public void onResponse(int code, OfferProductDescDetailsModel response, Headers headers) {
                load.dismissLoading();
                responseMainBrand = response;
                if (response.getResponse().getResponseVal()) {

                    tvProductName.setText(String.format("%s", response.getProductName())+"("+response.getStoreName()+")");
                    tvDesc.setText(String.format("%s", response.getSubProductQTY()));
                    tvSellingPrice.setText("₹" + String.format("%s", response.getSellingPrice()));
                    tvMrpPrice.setText(String.format("%s", "(₹" + response.getMRPPrice() + ")"));
                    tvMrpPrice.setPaintFlags(tvMrpPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    GlideApp.with(getActivity())
                            .load(response.getProductImage())
                            .centerInside()
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .placeholder(R.mipmap.logo_pink)
                            .error(R.drawable.ic_launcher_background)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imgProduct);


                } else {
                    Toast.makeText(getActivity(), "" + response.getResponse().getReason(), Toast.LENGTH_SHORT).show();
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

}
