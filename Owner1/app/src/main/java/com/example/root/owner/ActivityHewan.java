package com.example.root.owner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityHewan extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private DatabaseReference databaseHewan;
    private DatabaseReference databaseJadwal;
    private DatabaseReference databaseRiwayat;
    private DatabaseReference databasePenyakit;
    private DatabaseReference databaseBobot;
    private DatabaseReference databasePakan;

    private Button buttonTambah;
    private TextView textViewHewan;
    private TextView textViewKapasitas;

    private EditText editTextCode;
    private EditText editTextBobot;
    private Spinner spinnerJenis;
    private EditText search_box;
    private Button buttonSearch;

    private RecyclerView recyclerViewHewan;
    private RecyclerView.Adapter adapter;

    List<Data_Hewan> hewans;
    List<Data_Kandang>kandangList;

    public static final String KANDANG_ID = "kandangId";
    public static final String HEWAN_ID = "code";
    public static final String HEWAN_CODE = "hewanId";
    public static final String HEWAN_BOBOT = "hewanBobot";
    public static final String HEWAN_GENDER = "hewanGener";
    public static final String HEWAN_NAMA = "nama";
    public static final String HEWAN_URL = "url";
    public static final String HEWAN_STATUS = "status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Daftar Hewan");
        setContentView(R.layout.activity_hewan);

        textViewHewan = (TextView) findViewById(R.id.textViewHewan);
        //buttonTambah = (Button) findViewById(R.id.buttonTambah);
        search_box = (EditText) findViewById(R.id.search_box);
        buttonSearch = (Button) findViewById(R.id.button_search);
        Intent intent = getIntent();

        hewans = new ArrayList<>();
        final String id = intent.getStringExtra(ActivityBeranda.KANDANG_ID);
        String nama = intent.getStringExtra(ActivityBeranda.KANDANG_NAME);

        recyclerViewHewan = (RecyclerView) findViewById(R.id.recyclerHewan);
        recyclerViewHewan.setHasFixedSize(true);
        recyclerViewHewan.setLayoutManager(new LinearLayoutManager(this));

        textViewHewan.setText(nama);

        databaseHewan = FirebaseDatabase.getInstance().getReference("hewan").child(id);


        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String cari = search_box.getText().toString().trim();

                searchData(cari);
            }
        });
        /*buttonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveHewan();
                Intent intent = new Intent(getApplicationContext(), ActivityAddHewan.class);
                intent.putExtra(KANDANG_ID, id);
                startActivity(intent);
            }
        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseHewan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hewans = new ArrayList<Data_Hewan>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Data_Hewan data_hewan = dataSnapshot1.getValue(Data_Hewan.class);
                    hewans.add(data_hewan);
                }
                Hewan_List adapter = new Hewan_List(hewans, R.layout.list_hewan, ActivityHewan.this);
                RecyclerView.LayoutManager manager = new GridLayoutManager(ActivityHewan.this, 1);
                //RecyclerView.LayoutManager manager = new GridLayoutManager(ActivityHewan.this, 2);
                recyclerViewHewan.setLayoutManager(manager);
                recyclerViewHewan.setItemAnimator(new DefaultItemAnimator());
                recyclerViewHewan.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void searchData(String cari){
        Query searchQuery = databaseHewan.orderByChild("hewanId").startAt(cari).endAt(cari+"\uf8ff");

        searchQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hewans.clear();

                for(DataSnapshot potongSnapshot : dataSnapshot.getChildren()){
                    Data_Hewan hewan = potongSnapshot.getValue(Data_Hewan.class);
                    hewans.add(hewan);
                }
                Hewan_List adapter = new Hewan_List(hewans, R.layout.list_hewan, ActivityHewan.this);
                RecyclerView.LayoutManager manager = new GridLayoutManager(ActivityHewan.this, 1);
                recyclerViewHewan.setLayoutManager(manager);
                recyclerViewHewan.setItemAnimator(new DefaultItemAnimator());
                recyclerViewHewan.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Data_Hewan hewan = hewans.get(position);

        Intent intentK = getIntent();
        final String idK = intentK.getStringExtra(ActivityHewan.KANDANG_ID);

        Intent intent3 = new Intent(getApplicationContext(), ActivityProfil_Sapi.class);

        intent3.putExtra(KANDANG_ID, idK);
        intent3.putExtra(HEWAN_ID, hewan.getCode());
        intent3.putExtra(HEWAN_CODE, hewan.getHewanId());
        intent3.putExtra(HEWAN_BOBOT, hewan.getHewanBobot());
        intent3.putExtra(HEWAN_GENDER, hewan.getHewanGender());
        intent3.putExtra(HEWAN_URL, hewan.getUrl());
        intent3.putExtra(HEWAN_STATUS, hewan.getStatus());
        startActivity(intent3);
    }
}
