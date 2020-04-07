package com.example.covidapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class NegaraAdapter extends RecyclerView.Adapter<NegaraAdapter.NegaraViewHolder> {
    private ArrayList<NegaraItem> mNegaraList;

    public static class NegaraViewHolder extends RecyclerView.ViewHolder{
        public TextView textNegara;
        public TextView textPositif;
        public TextView textSembuh;
        public TextView textMeninggal;

        public NegaraViewHolder(@NonNull View itemView) {
            super(itemView);

            textNegara = itemView.findViewById(R.id.tv_negara);
            textPositif = itemView.findViewById(R.id.tv_positif_negara);
            textSembuh = itemView.findViewById(R.id.tv_sembuh_negara);
            textMeninggal = itemView.findViewById(R.id.tv_meninggal_negara);
        }
    }

    public NegaraAdapter(ArrayList<NegaraItem> negaraList){
        mNegaraList = negaraList;
    }

    @NonNull
    @Override
    public NegaraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kasus, parent, false);
        NegaraViewHolder nvh = new NegaraViewHolder(v);

        return nvh;
    }

    @Override
    public void onBindViewHolder(@NonNull NegaraViewHolder holder, int position) {
        NegaraItem currentItem = mNegaraList.get(position);

        holder.textNegara.setText(currentItem.getNegaraTittle());
        holder.textPositif.setText(currentItem.getNegaraPositif());
        holder.textSembuh.setText(currentItem.getNegaraSembuh());
        holder.textMeninggal.setText(currentItem.getNegaraMeninggal());
    }

    @Override
    public int getItemCount() {
        return mNegaraList.size();
    }
}
