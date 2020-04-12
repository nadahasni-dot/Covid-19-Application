package com.example.covidapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ProvinsiAdapter extends RecyclerView.Adapter<ProvinsiAdapter.ProvinsiViewHolder> implements Filterable {
    private ArrayList<ProvinsiItem> mProvinsiList;
    private ArrayList<ProvinsiItem> mProvinsiListFull;

    @Override
    public Filter getFilter() {
        return provinsiFilter;
    }

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

    public ProvinsiAdapter(ArrayList<ProvinsiItem> provinsiList){
        mProvinsiList = provinsiList;
        mProvinsiListFull = new ArrayList<>(provinsiList);
    }


    @NonNull
    @Override
    public ProvinsiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

    private Filter provinsiFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ProvinsiItem> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(mProvinsiListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ProvinsiItem item : mProvinsiListFull){
                    if(item.getProvinsiTittle().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mProvinsiList.clear();
            mProvinsiList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
