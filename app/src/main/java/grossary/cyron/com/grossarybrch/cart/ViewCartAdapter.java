package grossary.cyron.com.grossarybrch.cart;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

import grossary.cyron.com.grossarybrch.R;
import grossary.cyron.com.grossarybrch.utility.GlideApp;
import grossary.cyron.com.grossarybrch.utility.callback.OnItemClickListener;

import static grossary.cyron.com.grossarybrch.utility.Constant.CATEGORY.ADD;
import static grossary.cyron.com.grossarybrch.utility.Constant.CATEGORY.DELETE;
import static grossary.cyron.com.grossarybrch.utility.Constant.CATEGORY.MIN;


public class ViewCartAdapter extends RecyclerView.Adapter {

    private List<ViewAddtoCartDetailsModel.ObjviewaddcartlistEntity> dataSet;
    private Activity activity;
    private OnItemClickListener<ViewAddtoCartDetailsModel.ObjviewaddcartlistEntity> clickListener;
    private ViewAddtoCartDetailsModel response;

    public ViewCartAdapter(Activity activity, OnItemClickListener<ViewAddtoCartDetailsModel.ObjviewaddcartlistEntity> clickListener) {
        this.activity = activity;
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        if(listPosition==dataSet.size()){
            ((LastTypeViewHolder) holder).tvItem.setText("₹"+response.getTotalshippingcharges());
            ((LastTypeViewHolder) holder).tvTotal.setText("₹"+response.getGrandtoal());
            float difference2 = response.getGrandtoal();
            float difference1 = response.getTotalmrp();
            float difference =difference1-difference2;
            ((LastTypeViewHolder) holder).tvTotalSaving.setText("₹"+difference);

        }else {

            final ViewAddtoCartDetailsModel.ObjviewaddcartlistEntity object = dataSet.get(listPosition);
            ((ImageTypeViewHolder) holder).tvProductName.setText(String.format("%s", object.getProductname()));
            ((ImageTypeViewHolder) holder).tvPrice.setText("₹" + String.format("%s", object.getSellingprice()));

            //UnitQty in spinner
            ((ImageTypeViewHolder) holder).txtCount.setText(""+object.getUnitqty());

            GlideApp.with(activity)
                    .load(object.getProductimage())
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.mipmap.logo_pink)
                    .error(R.drawable.ic_launcher_background)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(((ImageTypeViewHolder) holder).imgView);


            ((ImageTypeViewHolder) holder).btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(object, ((ImageTypeViewHolder) holder).card_parent, listPosition, DELETE);
                }
            });
            ((ImageTypeViewHolder) holder).btnMin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int i=object.getUnitqty();
                    if(i<=1){
                        Toast.makeText(activity,"Invalid Qty",Toast.LENGTH_LONG).show();
                    }else{
                        i--;
                        object.setUnitqty(i);
                        ((ImageTypeViewHolder) holder).txtCount.setText(""+i);
                        clickListener.onItemClick(object, ((ImageTypeViewHolder) holder).card_parent, listPosition, ADD);

                    }
                }
            });
            ((ImageTypeViewHolder) holder).btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i=object.getUnitqty();
                        i++;
                        object.setUnitqty(i);
                        ((ImageTypeViewHolder) holder).txtCount.setText(""+i);
                    clickListener.onItemClick(object, ((ImageTypeViewHolder) holder).card_parent, listPosition, MIN);


                }
            });

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cart, parent, false);
            return new ImageTypeViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cart_last, parent, false);
            return new LastTypeViewHolder(view);
        }

    }

    @Override
    public int getItemCount() {
        if (dataSet == null)
            return 0;
        return dataSet.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==dataSet.size())
            return 9;
        return 0;
    }

    public void setAdapterData(List<ViewAddtoCartDetailsModel.ObjviewaddcartlistEntity> sellerList, ViewAddtoCartDetailsModel response) {
        dataSet = sellerList;
        this.response=response;
        notifyDataSetChanged();

    }


    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        private TextView tvProductName,tvPrice,txtCount;
        private CardView card_parent;
        private ImageView imgView;
        private Button btnDelete,btnAdd,btnMin;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);
            this.tvProductName = itemView.findViewById(R.id.tvProductName);
            this.tvPrice=itemView.findViewById(R.id.tvPrice);
            this.card_parent = itemView.findViewById(R.id.card_parent);
            this.imgView = itemView.findViewById(R.id.imgView);
            this.btnDelete=itemView.findViewById(R.id.btnDelete);
            this.txtCount=itemView.findViewById(R.id.txtCount);
            this.btnAdd=itemView.findViewById(R.id.btnAdd);
            this.btnMin=itemView.findViewById(R.id.btnMin);
        }
    }
    public static class LastTypeViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItem,tvTotal,tvTotalSaving;


        public LastTypeViewHolder(View itemView) {
            super(itemView);
            this.tvItem = itemView.findViewById(R.id.tvItem);
            this.tvTotal=itemView.findViewById(R.id.tvTotal);
            this.tvTotalSaving=itemView.findViewById(R.id.tvTotalSaving);
        }
    }

}