package com.example.root.ternaknesia;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HewanSakit extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private DatabaseReference databaseDeases, databaseHewan, databaseRecord, dbhewan;
    private TextView textViewHewanSakit;
    private ListView listViewPenyakit;

    List<DaftarPenyakit> deasesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hewan_sakit);

        textViewHewanSakit = (TextView) findViewById(R.id.textViewHewanSakit);
        listViewPenyakit = (ListView) findViewById(R.id.listViewPenyakit);
        Intent intent = getIntent();

        deasesList = new ArrayList<>();

        String nama = intent.getStringExtra(PenyakitList.DEASES_NAME);
        String id = intent.getStringExtra(PenyakitList.DEASES_ID);

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
                    DaftarPenyakit penyakit = deasesSnapshot.getValue(DaftarPenyakit.class);
                    deasesList.add(penyakit);
                }
                DeasesList adapter = new DeasesList(HewanSakit.this, deasesList);
                listViewPenyakit.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final DaftarPenyakit penyakit = deasesList.get(position);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        final String strTanggal = mdformat.format(calendar.getTime());

        builder.setTitle("Hewan Sembuh").setMessage("Anda Yakin? "+strTanggal).setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //action Yes
                        String idP = penyakit.getIdP();
                        String idK = penyakit.getIdK();
                        String idH = penyakit.getCodeH();
                        String namaP = penyakit.getNamaP();
                        String dateP = penyakit.getDateP();

                        String idPenyakit = idP;
                        String statusH = "sehat";

                        delete(idPenyakit);
                        updateHewan(idK, idH, statusH);
                        updateRecord(idH, idP, namaP, dateP, strTanggal);
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

    private boolean updateRecord(String idH, String idP, String namaP, String dateP, String date) {

        databaseRecord = FirebaseDatabase.getInstance().getReference("recordP");
        dbhewan = FirebaseDatabase.getInstance().getReference("RecordHewan");

        DaftarRecordPenyakit recordPenyakit = new DaftarRecordPenyakit(idH, idP, namaP, dateP, date);
        DaftarRHewan rHewan = new DaftarRHewan(idH, namaP, dateP, date);

        databaseRecord.child(idH).child(idP).setValue(recordPenyakit);
        dbhewan.child(idH).setValue(rHewan);

        return true;
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
                String hewanTanggal = daftarHewan.getTanggal();
                String hewanUrl = daftarHewan.getUrl();
                String hewanStatus = "sehat";

                DaftarHewan daftarHewan1 = new DaftarHewan(codeH, hewanId, hewanGender, hewanBobot, hewanTanggal, hewanUrl, hewanStatus);

                databaseHewan.setValue(daftarHewan1);
                //databaseReference.child(idH).setValue(daftarHewan1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Toast.makeText(this, "Data hewan sembuh berhasil", Toast.LENGTH_SHORT).show();

        return true;

    }

}
