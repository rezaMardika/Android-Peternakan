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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    private EditText editTextNama;
    private EditText editTextAddress;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private TextView textView;

    private DatabaseReference databaseUser;
    private FirebaseUser user;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseUser = FirebaseDatabase.getInstance().getReference("user");
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseAuth.getCurrentUser() != null){
            //berada activity here
            finish();
            startActivity(new Intent(getApplication(), login.class));
        }

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        textView = (TextView) findViewById(R.id.textView);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }

    private  void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Tunggu sebentar...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //user is succesfully registered
                            saveUser();
                            finish();
                            sendEmailVerification();
                            startActivity(new Intent(getApplication(), login.class));
                            firebaseAuth.signOut();
                        }
                        else {
                            Toast.makeText(Register.this, "Could not register.. please try again", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void saveUser() {
        String nama = editTextNama.getText().toString().trim();
        String alamat = editTextNama.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(!TextUtils.isEmpty(nama) && !TextUtils.isEmpty(alamat) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            String id = databaseUser.push().getKey();

            pegawai pegawai = new pegawai(id, nama, alamat, email, password);

            databaseUser.child(id).setValue(pegawai);

            Toast.makeText(this, "Register Complete", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "You must fill all",  Toast.LENGTH_LONG).show();
        }
    }

    private void sendEmailVerification() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Register.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                }
            });
        }

    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister){
            registerUser();
        }
        if(view == textViewSignin){
            startActivity(new Intent(this, login.class));
        }
    }
}
