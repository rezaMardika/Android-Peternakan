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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forget_Password extends AppCompatActivity {

    private EditText editTextEmail;
    private Button buttonReset;
    private Button buttonBack;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__password);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        buttonReset = (Button) findViewById(R.id.buttonReset);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplication(), login.class));
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    editTextEmail.setError("Harus diisi");
                    return;
                }

                progressDialog.setMessage("Reset Password...");
                progressDialog.show();

                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Forget_Password.this, "Kami akan mengirimkan intruksi untuk reset password anda", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplication(), login.class));
                        }else {
                            Toast.makeText(Forget_Password.this, "Gagal reset email", Toast.LENGTH_SHORT).show();
                        }

                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}
