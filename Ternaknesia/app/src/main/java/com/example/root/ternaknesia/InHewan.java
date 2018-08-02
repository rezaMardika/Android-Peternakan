package com.example.root.ternaknesia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*public class InHewan extends AppCompatActivity {

    private EditText editTextGender;
    private EditText editTextBobot;
    private Button buttonSaveSapi;
    private TextView textViewHewan;
    private ListView list_item_Hewan;

    private DatabaseReference databaseHewan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_hewan);
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

        Intent intent = getIntent();

        String id = intent.getStringExtra(Beranda1.KANDANG_ID);

        databaseHewan = FirebaseDatabase.getInstance().getReference("hewan").child(id);

        textViewHewan =(TextView) findViewById(R.id.textViewHewan);
        editTextGender = (EditText) findViewById(R.id.editTextGender);
        editTextBobot = (EditText) findViewById(R.id.editTextBobot);
        buttonSaveSapi = (Button) findViewById(R.id.buttonSaveSapi);

        list_item_Hewan = (ListView) findViewById(R.id.list_item_Hewan);
        Intent intent = getIntent();

        buttonSaveSapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tambah();
            }
        });

    }

   private void Tambah(){

        String Gender = editTextGender.getText().toString().trim();
        String Bobot = editTextBobot.getText().toString().trim();

        if(!TextUtils.isEmpty(Gender) && !TextUtils.isEmpty(Bobot)){
            String code = databaseHewan.push().getKey();

            DaftarHewan daftarHewan = new DaftarHewan(code, Gender, Bobot);

            databaseHewan.child(code).setValue(daftarHewan);
            Toast.makeText(this, "Hewan berhasil ditambahkan", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Semua data harus diisi", Toast.LENGTH_LONG).show();

        }

    }
}*/
