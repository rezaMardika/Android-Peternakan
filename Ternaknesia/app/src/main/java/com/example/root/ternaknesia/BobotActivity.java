package com.example.root.ternaknesia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BobotActivity extends AppCompatActivity {

    private DatabaseReference databaseBobot;
    private TextView textViewJudul;
    private ListView listViewBobot;
    private Button buttonChart;

    private List<DaftarBobot>bobotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bobot);

        textViewJudul = (TextView) findViewById(R.id.textViewJudul);
        listViewBobot = (ListView) findViewById(R.id.listViewBobot);
        buttonChart = (Button) findViewById(R.id.buttonChart);

        Intent intent = getIntent();

        bobotList = new ArrayList<>();

        String code = intent.getStringExtra(Hwean.HEWAN_ID);
        String id = intent.getStringExtra(Hwean.HEWAN_CODE);

        textViewJudul.setText("Riwayat Hewan"+id);

        databaseBobot = FirebaseDatabase.getInstance().getReference("bobot").child(id);

        buttonChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChartActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseBobot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bobotList.clear();

                for (DataSnapshot bobotSnapshot : dataSnapshot.getChildren()){
                    DaftarBobot daftarBobot = bobotSnapshot.getValue(DaftarBobot.class);
                    bobotList.add(daftarBobot);
                }
                BobotList adapter = new BobotList(BobotActivity.this, bobotList);
                listViewBobot.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

