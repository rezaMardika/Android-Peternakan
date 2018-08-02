package com.example.root.ternaknesia;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Random;

public class Profil_Sapi extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference databaseImage;
    private ImageView imageViewProfil;
    private Button buttonUpload;
    private TextView textViewId;
    private TextView textViewBobot;
    private TextView textViewGender, textViewTanggal, textViewClose;

    private Uri filePath;

    private StorageReference storageReferenceSapi;

    private static final int PICK_IMAGE_REQUEST = 234;
    private static final int REQUEST_CODE = 1234;
    private static final String FB_STORAGE_PATH = " image/";
    private static final String FB_DATABASE_PATH = " hewan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil__sapi);

        Intent intent = getIntent();
        String code = intent.getStringExtra(Hwean.HEWAN_ID);
        String id = intent.getStringExtra(Hwean.HEWAN_CODE);
        String bobot = intent.getStringExtra(Hwean.HEWAN_BOBOT);
        String gender = intent.getStringExtra(Hwean.HEWAN_GENDER);
        String tanggal = intent.getStringExtra(Hwean.HEWAN_TANGGAL);
        String url = intent.getStringExtra(Hwean.HEWAN_URL);

        storageReferenceSapi = FirebaseStorage.getInstance().getReference();
        databaseImage = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH).child(code);

        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewBobot = (TextView) findViewById(R.id.textViewBobot);
        textViewGender = (TextView) findViewById(R.id.textViewGender);
        textViewTanggal = (TextView) findViewById(R.id.textViewTanggal);
        textViewClose = (TextView) findViewById(R.id.textViewClose);

        imageViewProfil = (ImageView) findViewById(R.id.imageViewProfil);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);

        Intent intentK = getIntent();
        final String idK = intentK.getStringExtra(Hwean.KANDANG_ID);

        textViewId.setText("Code    :"+id);
        textViewBobot.setText(bobot+" kg");
        textViewGender.setText(gender);
        textViewTanggal.setText(tanggal);

        buttonUpload.setOnClickListener(this);

        imageViewProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        textViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), Hwean.class));
            }
        });

        getImage(imageViewProfil, url);
    }

    public void getImage(final ImageView imageViewHewan, String uri){

        Glide.with(this)
                .load(uri)
                .placeholder(R.drawable.ic_menu_camera)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(imageViewHewan);

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
                imageViewProfil.setImageBitmap(bitmap);
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
                            String urlBaru = taskSnapshot.getDownloadUrl().toString();

                            Intent intentK = getIntent();
                            final String idK = intentK.getStringExtra(Hwean.KANDANG_ID);

                            String id = intentK.getStringExtra(Hwean.HEWAN_CODE);
                            String code = intentK.getStringExtra(Hwean.HEWAN_ID);
                            String bobot = intentK.getStringExtra(Hwean.HEWAN_BOBOT);
                            String gender = intentK.getStringExtra(Hwean.HEWAN_GENDER);
                            String tanggal = intentK.getStringExtra(Hwean.HEWAN_TANGGAL);
                            String status = intentK.getStringExtra(Hwean.HEWAN_STATUS);
                            //String url = intentK.getStringExtra(Hwean.HEWAN_URL);

                            String codeHewan = code;
                            String idHewan = id;
                            String jenisHewan = gender;
                            String bobotHewan = bobot;
                            String tanggalHewan = tanggal;
                            String statusHewan = status;

                            updateProfil(idK, codeHewan, idHewan, jenisHewan, bobotHewan, tanggalHewan, urlBaru, statusHewan);
                            /*Intent intent1 = new Intent(getApplicationContext(), Profil_Sapi.class);
                            startActivity(intent1);

                            Toast.makeText(getApplicationContext(), "Hewan berhasil ditambahkan", Toast.LENGTH_LONG).show();*/

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), Profil_Sapi.class);
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

    private boolean updateProfil(String idK, String code, String id, String gender, String bobot, String nama, String url, String status){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("hewan").child(idK);

        DaftarHewan daftarHewan = new DaftarHewan(code, id, gender, bobot, nama, url, status);

        databaseReference.child(code).setValue(daftarHewan);
        Toast.makeText(this, "Data hewan berhasil diUpdate", Toast.LENGTH_LONG).show();

        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == buttonUpload){
            uploadFile();
        }
    }
}
