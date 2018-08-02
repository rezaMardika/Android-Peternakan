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

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewForget;

    private Task<Void> user;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseAuth.getCurrentUser() != null){
            //berada activity here
            finish();
            startActivity(new Intent(getApplication(), Beranda1.class));
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignin = (Button) findViewById(R.id.buttonSignin);
        textViewForget = (TextView) findViewById(R.id.textViewForget);

        progressDialog = new ProgressDialog(this);

        buttonSignin.setOnClickListener(this);
        textViewForget.setOnClickListener(this);

    }

    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password) && password.length() < 6){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseUser = firebaseAuth.getCurrentUser();
        //firebaseUser.isEmailVerified();

        progressDialog.setMessage("Login User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    //start beranda activity
                    finish();
                    startActivity(new Intent(getApplication(), Beranda1.class));
                } else {
                    Toast.makeText(login.this, "Please Register", Toast.LENGTH_SHORT).show();
                    //FirebaseAuth.getInstance().signOut();
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("Thaks") != null) {
               Toast.makeText(getApplicationContext(), "data:" + bundle.getString("Thaks"), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignin){
            userLogin();
        }
        else if(view == textViewForget){
            finish();
            startActivity(new Intent(this, Forget_Password.class));
        }

    }
}
