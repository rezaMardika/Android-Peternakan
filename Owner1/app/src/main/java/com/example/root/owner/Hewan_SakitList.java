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
 * Created by reza on 21/12/17.
 */

public class Hewan_SakitList extends ArrayAdapter<Data_Penyakit> {

    private Activity context;
    private List<Data_Penyakit> deasesList;

    public Hewan_SakitList(Activity context, List<Data_Penyakit> deasesList){
        super(context, R.layout.list_pakan, deasesList);
        this.context = context;
        this.deasesList = deasesList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_hewan_sakit, null, true);

        TextView textViewNamaPenyakit = (TextView) listViewItem.findViewById(R.id.textViewNamaPenyakit);
        TextView textViewNamaHewan = (TextView) listViewItem.findViewById(R.id.textViewNamaHewan);
        TextView textViewBobot = (TextView) listViewItem.findViewById(R.id.textViewBobot);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDate);

        Data_Penyakit penyakit = deasesList.get(position);

        textViewNamaPenyakit.setText(penyakit.getNamaP());
        textViewNamaHewan.setText(penyakit.getNamaH());
        textViewBobot.setText(penyakit.getBobotP());
        textViewDate.setText(penyakit.getDateP());
        return listViewItem;
    }
}
