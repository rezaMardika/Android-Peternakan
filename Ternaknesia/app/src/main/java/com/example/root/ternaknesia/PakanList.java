package com.example.root.ternaknesia;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by root on 05/07/17.
 */


public class PakanList extends ArrayAdapter<DaftarPakan> {
    private Activity context;
    private List<DaftarPakan> pakans;

    public PakanList(Activity context, List<DaftarPakan> pakans){
        super(context, R.layout.list_pakan, pakans);
        this.context = context;
        this.pakans = pakans;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_pakan, null, true);

        TextView textViewNama = (TextView) listViewItem.findViewById(R.id.textViewNama);
        TextView textViewJumlah = (TextView) listViewItem.findViewById(R.id.textViewJumlah);

        DaftarPakan daftarPakan = pakans.get(position);

        textViewNama.setText(daftarPakan.getNamaPakan());
        textViewJumlah.setText(daftarPakan.getKapasitasPakan()+"kg");
        return listViewItem;
    }
}