package com.example.root.owner;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityAddHewan extends AppCompatActivity {

    DatabaseReference databaseRiwayat;

    ArrayList<BarEntry> barEntries;
    ArrayList<String> barLabel;
    BarDataSet barDataSet;
    BarData barData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_pakan);

        Intent intent = getIntent();
        String code = intent.getStringExtra(ActivityRiwayat.HEWAN_ID);

        BarChart barChart = (BarChart) findViewById(R.id.barChart);

        barEntries = new ArrayList<BarEntry>();
        barLabel = new ArrayList<String>();

        barLabel.add("");
        barEntries.add(new BarEntry(1, 70f));
        barLabel.add("Project A");
        barEntries.add(new BarEntry(2, 80f));
        barLabel.add("Project B");
        barEntries.add(new BarEntry(3, 90f));
        barLabel.add("Project C");

        barDataSet = new BarDataSet(barEntries, "Project");

        barData = new BarData(barDataSet);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(barLabel));

        barDataSet.setColor(Color.rgb(193, 37, 82));

        barChart.setData(barData);
        barChart.animateY(3000);
        /*BarData barData = new BarData(getXAxisValues(), getDataSet());

        chart.setData(barData);
        chart.setDescription("Riwayat bobot");
        chart.invalidate();
        chart.setTouchEnabled(true);*/
    }

    /*private ArrayList<IBarDataSet> getDataSet(){
        ArrayList<IBarDataSet> dataSets = null;
        final ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        Intent intent = getIntent();
        String code = intent.getStringExtra(ActivityRiwayat.HEWAN_ID);

        databaseRiwayat = FirebaseDatabase.getInstance().getReference("bobot").child(code);

        databaseRiwayat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot bobotSnapshot : dataSnapshot.getChildren()){
                    Data_Riwayat riwayat = bobotSnapshot.getValue(Data_Riwayat.class);

                    String bobotHewan = riwayat.getBobotB();

                    float bobot = Float.parseFloat(bobotHewan);
                    int i = 0;
                    BarEntry value1 = new BarEntry(bobot,0);
                    valueSet1.add(value1);

                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        BarDataSet barDataSet = new BarDataSet(valueSet1, "Bobot");
        barDataSet.setColor(Color.rgb(193, 37, 82));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet);
        return dataSets;
    }*/




    /*private ArrayList<String> getXAxisValues(){
        final ArrayList<String> xAxis = new ArrayList<>();
        Intent intent = getIntent();
        String code = intent.getStringExtra(ActivityRiwayat.HEWAN_ID);

        databaseRiwayat = FirebaseDatabase.getInstance().getReference("bobot").child(code);

        databaseRiwayat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot bobotSnapshot : dataSnapshot.getChildren()){
                    Data_Riwayat riwayat = bobotSnapshot.getValue(Data_Riwayat.class);

                    String tanggal = riwayat.getDateB();

                    xAxis.add(tanggal);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return xAxis;
    }*/

}
