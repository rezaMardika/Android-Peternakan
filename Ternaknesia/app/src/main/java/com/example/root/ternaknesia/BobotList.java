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
 * Created by root on 18/07/17.
 */

public class BobotList extends ArrayAdapter<DaftarBobot>{

    private Activity context;
    private List<DaftarBobot> bobotList;

    public BobotList(Activity context, List<DaftarBobot> bobotList){
        super(context, R.layout.list_bobot, bobotList);
        this.context = context;
        this.bobotList = bobotList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_bobot, null, true);

        TextView textViewId = (TextView) listViewItem.findViewById(R.id.textViewId);
        TextView textViewBobot = (TextView) listViewItem.findViewById(R.id.textViewBobot);
        TextView textViewTanggal = (TextView) listViewItem.findViewById(R.id.textViewTanggal);

        DaftarBobot daftarBobot = bobotList.get(position);

        textViewId.setText(daftarBobot.getIdB());
        textViewBobot.setText(daftarBobot.getBobotB());
        textViewTanggal.setText(daftarBobot.getDateB());

        return listViewItem;
    }

}
