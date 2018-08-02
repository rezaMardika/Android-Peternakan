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

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by root on 14/09/17.
 */

public class Kandang_List extends RecyclerView.Adapter<Kandang_List.ViewHolder> {

    private List<Data_Kandang> kandangList;
    private Context context;
    private int rowLayout;
    public static final String nama_kandang = "namaKandang";
    public static final String id_kandang = "kandangId";

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
                holder.textViewKapasitas.setText("Hewan: "+Integer.toString(jmlh));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.textViewNama.setText("Nama: "+data_kandang.getNamaKandang());
    }

    @Override
    public int getItemCount() {
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

}
