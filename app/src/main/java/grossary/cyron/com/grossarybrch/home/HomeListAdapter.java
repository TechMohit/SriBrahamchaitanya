package grossary.cyron.com.grossarybrch.home;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;

import grossary.cyron.com.grossarybrch.R;
import grossary.cyron.com.grossarybrch.utility.GlideApp;
import grossary.cyron.com.grossarybrch.utility.callback.OnItemClickListener;

import static grossary.cyron.com.grossarybrch.utility.Constant.CATEGORY.ONCLICK;


public class HomeListAdapter extends RecyclerView.Adapter {

    private ArrayList<HomeModel.ObjCategoryListEntity> dataSet;
    private Activity activity;
    private OnItemClickListener<HomeModel.ObjCategoryListEntity> clickListener;

    public HomeListAdapter(Activity activity, OnItemClickListener<HomeModel.ObjCategoryListEntity> clickListener) {
        this.activity = activity;
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        final HomeModel.ObjCategoryListEntity object = dataSet.get(listPosition);
        ((ImageTypeViewHolder) holder).title.setText(String.format("%s", object.getCatergoryName()));

        GlideApp.with(activity)
                .load(object.getCatergoryImage())
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.mipmap.logo_pink)
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(((ImageTypeViewHolder) holder).imgView);


        ((ImageTypeViewHolder) holder).card_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(object, ((ImageTypeViewHolder) holder).card_parent, listPosition,ONCLICK);
            }
        });


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sellers, parent, false);
        return new ImageTypeViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (dataSet == null)
            return 0;
        return dataSet.size();
    }

    public void setAdapterData(ArrayList<HomeModel.ObjCategoryListEntity> sellerList) {
        dataSet = sellerList;
        notifyDataSetChanged();

    }


    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private CardView card_parent;
        private ImageView imgView;


        public ImageTypeViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.tvProductName);
            this.card_parent = itemView.findViewById(R.id.card_parent);
            this.imgView = itemView.findViewById(R.id.imgView);
        }
    }

}