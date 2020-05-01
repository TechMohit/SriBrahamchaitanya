package grossary.cyron.com.grossarybrch.brands;

import android.app.Activity;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

import grossary.cyron.com.grossarybrch.R;
import grossary.cyron.com.grossarybrch.home.HomeModel;
import grossary.cyron.com.grossarybrch.utility.GlideApp;
import grossary.cyron.com.grossarybrch.utility.callback.OnItemClickListener;

import static grossary.cyron.com.grossarybrch.utility.Constant.CATEGORY.ADD_TO_CART;
import static grossary.cyron.com.grossarybrch.utility.Constant.CATEGORY.ONCLICK;


public class BrandsListAdapter extends RecyclerView.Adapter {

    private List<HomeModel.ObjOfferProdListEntity> dataSet;
    private Activity activity;
    private OnItemClickListener<HomeModel.ObjOfferProdListEntity> clickListener;

    public BrandsListAdapter(Activity activity, OnItemClickListener<HomeModel.ObjOfferProdListEntity> clickListener) {
        this.activity = activity;
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,  int listPosition) {

        final HomeModel.ObjOfferProdListEntity object = dataSet.get(listPosition);
        ((ImageTypeViewHolder) holder).tvName.setText(""+object.getProductName()+"("+object.getStoreName()+")");
        ((ImageTypeViewHolder) holder).tvMrpPrice.setText("\u20B9"+object.getMRPPrice());

        ((ImageTypeViewHolder) holder).tvMrpPrice.setPaintFlags(((ImageTypeViewHolder) holder).tvMrpPrice.getPaintFlags()
                | Paint.STRIKE_THRU_TEXT_FLAG);
        ((ImageTypeViewHolder) holder).tvSellerPrice.setText("\u20B9"+object.getSellingPrice());

        GlideApp.with(activity)
                .load(object.getProductImage())
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.mipmap.logo_pink)
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(((ImageTypeViewHolder) holder).imgView);

        ((ImageTypeViewHolder) holder).card_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(object, ((ImageTypeViewHolder) holder).card_parent, holder.getAdapterPosition(),ONCLICK);
            }
        });

        ((ImageTypeViewHolder) holder).btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(object, ((ImageTypeViewHolder) holder).card_parent, holder.getAdapterPosition(),ADD_TO_CART);
            }
        });

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_brands, parent, false);
        return new ImageTypeViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (dataSet == null)
            return 0;
        return dataSet.size();
    }

    public void setAdapterData(List<HomeModel.ObjOfferProdListEntity> brandsList) {
        dataSet = brandsList;
        notifyDataSetChanged();
    }


    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        private CardView card_parent;
        private ImageView imgView;
        private TextView tvSellerPrice,tvMrpPrice,tvName;
        private Button btnAdd;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);
            this.card_parent = itemView.findViewById(R.id.card_parent);
            this.imgView = itemView.findViewById(R.id.imgView);
            this.tvName = itemView.findViewById(R.id.tvName);
            this.tvMrpPrice = itemView.findViewById(R.id.tvMrpPrice);
            this.tvSellerPrice = itemView.findViewById(R.id.tvSellerPrice);
            this.btnAdd=itemView.findViewById(R.id.btnAdd);
        }
    }

}