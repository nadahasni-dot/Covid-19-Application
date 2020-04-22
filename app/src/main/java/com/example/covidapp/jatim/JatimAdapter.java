package com.example.covidapp.jatim;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidapp.R;

import java.util.ArrayList;

public class JatimAdapter extends RecyclerView.Adapter<JatimAdapter.JatimViewHolder> {
    private ArrayList<JatimItem> mJatimList;

    public static class JatimViewHolder extends RecyclerView.ViewHolder{
        public TextView textKota;
        public TextView textPositif;
        public TextView textOdr;
        public TextView textOtg;
        public TextView textOdp;
        public TextView textPdp;

        public JatimViewHolder(@NonNull View itemView) {
            super(itemView);

            textKota = itemView.findViewById(R.id.tv_kota);
            textPositif = itemView.findViewById(R.id.jumlah_positif);
            textOdr = itemView.findViewById(R.id.tv_odr);
            textOtg = itemView.findViewById(R.id.tv_otg);
            textOdp = itemView.findViewById(R.id.tv_odp);
            textPdp = itemView.findViewById(R.id.tv_pdp);
        }
    }

    public JatimAdapter(ArrayList<JatimItem> jatimList){
        mJatimList = jatimList;
    }


    @NonNull
    @Override
    public JatimViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kasus_jatim, parent, false);
        JatimViewHolder jvh = new JatimViewHolder(v);

        return jvh;
    }

    @Override
    public void onBindViewHolder(@NonNull JatimViewHolder holder, int position) {
        JatimItem currentItem = mJatimList.get(position);

        holder.textKota.setText(currentItem.getKotaTittle());
        holder.textPositif.setText(currentItem.getKotaPositif());
        holder.textOdr.setText(currentItem.getKotaOdr());
        holder.textOtg.setText(currentItem.getKotaOtg());
        holder.textOdp.setText(currentItem.getKotaOdp());
        holder.textPdp.setText(currentItem.getKotaPdp());
    }

    @Override
    public int getItemCount() {
        return mJatimList.size();
    }
}
