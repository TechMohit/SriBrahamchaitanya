package grossary.cyron.com.grossarybrch.order;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import grossary.cyron.com.grossarybrch.R;
import grossary.cyron.com.grossarybrch.account.LoginModel;
import grossary.cyron.com.grossarybrch.cart.ViewCartModel;
import grossary.cyron.com.grossarybrch.category.CategoryActivity;
import grossary.cyron.com.grossarybrch.utility.LoadingView;
import grossary.cyron.com.grossarybrch.utility.PreferenceManager;
import grossary.cyron.com.grossarybrch.utility.callback.OnItemClickListener;
import grossary.cyron.com.grossarybrch.utility.retrofit.RetrofitClient;
import grossary.cyron.com.grossarybrch.utility.retrofit.RetrofitRequest;
import grossary.cyron.com.grossarybrch.utility.retrofit.callbacks.Request;
import grossary.cyron.com.grossarybrch.utility.retrofit.callbacks.ResponseListener;
import okhttp3.Headers;
import retrofit2.Call;

import static grossary.cyron.com.grossarybrch.utility.Constant.KEY_NAME.CURRENT_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.KEY_NAME.FRAG_PARAMETER;
import static grossary.cyron.com.grossarybrch.utility.Constant.URL.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderDetailFragment extends Fragment implements OnItemClickListener<OrderDetailsModel.OrderdetailEntity>  {

    private RecyclerView recyclerView;
    private ArrayList<ViewCartModel> list = new ArrayList<>();
    private MyOrderDetailsdapter adapter;
    private LoadingView load;
    private Context context;

    public MyOrderDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getActivity() fragment
        View view = inflater.inflate(R.layout.fragment_my_order_detail, container, false);
        initView(view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setAdapter();
        callApiViewCart();
        ((CategoryActivity)getActivity()).callApiCount();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycle_view);

    }

    private void callApiViewCart() {
        load = new LoadingView(getActivity());
        load.setCancalabe(false);
        load.showLoading();
        String url = BASE_URL + "/order/OrderDetails";
        LoginModel res = new PreferenceManager(getActivity()).getLoginModel();

        Log.e("URl", "*** " + url);
        String value=(getArguments().getString(FRAG_PARAMETER));
        String current=(getArguments().getString(CURRENT_FRG));

        ViewOrderListModel.OrderlistEntity product = new Gson().fromJson(value, ViewOrderListModel.OrderlistEntity.class);

        Call<OrderDetailsModel> call = RetrofitClient.getAPIInterface().orderDetails(url,""+res.getUserid(),""+product.getTranno());
        Request request = new RetrofitRequest<>(call, new ResponseListener<OrderDetailsModel>() {
            @Override
            public void onResponse(int code, OrderDetailsModel response, Headers headers) {
                load.dismissLoading();

                if (response.getResponse().getResponseval()) {

                    adapter.setAdapterData(response.getOrderdetail(),response);
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

    private void setAdapter() {
        adapter = new MyOrderDetailsdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(OrderDetailsModel.OrderdetailEntity obj, View view, int position,String type) {



    }

}
