package com.example.root.owner;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class ActivityPeternak extends AppCompatActivity {

    private ListView listViewPeternak;
    private DatabaseReference databasePeternak;
    private List<DaftarPeternak> peternakList;

    public static final String nama_peternak = "namaPeternak";
    public static final String id_peternak = "peternakId";
    public static final String alamat_peternak = "peternakAlamat";
    public static final String email_peternak = "emailPeternak";
    public static final String job_peternak = "jobPeternak";
    public static final String url_peternak = "urlPeternak";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peternak);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databasePeternak = FirebaseDatabase.getInstance().getReference("peternak");
        peternakList = new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityAddPeternak.class);
                startActivity(intent);
            }
        });

        listViewPeternak = (ListView) findViewById(R.id.listViewPeternak);

        listViewPeternak.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final DaftarPeternak peternak = peternakList.get(position);

                Intent intent = new Intent(getApplicationContext(), ActivityProfilUser.class);
                intent.putExtra(nama_peternak, peternak.getUserName());
                intent.putExtra(id_peternak, peternak.getUserId());
                intent.putExtra(email_peternak, peternak.getUserEmail());
                intent.putExtra(job_peternak, peternak.getUserJob());
                intent.putExtra(url_peternak, peternak.getUserFoto());
                intent.putExtra(alamat_peternak, peternak.getUserAddress());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databasePeternak.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                peternakList.clear();

                for(DataSnapshot peternakSnapshot : dataSnapshot.getChildren()){
                    DaftarPeternak peternak = peternakSnapshot.getValue(DaftarPeternak.class);
                    peternakList.add(peternak);
                }
                Peternak_List adapter = new Peternak_List(ActivityPeternak.this, peternakList);
                listViewPeternak.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
