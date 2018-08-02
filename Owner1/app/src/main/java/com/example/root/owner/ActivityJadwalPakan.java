package com.example.root.owner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityJadwalPakan extends AppCompatActivity {

    private DatabaseReference databaseJadwal;
    private ListView listViewJadwal;
    private TextView textViewJadwal;
    private List<Data_Jadwal>jadwalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_pakan);

        Intent intent = getIntent();
        String id = intent.getStringExtra(ActivityProfil_Sapi.HEWAN_ID);
        String code = intent.getStringExtra(ActivityProfil_Sapi.HEWAN_CODE);

        databaseJadwal = FirebaseDatabase.getInstance().getReference("jadwal").child(id);
        textViewJadwal = (TextView) findViewById(R.id.textViewJadwal);
        listViewJadwal = (ListView) findViewById(R.id.listViewJadwal);

        jadwalList = new ArrayList<>();
        textViewJadwal.setText("Hewan "+code);
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseJadwal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jadwalList.clear();

                for(DataSnapshot jadwalSnapshot : dataSnapshot.getChildren()){
                    Data_Jadwal jadwal = jadwalSnapshot.getValue(Data_Jadwal.class);
                    jadwalList.add(jadwal);
                }

                Jadwal_List adapter = new Jadwal_List(ActivityJadwalPakan.this, jadwalList);
                listViewJadwal.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
