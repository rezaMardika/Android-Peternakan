package com.example.root.owner;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActivityPotong extends Activity{

    private DatabaseReference databasePotong;

    private EditText editTextCari;

    private ListView listViewPotong;
    private List<Data_Potong> potongList;
    private Button buttonCari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potong);

        databasePotong = FirebaseDatabase.getInstance().getReference("riwayat");
        potongList = new ArrayList<>();

        listViewPotong = (ListView) findViewById(R.id.listViewPotong);
        editTextCari = (EditText) findViewById(R.id.editTextCari);
        buttonCari = (Button) findViewById(R.id.buttonCari);
        /*listViewPotong.setTextFilterEnabled(true);
        adapter = new Potong_List(this, R.layout.list_potong, potongList);
        listViewPotong.setAdapter(adapter);*/


        //final ArrayAdapter adapter = new ArrayAdapter(ActivityPotong.this, R.layout.list_potong, potongList);
        /*Potong_List adapter = new Potong_List(ActivityPotong.this, potongList)
        listViewPotong.setAdapter(adapter);

        databasePotong.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot potongSnapshot : dataSnapshot.getChildren()){
                    Data_Potong potong = potongSnapshot.getValue(Data_Potong.class);
                    potongList.add(potong);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        searchViewPotong.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });*/

        /*buttonPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPDF(v);
            }
        });*/

        buttonCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cari = editTextCari.getText().toString().trim();

                searchData(cari);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        databasePotong.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                potongList.clear();

                for(DataSnapshot potongSnapshot : dataSnapshot.getChildren()){
                    Data_Potong potong = potongSnapshot.getValue(Data_Potong.class);
                    potongList.add(potong);
                }

                Potong_List adapter = new Potong_List(ActivityPotong.this, R.layout.list_potong, potongList);
                listViewPotong.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void searchData(String cari){
        //Query searchQuery = databasePotong.orderByChild("tanggalH").equalTo(cari);
        Query searchQuery = databasePotong.orderByChild("periodeH").startAt(cari).endAt(cari+"\uf8ff");

        searchQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                potongList.clear();

                for(DataSnapshot potongSnapshot : dataSnapshot.getChildren()){
                    Data_Potong potong = potongSnapshot.getValue(Data_Potong.class);
                    potongList.add(potong);
                }
                Potong_List adapter = new Potong_List(ActivityPotong.this, R.layout.list_potong, potongList);
                listViewPotong.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
