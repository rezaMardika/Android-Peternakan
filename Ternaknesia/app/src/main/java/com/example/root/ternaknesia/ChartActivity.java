package com.example.root.ternaknesia;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChartActivity extends AppCompatActivity {

    private GraphView graphViewBobot;

    private LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[0]);
    private DatabaseHelper helper;
    private SQLiteDatabase sqLiteDatabase;

    //private SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
    private SimpleDateFormat sdf = new SimpleDateFormat("MMMM");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        graphViewBobot = (GraphView) findViewById(R.id.GraphViewBobot);

        helper = new DatabaseHelper(this);
        sqLiteDatabase = helper.getWritableDatabase();
        graphViewBobot.addSeries(series);

        graphViewBobot.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX){
                    return sdf.format(new Date((long)value));
                }else {
                    return super.formatLabel(value, isValueX);
                }
            }
        });
        series.resetData(getDataPoint());
        insertData();
    }

    private void insertData() {

       series.resetData(getDataPoint());

       graphViewBobot.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
           @Override
           public String formatLabel(double value, boolean isValueX) {
               if (isValueX){
                    return sdf.format(new Date((long)value));
               }else {
                    return super.formatLabel(value, isValueX);
               }
           }
       });
    }

    public DataPoint[] getDataPoint() {
        String[] columns = {"bulan", "berat"};
        Cursor cursor=sqLiteDatabase.query("data", columns, null, null, null, null, null);
        DataPoint[] dp = new DataPoint[cursor.getCount()];

        for(int i=0; i<cursor.getCount(); i++){
            cursor.moveToNext();
            dp[i] = new DataPoint(cursor.getLong(0), cursor.getInt(1));

        }
        return dp;
    }
}
