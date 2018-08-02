package com.example.root.ternaknesia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 04/06/17.
 */

public class KandangList extends RecyclerView.Adapter<KandangList.ViewHolder>{

    private List<DaftarKandang> kandangList;
    private Context context;
    private int rowLayout;
    public static final String KANDANG_NAME = "namaKandang";
    public static final String KANDANG_ID = "kandangId";

    public KandangList(List<DaftarKandang> kandangList, int rowLayout, Context context) {
        this.kandangList = kandangList;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        //ViewHolder viewHolder = new ViewHolder(v);
        //return viewHolder;
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        DaftarKandang kandang = kandangList.get(position);

        final String id = kandang.getKandangId();
        DatabaseReference dbKandang = FirebaseDatabase.getInstance().getReference("hewan");

        dbKandang.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int jum = (int) dataSnapshot.getChildrenCount();
                holder.textViewKapasitas.setText("Jumlah Hewan : "+Integer.toString(jum));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.textViewNama.setText("Kadang : "+kandang.getNamaKandang());

        //holder.textViewKapasitas.setText(kandang.getKapasitasKandang());
    }

    @Override
    public int getItemCount() {
        /*int arr = 0;

        try{
            if(kandangList.size() == 0){
                arr = 0;
            }else {
                arr = kandangList.size();
            }
        }catch (Exception e){

        }
        return arr;*/
        return kandangList == null ? 0 : kandangList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNama;
        public TextView textViewKapasitas;
        public ImageView imageViewKandang;
        public ViewHolder(View itemView) {
            super(itemView);

            textViewNama = (TextView) itemView.findViewById(R.id.textViewNama);
            textViewKapasitas = (TextView) itemView.findViewById(R.id.textViewKapasitas);
            imageViewKandang = (ImageView) itemView.findViewById(R.id.imageViewKandang);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Context context = v.getContext();
                    DaftarKandang kandang = kandangList.get(position);
                    Intent intent = new Intent(context, Hwean.class);
                    intent.putExtra(KANDANG_ID, kandang.getKandangId());
                    intent.putExtra(KANDANG_NAME, kandang.getNamaKandang());
                    context.startActivity(intent);
                }
            });

            imageViewKandang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position1 = getAdapterPosition();

                    Context context1 = v.getContext();
                    DaftarKandang daftarKandang = kandangList.get(position1);
                    Intent intent = new Intent(context1, Hwean.class);
                    intent.putExtra(KANDANG_ID, daftarKandang.getKandangId());
                    intent.putExtra(KANDANG_NAME, daftarKandang.getNamaKandang());
                    context.startActivity(intent);
                }
            });
        }
    }



    /*private Activity context;
    private List<DaftarKandang> kandangList;

    public KandangList(Activity context, List<DaftarKandang> kandangList){
        super(context, R.layout.list_layout, kandangList);
        this.context = context;
        this.kandangList = kandangList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        final TextView textViewNama = (TextView) listViewItem.findViewById(R.id.textViewNama);
        final TextView textViewKapasitas = (TextView) listViewItem.findViewById(R.id.textViewKapasitas);

        DaftarKandang daftarKandang = kandangList.get(position);

        final String id = daftarKandang.getKandangId();
        DatabaseReference dbKandang = FirebaseDatabase.getInstance().getReference("hewan");

        dbKandang.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int jum = (int) dataSnapshot.getChildrenCount();
                textViewKapasitas.setText(Integer.toString(jum));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        textViewNama.setText(daftarKandang.getNamaKandang());
        return listViewItem;
    }*/

}