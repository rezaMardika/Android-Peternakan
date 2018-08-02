package com.example.root.ternaknesia;

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
 * Created by root on 14/07/17.
 */

public class PenyakitList extends RecyclerView.Adapter<PenyakitList.ViewHolder> {

    private List<DaftarSakit> penyakitList;
    private Context context;
    private int rowLayout;

    public static final String DEASES_NAME = "namaPenyakit";
    public static final String DEASES_ID = "idPenyakit";

    public PenyakitList(List<DaftarSakit> penyakitList, int rowLayout, Context context) {
        this.penyakitList = penyakitList;
        this.context = context;
        this.rowLayout = rowLayout;
    }

    @Override
    public PenyakitList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PenyakitList.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PenyakitList.ViewHolder holder, int position) {
        DaftarSakit penyakit = penyakitList.get(position);

        final String id = penyakit.getIdPenyakit();
        DatabaseReference dbPenyakit = FirebaseDatabase.getInstance().getReference("penyakit");

        dbPenyakit.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int jum = (int) dataSnapshot.getChildrenCount();
                holder.textViewJumlah.setText(Integer.toString(jum));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.textViewNama.setText(penyakit.getNamaPenyakit());
    }

    @Override
    public int getItemCount() {
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
                    DaftarSakit penyakit = penyakitList.get(position);
                    Intent intent = new Intent(context, HewanSakit.class);
                    intent.putExtra(DEASES_ID, penyakit.getIdPenyakit());
                    intent.putExtra(DEASES_NAME, penyakit.getNamaPenyakit());
                    context.startActivity(intent);
                }
            });
        }
    }
}
