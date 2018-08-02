package com.example.root.owner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityPenyakit extends AppCompatActivity {

    private List<Daftar_Penyakit> penyakitList;
    private RecyclerView recyclerViewPenyakit;
    private DatabaseReference databasePenyakit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyakit);

        databasePenyakit = FirebaseDatabase.getInstance().getReference("daftar_penyakit");
        penyakitList = new ArrayList<>();

        recyclerViewPenyakit = (RecyclerView) findViewById(R.id.recyclerViewPenyakit);
        recyclerViewPenyakit.setHasFixedSize(true);
        recyclerViewPenyakit.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        databasePenyakit.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                penyakitList = new ArrayList<Daftar_Penyakit>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Daftar_Penyakit penyakit = snapshot.getValue(Daftar_Penyakit.class);
                    penyakitList.add(penyakit);
                }
                Penyakit_List adapter = new Penyakit_List(penyakitList, R.layout.list_penyakit, ActivityPenyakit.this);
                RecyclerView.LayoutManager manager = new GridLayoutManager(ActivityPenyakit.this, 1);
                recyclerViewPenyakit.setLayoutManager(manager);
                recyclerViewPenyakit.setItemAnimator(new DefaultItemAnimator());
                recyclerViewPenyakit.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
