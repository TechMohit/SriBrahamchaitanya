package grossary.cyron.com.grossarybrch.drawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import grossary.cyron.com.grossarybrch.R;
import grossary.cyron.com.grossarybrch.utility.callback.OnItemClickListener;

import static grossary.cyron.com.grossarybrch.utility.Constant.CATEGORY.ONCLICK;


/**
 * Created by Satyam Kumar Naik on 1/11/2018.
 */

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private final List<DrawerItem> list;
    private final OnItemClickListener onItemClickListener;
    public int selectedPosition = 0;
    private Activity activity;

    public DrawerAdapter(Activity activity, List<DrawerItem> list, OnItemClickListener onItemClickListener) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        this.onItemClickListener = onItemClickListener;
        this.activity=activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_drawer_item, parent, false);
        return new ImageTypeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,  int position) {
        final DrawerItem object = list.get(position);
        if(position==selectedPosition){
            ((ImageTypeViewHolder) holder).linItem.setBackgroundColor(activity.getResources().getColor(R.color.colorAccent));
            ((ImageTypeViewHolder) holder).tv_text.setTextColor(Color.parseColor("#ffffff"));
            ((ImageTypeViewHolder) holder).img_icon.setImageDrawable(ContextCompat.getDrawable(activity,object.getIconUnSelect()));

        }else {
            ((ImageTypeViewHolder) holder).linItem.setBackground(null);
            ((ImageTypeViewHolder) holder).tv_text.setTextColor(Color.parseColor("#000000"));
            ((ImageTypeViewHolder) holder).img_icon.setImageDrawable(ContextCompat.getDrawable(activity,object.getIcon()));
        }

        ((ImageTypeViewHolder) holder).tv_text.setText("" + object.getText());

        ((ImageTypeViewHolder) holder).linItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onItemClickListener.onItemClick(object, view, holder.getAdapterPosition(),ONCLICK);
            }
        });
    }

    public void selectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout linItem;
        private TextView tv_text;
        private ImageView img_icon;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);
            this.linItem = itemView.findViewById(R.id.lin_item);
            this.tv_text = itemView.findViewById(R.id.tv_text);
            this.img_icon=itemView.findViewById(R.id.img_icon);
        }
    }

}