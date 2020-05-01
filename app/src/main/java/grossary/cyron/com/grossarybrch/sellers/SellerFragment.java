package grossary.cyron.com.grossarybrch.sellers;


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

import static grossary.cyron.com.grossarybrch.utility.Constant.CURRENT_STATE.SELLER_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.KEY_NAME.ACT_HOME_PARAMETER;
import static grossary.cyron.com.grossarybrch.utility.Constant.KEY_NAME.CURRENT_FRG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellerFragment extends Fragment implements OnItemClickListener<HomeModel.ObjStoreDetailsListEntity> {

    private RecyclerView recyclerView;
    private ArrayList<HomeModel.ObjStoreDetailsListEntity> sellerList = new ArrayList<>();
    private SellersListAdapter adapter;

    public SellerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seller, container, false);
        initView(view);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        setAdapter();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(adapter.getItemCount()<=0)
        {
            if(((HomeActivity)getActivity()).getHomeModel()!=null)
            setData(((HomeActivity)getActivity()).getHomeModel().getObjStoreDetailsList());
        }
    }

    private void setAdapter() {
        adapter = new SellersListAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycle_view);
    }

    @Override
    public void onItemClick(HomeModel.ObjStoreDetailsListEntity sellersModel, View view, int position,String type) {

        Intent intent=new Intent(getActivity(),CategoryActivity.class);
        intent.putExtra(CURRENT_FRG,SELLER_FRG);
        intent.putExtra(ACT_HOME_PARAMETER,new Gson().toJson(sellersModel));
        startActivity(intent);
    }


    public void setData(List<HomeModel.ObjStoreDetailsListEntity> sellerList) {
        if(adapter==null || sellerList==null)
            return;
        if (this.sellerList.size() > 0)
            this.sellerList.clear();
        this.sellerList.addAll(sellerList);
        adapter.setAdapterData(this.sellerList);
    }

    public List<HomeModel.ObjStoreDetailsListEntity> getData() {
        return sellerList;
    }
}
