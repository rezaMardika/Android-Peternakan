package com.example.root.owner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by reza on 01/10/17.
 */

public class Penyakit_List extends RecyclerView.Adapter<Penyakit_List.ViewHolder>{

    private Context context;
    private List<Daftar_Penyakit> penyakitList;
    private int rowLayout;
    public static final String nama_penyakit = "namaPenyakit";
    public static final String id_Penyakit = "penyakitId";


    public  Penyakit_List(List<Daftar_Penyakit> penyakitList, int rowLayout, Context context){
        this.penyakitList = penyakitList;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Daftar_Penyakit daftar_penyakit = penyakitList.get(position);

        final String id = daftar_penyakit.getIdPenyakit(); //kapasitas
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("penyakit");

        databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int jumlah = (int) dataSnapshot.getChildrenCount();
                holder.textViewJumlah.setText(Integer.toString(jumlah));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        holder.textViewNama.setText(daftar_penyakit.getNamaPenyakit());
    }

    @Override
    public int getItemCount() {
        int a = 0;

        try{
            if(penyakitList.size() == 0){
                a = 0;
            }else {
                a = penyakitList.size();
            }
        }catch (Exception e){

        }

        return penyakitList == null ? 0 : penyakitList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNama;
        public TextView textViewJumlah;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewNama = (TextView) itemView.findViewById(R.id.textViewNama);
            textViewJumlah = (TextView) itemView.findViewById(R.id.textViewJumlah);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    Context context = v.getContext();
                    Daftar_Penyakit daftar_penyakit = penyakitList.get(position);
                    Intent intent = new Intent(context, ActivityHewanSakit.class);
                    intent.putExtra(id_Penyakit, daftar_penyakit.getIdPenyakit());
                    intent.putExtra(nama_penyakit, daftar_penyakit.getNamaPenyakit());
                    context.startActivity(intent);

                }
            });

        }
    }

    /*
     public Kandang_List(List<Data_Kandang> kandangList, int rowLayout, Context context) {
        this.kandangList = kandangList;
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
    public void onBindViewHolder(final Kandang_List.ViewHolder holder, int position) {
        Data_Kandang data_kandang = kandangList.get(position);

        final String id = data_kandang.getKandangId(); //kapasitas
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("hewan");

        databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int jmlh = (int) dataSnapshot.getChildrenCount();
                holder.textViewKapasitas.setText(Integer.toString(jmlh));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.textViewNama.setText(data_kandang.getNamaKandang());
    }

    @Override
    public int getItemCount() {
    int a = 0;

        try{
            if(kandangList.size() == 0){
                a = 0;
            }else {
                a = kandangList.size();
            }
        }catch (Exception e){

        }

        return a;
        return kandangList == null ? 0 : kandangList.size();
}

public class ViewHolder extends RecyclerView.ViewHolder{

    public TextView textViewNama;
    public TextView textViewKapasitas;


    public ViewHolder(View itemView) {
        super(itemView);

        textViewNama = (TextView) itemView.findViewById(R.id.textViewNama);
        textViewKapasitas = (TextView) itemView.findViewById(R.id.textViewKapasitas);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = getAdapterPosition();

                Context context = v.getContext();
                Data_Kandang kandang = kandangList.get(position);
                Intent intent = new Intent(context, ActivityHewan.class);
                intent.putExtra(id_kandang, kandang.getKandangId());
                intent.putExtra(nama_kandang, kandang.getNamaKandang());
                context.startActivity(intent);

            }
        });

    }
}
     */
}
