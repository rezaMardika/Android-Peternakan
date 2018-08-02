package com.example.root.owner;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by root on 15/09/17.
 */

public class Peternak_List extends ArrayAdapter<DaftarPeternak> {

    private Activity context;
    private List<DaftarPeternak> peternakList;

    public Peternak_List(Activity context, List<DaftarPeternak> peternakList){
        super(context, R.layout.list_peternak, peternakList);
        this.context = context;
        this.peternakList = peternakList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_peternak, null, true);

        final TextView textViewNama = (TextView) listViewItem.findViewById(R.id.textViewNama);
        final TextView textViewAlamat = (TextView) listViewItem.findViewById(R.id.textViewAlamat);
        final ImageView imageViewPeternak = (ImageView) listViewItem.findViewById(R.id.imageViewPeternak);

        DaftarPeternak peternak = peternakList.get(position);

        textViewNama.setText(peternak.getUserName());
        textViewAlamat.setText(peternak.getUserAddress());

        String uri = peternak.getUserFoto();
        getImage(position, imageViewPeternak, uri);
        return listViewItem;
    }

    public void getImage(Integer position, final ImageView imageViewHewan, String uri){

        Glide.with(getContext())
                .load(uri)
                .placeholder(R.drawable.ic_menu_camera)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(imageViewHewan);
    }
}
