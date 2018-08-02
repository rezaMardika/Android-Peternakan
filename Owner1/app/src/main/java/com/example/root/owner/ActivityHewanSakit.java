package com.example.root.owner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityHewanSakit extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private DatabaseReference databaseDeases, databaseHewan;
    private TextView textViewHewanSakit;
    private ListView listViewPenyakit;

    List<Data_Penyakit> deasesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hewan_sakit);

        textViewHewanSakit = (TextView) findViewById(R.id.textViewHewanSakit);
        listViewPenyakit = (ListView) findViewById(R.id.listViewPenyakit);
        Intent intent = getIntent();

        deasesList = new ArrayList<>();

        String nama = intent.getStringExtra(Penyakit_List.nama_penyakit);
        String id = intent.getStringExtra(Penyakit_List.id_Penyakit);

        textViewHewanSakit.setText("List Penyakit Hewan "+nama);

        databaseDeases = FirebaseDatabase.getInstance().getReference("penyakit").child(id);

        listViewPenyakit.setOnItemClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseDeases.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                deasesList.clear();

                for(DataSnapshot deasesSnapshot : dataSnapshot.getChildren()){
                    Data_Penyakit penyakit = deasesSnapshot.getValue(Data_Penyakit.class);
                    deasesList.add(penyakit);
                }
                Hewan_SakitList adapter = new Hewan_SakitList(ActivityHewanSakit.this, deasesList);
                listViewPenyakit.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
