package com.example.pdf_signature.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pdf_signature.PDFActivity;
import com.example.pdf_signature.R;
import com.example.pdf_signature.model.result.ListResult;

import java.util.List;

import cn.hutool.core.util.StrUtil;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<ListResult> data;

    private Activity activity;

    public MyAdapter(List<ListResult> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    public void updateData(List<ListResult> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListResult item = data.get(position);
        holder.ticketNo.setText(item.getTicketNo());
        holder.orderDocNo.setText(item.getOrderDocNo());
        holder.partnerDescr.setText(item.getPartnerDescr());
        holder.productCode.setText(item.getProductCode());
        holder.accountingVolume.setText(String.valueOf(item.getAccountingVolume()));
        holder.priceUom.setText(item.getPriceUom());
        holder.vehicleNo.setText(item.getVehicleNo());

//        String closeDate = item.getCloseDate();
//        closeDate = closeDate.replace("00:00:00", formatTime(item.getCloseTime()));
        holder.startTime.setText(item.getCloseDateTime());


        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(activity, PDFActivity.class);
            intent.putExtra("url", item.getDocumentUrl());
            intent.putExtra("ticketNo", item.getTicketNo());
            activity.startActivity(intent);
        });
    }

    private String formatTime(String timeString) {
        if (timeString.length() != 4) {
            return "";
        }

        String hour = timeString.substring(0, 2);
        String minute = timeString.substring(2, 4);

        return hour + ":" + minute;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ticketNo;
        public TextView orderDocNo;
        public TextView partnerDescr;
        public TextView productCode;
        public TextView accountingVolume;
        public TextView priceUom;
        public TextView startTime;
        public TextView vehicleNo;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ticketNo = itemView.findViewById(R.id.ticketNo);
            orderDocNo = itemView.findViewById(R.id.orderDocNo);
            partnerDescr = itemView.findViewById(R.id.partnerDescr);
            productCode = itemView.findViewById(R.id.productCode);
            accountingVolume = itemView.findViewById(R.id.accountingVolume);
            priceUom = itemView.findViewById(R.id.priceUom);
            startTime = itemView.findViewById(R.id.startTime);
            vehicleNo = itemView.findViewById(R.id.vehicleNo);
        }
    }
}