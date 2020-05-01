package grossary.cyron.com.grossarybrch.search;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

import grossary.cyron.com.grossarybrch.R;
import grossary.cyron.com.grossarybrch.utility.GlideApp;
import grossary.cyron.com.grossarybrch.utility.callback.OnItemClickListener;

import static grossary.cyron.com.grossarybrch.utility.Constant.CATEGORY.ONCLICK;


public class ProductSearchDetailsAdapter extends RecyclerView.Adapter implements Filterable {

    private List<ProductSearchDetailsModel.ObjproductsearchdetailsEntity> dataSet;
    private List<ProductSearchDetailsModel.ObjproductsearchdetailsEntity> dataSetFilter = new ArrayList<>();

    private Activity activity;
    private OnItemClickListener<ProductSearchDetailsModel.ObjproductsearchdetailsEntity> clickListener;

    public ProductSearchDetailsAdapter(Activity activity, OnItemClickListener<ProductSearchDetailsModel.ObjproductsearchdetailsEntity> clickListener) {
        this.activity = activity;
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,  int listPosition) {

        final ProductSearchDetailsModel.ObjproductsearchdetailsEntity object = dataSetFilter.get(listPosition);
        ((ImageTypeViewHolder) holder).tvProductName.setText(""+ object.getProductname());
        ((ImageTypeViewHolder) holder).tvCatName.setText(""+object.getCategoryname());

        GlideApp.with(activity)
                .load(object.getProductimage())
                .centerInside()
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


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product_search_details, parent, false);
        return new ImageTypeViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (dataSetFilter == null)
            return 0;
        return dataSetFilter.size();
    }

    public void setAdapterData(List<ProductSearchDetailsModel.ObjproductsearchdetailsEntity> sellerList) {
        dataSet = sellerList;
//        dataSetFilter=sellerList;
        notifyDataSetChanged();

    }



    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        private TextView tvProductName,tvCatName;
        private ImageView imgView;
        private CardView card_parent;
        public ImageTypeViewHolder(View itemView) {
            super(itemView);
            this.tvProductName = itemView.findViewById(R.id.tvProductName);
            this.tvCatName = itemView.findViewById(R.id.tvCatName);
            this.imgView = itemView.findViewById(R.id.imgView);
            this.card_parent=itemView.findViewById(R.id.card_parent);
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                List<ProductSearchDetailsModel.ObjproductsearchdetailsEntity> filteredList = new ArrayList<>();
                if (charString.isEmpty()) {
//                    dataSetFilter = dataSet;
                } else {
                    for (ProductSearchDetailsModel.ObjproductsearchdetailsEntity row : dataSet) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getProductname().toLowerCase().contains(charString.toString().toLowerCase()) ||
                                row.getCategoryname().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                }
                dataSetFilter = filteredList;

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataSetFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataSetFilter = (ArrayList<ProductSearchDetailsModel.ObjproductsearchdetailsEntity>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

}