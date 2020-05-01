package grossary.cyron.com.grossarybrch.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import grossary.cyron.com.grossarybrch.HomeActivity;
import grossary.cyron.com.grossarybrch.R;
import grossary.cyron.com.grossarybrch.category.CategoryActivity;
import grossary.cyron.com.grossarybrch.custom.CirclePageIndicator;
import grossary.cyron.com.grossarybrch.utility.callback.OnItemClickListener;

import static grossary.cyron.com.grossarybrch.utility.Constant.CURRENT_STATE.HOME_FRG;
import static grossary.cyron.com.grossarybrch.utility.Constant.KEY_NAME.ACT_HOME_PARAMETER;
import static grossary.cyron.com.grossarybrch.utility.Constant.KEY_NAME.CURRENT_FRG;


public class HomeFragment extends Fragment implements OnItemClickListener<HomeModel.ObjCategoryListEntity> {

    private CirclePageIndicator indicator;
    private ViewPager pager;
    private List<HomeModel.ObjOfferImageListEntity> homeOfferList = new ArrayList<>();
    private ArrayList<HomeModel.ObjCategoryListEntity> homeList = new ArrayList<>();
    private Timer timer;
    private HomeListAdapter adapter;
    private RecyclerView recyclerView;

    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
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
            setData(((HomeActivity)getActivity()).getHomeModel().getObjCategoryList(), ((HomeActivity)getActivity()).getHomeModel().getObjOfferImageList());
        }
    }

    private void setAdapter() {
        adapter = new HomeListAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);
    }

    private void initView(View view) {

        recyclerView = view.findViewById(R.id.recycle_view);
        indicator = view.findViewById(R.id.indicator);
        pager = view.findViewById(R.id.pager);

    }

    private void setViewPagerTimer(final List<HomeModel.ObjOfferImageListEntity> homeOfferList) {

        pager.setAdapter(new SlidingImage_Adapter(getActivity(), homeOfferList));
        indicator.setViewPager(pager);
        changePagerScroller();
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                pager.post(new Runnable() {

                    @Override
                    public void run() {

                        pager.setCurrentItem((pager.getCurrentItem() + 1) % homeOfferList.size(), true);
                    }
                });
            }
        };
        if(timer==null)
        timer = new Timer();
        timer.schedule(timerTask, 3000, 3000);
    }


    private void changePagerScroller() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller scroller = new ViewPagerScroller(pager.getContext());
            mScroller.set(pager, scroller);
        } catch (Exception e) {
        }
    }

    public void setData(List<HomeModel.ObjCategoryListEntity> data, List<HomeModel.ObjOfferImageListEntity> homeOfferList) {

        if(adapter==null || data==null || homeOfferList==null)
            return;
        if(this.homeOfferList.size()>0)
            this.homeOfferList.clear();
        this.homeOfferList.addAll(homeOfferList);

        if(homeList.size()>0)
            homeList.clear();
        homeList.addAll(data);
        adapter.setAdapterData(homeList);

        if(this.homeOfferList.size()>0)
        setViewPagerTimer(this.homeOfferList);

    }

    public List<HomeModel.ObjCategoryListEntity> getData() {
        return homeList;
    }

    @Override
    public void onItemClick(HomeModel.ObjCategoryListEntity objstoredetailslist, View view, int position,String type) {
        Intent intent=new Intent(getActivity(),CategoryActivity.class);
        intent.putExtra(CURRENT_FRG,HOME_FRG);
        intent.putExtra(ACT_HOME_PARAMETER,new Gson().toJson(objstoredetailslist));
        startActivity(intent);

    }
}
