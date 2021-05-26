package com.example.coronameter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    onClickListener clickListener;
    Context context;
    List<Corona> coronaList;
    private static final String TAG = "MyAdapter";
    public MyAdapter(Context context, List<Corona> coronaList, onClickListener clickListener) {
        this.context = context;
        this.coronaList = coronaList;
        this.clickListener = clickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView newConfirmed,newRecovered,newDeaths,totalConfirm,totalDeaths,totalRecovered,country;
        onClickListener clickListener;

        public MyViewHolder(@NonNull View itemView, onClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
            newConfirmed = itemView.findViewById(R.id.newConfirmed2);
            newRecovered = itemView.findViewById(R.id.newRecovered2);
            newDeaths = itemView.findViewById(R.id.newDeaths2);
            totalConfirm = itemView.findViewById(R.id.totalConfirmed2);
            totalRecovered = itemView.findViewById(R.id.totalRecovered2);
            totalDeaths = itemView.findViewById(R.id.totalDeaths2);
            country= itemView.findViewById(R.id.country2);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.corona_log, parent, false);
        return new MyViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        holder.newConfirmed.setText(""+NumberFormat.getNumberInstance(Locale.US).format(coronaList.get(position).getNewConfirmed()));
        holder.newDeaths.setText(""+NumberFormat.getNumberInstance(Locale.US).format(coronaList.get(position).getNewDeaths()));
        holder.newRecovered.setText(""+NumberFormat.getNumberInstance(Locale.US).format(coronaList.get(position).getNewRecovered()));
        holder.totalConfirm.setText(""+NumberFormat.getNumberInstance(Locale.US).format(coronaList.get(position).getTotalConfirmed()));
        holder.totalDeaths.setText(""+NumberFormat.getNumberInstance(Locale.US).format(coronaList.get(position).getTotalDeaths()));
        holder.totalRecovered.setText(""+NumberFormat.getNumberInstance(Locale.US).format(coronaList.get(position).getTotalRecovered()));
        holder.country.setText(""+coronaList.get(position).getCountry());
    }

    @Override
    public int getItemCount() {
        return coronaList.size();
    }

    public interface onClickListener {
        void onClick(int position);
    }


}
