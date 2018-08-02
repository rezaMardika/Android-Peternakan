package com.example.root.owner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ActivityProfilUser extends AppCompatActivity {

    private ImageView imageViewProfil;
    private TextView textViewEmail, textViewNama, textViewAlamat, textViewJob;
    private Button buttonUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);

        imageViewProfil = (ImageView) findViewById(R.id.imageViewProfil);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewNama = (TextView) findViewById(R.id.textViewNama);
        textViewAlamat = (TextView) findViewById(R.id.textViewAlamat);
        textViewJob = (TextView) findViewById(R.id.textViewJob);

        Intent intent = getIntent();
        final String id = intent.getStringExtra(ActivityPeternak.id_peternak);
        String nama = intent.getStringExtra(ActivityPeternak.nama_peternak);
        String alamat = intent.getStringExtra(ActivityPeternak.alamat_peternak);
        String email = intent.getStringExtra(ActivityPeternak.email_peternak);
        String job = intent.getStringExtra(ActivityPeternak.job_peternak);
        String url = intent.getStringExtra(ActivityPeternak.url_peternak);

        textViewEmail.setText(nama);
        textViewEmail.setText(email);
        textViewAlamat.setText(alamat);
        textViewJob.setText(job);

        getImage(imageViewProfil, url);
    }

    public void getImage(final ImageView imageViewHewan, String uri){
        Glide.with(getApplication())
                .load(uri)
                .placeholder(R.drawable.ic_menu_camera)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(imageViewHewan);

    }
}
