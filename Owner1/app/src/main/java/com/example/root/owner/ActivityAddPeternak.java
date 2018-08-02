package com.example.root.owner;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class ActivityAddPeternak extends AppCompatActivity {

    private DatabaseReference databasePeternak;

    private Button buttonAdd;

    private ImageView imageViewPeternak;
    private EditText editTextNama, editTextAlamat, editTextEmail, editTextPassword;
    private Uri filePath;
    private Spinner spinnerJob;

    private StorageReference storageReferenceSapi;
    private DatabaseReference databaseHewan;

    private static final int REQUEST_CODE = 1234;
    private static final String FB_STORAGE_PATH = " peternak/";
    private FirebaseUser user;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_peternak);

        firebaseAuth = FirebaseAuth.getInstance();
        databasePeternak = FirebaseDatabase.getInstance().getReference("peternak");
        storageReferenceSapi = FirebaseStorage.getInstance().getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();
        progressDialog = new ProgressDialog(this);


        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextAlamat = (EditText) findViewById(R.id.editTextAlamat);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        spinnerJob = (Spinner) findViewById(R.id.spinnerJob);
        imageViewPeternak = (ImageView) findViewById(R.id.imageViewPeternak);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);

        imageViewPeternak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
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
                            //uploadFile();
                            //finish();
                            //Intent intent = new Intent(getApplicationContext(), ActivityPeternak.class);
                            //startActivity(intent);
                            //Toast.makeText(getApplicationContext(), "Hewan berhasil ditambahkan", Toast.LENGTH_LONG).show();
                            firebaseAuth.signOut();
                        }
                        else {
                            Toast.makeText(ActivityAddPeternak.this, "Could not register.. please try again", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
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
                imageViewPeternak.setImageBitmap(resized);
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
            StorageReference riversRef = storageReferenceSapi.child(FB_STORAGE_PATH + System.currentTimeMillis() +"."+getImageExt(filePath));

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_LONG).show();
                            String url = taskSnapshot.getDownloadUrl().toString();

                            databasePeternak = FirebaseDatabase.getInstance().getReference("peternak");
                            final Random random = new Random();
                            int numb = random.nextInt(100)+10;
                            String number = String.valueOf(numb);

                            String namaPeternak = editTextNama.getText().toString().trim();
                            String alamatPeternak = editTextAlamat.getText().toString().trim();
                            String emailPeternak = editTextEmail.getText().toString().trim();
                            String passwordPeternak = editTextPassword.getText().toString().trim();
                            String jobPeternak = spinnerJob.getSelectedItem().toString();

                            if(namaPeternak.isEmpty()){
                                editTextNama.setError("Harus diisi");
                            }

                            if(!TextUtils.isEmpty(namaPeternak)){
                                String code = databasePeternak.push().getKey();

                                DaftarPeternak peternak = new DaftarPeternak(code, namaPeternak, alamatPeternak, emailPeternak, passwordPeternak, url, jobPeternak);

                                databasePeternak.child(code).setValue(peternak);

                                registerUser();
                                finish();
                                //Intent intent = new Intent(getApplicationContext(), ActivityPeternak.class);
                                //startActivity(intent);
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
}
