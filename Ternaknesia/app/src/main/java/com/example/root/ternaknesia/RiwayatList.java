package com.example.root.ternaknesia;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by root on 12/07/17.
 */

public class RiwayatList extends RecyclerView.Adapter<RiwayatList.ViewHolder>{

    private List<DaftarRiwayat> riwayatList;
    private int rowLayout;
    private Context context;

    public RiwayatList(List<DaftarRiwayat> riwayatList, int rowLayout, Context context) {
        this.riwayatList = riwayatList;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new RiwayatList.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DaftarRiwayat riwayat = riwayatList.get(position);
        holder.textViewId.setText("Code :"+riwayat.getCodeH());
        holder.textViewBobot.setText("Bobot :"+riwayat.getBobotH());
        holder.textViewTanggal.setText("Tanggal :"+riwayat.getTanggalH());
        holder.textViewKandang.setText("Kandang :"+riwayat.getKandangH());
    }

    @Override
    public int getItemCount() {
        return riwayatList == null ? 0 : riwayatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewId, textViewBobot, textViewTanggal, textViewKandang;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewId = (TextView) itemView.findViewById(R.id.textViewId);
            textViewBobot = (TextView) itemView.findViewById(R.id.textViewBobot);
            textViewTanggal = (TextView) itemView.findViewById(R.id.textViewTanggal);
            textViewKandang = (TextView) itemView.findViewById(R.id.textViewKandang);
        }
    }



    /*private Activity context;
    private List<DaftarRiwayat> riwayatList;

    public RiwayatList(Activity context, List<DaftarRiwayat> riwayatList){
        super(context, R.layout.list_riwayat, riwayatList);
        this.context = context;
        this.riwayatList = riwayatList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_riwayat, null, true);

        TextView textViewId = (TextView) listViewItem.findViewById(R.id.textViewId);
        TextView textViewBobot = (TextView) listViewItem.findViewById(R.id.textViewBobot);
        TextView textViewTanggal = (TextView) listViewItem.findViewById(R.id.textViewTanggal);
        TextView textViewKandang = (TextView) listViewItem.findViewById(R.id.textViewKandang);

        DaftarRiwayat daftarRiwayat = riwayatList.get(position);

        textViewId.setText(daftarRiwayat.getIdH());
        textViewBobot.setText(daftarRiwayat.getBobotH());
        textViewTanggal.setText(daftarRiwayat.getTanggalH());
        textViewKandang.setText(daftarRiwayat.getKandangH());

        return listViewItem;
    }*/
}
