package grossary.cyron.com.grossarybrch.brands;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import grossary.cyron.com.grossarybrch.HomeActivity;
import grossary.cyron.com.grossarybrch.R;
import grossary.cyron.com.grossarybrch.category.CategoryActivity;
import grossary.cyron.com.grossarybrch.home.HomeModel;
import grossary.cyron.com.grossarybrch.utility.callback.OnItemClickListener;

import static grossary.cyron.com.grossarybrch.utility.Constant.CATEGORY.ADD_TO_CART;
import static grossary.cyron.com.grossarybrch.utility.Constant.CATEGORY.ONCLICK;
import static grossary.cyron.com.grossarybrch.utility.Constant.CURRENT_STATE.BRAND_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.KEY_NAME.ACT_HOME_PARAMETER;
import static grossary.cyron.com.grossarybrch.utility.Constant.KEY_NAME.CURRENT_FRG;

/**
 * A simple {@link Fragment} subclass.
 */
public class BrandsFragment extends Fragment implements OnItemClickListener<HomeModel.ObjOfferProdListEntity> {

    private RecyclerView recyclerView;
    private ArrayList<HomeModel.ObjOfferProdListEntity> brandsList = new ArrayList<>();
    private ArrayList<HomeModel.ObjOfferProdListEntity> objofferprodlist = new ArrayList<>();
    private BrandsListAdapter adapter;

    public BrandsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_brands, container, false);
        initView(view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        setAdapter();
        return view;
    }


    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycle_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (adapter.getItemCount() <= 0) {
            if (((HomeActivity) getActivity()).getHomeModel() != null)
                setData(((HomeActivity) getActivity()).getHomeModel().getObjOfferProdList());

        }
    }

    private void setAdapter() {
        adapter = new BrandsListAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(HomeModel.ObjOfferProdListEntity brandsModel, View view, int position, String type) {

        if (type.equalsIgnoreCase(ONCLICK)) {

           Intent intent = new Intent(getActivity(), CategoryActivity.class);
            intent.putExtra(CURRENT_FRG, BRAND_FRG);
            intent.putExtra(ACT_HOME_PARAMETER, new Gson().toJson(brandsModel));
            startActivity(intent);
        } else if (type.equalsIgnoreCase(ADD_TO_CART)) {

            ((HomeActivity) getActivity()).callApiAddtoCart("" + brandsModel.getProductDescId(), "" + brandsModel.getProductId(),
                    brandsModel.getStoreId()+"", brandsModel.getSellingPrice(), "1");
        }
    }

    public void setData(List<HomeModel.ObjOfferProdListEntity> objofferprodlist) {

        if (adapter == null || objofferprodlist == null)
            return;
        if (this.objofferprodlist.size() > 0)
            this.objofferprodlist.clear();
        this.objofferprodlist.addAll(objofferprodlist);
        adapter.setAdapterData(this.objofferprodlist);
    }

    public List<HomeModel.ObjOfferProdListEntity> getData() {
        return objofferprodlist;
    }
}
