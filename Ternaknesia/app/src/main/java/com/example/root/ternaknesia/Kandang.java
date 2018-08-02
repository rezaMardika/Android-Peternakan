package com.example.root.ternaknesia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 07/06/17.
 */

public class Kandang extends AppCompatActivity{
    /*DatabaseReference databaseKandang;

    private ListView listViewKandang;
    private Button buttonAdd;

    List<DaftarKandang> kandangList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda1);

        databaseKandang = FirebaseDatabase.getInstance().getReference("kandang");

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        listViewKandang = (ListView) findViewById(R.id.listViewKandang);

        kandangList = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseKandang.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                kandangList.clear();
                for(DataSnapshot kandangSnapshot : dataSnapshot.getChildren()){
                    DaftarKandang daftarKandang = kandangSnapshot.getValue(DaftarKandang.class);

                    kandangList.add(daftarKandang);

                }
                KandangList adapter = new KandangList(Kandang.this, kandangList);
                listViewKandang.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == buttonAdd){
            startActivity(new Intent(this, InKandang.class));
        }
    }*/
}
