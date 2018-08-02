package com.example.root.ternaknesia;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InKandang extends AppCompatActivity {

    private EditText editTextNamaKandang;
    private EditText editTextEmails;
    private Button buttonSaveKandang;

    private DatabaseReference databaseReference;
    //private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_kandang);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("some") != null){
                Toast.makeText(getApplicationContext(), "data:" + bundle.getString("some"),
                        Toast.LENGTH_SHORT).show();
            }
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("kandang");

        editTextNamaKandang = (EditText) findViewById(R.id.editTextNamaKandang);
        editTextEmails = (EditText) findViewById(R.id.editTextEmails);
        buttonSaveKandang = (Button) findViewById(R.id.buttonSaveKandang);

        buttonSaveKandang.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Simpan();
                finish();
                startActivity(new Intent(getApplication(), Beranda1.class));
            }
        });

    }

    private void Simpan(){

        String Nama = editTextNamaKandang.getText().toString().trim();
        String emails = editTextEmails.getText().toString().trim();

        if(!TextUtils.isEmpty(Nama) && !TextUtils.isEmpty(emails)){

            String id = databaseReference.push().getKey();

            DaftarKandang daftarKandang = new DaftarKandang(id, Nama, emails);

            databaseReference.child(id).setValue(daftarKandang);

            Toast.makeText(this, "Kandang Berhasil ditambahkan", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Gagal ditambahkan",  Toast.LENGTH_LONG).show();
        }
    }

}
