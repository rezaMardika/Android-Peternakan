package com.example.root.owner;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16/09/17.
 */

public class Jadwal_List extends ArrayAdapter<Data_Jadwal>{

    private Activity context;
    private List<Data_Jadwal> jadwalList;

    public  Jadwal_List(Activity context, List<Data_Jadwal> jadwalList){
        super(context, R.layout.list_jadwal, jadwalList);
        this.context = context;
        this.jadwalList = jadwalList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_jadwal, null, true);

        TextView textViewNama = (TextView) listViewItem.findViewById(R.id.textViewNama);
        TextView textViewBanyak = (TextView) listViewItem.findViewById(R.id.textViewBanyak);
        TextView textViewJam = (TextView) listViewItem.findViewById(R.id.textViewJam);

        Data_Jadwal data_jadwal = jadwalList.get(position);

        textViewNama.setText(data_jadwal.getNamaP());
        textViewBanyak.setText(data_jadwal.getBanyakP());
        textViewJam.setText(data_jadwal.getJamp());

        return listViewItem;
    }
}
