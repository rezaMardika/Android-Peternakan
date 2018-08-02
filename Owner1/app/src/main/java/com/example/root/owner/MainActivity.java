package com.example.root.owner;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewDaftar;
    private TextView textViewForget;

    private Task<Void> user;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    public static final String USER_EMAIL = "userEmail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseAuth.getCurrentUser() != null){
            //berada activity here
            finish();
            startActivity(new Intent(getApplication(), ActivityBeranda.class));
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignin = (Button) findViewById(R.id.buttonSignin);
        textViewDaftar = (TextView) findViewById(R.id.textViewDaftar);
        textViewForget = (TextView) findViewById(R.id.textViewForget);

        progressDialog = new ProgressDialog(this);

        buttonSignin.setOnClickListener(this);
        textViewDaftar.setOnClickListener(this);
        textViewForget.setOnClickListener(this);

    }

    private void userLogin(){
        final String email = editTextEmail.getText().toString().trim();
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
                            Intent intent = new Intent(getApplicationContext(), ActivityBeranda.class);
                            startActivity(intent);
                            //startActivity(new Intent(getApplication(), ActivityBeranda.class));
                        } else {
                            Toast.makeText(MainActivity.this, "Cek Kembali email atau password anda", Toast.LENGTH_SHORT).show();
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
        else if(view == textViewDaftar){
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }
        else if(view == textViewForget){
            startActivity(new Intent(this, For_Password_Activity.class));
        }
    }
}
