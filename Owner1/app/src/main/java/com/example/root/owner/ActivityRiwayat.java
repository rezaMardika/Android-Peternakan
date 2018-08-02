package com.example.root.owner;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
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

public class ActivityRiwayat extends AppCompatActivity {

    private DatabaseReference databaseRiwayat;
    private TextView textViewRiwayat, textViewJumlah;
    private ListView listViewRiwayat;
    private List<Data_Riwayat> riwayatList;
    private Button buttonChart;

    public static final String HEWAN_ID = "code";
    public static final String HEWAN_CODE = "hewanId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        final Intent intent = getIntent();
        final String code = intent.getStringExtra(ActivityProfil_Sapi.HEWAN_ID);
        final String id  = intent.getStringExtra(ActivityProfil_Sapi.HEWAN_CODE);
        databaseRiwayat = FirebaseDatabase.getInstance().getReference("bobot").child(id);

        textViewRiwayat = (TextView) findViewById(R.id.textViewRiwayat);
        listViewRiwayat = (ListView) findViewById(R.id.listViewRiwayat);
        textViewJumlah = (TextView) findViewById(R.id.textViewJumlah);
        buttonChart = (Button) findViewById(R.id.buttonChart);

        riwayatList = new ArrayList<>();
        textViewRiwayat.setText("Hewan "+id);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        final String strDate = mdformat.format(calendar.getTime());

        final DatabaseReference databasebbt = FirebaseDatabase.getInstance().getReference("bbt").child(id);

        databasebbt.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String date = dataSnapshot.child("dateB").getValue().toString();

                textViewJumlah.setText(date+" & "+strDate);
                if (strDate.equals(date)){
                    notif();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), ChartActivity.class);
                intent1.putExtra(HEWAN_CODE, id);
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseRiwayat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                riwayatList.clear();

                for(DataSnapshot riwayatSnapshot : dataSnapshot.getChildren()){
                    Data_Riwayat riwayat = riwayatSnapshot.getValue(Data_Riwayat.class);
                    riwayatList.add(riwayat);
                }

                Riwayat_List adapter = new Riwayat_List(ActivityRiwayat.this, riwayatList);
                listViewRiwayat.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void notif(){

        Uri sound = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.notification);
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_warning)
                .setContentTitle("Pemberitahuan Perkembangan Bobot Hewan")
                .setSound(sound)
                .setLights(Color.RED, 3000,3000)
                .setVibrate(new long[]{1000,1000,1000,1000,1000});

        Intent notificationIntent = new Intent(this, ActivityRiwayat.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, builder.build());

    }
}