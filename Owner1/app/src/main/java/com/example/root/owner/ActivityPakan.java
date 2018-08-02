package com.example.root.owner;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public class ActivityPakan extends AppCompatActivity {

    private DatabaseReference databasePakan;
    private ListView listViewPakan;

    List<Data_Pakan>pakanList;
    private RecyclerView recyclerViewPakan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Daftar Pakan");
        setContentView(R.layout.activity_pakan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        databasePakan = FirebaseDatabase.getInstance().getReference("pakan");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pakanHewan();
            }
        });

        pakanList = new ArrayList<>();

        recyclerViewPakan = (RecyclerView) findViewById(R.id.recyclePakan);
        recyclerViewPakan.setHasFixedSize(true);
        recyclerViewPakan.setLayoutManager(new LinearLayoutManager(this));

        notificationM();
    }

   @Override
    protected void onStart() {
        super.onStart();

        databasePakan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pakanList = new ArrayList<Data_Pakan>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Data_Pakan data_pakan = dataSnapshot1.getValue(Data_Pakan.class);
                    pakanList.add(data_pakan);
                }
                Pakan_List adapter = new Pakan_List(pakanList, R.layout.list_pakan, ActivityPakan.this);
                RecyclerView.LayoutManager manager = new GridLayoutManager(ActivityPakan.this, 1);
                recyclerViewPakan.setLayoutManager(manager);
                recyclerViewPakan.setItemAnimator(new DefaultItemAnimator());
                recyclerViewPakan.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void notificationM(){
        databasePakan = FirebaseDatabase.getInstance().getReference("pakan");

        databasePakan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pakanList.clear();

                for(DataSnapshot pakanSnapshot : dataSnapshot.getChildren()){
                    Data_Pakan pakan = pakanSnapshot.getValue(Data_Pakan.class);

                    String stok = pakan.getKapasitasPakan();
                    float pakanStok = Float.parseFloat(stok);

                    if(pakanStok < 5){
                        notif();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void notif(){

        Uri sound = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.notification);
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_menu_manage)
                .setContentTitle("Pemberitahuan Stok Pakan ")
                .setContentText("Stok pakan hampir habis, Isi ulang pakan hewan anda")
                .setSound(sound)
                .setLights(Color.RED, 3000,3000)
                .setVibrate(new long[]{1000,1000,1000,1000,1000});

        Intent notificationIntent = new Intent(this, ActivityPakan.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, builder.build());

    }

    private void updatePakan(final String idP, final String nama, final String banyak){
        AlertDialog.Builder pakanBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View pakanView = inflater.inflate(R.layout.dialog_update_pakan, null);

        pakanBuilder.setView(pakanView);

        final EditText editTextBanyak = (EditText) pakanView.findViewById(R.id.editTextBanyak);
        final Button buttonTambah = (Button) pakanView.findViewById(R.id.buttonTambah);
        pakanBuilder.setTitle("Tambahkan pakan");

        final AlertDialog alertDialog = pakanBuilder.create();
        alertDialog.show();

        buttonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();

                String idPakan = idP;
                String NamaPakan = nama;
                String Banyak = editTextBanyak.getText().toString().trim();
                String BanyakL = banyak;

                float lama = Float.parseFloat(BanyakL);
                float baru = Float.parseFloat(Banyak);
                float fix = lama + baru;
                String jumlah = Float.toString(fix);

                int day = c.get(Calendar.DAY_OF_MONTH);
                int mounth = c.get(Calendar.MONTH)+1;
                int year = c.get(Calendar.YEAR);
                String date = day+"-"+mounth+"-"+year;

                if (TextUtils.isEmpty(Banyak)){
                    editTextBanyak.setError("Required");
                    return;
                }

                update(idPakan, NamaPakan, jumlah, date);
                alertDialog.dismiss();

            }
        });

    }

    private boolean update(String id, String nama, String banyak, String date){

        DatabaseReference databasePakan = FirebaseDatabase.getInstance().getReference("pakan");

        Data_Pakan pakan = new Data_Pakan(id, nama, banyak, date);

        databasePakan.child(id).setValue(pakan);
        Toast.makeText(this, "Data Pakan berhasil diUpdate", Toast.LENGTH_LONG).show();

        return true;
    }

    private void pakanHewan(){

        AlertDialog.Builder pakanBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View pakanView = inflater.inflate(R.layout.dialog_pakan, null);

        pakanBuilder.setView(pakanView);

        final EditText editTextNamaPakan = (EditText) pakanView.findViewById(R.id.editTextPakan);
        final EditText editTextBanyak = (EditText) pakanView.findViewById(R.id.editTextBanyak);
        final Button buttonTambah = (Button) pakanView.findViewById(R.id.buttonTambah);
        pakanBuilder.setTitle("Tambahkan pakan");

        final AlertDialog alertDialog = pakanBuilder.create();
        alertDialog.show();

        databasePakan = FirebaseDatabase.getInstance().getReference("pakan");

        buttonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();

                String NamaPakan = editTextNamaPakan.getText().toString().trim();
                String Banyak = editTextBanyak.getText().toString().trim();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int mounth = c.get(Calendar.MONTH)+1;
                int year = c.get(Calendar.YEAR);
                String date = day+"-"+mounth+"-"+year;

                if (TextUtils.isEmpty(NamaPakan)){
                    editTextNamaPakan.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(Banyak)){
                    editTextBanyak.setError("Required");
                    return;
                }

                String idPakan = databasePakan.push().getKey();

                Data_Pakan pakan = new Data_Pakan(idPakan, NamaPakan, Banyak, date);

                databasePakan.child(idPakan).setValue(pakan);

                alertDialog.dismiss();

            }
        });

    }
}
