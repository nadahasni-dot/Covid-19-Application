package com.example.covidapp.jember;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidapp.R;

import java.util.ArrayList;

public class JemberAdapter extends RecyclerView.Adapter<JemberAdapter.JemberViewHolder> {
    private ArrayList<JemberItem> mJemberList;

    public static class JemberViewHolder extends RecyclerView.ViewHolder{
        public TextView textKecamatan;
        public TextView textPositif;
        public TextView textOdr;
        public TextView textOdp;
        public TextView textPdp;

        public JemberViewHolder(@NonNull View itemView) {
            super(itemView);

            textKecamatan = itemView.findViewById(R.id.tv_kec);
            textPositif = itemView.findViewById(R.id.jumlah_positif);
            textOdr = itemView.findViewById(R.id.tv_odr);
            textOdp = itemView.findViewById(R.id.tv_odp);
            textPdp = itemView.findViewById(R.id.tv_pdp);
        }
    }

    public JemberAdapter(ArrayList<JemberItem> jemberList){
        mJemberList = jemberList;
    }

    @NonNull
    @Override
    public JemberAdapter.JemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kasus_jember, parent, false);
        JemberAdapter.JemberViewHolder jvh = new JemberAdapter.JemberViewHolder(v);

        return jvh;
    }

    @Override
    public void onBindViewHolder(@NonNull JemberAdapter.JemberViewHolder holder, int position) {
        JemberItem currentItem = mJemberList.get(position);

        holder.textKecamatan.setText(currentItem.getKecamatanTittle());
        holder.textPositif.setText(currentItem.getKecamatanPositif());
        holder.textOdr.setText(currentItem.getKecamatanOdr());
        holder.textOdp.setText(currentItem.getKecamatanOdp());
        holder.textPdp.setText(currentItem.getKecamatanPdp());
    }

    @Override
    public int getItemCount() {
        return mJemberList.size();
    }
}
