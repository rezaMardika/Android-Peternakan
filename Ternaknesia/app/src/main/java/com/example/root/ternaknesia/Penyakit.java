package com.example.root.ternaknesia;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Penyakit extends AppCompatActivity {

    //implements AdapterView.OnItemClickListener
    private DatabaseReference databasePenyakit;
    private DatabaseReference databaseHewan;

    private RecyclerView recyclerViewPenyakit;
    private List<DaftarSakit> penyakitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyakit);

        recyclerViewPenyakit = (RecyclerView) findViewById(R.id.recyclerViewPenyakit);
        recyclerViewPenyakit.setHasFixedSize(true);
        recyclerViewPenyakit.setLayoutManager(new LinearLayoutManager(this));

        penyakitList = new ArrayList<>();

        databasePenyakit = FirebaseDatabase.getInstance().getReference("daftar_penyakit");

        //listViewPenyakit.setOnItemClickListener(this);
    }



    @Override
    protected void onStart() {
        super.onStart();

        databasePenyakit.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                penyakitList = new ArrayList<DaftarSakit>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    DaftarSakit penyakit = dataSnapshot1.getValue(DaftarSakit.class);
                    penyakitList.add(penyakit);
                }
                PenyakitList adapter = new PenyakitList(penyakitList, R.layout.list_penyakit, Penyakit.this);
                RecyclerView.LayoutManager manager = new GridLayoutManager(Penyakit.this, 1);
                recyclerViewPenyakit.setLayoutManager(manager);
                recyclerViewPenyakit.setItemAnimator(new DefaultItemAnimator());
                recyclerViewPenyakit.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final DaftarPenyakit penyakit = penyakitList.get(position);

        builder.setTitle("Hewan Sembuh").setMessage("Anda Yakin?").setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //action Yes
                        String idP = penyakit.getIdP();
                        String idK = penyakit.getIdK();
                        String idH = penyakit.getCodeH();

                        String idPenyakit = idP;
                        String statusH = "sehat";

                        delete(idPenyakit);
                        updateHewan(idK, idH, statusH);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    private void delete(String idPenyakit) {

        DatabaseReference dataHewan = FirebaseDatabase.getInstance().getReference("penyakit").child(idPenyakit);

        dataHewan.removeValue();
    }

    private boolean updateHewan(String idK, String idH, final String statusH){

        databaseHewan = FirebaseDatabase.getInstance().getReference("hewan").child(idK).child(idH);

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("hewan").child(idK);

        databaseHewan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    DaftarHewan daftarHewan = dataSnapshot.getValue(DaftarHewan.class);

                    String codeH = daftarHewan.getCode();
                    String hewanId = daftarHewan.getHewanId();
                    String hewanBobot = daftarHewan.getHewanBobot();
                    String hewanGender = daftarHewan.getHewanGender();
                    String hewanNama = daftarHewan.getNama();
                    String hewanUrl = daftarHewan.getUrl();
                    String hewanStatus = "sehat";

                    DaftarHewan daftarHewan1 = new DaftarHewan(codeH, hewanId, hewanGender, hewanBobot, hewanNama, hewanUrl, hewanStatus);

                    databaseHewan.setValue(daftarHewan1);
                    //databaseReference.child(idH).setValue(daftarHewan1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Toast.makeText(this, "Data hewan sembuh berhasil", Toast.LENGTH_SHORT).show();

        return true;

    }*/

}
