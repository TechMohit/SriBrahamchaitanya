package grossary.cyron.com.grossarybrch;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import grossary.cyron.com.grossarybrch.account.LoginModel;
import grossary.cyron.com.grossarybrch.account.SigninActivity;
import grossary.cyron.com.grossarybrch.brands.BrandsFragment;
import grossary.cyron.com.grossarybrch.category.AddToCartDetailsModel;
import grossary.cyron.com.grossarybrch.category.CategoryActivity;
import grossary.cyron.com.grossarybrch.category.ViewCartItemCountDetailsModel;
import grossary.cyron.com.grossarybrch.drawer.DrawerFragment;
import grossary.cyron.com.grossarybrch.home.HomeFragment;
import grossary.cyron.com.grossarybrch.home.HomeModel;
import grossary.cyron.com.grossarybrch.offers.OffersFragment;
import grossary.cyron.com.grossarybrch.profile.ProfileActivity;
import grossary.cyron.com.grossarybrch.search.ProductSearchDetailsAdapter;
import grossary.cyron.com.grossarybrch.search.ProductSearchDetailsModel;
import grossary.cyron.com.grossarybrch.sellers.SellerFragment;
import grossary.cyron.com.grossarybrch.utility.Constant;
import grossary.cyron.com.grossarybrch.utility.FragmentHelper;
import grossary.cyron.com.grossarybrch.utility.LoadingView;
import grossary.cyron.com.grossarybrch.utility.PreferenceManager;
import grossary.cyron.com.grossarybrch.utility.callback.OnItemClickListener;
import grossary.cyron.com.grossarybrch.utility.retrofit.RetrofitClient;
import grossary.cyron.com.grossarybrch.utility.retrofit.RetrofitRequest;
import grossary.cyron.com.grossarybrch.utility.retrofit.callbacks.Request;
import grossary.cyron.com.grossarybrch.utility.retrofit.callbacks.ResponseListener;
import grossary.cyron.com.grossarybrch.webview.WebViewActivity;
import okhttp3.Headers;
import retrofit2.Call;

import static grossary.cyron.com.grossarybrch.utility.Constant.CURRENT_STATE.MY_ORDER_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.CURRENT_STATE.SEARCH_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.CURRENT_STATE.VIEW_CART_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.KEY_NAME.ACT_HOME_PARAMETER;
import static grossary.cyron.com.grossarybrch.utility.Constant.KEY_NAME.CURRENT_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.URL.BASE_URL;
import static grossary.cyron.com.grossarybrch.utility.Util.openKeyPad;

public class HomeActivity extends AppCompatActivity implements
        FragmentManager.OnBackStackChangedListener, DrawerFragment.DrawerListener,
        android.app.FragmentManager.OnBackStackChangedListener,
        OnItemClickListener<ProductSearchDetailsModel.ObjproductsearchdetailsEntity> {

    private DrawerLayout drawer;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LoadingView load;
    private HomeModel homeModel;
    private FrameLayout layConnection;
    private Button btnRetry;
    private TextView tvCartCount;
    private ImageView tv_hamburger, img_cart, imgSearch;
    private Dialog dialog;

    private RecyclerView recyclerView;
    private ProductSearchDetailsAdapter adapter;

    private int[] tabIcons = {
            R.mipmap.home,
            R.mipmap.offer_icon,
            R.mipmap.search_icon,
            R.mipmap.brand_icon,
            R.mipmap.logout
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, null, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Fragment drawerFrag;
                drawerFrag = FragmentHelper.getFragment(HomeActivity.this, "drawer");
                if (drawerFrag != null) {
                    ((DrawerFragment) drawerFrag).focus();
                }
            }
        };
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getFragmentManager().addOnBackStackChangedListener(this);
        FragmentHelper.addFragment(this, R.id.navigation_container, new DrawerFragment());

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHomeApi();
            }
        });

        tv_hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                intent.putExtra(CURRENT_FRG, VIEW_CART_FRG);
                intent.putExtra(ACT_HOME_PARAMETER, "");
                startActivity(intent);

            }
        });

//        if ((savedInstanceState != null)
//                && (savedInstanceState.getSerializable("HomeActivityStore") != null)) {
//            homeModel = (HomeModel) savedInstanceState.getSerializable("HomeActivityStore");
//            setHomeModel(homeModel);
//
//        }else{
            setHome();
//        }

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(HomeActivity.this);
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

                recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                setAdapter();

                openKeyPad(HomeActivity.this,etSearch);

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
        adapter = new ProductSearchDetailsAdapter(HomeActivity.this, this);
        recyclerView.setAdapter(adapter);
    }

    private void callApiProductSearchDetails() {
        load = new LoadingView(HomeActivity.this);
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
                    Toast.makeText(HomeActivity.this, ""+response.getResponse().getReason() , Toast.LENGTH_SHORT).show();
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

    private void setHome() {

        callHomeApi();

    }

    private void callHomeApi() {

        load = new LoadingView(this);
        load.setCancalabe(false);
        load.showLoading();
        String url = BASE_URL + "/Home/HomeDetails";
        layConnection.setVisibility(View.GONE);

        Log.e("URl", "*** " + url);
        LoginModel res = new PreferenceManager(HomeActivity.this).getLoginModel();

        Call<HomeModel> call = RetrofitClient.getAPIInterface().homeDetailsAPI(url,
                "" + res.getUserid());
        Request request = new RetrofitRequest<>(call, new ResponseListener<HomeModel>() {
            @Override
            public void onResponse(int code, HomeModel response, Headers headers) {
                callApiCount();
                load.dismissLoading();
                setHomeModel(response);
                layConnection.setVisibility(View.GONE);
            }

            @Override
            public void onError(int error) {
                load.dismissLoading();
                layConnection.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e("HomeActivity", "failure ---->");
                load.dismissLoading();
                layConnection.setVisibility(View.VISIBLE);
            }
        });
        request.enqueue();

    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Home");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[0], 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Offers");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[1], 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Seller");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[2], 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("SplOffer");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[3], 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);

      /*  TextView tabfive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabfive.setText("ProdList");
        tabfive.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[4], 0, 0);
        tabLayout.getTabAt(4).setCustomView(tabfive);*/
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HomeFragment(), Constant.TABS.HOME);
        adapter.addFrag(new OffersFragment(), Constant.TABS.OFFER);
        adapter.addFrag(new SellerFragment(), Constant.TABS.SELLER);
        adapter.addFrag(new BrandsFragment(), Constant.TABS.BRANDS);
       // adapter.addFrag(new ProductListFragment(), Constant.TABS.ProductList);
        viewPager.setAdapter(adapter);
    }

    public void setHomeModel(HomeModel response) {
        if (homeModel != null)
            homeModel = new HomeModel();
        homeModel = response;
//        if (homeModel.objTotalCartItemCount != null)
//            tvCartCount.setText("" + homeModel.objTotalCartItemCount.totalItemCount);
        ViewPagerAdapter fa = (ViewPagerAdapter) viewPager.getAdapter();
        HomeFragment homeFragment = (HomeFragment) fa.getItem(0);
        OffersFragment theFragment = (OffersFragment) fa.getItem(1);
        SellerFragment sellerFragment = (SellerFragment) fa.getItem(2);
        BrandsFragment brandsFragment = (BrandsFragment) fa.getItem(3);
       // ProductListFragment productListFragment= (ProductListFragment) fa.getItem(4);

        homeFragment.setData(homeModel.getObjCategoryList(), response.getObjOfferImageList());
        theFragment.setData(homeModel.getObjOfferDetailsList());
        sellerFragment.setData(homeModel.getObjStoreDetailsList());
        brandsFragment.setData(homeModel.getObjOfferProdList());
        //productListFragment.setData(homeModel.getObjOfferProdList());

    }

    public HomeModel getHomeModel() {
        return homeModel;
    }

    @Override
    public void onItemClick(ProductSearchDetailsModel.ObjproductsearchdetailsEntity objproductsearchdetailsEntity, View view, int position, String type) {

        Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
        intent.putExtra(CURRENT_FRG, SEARCH_FRG);
        intent.putExtra(ACT_HOME_PARAMETER, new Gson().toJson(objproductsearchdetailsEntity));
        startActivity(intent);
        if(dialog!=null && dialog.isShowing())
            dialog.dismiss();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void initView() {
        drawer = findViewById(R.id.drawer);
        layConnection = findViewById(R.id.layConnection);
        tvCartCount = findViewById(R.id.tvCartCount);
        btnRetry = findViewById(R.id.btnRetry);
        tv_hamburger = findViewById(R.id.tv_hamburger);
        img_cart = findViewById(R.id.img_cart);
        imgSearch = findViewById(R.id.imgSearch);
    }

    @Override
    public void onBackStackChanged() {

    }

    @Override
    public void drawerOnItemClicked(String tag) {

        Intent intent = null;
        switch (tag) {

            case Constant.NAV_DRAWER.MY_HOME:
                setHome();
                break;
            case Constant.NAV_DRAWER.MY_PROFILE:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case Constant.NAV_DRAWER.MY_ORDER:
                intent = new Intent(HomeActivity.this, CategoryActivity.class);
                intent.putExtra(CURRENT_FRG, MY_ORDER_FRG);
                intent.putExtra(ACT_HOME_PARAMETER, "");
                startActivity(intent);
                break;
            case Constant.NAV_DRAWER.PRIVICY:
                startActivity(new Intent(this, WebViewActivity.class));
                break;
            case Constant.NAV_DRAWER.LOG_OUT:
                new PreferenceManager(HomeActivity.this).setAutoLogin(false);
                intent = new Intent(HomeActivity.this, SigninActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                break;

        }
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        tvCartCount.setText("" + new PreferenceManager(HomeActivity.this).getCount());

    }

    public void callApiCount() {

//        load = new LoadingView(HomeActivity.this);
//        load.setCancalabe(false);
//        load.showLoading();

        String url = BASE_URL + "/ShoppingCart/ViewCartItemCountDetails";

        Log.e("URl", "*** " + url);
        final LoginModel res = new PreferenceManager(HomeActivity.this).getLoginModel();


        Call<ViewCartItemCountDetailsModel> call = RetrofitClient.getAPIInterface().viewCartItemCountDetails(url, "" + res.getUserid());
        Request request = new RetrofitRequest<>(call, new ResponseListener<ViewCartItemCountDetailsModel>() {
            @Override
            public void onResponse(int code, ViewCartItemCountDetailsModel response, Headers headers) {
//                load.dismissLoading();
                if (response.getResponse().getResponseval()) {
                    tvCartCount.setText("" + response.getTotalitemcount());
                    new PreferenceManager(HomeActivity.this).setCount("" + response.getTotalitemcount());

                } else {
                    Toast.makeText(HomeActivity.this, "" + response.getResponse().getReason(), Toast.LENGTH_SHORT).show();
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
        request.enqueue();
    }

    public void callApiAddtoCart(String productDescId, String productId, String stroeId, String sellingPrice, String qty) {

        load = new LoadingView(HomeActivity.this);
        load.setCancalabe(false);
        load.showLoading();

        String url = BASE_URL + "/ShoppingCart/AddToCartDetails";

        Log.e("URl", "*** " + url);
        LoginModel res = new PreferenceManager(HomeActivity.this).getLoginModel();


        Call<AddToCartDetailsModel> call = RetrofitClient.getAPIInterface().addToCartDetails(url, "" + res.getUserid(),
                "" + productDescId,
                "" + productId,
                "" + stroeId,
                "" + sellingPrice, "" + qty);
        Request request = new RetrofitRequest<>(call, new ResponseListener<AddToCartDetailsModel>() {
            @Override
            public void onResponse(int code, AddToCartDetailsModel response, Headers headers) {
                load.dismissLoading();
                if (response.getResponse().getResponseval()) {
                    callApiCount();
                    Toast.makeText(HomeActivity.this, "" + response.getResponse().getReason(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(HomeActivity.this, "" + response.getResponse().getReason(), Toast.LENGTH_SHORT).show();
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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putParcelable("HomeActivityStore", homeModel);
    }
}
