package com.example.root.ternaknesia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Pakan extends AppCompatActivity {

    private DatabaseReference databasePakan;
    private TextView textViewJudul;
    private ListView listViewJadwal;

    List<DaftarJadwal> jadwals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pakan);

        textViewJudul = (TextView) findViewById(R.id.textViewJudul);
        listViewJadwal = (ListView) findViewById(R.id.listViewJadwal);

        Intent intent = getIntent();

        jadwals = new ArrayList<>();

        String id = intent.getStringExtra(Hwean.HEWAN_ID);
        String code = intent.getStringExtra(Hwean.HEWAN_CODE);

        textViewJudul.setText("Jadwal pakan hewan "+code);

        databasePakan = FirebaseDatabase.getInstance().getReference("jadwal").child(id);

    }

    @Override
    protected void onStart() {
        super.onStart();

        databasePakan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jadwals.clear();

                for(DataSnapshot jadwalSnapshot : dataSnapshot.getChildren()){
                    DaftarJadwal daftarJadwal = jadwalSnapshot.getValue(DaftarJadwal.class);
                    jadwals.add(daftarJadwal);
                }
                JadwalList adapter = new JadwalList(Pakan.this, jadwals);
                listViewJadwal.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
