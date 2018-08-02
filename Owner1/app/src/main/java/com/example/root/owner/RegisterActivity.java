package com.example.root.owner;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextNama;
    private EditText editTextAddress;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private ImageView imageViewOwner;

    private Uri filePath;

    private StorageReference storageReferenceOwner;
    private DatabaseReference databaseHewan;

    private static final int REQUEST_CODE = 1234;
    private static final String FB_STORAGE_PATH = " owner/";

    private DatabaseReference databaseUser;
    private FirebaseUser user;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseUser = FirebaseDatabase.getInstance().getReference("owner");
        storageReferenceOwner = FirebaseStorage.getInstance().getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseAuth.getCurrentUser() != null){
            //berada activity here
            finish();
            startActivity(new Intent(getApplication(), MainActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        imageViewOwner =(ImageView) findViewById(R.id.imageViewOwner);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
        imageViewOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
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
                            //sendEmailVerification();
                            //startActivity(new Intent(getApplication(), MainActivity.class));
                            firebaseAuth.signOut();
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Could not register.. please try again", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    /*private void saveOwner() {
        String nama = editTextNama.getText().toString().trim();
        String alamat = editTextNama.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(!TextUtils.isEmpty(nama) && !TextUtils.isEmpty(alamat) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            String id = databaseUser.push().getKey();

            DaftarOwner owner = new DaftarOwner(id, nama, alamat, email, password);

            databaseUser.child(id).setValue(owner);

            Toast.makeText(this, "Register Complete", Toast.LENGTH_LONG).show();

            startActivity(new Intent(this, MainActivity.class));
        }
        else{
            Toast.makeText(this, "You must fill all",  Toast.LENGTH_LONG).show();
        }
    }*/

    private void sendEmailVerification() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                }
            });
        }

    }

    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an image"), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.8), (int)(bitmap.getHeight()*0.8), true);
                imageViewOwner.setImageBitmap(resized);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImageExt(Uri filePath){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(filePath));
    }

    private void uploadFile(){
        if (filePath != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading......");
            progressDialog.show();

            //get storage reference
            StorageReference riversRef = storageReferenceOwner.child(FB_STORAGE_PATH + System.currentTimeMillis() +"."+getImageExt(filePath));

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_LONG).show();
                            String urlOwner = taskSnapshot.getDownloadUrl().toString();

                            databaseUser = FirebaseDatabase.getInstance().getReference("Owner");
                            final Random random = new Random();
                            int numb = random.nextInt(100)+10;
                            String number = String.valueOf(numb);

                            String namaOwner = editTextNama.getText().toString().trim();
                            String alamatOwner = editTextAddress.getText().toString().trim();
                            String emailOwner = editTextEmail.getText().toString().trim();
                            String passwordOwner = editTextPassword.getText().toString().trim();

                            if(namaOwner.isEmpty()){
                                editTextNama.setError("Harus diisi");
                            }

                            if(!TextUtils.isEmpty(namaOwner)){
                                String codeOwner = databaseUser.push().getKey();

                                //DaftarPeternak peternak = new DaftarPeternak(code, namaPeternak, alamatPeternak, emailPeternak, passwordPeternak, url);
                                DaftarOwner owner = new DaftarOwner(codeOwner, namaOwner, alamatOwner, emailOwner, passwordOwner, urlOwner);

                                databaseUser.child(codeOwner).setValue(owner);

                                registerUser();
                                finish();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }

                            //Toast.makeText(getApplicationContext(), "Hewan berhasil ditambahkan", Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), ActivityAddPeternak.class);
                            startActivity(intent);
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progres = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getBytesTransferred();
                            progressDialog.setMessage(((int) progres) + "% Uploaded...");

                        }
                    })
            ;
        }else {
            Toast.makeText(getApplicationContext(), "Please select image", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister){
            uploadFile();
            //registerUser();
        }
        if(view == textViewSignin){
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
