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


public class NegaraAdapter extends RecyclerView.Adapter<NegaraAdapter.NegaraViewHolder> implements Filterable {
    private ArrayList<NegaraItem> mNegaraList;
    private ArrayList<NegaraItem> mNegaraListFull;

    @Override
    public Filter getFilter() {
        return negaraFilter;
    }

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
        this.mNegaraList = negaraList;
        mNegaraListFull = new ArrayList<>(negaraList);
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



    private Filter negaraFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<NegaraItem> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(mNegaraListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (NegaraItem item : mNegaraListFull){
                    if(item.getNegaraTittle().toLowerCase().contains(filterPattern)){
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
            mNegaraList.clear();
            mNegaraList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
