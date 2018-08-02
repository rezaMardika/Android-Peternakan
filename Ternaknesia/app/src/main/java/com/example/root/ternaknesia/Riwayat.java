package com.example.root.ternaknesia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Riwayat extends AppCompatActivity {

    private DatabaseReference databaseRiwayat;

    private RecyclerView recyclerViewRiwyat;

    List<DaftarRiwayat> riwayatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        recyclerViewRiwyat = (RecyclerView) findViewById(R.id.recyclerViewRiwayat);

        riwayatList = new ArrayList<>();

        databaseRiwayat = FirebaseDatabase.getInstance().getReference("riwayat");
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseRiwayat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot riwayatSnapshot : dataSnapshot.getChildren()){
                    DaftarRiwayat daftarRiwayat = riwayatSnapshot.getValue(DaftarRiwayat.class);
                    riwayatList.add(daftarRiwayat);
                }

                RiwayatList adapter = new RiwayatList(riwayatList, R.layout.list_riwayat, Riwayat.this);
                RecyclerView.LayoutManager manager = new GridLayoutManager(Riwayat.this, 1);
                recyclerViewRiwyat.setLayoutManager(manager);
                recyclerViewRiwyat.setItemAnimator(new DefaultItemAnimator());
                recyclerViewRiwyat.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
