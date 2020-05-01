package grossary.cyron.com.grossarybrch.order;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import grossary.cyron.com.grossarybrch.R;
import grossary.cyron.com.grossarybrch.utility.callback.OnItemClickListener;

import static grossary.cyron.com.grossarybrch.utility.Constant.CATEGORY.CANCELORDER;
import static grossary.cyron.com.grossarybrch.utility.Constant.CATEGORY.ONCLICK;


public class MyOrdersAdapter extends RecyclerView.Adapter {

    private List<ViewOrderListModel.OrderlistEntity> dataSet;
    private Activity activity;
    private OnItemClickListener<ViewOrderListModel.OrderlistEntity> clickListener;

    public MyOrdersAdapter(Activity activity, OnItemClickListener<ViewOrderListModel.OrderlistEntity> clickListener) {
        this.activity = activity;
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        final ViewOrderListModel.OrderlistEntity object = dataSet.get(listPosition);
        ((ImageTypeViewHolder) holder).tvOrderId.setText("Order ID: "+object.getTranno());
        if(object.getDeliverycharges().equalsIgnoreCase("0.00")){
            ((ImageTypeViewHolder) holder).tvDeliveryCharge.setText("Free");

        }else {
            ((ImageTypeViewHolder) holder).tvDeliveryCharge.setText("₹" + object.getDeliverycharges());
        }
        ((ImageTypeViewHolder) holder).tvDatePlaced.setText("Place on "+object.getTrandate());
        ((ImageTypeViewHolder) holder).tvAddress.setText(""+object.getShippingaddress());
        ((ImageTypeViewHolder) holder).tvCharge.setText("₹"+ object.getTotalamount());
        ((ImageTypeViewHolder) holder).tvAmount.setText("₹"+ object.getTotalamount());
       String Status =  object.getInvoicestatus();
       if(Status.equalsIgnoreCase("completed")){
           ((ImageTypeViewHolder) holder).orderStatus.setText("Order has been Packed");

       }

//        ((ImageTypeViewHolder) holder).tvMrpPrice.setPaintFlags(((ImageTypeViewHolder) holder).tvMrpPrice.getPaintFlags()
//                | Paint.STRIKE_THRU_TEXT_FLAG);
//
//
        ((ImageTypeViewHolder) holder).btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(object, ((ImageTypeViewHolder) holder).btnDetails, listPosition,ONCLICK);
            }
        });

//        ((ImageTypeViewHolder) holder).btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clickListener.onItemClick(object, ((ImageTypeViewHolder) holder).card_parent, listPosition,ADD);
//            }
//        });
        ((ImageTypeViewHolder) holder).btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                builder1.setMessage("Do you want to cancel this order");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                clickListener.onItemClick(object, ((ImageTypeViewHolder) holder).btnCancelOrder, listPosition,CANCELORDER);

                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_order, parent, false);
        return new ImageTypeViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (dataSet == null)
            return 0;
        return dataSet.size();
    }

    public void setAdapterData(List<ViewOrderListModel.OrderlistEntity> sellerList) {
        dataSet = sellerList;
        notifyDataSetChanged();

    }


    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        private TextView tvAmount,tvOrderId,tvDeliveryCharge,tvDatePlaced,tvAddress,tvCharge,orderStatus;
        private Button btnDetails,btnCancelOrder;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);
            this.tvAmount = itemView.findViewById(R.id.tvAmount);
            this.tvOrderId=itemView.findViewById(R.id.tvOrderId);
            this.tvDeliveryCharge=itemView.findViewById(R.id.tvDeliveryCharge);
            this.tvDatePlaced=itemView.findViewById(R.id.tvDatePlaced);
            this.tvAddress=itemView.findViewById(R.id.tvAddress);
            this.tvCharge=itemView.findViewById(R.id.tvCharge);
            this.btnDetails=itemView.findViewById(R.id.btnDetails);
            this.orderStatus=itemView.findViewById(R.id.orderStatus);
            this.btnCancelOrder=itemView.findViewById(R.id.btnCancelOrder);
        }
    }

}