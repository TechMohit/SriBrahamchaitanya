package grossary.cyron.com.grossarybrch.order;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

import grossary.cyron.com.grossarybrch.R;
import grossary.cyron.com.grossarybrch.utility.GlideApp;
import grossary.cyron.com.grossarybrch.utility.callback.OnItemClickListener;


public class MyOrderDetailsdapter extends RecyclerView.Adapter {

    private List<OrderDetailsModel.OrderdetailEntity> dataSet;
    private Activity activity;
    private OnItemClickListener<OrderDetailsModel.OrderdetailEntity> clickListener;
    private OrderDetailsModel response;

    public MyOrderDetailsdapter(Activity activity, OnItemClickListener<OrderDetailsModel.OrderdetailEntity> clickListener) {
        this.activity = activity;
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        if(listPosition==dataSet.size()){


        }else {

            final OrderDetailsModel.OrderdetailEntity object = dataSet.get(listPosition);
            ((ImageTypeViewHolder) holder).tvProductName.setText(String.format("%s", object.getProductname()));

            Float sum=(Float.valueOf(object.getProductprice())*Float.valueOf(object.getOrderitemqty()));
            ((ImageTypeViewHolder) holder).tvPrice.setText("₹"+sum);
            ((ImageTypeViewHolder) holder).tvQty.setText("₹"+object.getProductprice()+"*"+object.getOrderitemqty());

            GlideApp.with(activity)
                    .load(object.getProductimage())
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.mipmap.logo_pink)
                    .error(R.drawable.ic_launcher_background)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(((ImageTypeViewHolder) holder).imgView);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_order_detail, parent, false);
            return new ImageTypeViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_order_detail_last, parent, false);
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

    public void setAdapterData(List<OrderDetailsModel.OrderdetailEntity> sellerList, OrderDetailsModel response) {
        dataSet = sellerList;
        this.response=response;
        notifyDataSetChanged();

    }


    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        private TextView tvProductName,tvPrice,tvQty;
        private ImageView imgView;


        public ImageTypeViewHolder(View itemView) {
            super(itemView);
            this.tvProductName = itemView.findViewById(R.id.tvProductName);
            this.imgView = itemView.findViewById(R.id.imgView);
            this.tvPrice=itemView.findViewById(R.id.tvPrice);
            this.tvQty=itemView.findViewById(R.id.tvQty);
        }
    }
    public static class LastTypeViewHolder extends RecyclerView.ViewHolder {



        public LastTypeViewHolder(View itemView) {
            super(itemView);

        }
    }

}