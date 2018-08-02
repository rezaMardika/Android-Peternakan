package com.example.root.owner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 14/09/17.
 */

public class Hewan_List extends RecyclerView.Adapter<Hewan_List.ViewHolder> {

    private List<Data_Hewan> hewans;
    private int rowLayout;
    private Context context;

    public static final String KANDANG_ID = "kandangId";
    public static final String HEWAN_ID = "code";
    public static final String HEWAN_CODE = "hewanId";
    public static final String HEWAN_BOBOT = "hewanBobot";
    public static final String HEWAN_GENDER = "hewanGener";
    public static final String HEWAN_NAMA = "nama";
    public static final String HEWAN_URL = "url";
    public static final String HEWAN_STATUS = "status";

    public Hewan_List(List<Data_Hewan> hewans, int rowLayout, Context context) {
        this.hewans = hewans;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        //ViewHolder viewHolder = new ViewHolder(v);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Data_Hewan hewan1 = hewans.get(position);

        String uri = hewan1.getUrl();
        String status = hewan1.getStatus();
        holder.textViewCode.setText(hewan1.getHewanId());
        holder.textViewGender.setText(hewan1.getHewanGender());
        holder.textViewPenyakit.setText(hewan1.getStatus());

        //getImage(holder.imageViewHewan, uri);
        String status1="sehat";
        if(status1.equals(status)){
            //getDeases(holder.imageViewHewan);
            getImage(holder.imageViewHewan, uri);
        }
        else {
            //getImage(holder.imageViewHewan, uri);
            getDeases(holder.imageViewHewan);
        }
    }

    public void getImage(final ImageView imageViewHewan, String uri){
        Glide.with(context)
                .load(uri)
                .placeholder(R.drawable.ic_menu_camera)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(imageViewHewan);

    }

    @Override
    public int getItemCount() {
        return hewans == null ? 0 : hewans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewCode;
        public TextView textViewGender;
        public TextView textViewPenyakit;
        public ImageView imageViewHewan;
        //public TextView textViewOption;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewCode = (TextView) itemView.findViewById(R.id.textViewCode);
            textViewGender = (TextView) itemView.findViewById(R.id.textViewGender);
            textViewPenyakit = (TextView) itemView.findViewById(R.id.textViewPenyakit);
            imageViewHewan = (ImageView) itemView.findViewById(R.id.imageViewHewan);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intentK = ((Activity) context).getIntent();
                    final String idK = intentK.getStringExtra(ActivityHewan.KANDANG_ID);

                    Context context = v.getContext();
                    Data_Hewan hewan = hewans.get(position);
                    Intent intent = new Intent(context, ActivityProfil_Sapi.class);
                    intent.putExtra(KANDANG_ID, idK);
                    intent.putExtra(HEWAN_ID, hewan.getCode());
                    intent.putExtra(HEWAN_CODE, hewan.getHewanId());
                    intent.putExtra(HEWAN_BOBOT, hewan.getHewanBobot());
                    intent.putExtra(HEWAN_GENDER, hewan.getHewanGender());
                    intent.putExtra(HEWAN_URL, hewan.getUrl());
                    intent.putExtra(HEWAN_STATUS, hewan.getStatus());
                    context.startActivity(intent);
                }
            });

        }
    }

    /*private Activity context;
    private List<Data_Hewan> hewans;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference reference = FirebaseStorage.getInstance().getReference();
    final ArrayList<Uri> uris = new ArrayList<>();
    private static final String FB_STORAGE_PATH = " image/";

    public  Hewan_List(Activity context, List<Data_Hewan> hewans){
        super(context, R.layout.list_hewan, hewans);
        this.context = context;
        this.hewans = hewans;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_hewan, null, true);

        TextView textViewGender = (TextView) listViewItem.findViewById(R.id.textViewGender);
        TextView textViewBobot = (TextView) listViewItem.findViewById(R.id.textViewBobot);
        TextView textViewCode = (TextView) listViewItem.findViewById(R.id.textViewCode);
        ImageView imageViewHewan = (ImageView) listViewItem.findViewById(R.id.imageViewHewan);

        Data_Hewan hewan = hewans.get(position);

        textViewCode.setText(hewan.getHewanId());
        textViewGender.setText(hewan.getHewanGender());
        textViewBobot.setText(hewan.getHewanBobot());

        String status = hewan.getStatus();
        String uri = hewan.getUrl();
        getImage(position, imageViewHewan, uri);
        String status1="sakit";
        if(status1.equals(status)){
            listViewItem.setBackgroundColor(Color.RED);
        }
        return listViewItem;
    }*/


    public void getDeases(ImageView imageViewSakit){
        Glide.with(context)
                .load(R.drawable.ic_pmi)
                .placeholder(R.drawable.ic_menu_camera)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(imageViewSakit);
    }
}
