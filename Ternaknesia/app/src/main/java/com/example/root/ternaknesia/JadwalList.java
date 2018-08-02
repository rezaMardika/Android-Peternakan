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
 * Created by root on 07/07/17.
 */

public class JadwalList extends ArrayAdapter<DaftarJadwal>{

    private Activity context;
    private List<DaftarJadwal> jadwals;

    public  JadwalList(Activity context, List<DaftarJadwal> jadwals){
        super(context, R.layout.list_jadwal, jadwals);
        this.context = context;
        this.jadwals = jadwals;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_jadwal, null, true);

        TextView textViewJam = (TextView) listViewItem.findViewById(R.id.textViewJam);
        TextView textViewNama = (TextView) listViewItem.findViewById(R.id.textViewNama);

        DaftarJadwal daftarJadwal = jadwals.get(position);

        textViewJam.setText(daftarJadwal.getJamp());
        textViewNama.setText(daftarJadwal.getNamaP());

        return listViewItem;
    }

}
