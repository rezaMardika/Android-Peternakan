package com.example.root.owner;

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
 * Created by root on 16/09/17.
 */

public class Riwayat_List extends ArrayAdapter<Data_Riwayat> {

    private Activity context;
    private List<Data_Riwayat> riwayatList;

    public  Riwayat_List(Activity context, List<Data_Riwayat> riwayatList){
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

        Data_Riwayat riwayat = riwayatList.get(position);

        textViewId.setText(riwayat.getIdB());
        textViewBobot.setText(riwayat.getBobotB());
        textViewTanggal.setText(riwayat.getDateB());

        return listViewItem;
    }

}
