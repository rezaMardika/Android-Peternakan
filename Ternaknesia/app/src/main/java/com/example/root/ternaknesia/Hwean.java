package com.example.root.ternaknesia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fasterxml.jackson.core.JsonParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.*;
import org.json.JSONArray;
import org.json.simple.parser.JSONParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Hwean extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private DatabaseReference databaseHewan;
    private DatabaseReference databaseJadwal;
    private DatabaseReference databaseRiwayat;
    private DatabaseReference databasePenyakit;
    private DatabaseReference databaseBobot;
    private DatabaseReference databasePakan;

    private RecyclerView recyclerViewHewan;
    //private ListView listViewHewan;
    private Button buttonTambah;
    private TextView textViewHewan;
    private TextView textViewKapasitas;

    private EditText editTextCode;
    private EditText editTextBobot, search_box;
    private Spinner spinnerJenis;
    private Button buttonCari;

    List<DaftarHewan> hewans;
    List<DaftarKandang>kandangList;

    public static final String KANDANG_ID = "kandangId";
    public static final String HEWAN_ID = "code";
    public static final String HEWAN_CODE = "hewanId";
    public static final String HEWAN_BOBOT = "hewanBobot";
    public static final String HEWAN_GENDER = "hewanGener";
    public static final String HEWAN_TANGGAL = "tanggal";
    public static final String HEWAN_URL = "url";
    public static final String HEWAN_STATUS = "status";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hwean);

        textViewHewan = (TextView) findViewById(R.id.textViewHewan);
        //listViewHewan = (ListView) findViewById(R.id.listViewHewan);
        recyclerViewHewan = (RecyclerView) findViewById(R.id.recyclerViewHewan);
        search_box = (EditText) findViewById(R.id.search_box);
        buttonCari = (Button) findViewById(R.id.button_search);

        Intent intent = getIntent();
        hewans = new ArrayList<>();
        final String id = intent.getStringExtra(Beranda1.KANDANG_ID);
        String nama = intent.getStringExtra(Beranda1.KANDANG_NAME);

        //textViewHewan.setText(nama);

        databaseHewan = FirebaseDatabase.getInstance().getReference("hewan").child(id);

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TambahHewan.class);
                intent.putExtra(KANDANG_ID, id);
                startActivity(intent);
            }
        });

        buttonCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = search_box.getText().toString().trim();

                searchData(search);
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
                    DaftarHewan hewan = potongSnapshot.getValue(DaftarHewan.class);
                    hewans.add(hewan);
                }
                /*Potong_List adapter = new Potong_List(ActivityPotong.this, R.layout.list_potong, potongList);
                listViewPotong.setAdapter(adapter);*/
                HewanList adapter = new HewanList(hewans, R.layout.list_hewan, Hwean.this);
                RecyclerView.LayoutManager manager = new GridLayoutManager(Hwean.this, 1);
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
    protected void onStart() {
        super.onStart();

        databaseHewan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hewans = new ArrayList<DaftarHewan>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    DaftarHewan hewan = dataSnapshot1.getValue(DaftarHewan.class);
                    hewans.add(hewan);
                }
                HewanList adapter = new HewanList(hewans, R.layout.list_hewan, Hwean.this);
                RecyclerView.LayoutManager manager = new GridLayoutManager(Hwean.this, 1);
                recyclerViewHewan.setLayoutManager(manager);
                recyclerViewHewan.setItemAnimator(new DefaultItemAnimator());
                recyclerViewHewan.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*databaseHewan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hewans.clear();

                for (DataSnapshot hewanSnapshot : dataSnapshot.getChildren()){
                    DaftarHewan daftarHewan = hewanSnapshot.getValue(DaftarHewan.class);
                    hewans.add(daftarHewan);
                }

                HewanList adapter = new HewanList(Hwean.this, hewans);
                listViewHewan.setAdapter(adapter);

                //registerForContextMenu(listViewHewan);
                //listViewHewan.setOnItemClickListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_context_menu1, menu);

        Intent intent = getIntent();
        final String id = intent.getStringExtra(Beranda1.KANDANG_ID);
        intent.putExtra(KANDANG_ID, id);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.openContextMenu(view);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int id = item.getItemId();

        switch (id){
            case R.id.update_id:
                DaftarHewan daftarHewan =  hewans.get(info.position);


                //updateDialogHewan(daftarHewan.getCode(), daftarHewan.getHewanId(), daftarHewan.getHewanGender(), daftarHewan.getNama(), daftarHewan.getUrl(), daftarHewan.getStatus(), Uid);
               break;
            case R.id.pakan_id:
                DaftarHewan daftarHewan1  = hewans.get(info.position);
                Intent intent2 = new Intent(getApplicationContext(), PakanHw.class);
                intent2.putExtra(HEWAN_ID, daftarHewan1.getCode());
                startActivity(intent2);
                //dialogPakanHewan(daftarHewan1.getCode(), daftarHewan1.getHewanBobot());
               break;
            case R.id.jadwal_id:
                DaftarHewan daftarHewan2  = hewans.get(info.position);
                Intent intent = new Intent(getApplicationContext(), Pakan.class);
                intent.putExtra(HEWAN_ID, daftarHewan2.getCode());
                startActivity(intent);
                break;
            case R.id.riwayat_id:
                final DaftarHewan daftarHewan3 = hewans.get(info.position);
                Intent intent1 = new Intent(getApplicationContext(), BobotActivity.class);
                intent1.putExtra(HEWAN_ID, daftarHewan3.getCode());
                intent1.putExtra(HEWAN_CODE, daftarHewan3.getHewanId());
                startActivity(intent1);
                break;
            case R.id.penyakit_id:
                final DaftarHewan daftarHewan4 = hewans.get(info.position);
                //penyakitHewan(daftarHewan4.getCode(), daftarHewan4.getHewanId(), daftarHewan4.getHewanBobot(), daftarHewan4.getHewanGender(), daftarHewan4.getNama(), daftarHewan4.getUrl(), daftarHewan4.getStatus());

                break;
            case R.id.potong_id:
                final DaftarHewan daftarHewan5 = hewans.get(info.position);
                //potongHewan(daftarHewan5.getCode(), daftarHewan5.getHewanId(), daftarHewan5.getHewanGender(), daftarHewan5.getHewanBobot(), daftarHewan5.getUrl());
                break;
            case R.id.profil_id:;
                final DaftarHewan daftarHewan6 = hewans.get(info.position);

                Intent intentK = getIntent();
                final String idK = intentK.getStringExtra(Hwean.KANDANG_ID);

                Intent intent3 = new Intent(getApplicationContext(), Profil_Sapi.class);

                intent3.putExtra(KANDANG_ID, idK);
                intent3.putExtra(HEWAN_ID, daftarHewan6.getCode());
                intent3.putExtra(HEWAN_CODE, daftarHewan6.getHewanId());
                intent3.putExtra(HEWAN_BOBOT, daftarHewan6.getHewanBobot());
                intent3.putExtra(HEWAN_GENDER, daftarHewan6.getHewanGender());
                intent3.putExtra(HEWAN_URL, daftarHewan6.getUrl());
                intent3.putExtra(HEWAN_STATUS, daftarHewan6.getStatus());
                startActivity(intent3);
                break;
        }
        return true;
    }
}