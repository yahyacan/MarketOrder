package com.order.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.order.R;
import com.order.listener.ItemClickListener;
import com.order.model.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{

    private List<Order> mList;
    private Context mContext;
    private ItemClickListener mListener;
    private List<String> months;

    public OrderAdapter(Context context, List<Order> list, ItemClickListener listener) {
        this.mList = list;
        this.mContext = context;
        this.mListener = listener;
        months = Arrays.asList(context.getResources().getStringArray(R.array.months));
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_order,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        Order entity = mList.get(position);
        if (entity.isVisible()){
            holder.tvDay.setVisibility(View.VISIBLE);
            holder.tvDay.setText(entity.getDate());

            holder.tvMonth.setVisibility(View.VISIBLE);
            holder.tvMonth.setText(months.get(Integer.parseInt(entity.getMonth())-1));
        }else{
            holder.tvDay.setVisibility(View.INVISIBLE);
            holder.tvMonth.setVisibility(View.INVISIBLE);
        }

        holder.tvMarketName.setText(entity.getMarketName());
        holder.tvOrderName.setText(entity.getOrderName());
        holder.tvProductPrice.setText(entity.getProductPrice().doubleValue() + " TL");
        holder.tvProductState.setText(entity.getProductState());
        holder.tvOrderDetail.setText(entity.getProductDetail().getOrderDetail());
        holder.tvSummaryPrice.setText(entity.getProductDetail().getSummaryPrice().doubleValue() + " TL");
        if ("Hazırlanıyor".equals(entity.getProductState())){
            holder.imgProductState.setBackground(mContext.getResources().getDrawable(R.drawable.preparing_rectangle));
            holder.tvProductState.setTextColor(mContext.getResources().getColor(R.color.preparing_color));
        }else if ("Onay Bekliyor".equals(entity.getProductState())){
            holder.imgProductState.setBackground(mContext.getResources().getDrawable(R.drawable.waiting_aprove_rectangle));
            holder.tvProductState.setTextColor(mContext.getResources().getColor(R.color.waiting_approve_color));
        }else if ("Yolda".equals(entity.getProductState())){
            holder.imgProductState.setBackground(mContext.getResources().getDrawable(R.drawable.on_way_rectangle));
            holder.tvProductState.setTextColor(mContext.getResources().getColor(R.color.order_on_way_color));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{

        private TextView tvDay;
        private TextView tvMonth;
        private TextView tvMarketName;
        private TextView tvOrderName;
        private TextView tvProductPrice;
        private ImageButton btnDetail;
        private ImageView imgProductState;
        private LinearLayout llContent;
        private TextView tvProductState;
        private TextView tvOrderDetail;
        private TextView tvSummaryPrice;
        private RelativeLayout rlDetail;

        public OrderViewHolder(final View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            tvMarketName = itemView.findViewById(R.id.tvMarketName);
            tvOrderName = itemView.findViewById(R.id.tvOrderName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductState = itemView.findViewById(R.id.tvProductState);
            tvOrderDetail = itemView.findViewById(R.id.tvOrderDetail);
            tvSummaryPrice = itemView.findViewById(R.id.tvSummaryPrice);
            btnDetail = itemView.findViewById(R.id.btnDetail);
            imgProductState = itemView.findViewById(R.id.imgProductState);
            llContent = itemView.findViewById(R.id.llContent);
            rlDetail = itemView.findViewById(R.id.rlDetail);
            llContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onDetail(itemView,getAdapterPosition());
                }
            });
        }
    }
}