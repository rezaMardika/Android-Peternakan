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
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActivityProfil_Sapi extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference databaseImage;
    private ImageView imageViewProfil;
    private Button buttonUpload;
    private TextView textViewId;
    private TextView textViewBobot;
    private TextView textViewGender, textViewSakit;
    private EditText editTextNameImage;
    private Button buttonJadwal;
    private Button buttonRiwayat;

    public static final String HEWAN_ID = "code";
    public static final String HEWAN_CODE = "hewanId";

    private Uri filePath;

    private StorageReference storageReferenceSapi;

    private List<Data_Hewan> hewans;

    private static final int PICK_IMAGE_REQUEST = 234;
    private static final int REQUEST_CODE = 1234;
    private static final String FB_STORAGE_PATH = " image/";
    private static final String FB_DATABASE_PATH = " hewan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil__sapi);

        Intent intent = getIntent();
        final String code = intent.getStringExtra(ActivityHewan.HEWAN_ID);
        final String id = intent.getStringExtra(ActivityHewan.HEWAN_CODE);
        String bobot = intent.getStringExtra(ActivityHewan.HEWAN_BOBOT);
        String gender = intent.getStringExtra(ActivityHewan.HEWAN_GENDER);
        String nama = intent.getStringExtra(ActivityHewan.HEWAN_NAMA);
        String url = intent.getStringExtra(ActivityHewan.HEWAN_URL);
        String status = intent.getStringExtra(ActivityHewan.HEWAN_STATUS);

        storageReferenceSapi = FirebaseStorage.getInstance().getReference();
        databaseImage = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH).child(code);

        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewBobot = (TextView) findViewById(R.id.textViewBobot);
        textViewGender = (TextView) findViewById(R.id.textViewGender);
        textViewSakit = (TextView) findViewById(R.id.textViewSakit);
        //editTextNameImage = (EditText) findViewById(R.id.editTextNameImage);
        imageViewProfil = (ImageView) findViewById(R.id.imageViewProfil);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        buttonJadwal = (Button) findViewById(R.id.buttonJadwal);
        buttonRiwayat = (Button) findViewById(R.id.buttonRiwayat);

        Intent intentK = getIntent();
        final String idK = intentK.getStringExtra(ActivityHewan.KANDANG_ID);

        textViewId.setText("Code    :"+id);
        textViewBobot.setText("Bobot    :"+bobot+" kg");
        textViewGender.setText("Gender  :"+gender);
        textViewSakit.setText("Status :"+status);

        buttonUpload.setOnClickListener(this);

        imageViewProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        buttonJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityJadwalPakan.class);
                intent.putExtra(HEWAN_ID, code);
                intent.putExtra(HEWAN_CODE, id);
                startActivity(intent);
            }
        });

        buttonRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), ActivityRiwayat.class);
                intent1.putExtra(HEWAN_ID, code);
                intent1.putExtra(HEWAN_CODE, id);
                startActivity(intent1);
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
                            final String idK = intentK.getStringExtra(ActivityHewan.KANDANG_ID);

                            String id = intentK.getStringExtra(ActivityHewan.HEWAN_CODE);
                            String code = intentK.getStringExtra(ActivityHewan.HEWAN_ID);
                            String bobot = intentK.getStringExtra(ActivityHewan.HEWAN_BOBOT);
                            String gender = intentK.getStringExtra(ActivityHewan.HEWAN_GENDER);
                            String nama = intentK.getStringExtra(ActivityHewan.HEWAN_NAMA);
                            String status = intentK.getStringExtra(ActivityHewan.HEWAN_STATUS);
                            //String url = intentK.getStringExtra(Hwean.HEWAN_URL);

                            String codeHewan = code;
                            String idHewan = id;
                            String jenisHewan = gender;
                            String bobotHewan = bobot;
                            String namaGambar = nama;
                            String statusHewan = status;

                            updateProfil(idK, codeHewan, idHewan, jenisHewan, bobotHewan, namaGambar, urlBaru, statusHewan);
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
                            Intent intent = new Intent(getApplicationContext(), ActivityProfil_Sapi.class);
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

        Data_Hewan hewan = new Data_Hewan(code, id, gender, bobot, nama, url, status);

        databaseReference.child(code).setValue(hewan);
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
