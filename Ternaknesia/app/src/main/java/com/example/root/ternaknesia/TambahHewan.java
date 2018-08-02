package com.example.root.ternaknesia;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ChoiceFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TambahHewan extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextCode;
    private EditText editTextBobot;
    private Spinner spinnerJenis;
    private ImageView imageViewSapi;
    private Button buttonSave;
    private Calendar calander;
    private SimpleDateFormat simpledateformat;

    String mCurrentPhotoPath;
    private Uri filePath;
    private Uri fileUri;
    Bitmap photo;

    private StorageReference storageReferenceSapi;
    private DatabaseReference databaseHewan;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private static final int REQUEST_CODE = 1234;
    private static final String FB_STORAGE_PATH = " image/";
    private static final String FB_DATABASE_PATH = " hewan";
    private static final int CAMERA_REQUEST = 1888;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_hewan);

        buttonSave = (Button) findViewById(R.id.buttonSave);
        editTextCode = (EditText) findViewById(R.id.editTextCode);
        editTextBobot = (EditText) findViewById(R.id.editTextBobot);
        spinnerJenis = (Spinner) findViewById(R.id.spinnerJenis);
        imageViewSapi = (ImageView) findViewById(R.id.imageViewSapi);

        Intent intent = getIntent();
        final String id = intent.getStringExtra(Hwean.KANDANG_ID);

        databaseHewan = FirebaseDatabase.getInstance().getReference("hewan").child(id);
        storageReferenceSapi = FirebaseStorage.getInstance().getReference();

        /*buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHewan();
                Intent intent = new Intent(getApplicationContext(), Hwean.class);
                startActivity(intent);
            }
        });*/
        buttonSave.setOnClickListener(this);

        imageViewSapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery"
        };
        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        showFileChooser();
                        break;
                    /*case 1:
                        cameraChooser();
                        break;*/
                }
            }
        });
        pictureDialog.show();
    }

    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an image"), REQUEST_CODE);
    }

    private void cameraChooser(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, filePath);
        startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageViewSapi.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK && data != null){

            filePath = data.getData();

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageViewSapi.setImageBitmap(bitmap);
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

                            final Random random = new Random();
                            int numb = random.nextInt(100)+10;
                            String number = String.valueOf(numb);
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                            final String strDate = mdformat.format(calendar.getTime());

                            String jenisHewan = spinnerJenis.getSelectedItem().toString();
                            String bobotHewan = editTextBobot.getText().toString().trim();
                            String codeFix = "S"+number;
                            String namaImage = strDate;
                            String statusHewan ="sehat";

                            if(!TextUtils.isEmpty(bobotHewan)){
                                String code = databaseHewan.push().getKey();

                                DaftarHewan daftarHewan = new DaftarHewan(code, codeFix, jenisHewan, bobotHewan, namaImage, url, statusHewan);

                                databaseHewan.child(code).setValue(daftarHewan);

                                DatabaseReference databaseBobot = FirebaseDatabase.getInstance().getReference("bobot").child(codeFix);
                                DatabaseReference databasebbt = FirebaseDatabase.getInstance().getReference("bbt").child(codeFix);

                                String codeB = databaseBobot.push().getKey();
                                String Uid = user.getUid();
                                DaftarBobot daftarBobot1 = new DaftarBobot(codeB, codeFix, Uid, jenisHewan, bobotHewan, strDate);
                                databaseBobot.child(codeB).setValue(daftarBobot1);

                                databasebbt.setValue(daftarBobot1);

                                Intent intent = new Intent(getApplicationContext(), Hwean.class);
                                startActivity(intent);

                            }

                            Toast.makeText(getApplicationContext(), "Hewan berhasil ditambahkan", Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), TambahHewan.class);
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
        if(view == buttonSave){
            uploadFile();
            /*Intent intent = new Intent(getApplicationContext(), Hwean.class);
            startActivity(intent);*/
        }
        else {
            Toast.makeText(this, "Data ada yang salah", Toast.LENGTH_LONG).show();
        }
    }
}
