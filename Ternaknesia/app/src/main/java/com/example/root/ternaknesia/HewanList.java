package com.example.root.ternaknesia;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.data.StreamAssetPathFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StringLoader;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jjoe64.graphview.DefaultLabelFormatter;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 05/07/17.
 */


public class HewanList extends RecyclerView.Adapter<HewanList.ViewHolder> {

    private List<DaftarHewan> hewans;
    private Context context;
    private int rowLayout;

    private DatabaseHelper helper;
    private SQLiteDatabase sqLiteDatabase;


    private DatabaseReference databaseRiwayat;
    private DatabaseReference databasePenyakit;
    private DatabaseReference databaseBobot;
    private DatabaseReference databaseSakit;

    public static final String KANDANG_ID = "kandangId";
    public static final String HEWAN_ID = "code";
    public static final String HEWAN_CODE = "hewanId";
    public static final String HEWAN_BOBOT = "hewanBobot";
    public static final String HEWAN_GENDER = "hewanGener";
    public static final String HEWAN_TANGGAL = "tanggal";
    public static final String HEWAN_URL = "url";
    public static final String HEWAN_STATUS = "status";

    private ImageView imageViewProfil;
    private Button buttonUpload;
    private TextView textViewId;
    private TextView textViewBobot;
    private TextView textViewGender, textViewClose, textViewPenyakit;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public HewanList(List<DaftarHewan> hewans, int rowLayout, Context context) {
        this.hewans = hewans;
        this.context = context;
        this.rowLayout = rowLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final DaftarHewan hewan = hewans.get(position);

        String uri = hewan.getUrl();
        String status = hewan.getStatus();
        holder.textViewCode.setText("Code: "+hewan.getHewanId());
        holder.textViewGender.setText(hewan.getHewanGender());
        holder.textViewPenyakit.setText(hewan.getStatus());

        String status1="sehat";
        if(status1.equals(status)){
            //getDeases(holder.imageViewHewan);
            getImage(holder.imageViewHewan, uri);
        }
        else {
            //getImage(holder.imageViewHewan, uri);
            getDeases(holder.imageViewHewan);
        }

        holder.imageViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu menu = new PopupMenu(context, holder.imageViewOption);
                menu.inflate(R.menu.main_context_menu1);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();

                        switch (id){
                            case R.id.update_id:
                                DaftarHewan daftarHewan =  hewans.get(position);

                                String Uid = user.getUid();
                                updateDialogHewan(daftarHewan.getCode(), daftarHewan.getHewanId(), daftarHewan.getHewanGender(), daftarHewan.getTanggal(), daftarHewan.getUrl(), daftarHewan.getStatus(), Uid);
                                break;
                            case R.id.pakan_id:
                                DaftarHewan daftarHewan1  = hewans.get(position);
                                Intent intent2 = new Intent(context, PakanHw.class);
                                intent2.putExtra(HEWAN_ID, daftarHewan1.getCode());
                                context.startActivity(intent2);
                                break;
                            case R.id.jadwal_id:
                                DaftarHewan daftarHewan2  = hewans.get(position);
                                Intent intent = new Intent(context, Pakan.class);
                                intent.putExtra(HEWAN_ID, daftarHewan2.getCode());
                                context.startActivity(intent);
                                break;
                            case R.id.riwayat_id:
                                final DaftarHewan daftarHewan3 = hewans.get(position);
                                Intent intent1 = new Intent(context, BobotActivity.class);
                                intent1.putExtra(HEWAN_ID, daftarHewan3.getCode());
                                intent1.putExtra(HEWAN_CODE, daftarHewan3.getHewanId());
                                context.startActivity(intent1);
                                break;
                            case R.id.penyakit_id:
                                final DaftarHewan daftarHewan4 = hewans.get(position);
                                penyakitHewan(daftarHewan4.getCode(), daftarHewan4.getHewanId(), daftarHewan4.getHewanBobot(), daftarHewan4.getHewanGender(), daftarHewan4.getTanggal(), daftarHewan4.getUrl(), daftarHewan4.getStatus());

                                break;
                            case R.id.potong_id:
                                final DaftarHewan daftarHewan5 = hewans.get(position);

                                potongHewan(daftarHewan5.getCode(), daftarHewan5.getHewanId(), user.getUid(),daftarHewan5.getHewanGender(), daftarHewan5.getHewanBobot(), daftarHewan5.getUrl());
                                break;
                            case R.id.profil_id:;
                                final DaftarHewan daftarHewan6 = hewans.get(position);

                                Intent intentK = ((Activity) context).getIntent();
                                final String idK = intentK.getStringExtra(Hwean.KANDANG_ID);

                                Intent intent3 = new Intent(context, Profil_Sapi.class);

                                intent3.putExtra(KANDANG_ID, idK);
                                intent3.putExtra(HEWAN_ID, daftarHewan6.getCode());
                                intent3.putExtra(HEWAN_CODE, daftarHewan6.getHewanId());
                                intent3.putExtra(HEWAN_BOBOT, daftarHewan6.getHewanBobot());
                                intent3.putExtra(HEWAN_GENDER, daftarHewan6.getHewanGender());
                                intent3.putExtra(HEWAN_URL, daftarHewan6.getUrl());
                                intent3.putExtra(HEWAN_STATUS, daftarHewan6.getStatus());
                                intent3.putExtra(HEWAN_TANGGAL, daftarHewan6.getTanggal());
                                context.startActivity(intent3);

                                //ShowPopUp(v, idK, daftarHewan6.getCode(), daftarHewan6.getHewanId(), daftarHewan6.getHewanBobot(), daftarHewan6.getHewanGender(), daftarHewan6.getUrl(), daftarHewan6.getStatus());
                                break;
                        }

                        return false;
                    }
                });
                menu.show();
            }
        });
    }

    public void getDeases(ImageView imageViewSakit){
        Glide.with(context)
                .load(R.drawable.ic_pmi)
                .placeholder(R.drawable.ic_menu_camera)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(imageViewSakit);
    }

    /*public void ShowPopUp(View v, String idK, String codeH, String idH, String bobotH, String genderH, String urlH, String statusH){

        dialog.setContentView(R.layout.activity_profil__sapi);
        textViewId = (TextView) dialog.findViewById(R.id.textViewId);
        textViewBobot = (TextView) dialog.findViewById(R.id.textViewBobot);
        textViewGender = (TextView) dialog.findViewById(R.id.textViewGender);
        buttonUpload = (Button) dialog.findViewById(R.id.buttonUpload);
        textViewClose = (TextView) dialog.findViewById(R.id.textViewClose);

        storageReferenceSapi = FirebaseStorage.getInstance().getReference();
        databaseImage = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH).child(codeH);

        imageViewProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        getImage(imageViewProfil, urlH);


        textViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }*/

    public void getImage(final ImageView imageViewHewan, String uri){
        Glide.with(context)
                .load(uri)
                .placeholder(R.drawable.ic_menu_camera)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(imageViewHewan);
    }

    @Override
    public int getItemCount() {
        return hewans == null ? 0 : hewans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewCode;
        public TextView textViewGender;
        public TextView textViewBobot, textViewPenyakit;
        public ImageView imageViewHewan;
        public ImageView imageViewOption;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewCode = (TextView) itemView.findViewById(R.id.textViewCode);
            textViewGender = (TextView) itemView.findViewById(R.id.textViewGender);
            textViewBobot = (TextView) itemView.findViewById(R.id.textViewBobot);
            imageViewHewan = (ImageView) itemView.findViewById(R.id.imageViewHewan);
            imageViewOption = (ImageView) itemView.findViewById(R.id.imageViewOption);
            textViewPenyakit = (TextView) itemView.findViewById(R.id.textViewPenyakit);
        }
    }

    private void insertData(int bobot, int Date) {
        int bulan = Date;

        helper.insertData(bulan, bobot);
    }

    private void updateDialogHewan(final String hewanCode, final String hewanId, final String hewanGender, final String namaImage, final String urlImage, final String statusHewan, final String Uid){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View dialogView = inflater.inflate(R.layout.update_dialog, null);

        dialogBuilder.setView(dialogView);

        final EditText editTextBobot = (EditText) dialogView.findViewById(R.id.editTextBobot);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final TextView textViewTest = (TextView) dialogView.findViewById(R.id.textViewTest);

        dialogBuilder.setTitle("Update Hewan "+hewanId);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        databaseBobot = FirebaseDatabase.getInstance().getReference("bobot").child(hewanId);

        final DatabaseReference databasebbt = FirebaseDatabase.getInstance().getReference("bbt").child(hewanId);
        /*DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query last = reference.child("bobot").child(hewanId).orderByKey().limitToLast(1);
        last.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot snapshot = (DataSnapshot) dataSnapshot.getChildren();
                String date = snapshot.getValue().toString();
                textViewTest.setText(date);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        final String strDate = mdformat.format(calendar.getTime());
        //String date = day+"-"+mounth+"-"+year;

        //textViewTest.setText(strDate.substring(0,7));

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = hewanId;
                final String gender = hewanGender;
                final String bobot = editTextBobot.getText().toString().trim();
                final String nama = namaImage;
                final String url = urlImage;
                final String status = statusHewan;

                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                final int mounth = c.get(Calendar.MONTH)+1;
                int year = c.get(Calendar.YEAR);

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                final String strTanggal = mdformat.format(calendar.getTime());
                //String date = day+"-"+mounth+"-"+year;

                //textViewTest.setText(strDate.substring(0,7));
                if (TextUtils.isEmpty(bobot)){
                    editTextBobot.setError("bobot required");
                    return;
                }

                databasebbt.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String date = dataSnapshot.child("dateB").getValue().toString();
                        String tanggal = date.substring(0,7);

                        String date1 = strDate.substring(0,7);
                        if (tanggal.equals(date1)){
                            Toast.makeText(context, "Data bobot bulan ini sudah terisi", Toast.LENGTH_LONG).show();
                        }else {
                            String codeB = databaseBobot.push().getKey();
                            DaftarBobot daftarBobot1 = new DaftarBobot(codeB, id, Uid, gender, bobot, strDate);
                            databaseBobot.child(codeB).setValue(daftarBobot1);

                            databasebbt.setValue(daftarBobot1);

                            updateHewan(hewanCode, id, gender, bobot, nama, url, status);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                alertDialog.dismiss();
            }
        });

    }

    private boolean updateHewan(String code, String id, String gender, String bobot, String nama, String url, String status){

        Intent intent = ((Activity) context).getIntent();

        String idK = intent.getStringExtra(Beranda1.KANDANG_ID);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("hewan").child(idK);

        DaftarHewan daftarHewan = new DaftarHewan(code, id, gender, bobot, nama, url, status);

        databaseReference.child(code).setValue(daftarHewan);
        Toast.makeText(context, "Data hewan berhasil diUpdate", Toast.LENGTH_LONG).show();

        return true;
    }

    private void penyakitHewan(final String codeHewan, final String idHewan, final String bobotHewan, final String gender, final String nama, final String urlHewan, final String statusHewan){

        AlertDialog.Builder penyakitBuilder = new AlertDialog.Builder(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View penyakitView = inflater.inflate(R.layout.penyakit_dialog, null);

        penyakitBuilder.setView(penyakitView);

        final Spinner spinnerPenyakit = (Spinner) penyakitView.findViewById(R.id.spinnerPenyakit);
        final EditText editTextNamaPenyakit = (EditText) penyakitView.findViewById(R.id.editTextNamaPenyakit);
        final Button buttonPeyakit = (Button) penyakitView.findViewById(R.id.buttonPenyakit);
        penyakitBuilder.setTitle("Data Sakit Hewan" +idHewan);

        final AlertDialog alertDialog = penyakitBuilder.create();
        alertDialog.show();

        Intent intent = ((Activity) context).getIntent();
        final String idK = intent.getStringExtra(Beranda1.KANDANG_ID);
        final String codeH = codeHewan;
        //final String idH = idHewan;
        databasePenyakit = FirebaseDatabase.getInstance().getReference("penyakit");
        final DatabaseReference dbRecrdP = FirebaseDatabase.getInstance().getReference("recordP");

        spinnerPenyakit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String data = parent.getItemAtPosition(position).toString();

                if(!data.equals("Lainnya")){
                    editTextNamaPenyakit.setEnabled(false);
                    editTextNamaPenyakit.setText(data);
                }else {
                    editTextNamaPenyakit.setEnabled(true);
                    editTextNamaPenyakit.setText(null);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*data_sakit.child("daftar_penyakit").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> penyakit = new ArrayList<String>();

                for(DataSnapshot penyakitSnapshot : dataSnapshot.getChildren()){
                    String namaPenyakit = penyakitSnapshot.child("namaPenyakit").getValue(String.class);
                    penyakit.add(namaPenyakit);
                }
                ArrayAdapter<String> penyakitAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, penyakit);
                penyakitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPenyakit.setAdapter(penyakitAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        buttonPeyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data, idP = null;
                Calendar c = Calendar.getInstance();

                final String idKandang = idK;
                final String idH = idHewan;
                final String bobotH = bobotHewan;
                String genderH = gender;
                String namaI = nama;
                String NamaP = editTextNamaPenyakit.getText().toString().trim();
                data = editTextNamaPenyakit.getText().toString().trim();
                String data1 = spinnerPenyakit.getSelectedItem().toString();
                editTextNamaPenyakit.setText(data1);

                if (data1.equals("Anthrax"))
                    idP = "001";
                else if (data1.equals("Surra"))
                    idP = "002";
                else if (data1.equals("Kuku Busuk"))
                    idP = "003";
                else if (data1.equals("Kembung Perut"))
                    idP = "004";
                else if (data1.equals("Demam"))
                    idP = "005";
                else if (data1.equals("Ingusan"))
                    idP = "006";
                else if (data1.equals("Kudis"))
                    idP = "007";
                else if (data1.equals("Cacingan"))
                    idP = "008";
                else if (data1.equals("Ngorok"))
                    idP = "009";
                else if (data1.equals("Diare"))
                    idP = "010";
                else if (data1.equals("Brucellosis"))
                    idP = "011";
                else if (data1.equals("Radang Paha"))
                    idP = "012";
                else if (data1.equals("Botulisme"))
                    idP = "013";
                else if (data1.equals("Lainnya"))
                    idP = "0014";

                int day = c.get(Calendar.DAY_OF_MONTH);
                int mounth = c.get(Calendar.MONTH)+1;
                int year = c.get(Calendar.YEAR);
                final String date = day+"-"+mounth+"-"+year;
                String url = urlHewan;
                String tanggalSembuh = "-";

                if (TextUtils.isEmpty(NamaP)){
                    editTextNamaPenyakit.setError("Required");
                    return;
                }

                DaftarRecordPenyakit recordPenyakit = new DaftarRecordPenyakit(codeHewan, idP, data, date, tanggalSembuh);
                DaftarPenyakit penyakit = new DaftarPenyakit(idP, codeH, idKandang, idH, data, bobotH, date);

                databasePenyakit.child(idP).child(codeH).setValue(penyakit);
                dbRecrdP.child(codeHewan).child(idP).setValue(recordPenyakit);

                updateHewanSakit(codeH, idH, genderH, bobotH, namaI, url, data1);
                alertDialog.dismiss();
                Toast.makeText(context, "Data penyakit sukses disimpan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean updateHewanSakit(String code, String id, String gender, String bobot, String nama, String url, String status){

        Intent intent = ((Activity) context).getIntent();

        String idK = intent.getStringExtra(Beranda1.KANDANG_ID);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("hewan").child(idK);
        String status1 = "sakit";
        DaftarHewan daftarHewan = new DaftarHewan(code, id, gender, bobot, nama, url, status);

        databaseReference.child(code).setValue(daftarHewan);

        Toast.makeText(context, "Data hewan berhasil diUpdate", Toast.LENGTH_LONG).show();

        return true;
    }

    private void potongHewan(final String codeHewan, final String idHewan, final String Uid, final String genderHewan, final String bobotHewan, final String urlHewan){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final String codeH = codeHewan;
        final String idH = idHewan;

        Intent intent = ((Activity) context).getIntent();

        String idK = intent.getStringExtra(Beranda1.KANDANG_ID);

        DatabaseReference dbhewan = FirebaseDatabase.getInstance().getReference("hewan").child(idK).child(codeH);

        dbhewan.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String bobot = dataSnapshot.child("hewanBobot").getValue().toString();
                final String data1 = dataSnapshot.child("status").getValue().toString();
                int bbt = Integer.parseInt(bobot);
                String idP = null;

                if (bbt >= 350){
                    if (data1.equals("Anthrax"))
                        idP = "001";
                    else if (data1.equals("Surra"))
                        idP = "002";
                    else if (data1.equals("Kuku Busuk"))
                        idP = "003";
                    else if (data1.equals("Kembung Perut"))
                        idP = "004";
                    else if (data1.equals("Demam"))
                        idP = "005";
                    else if (data1.equals("Ingusan"))
                        idP = "006";
                    else if (data1.equals("Kudis"))
                        idP = "007";
                    else if (data1.equals("Cacingan"))
                        idP = "008";
                    else if (data1.equals("Ngorok"))
                        idP = "009";
                    else if (data1.equals("Diare"))
                        idP = "010";
                    else if (data1.equals("Brucellosis"))
                        idP = "011";
                    else if (data1.equals("Radang Paha"))
                        idP = "012";
                    else if (data1.equals("Botulisme"))
                        idP = "013";
                    else if (data1.equals("Lainnya"))
                        idP = "0014";

                    DatabaseReference dbPenyakit = FirebaseDatabase.getInstance().getReference("RecordHewan");

                    final DatabaseReference dbPnyakit = FirebaseDatabase.getInstance().getReference("RecordHewan").child(codeH);

                    dbPenyakit.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(codeH).exists()){

                                dbPnyakit.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        String tanggal = dataSnapshot.child("dateP").getValue().toString();
                                        String namaP = dataSnapshot.child("namaP").getValue().toString();

                                        builder.setTitle("Potong "+idH).setMessage("Anda Yakin? Riwayat Penyakit hewan: "+namaP+" pada tanggal: "+tanggal).setIcon(android.R.drawable.ic_dialog_alert)
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        //action Yes

                                                        databaseRiwayat = FirebaseDatabase.getInstance().getReference("riwayat");

                                                        Calendar c = Calendar.getInstance();

                                                        Intent intent = ((Activity) context).getIntent();
                                                        String nama = intent.getStringExtra(Beranda1.KANDANG_NAME);

                                                        String bulan = "";
                                                        String urlH = urlHewan;
                                                        String bobotH = bobotHewan;
                                                        String genderH = genderHewan;
                                                        int day = c.get(Calendar.DAY_OF_MONTH);
                                                        int month = c.get(Calendar.MONTH) + 1;
                                                        int year = c.get(Calendar.YEAR);

                                                        if(month == 1)
                                                            bulan ="Januari";
                                                        else if(month == 2)
                                                            bulan = "Febuari";
                                                        else if(month == 3)
                                                            bulan = "Maret";
                                                        else if(month == 4)
                                                            bulan = "April";
                                                        else if(month == 5)
                                                            bulan = "Mei";
                                                        else if(month == 6)
                                                            bulan = "Juni";
                                                        else if(month == 7)
                                                            bulan = "Juli";
                                                        else if(month == 8)
                                                            bulan = "Agustus";
                                                        else if(month == 9)
                                                            bulan = "September";
                                                        else if(month == 10)
                                                            bulan = "Oktober";
                                                        else if(month == 11)
                                                            bulan = "November";
                                                        else
                                                            bulan = "Desember";

                                                        String date = day+"-"+bulan+"-"+year;
                                                        String periode = bulan+"-"+year;

                                                        String idR = databaseRiwayat.push().getKey();

                                                        DaftarRiwayat riwayat = new DaftarRiwayat(idR, idH, Uid, bobotH, genderH, date, nama, urlH, periode);

                                                        databaseRiwayat.child(idR).setValue(riwayat);
                                                        delete(codeH);
                                                        Toast.makeText(context, "Data Riwayat berhasil disimpan", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                }).show();

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                /*String tanggal = dataSnapshot.child(codeH).child("dateP").getValue().toString();
                                String namaP = dataSnapshot.child(codeH).child("namaP").getValue().toString();

                                builder.setTitle("Potong "+idH).setMessage("Anda Yakin? Riwayat Penyakit hewan: "+namaP+" pada tanggal: "+tanggal).setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //action Yes

                                                databaseRiwayat = FirebaseDatabase.getInstance().getReference("riwayat");

                                                Calendar c = Calendar.getInstance();

                                                Intent intent = ((Activity) context).getIntent();
                                                String nama = intent.getStringExtra(Beranda1.KANDANG_NAME);

                                                String bulan = "";
                                                String urlH = urlHewan;
                                                String bobotH = bobotHewan;
                                                String genderH = genderHewan;
                                                int day = c.get(Calendar.DAY_OF_MONTH);
                                                int month = c.get(Calendar.MONTH) + 1;
                                                int year = c.get(Calendar.YEAR);

                                                if(month == 1)
                                                    bulan ="Januari";
                                                else if(month == 2)
                                                    bulan = "Febuari";
                                                else if(month == 3)
                                                    bulan = "Maret";
                                                else if(month == 4)
                                                    bulan = "April";
                                                else if(month == 5)
                                                    bulan = "Mei";
                                                else if(month == 6)
                                                    bulan = "Juni";
                                                else if(month == 7)
                                                    bulan = "Juli";
                                                else if(month == 8)
                                                    bulan = "Agustus";
                                                else if(month == 9)
                                                    bulan = "September";
                                                else if(month == 10)
                                                    bulan = "Oktober";
                                                else if(month == 11)
                                                    bulan = "November";
                                                else
                                                    bulan = "Desember";

                                                String date = day+"-"+bulan+"-"+year;
                                                String periode = bulan+"-"+year;

                                                String idR = databaseRiwayat.push().getKey();

                                                DaftarRiwayat riwayat = new DaftarRiwayat(idR, idH, Uid, bobotH, genderH, date, nama, urlH, periode);

                                                databaseRiwayat.child(idR).setValue(riwayat);
                                                delete(codeH);
                                                Toast.makeText(context, "Data Riwayat berhasil disimpan", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).show();*/
                            }else {

                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }else {
                    Toast.makeText(context, "Maaf Bobot Hewan Belum Mencukupi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*builder.setTitle("Potong "+idH).setMessage("Anda Yakin?").setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //action Yes

                        databaseRiwayat = FirebaseDatabase.getInstance().getReference("riwayat");

                        Calendar c = Calendar.getInstance();

                        Intent intent = ((Activity) context).getIntent();
                        String nama = intent.getStringExtra(Beranda1.KANDANG_NAME);

                        String bulan = "";
                        String urlH = urlHewan;
                        String bobotH = bobotHewan;
                        String genderH = genderHewan;
                        int day = c.get(Calendar.DAY_OF_MONTH);
                        int month = c.get(Calendar.MONTH) + 1;
                        int year = c.get(Calendar.YEAR);

                        if(month == 1)
                            bulan ="Januari";
                        else if(month == 2)
                            bulan = "Febuari";
                        else if(month == 3)
                            bulan = "Maret";
                        else if(month == 4)
                            bulan = "April";
                        else if(month == 5)
                            bulan = "Mei";
                        else if(month == 6)
                            bulan = "Juni";
                        else if(month == 7)
                            bulan = "Juli";
                        else if(month == 8)
                            bulan = "Agustus";
                        else if(month == 9)
                            bulan = "September";
                        else if(month == 10)
                            bulan = "Oktober";
                        else if(month == 11)
                            bulan = "November";
                        else
                            bulan = "Desember";

                        String date = day+"-"+bulan+"-"+year;
                        String periode = bulan+"-"+year;

                        String idR = databaseRiwayat.push().getKey();

                        DaftarRiwayat riwayat = new DaftarRiwayat(idR, idH, Uid, bobotH, genderH, date, nama, urlH, periode);

                        databaseRiwayat.child(idR).setValue(riwayat);
                        delete(codeH);
                        Toast.makeText(context, "Data Riwayat berhasil disimpan", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();*/
    }


    private void delete(String code) {
        Intent intent = ((Activity) context).getIntent();
        String id = intent.getStringExtra(Beranda1.KANDANG_ID);
        DatabaseReference dataHewan = FirebaseDatabase.getInstance().getReference("hewan").child(id).child(code);

        dataHewan.removeValue();
    }

}