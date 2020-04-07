package com.example.covidapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ProvinsiAdapter extends RecyclerView.Adapter<ProvinsiAdapter.ProvinsiViewHolder> {
    private ArrayList<ProvinsiItem> mProvinsiList;

    public static class ProvinsiViewHolder extends RecyclerView.ViewHolder{
        public TextView textProvinsi;
        public TextView textPositif;
        public TextView textSembuh;
        public TextView textMeninggal;

        public ProvinsiViewHolder(@NonNull View itemView) {
            super(itemView);

            textProvinsi = itemView.findViewById(R.id.tv_negara);
            textPositif = itemView.findViewById(R.id.tv_positif_negara);
            textSembuh = itemView.findViewById(R.id.tv_sembuh_negara);
            textMeninggal = itemView.findViewById(R.id.tv_meninggal_negara);
        }
    }

//    public static class ProvinsiViewholder extends RecyclerView.ViewHolder{
//        public TextView textProvinsi;
//        public TextView textPositif;
//        public TextView textSembuh;
//        public TextView textMeninggal;
//
//        public ProvinsiViewholder(@NonNull View itemView) {
//            super(itemView);
//
//            textProvinsi = itemView.findViewById(R.id.tv_negara);
//            textPositif = itemView.findViewById(R.id.tv_positif_negara);
//            textSembuh = itemView.findViewById(R.id.tv_sembuh_negara);
//            textMeninggal = itemView.findViewById(R.id.tv_meninggal_negara);
//        }
//    }

    public ProvinsiAdapter(ArrayList<ProvinsiItem> provinsiList){
        mProvinsiList = provinsiList;
    }


//    @NonNull
//    @Override
//    public ProvinsiViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kasus, parent, false);
//        ProvinsiViewholder nvh = new ProvinsiViewholder(v);
//
//        return nvh;
//    }

//    @Override
//    public void onBindViewHolder(@NonNull ProvinsiViewholder holder, int position) {
//        ProvinsiItem currentItem = mProvinsiList.get(position);
//
//        holder.textProvinsi.setText(currentItem.getProvinsiTittle());
//        holder.textPositif.setText(currentItem.getProvinsiPositif());
//        holder.textSembuh.setText(currentItem.getProvinsiSembuh());
//        holder.textMeninggal.setText(currentItem.getProvinsiMeninggal());
//    }

    @NonNull
    @Override
    public ProvinsiAdapter.ProvinsiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kasus, parent, false);
        ProvinsiViewHolder pvh = new ProvinsiViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProvinsiAdapter.ProvinsiViewHolder holder, int position) {
        ProvinsiItem currentItem = mProvinsiList.get(position);

        holder.textProvinsi.setText(currentItem.getProvinsiTittle());
        holder.textPositif.setText(currentItem.getProvinsiPositif());
        holder.textSembuh.setText(currentItem.getProvinsiSembuh());
        holder.textMeninggal.setText(currentItem.getProvinsiMeninggal());
    }

    @Override
    public int getItemCount() {
        return mProvinsiList.size();
    }
}
