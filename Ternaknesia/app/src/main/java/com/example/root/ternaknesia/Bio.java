package com.example.root.ternaknesia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Bio extends AppCompatActivity{

    /*private EditText editTextName;
    private EditText editTextaddress;
    private EditText editTextTelepon;
    private Spinner spinnerGender;
    private EditText editTextJob;
    private Button buttonSave;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);

        databaseReference = FirebaseDatabase.getInstance().getReference("pegawai");
        firebaseAuth = FirebaseAuth.getInstance();

        editTextName = (EditText) findViewById(R.id.editTextFullname);
        editTextaddress = (EditText) findViewById(R.id.editTextAddress);
        editTextTelepon = (EditText) findViewById(R.id.editTextTelepon);
        spinnerGender = (Spinner) findViewById(R.id.spinnerGender);
        editTextJob = (EditText) findViewById(R.id.editTextJob);
        buttonSave = (Button) findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser();

                finish();
                firebaseAuth.signOut();
                startActivity(new Intent(getApplication(), login.class));
            }
        });
    }

    private void saveUser(){
        //String Id = editTextId.getText().toString().trim();
        String Name = editTextName.getText().toString().trim();
        String address = editTextaddress.getText().toString().trim();
        String telepon = editTextTelepon.getText().toString().trim();
        String gender = spinnerGender.getSelectedItem().toString();
        String job = editTextJob.getText().toString().trim();

        if(!TextUtils.isEmpty(Name) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(telepon) && !TextUtils.isEmpty(job)){

            String id = databaseReference.push().getKey();

            pegawai pegawai = new pegawai(id, nama, an);

            databaseReference.child(id).setValue(pegawai);

            Toast.makeText(this, "Register Complete", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "You must fill all",  Toast.LENGTH_LONG).show();
        }
    }*/


    /*@Override
    public void onClick(View view) {
        if(view == buttonRegister){
            registerUser();
        }
        if(view == textViewSignin){
            //login activity
            startActivity(new Intent(this, login.class));
        }
    }*/
}
