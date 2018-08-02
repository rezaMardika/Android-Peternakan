package com.example.root.ternaknesia;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PakanHw extends AppCompatActivity {

    public static final String JUMLAH_PAKAN = "kapasitasPakan";
    private DatabaseReference databasePakanHw;
    private DatabaseReference databaseJadwal;
    private ListView listViewPakan;

    List<DaftarPakan> pakans;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pakan_hw);

        listViewPakan = (ListView) findViewById(R.id.listViewPakan);

        Intent intent = getIntent();

        pakans = new ArrayList<>();

        String id = intent.getStringExtra(Hwean.HEWAN_ID);

        databasePakanHw = FirebaseDatabase.getInstance().getReference("pakan");

        listViewPakan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final DaftarPakan daftarPakan = pakans.get(position);
                pakanHewan(daftarPakan.getIdPakan(), daftarPakan.getNamaPakan(), daftarPakan.getKapasitasPakan(), daftarPakan.getDatePakan());
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        databasePakanHw.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pakans.clear();

                for(DataSnapshot pakanSnapshot : dataSnapshot.getChildren()){
                    DaftarPakan daftarPakan = pakanSnapshot.getValue(DaftarPakan.class);
                    pakans.add(daftarPakan);
                }
                PakanList pakanList = new PakanList(PakanHw.this, pakans);
                listViewPakan.setAdapter(pakanList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void pakanHewan(final String codePakan, final String namaPakan, final String jumlaPakan, final String datePakan){
        AlertDialog.Builder pakanBulder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        final View pakanView = inflater.inflate(R.layout.pakan_dialog, null);

        pakanBulder.setView(pakanView);

        final EditText editTextBanyak = (EditText) pakanView.findViewById(R.id.editTextBanyak);
        pakanBulder.setTitle("Input banyak pakan (Kg)");

        final AlertDialog alertDialog = pakanBulder.create();
        alertDialog.show();

        Intent intent = getIntent();

        String idHewan = intent.getStringExtra(Hwean.HEWAN_ID);

        databaseJadwal = FirebaseDatabase.getInstance().getReference("jadwal").child(idHewan);

        final String code = codePakan;
        final String nama = namaPakan;
        final String jumlahP = jumlaPakan;
        final String date = datePakan;

        final float banyak = Float.parseFloat(jumlahP);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int mounth = c.get(Calendar.MONTH)+1;
        int year = c.get(Calendar.YEAR);
        int hour = c.get(Calendar.HOUR);
        int minutes = c.get(Calendar.MINUTE);

        final String waktu = hour+":"+minutes+" "+day+"-"+mounth+"-"+year;

        final Button buttonPakan = (Button) pakanView.findViewById(R.id.buttonPakan);

        buttonPakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jumlah = editTextBanyak.getText().toString().trim();

                float jum = Float.parseFloat(jumlah);
                if(banyak < jum){
                    alert1();
                    alertDialog.dismiss();
                }
                else {
                    float stok = banyak-jum;
                    String stok1 = String.valueOf(stok);

                    if (TextUtils.isEmpty(jumlah)){
                        editTextBanyak.setError("required");
                        return;
                    }

                    String idJ = databaseJadwal.push().getKey();
                    String Uid = user.getUid();
                    DaftarJadwal daftarJadwal = new DaftarJadwal(idJ, Uid, nama, jumlah, waktu);

                    databaseJadwal.child(idJ).setValue(daftarJadwal);

                    updatePakan(code, nama, stok1, date);
                    alert();
                    alertDialog.dismiss();
                    finish();
                    startActivity(new Intent(getApplication(), Hwean.class));
                }
                //alertDialog.dismiss();
            }
        });
    }

    public void alert(){
        Toast.makeText(this, "Pemberian pakan sukses disimpan", Toast.LENGTH_LONG).show();
    }

    public void alert1(){
        Toast.makeText(this, "Stok pakan tidak mencukupi", Toast.LENGTH_LONG).show();
    }

    private boolean updatePakan(String idPakan, String namaPakan, String kapasitasPakan, String datePakan){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pakan");

        DaftarPakan daftarPakan = new DaftarPakan(idPakan, namaPakan, kapasitasPakan, datePakan);

        databaseReference.child(idPakan).setValue(daftarPakan);
        return true;
    }
}
