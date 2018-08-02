package com.example.root.ternaknesia;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Beranda1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String KANDANG_NAME = "namaKandang";
    public static final String KANDANG_ID = "kandangId";

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseKandang;
    DatabaseReference databaseUser;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    //private ListView listViewKandang;
    private RecyclerView recyclerViewKandang;
    private RecyclerView.Adapter adapter;
    private List<DaftarKandang> kandangList;
    //List<DaftarKandang>kandangList;

    private TextView textViewNama, textViewEmail;
    private ImageView imageViewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseKandang = FirebaseDatabase.getInstance().getReference("kandang");
        //listViewKandang = (ListView) findViewById(R.id.listViewKandang);
        recyclerViewKandang = (RecyclerView) findViewById(R.id.recyclerViewKandang);
        recyclerViewKandang.setHasFixedSize(true);
        recyclerViewKandang.setLayoutManager(new LinearLayoutManager(this));

        //kandangList = new ArrayList<>();

        databaseKandang.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                kandangList = new ArrayList<DaftarKandang>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    DaftarKandang kandang = dataSnapshot1.getValue(DaftarKandang.class);
                    /*DaftarKandang daftar = new DaftarKandang();
                    String nama = kandang.getNamaKandang();
                    String kapasitas = kandang.getKapasitasKandang();

                    daftar.setNamaKandang(nama);
                    daftar.setKapasitasKandang(kapasitas);*/
                    kandangList.add(kandang);
                }
                KandangList adapter = new KandangList(kandangList, R.layout.list_layout, Beranda1.this);
                RecyclerView.LayoutManager manager = new GridLayoutManager(Beranda1.this, 2);
                recyclerViewKandang.setLayoutManager(manager);
                recyclerViewKandang.setItemAnimator(new DefaultItemAnimator());
                recyclerViewKandang.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (firebaseAuth == null){
            finish();
            startActivity(new Intent(this, login.class));
        }

        /*listViewKandang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DaftarKandang daftarKandang = kandangList.get(position);
                Intent intent = new Intent(getApplicationContext(), Hwean.class);
                intent.putExtra(KANDANG_ID, daftarKandang.getKandangId());
                intent.putExtra(KANDANG_NAME, daftarKandang.getNamaKandang());
                startActivity(intent);
            }
        });*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplication(), InKandang.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        databaseUser = FirebaseDatabase.getInstance().getReference("peternak");
        //View view = navigationView.inflateHeaderView(R.layout.nav_header_activity_beranda);
        View header=navigationView.getHeaderView(0);

        textViewEmail = (TextView) header.findViewById(R.id.textViewEmail);
        textViewNama = (TextView) header.findViewById(R.id.textViewNama);
        imageViewUser = (ImageView) header.findViewById(R.id.imageViewUser);
        String email = user.getEmail();

        textViewEmail.setText(email);

        databaseUser.orderByChild("userEmail").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    DaftarPeternak peternak = userSnapshot.getValue(DaftarPeternak.class);
                    //String idUser = peternak.getUserId();
                    String namaUser = peternak.getUserName();
                    String fotoUser = peternak.getUserFoto();

                    textViewNama.setText(namaUser);
                    getImage(imageViewUser, fotoUser);
                    /*DatabaseReference dbUser = FirebaseDatabase.getInstance().getReference("peternak").child(idUser);

                    DB(dbUser);*/
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getImage(final ImageView imageViewHewan, String uri){

        Glide.with(getApplicationContext())
                .load(uri)
                .placeholder(R.drawable.ic_menu_camera)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(imageViewHewan);
    }


    /*@Override
    protected void onStart() {
        super.onStart();

        databaseKandang.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                kandangList.clear();

                for(DataSnapshot kandangSnapshot : dataSnapshot.getChildren()){
                    DaftarKandang daftarKandang = kandangSnapshot.getValue(DaftarKandang.class);

                    kandangList.add(daftarKandang);
                }

                KandangList adapter = new KandangList(Beranda1.this, kandangList);
                listViewKandang.setAdapter(adapter);

                registerForContextMenu(listViewKandang);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_context_menu2, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int id = item.getItemId();

        switch(id){
            case R.id.buka_id:
                break;
            case R.id.hapus_id:
                DaftarKandang daftarKandang = kandangList.get(info.position);

                deleteKandang(daftarKandang.getKandangId());
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void deleteKandang(String kandangId) {
        DatabaseReference deleteKandang = FirebaseDatabase.getInstance().getReference("kandang").child(kandangId);
        DatabaseReference deleteHewan = FirebaseDatabase.getInstance().getReference("hewan").child(kandangId);

        deleteKandang.removeValue();
        deleteHewan.removeValue();

        Toast.makeText(this, "Kandang berhasil dihapus", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.beranda1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){

            case R.id.nav_beranda:
                Intent h = new Intent(Beranda1.this, Beranda1.class);
                startActivity(h);
                break;
            case R.id.nav_penyakit:
                Intent j = new Intent(Beranda1.this, Penyakit.class);
                startActivity(j);
                break;
            case R.id.nav_riwayat:
                Intent k = new Intent(Beranda1.this, Riwayat.class);
                startActivity(k);
                break;
            case R.id.nav_keluar:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, login.class));
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
