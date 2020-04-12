package com.example.covidapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class JatimAdapter extends RecyclerView.Adapter<JatimAdapter.JatimViewHolder> {
    private ArrayList<JatimItem> mJatimList;

    public static class JatimViewHolder extends RecyclerView.ViewHolder{
        public TextView textKota;
        public TextView textPositif;
        public TextView textSembuh;
        public TextView textMeninggal;

        public JatimViewHolder(@NonNull View itemView) {
            super(itemView);

            textKota = itemView.findViewById(R.id.tv_negara);
            textPositif = itemView.findViewById(R.id.tv_positif_negara);
            textSembuh = itemView.findViewById(R.id.tv_sembuh_negara);
            textMeninggal = itemView.findViewById(R.id.tv_meninggal_negara);
        }
    }

    public JatimAdapter(ArrayList<JatimItem> jatimList){
        mJatimList = jatimList;
    }

    @NonNull
    @Override
    public JatimViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kasus, parent, false);
        JatimViewHolder jvh = new JatimViewHolder(v);

        return jvh;
    }

    @Override
    public void onBindViewHolder(@NonNull JatimViewHolder holder, int position) {
        JatimItem currentItem = mJatimList.get(position);

        holder.textKota.setText(currentItem.getKotaTittle());
        holder.textPositif.setText(currentItem.getKotaPositif());
        holder.textSembuh.setText(currentItem.getKotaSembuh());
        holder.textMeninggal.setText(currentItem.getKotaMeninggal());
    }

    @Override
    public int getItemCount() {
        return mJatimList.size();
    }
}
