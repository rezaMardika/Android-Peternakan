package com.example.root.owner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityBeranda extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String KANDANG_NAME = "namaKandang";
    public static final String KANDANG_ID = "kandangId";

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseKandang;
    DatabaseReference databaseUser;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private RecyclerView recyclerViewKandang;
    private RecyclerView.Adapter adapter;
    List<Data_Kandang> kandangList;

    private NavigationView mNavigationView;
    private TextView textViewEmail, textViewNama;
    private ImageView imageViewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Beranda");
        setContentView(R.layout.activity_beranda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseKandang = FirebaseDatabase.getInstance().getReference("kandang");
        //listViewKandang = (ListView) findViewById(R.id.listViewKandang);

        kandangList = new ArrayList<>();
        if (firebaseAuth == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        recyclerViewKandang = (RecyclerView) findViewById(R.id.recycle);
        recyclerViewKandang.setHasFixedSize(true);
        recyclerViewKandang.setLayoutManager(new LinearLayoutManager(this));

        //Tampil Data
        databaseKandang.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                kandangList = new ArrayList<Data_Kandang>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Data_Kandang data_kandang = dataSnapshot1.getValue(Data_Kandang.class);
                    kandangList.add(data_kandang);
                }
                Kandang_List adapter = new Kandang_List(kandangList, R.layout.list_kadang, ActivityBeranda.this);
                RecyclerView.LayoutManager manager = new GridLayoutManager(ActivityBeranda.this, 1);
                recyclerViewKandang.setLayoutManager(manager);
                recyclerViewKandang.setItemAnimator(new DefaultItemAnimator());
                recyclerViewKandang.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        databaseUser = FirebaseDatabase.getInstance().getReference("Owner");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
                    DaftarOwner owner = userSnapshot.getValue(DaftarOwner.class);
                    //String idUser = peternak.getUserId();
                    String namaUser = owner.getUserName();
                    String fotoUser = owner.getUserUrl();

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

    public void getImage(final ImageView imageViewUser, String uri){

        Glide.with(getApplicationContext())
                .load(uri)
                .placeholder(R.drawable.ic_menu_camera)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(imageViewUser);
    }

   /* @Override
    protected void onStart() {
        super.onStart();

        databaseKandang.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                kandangList.clear();

                for(DataSnapshot kandangSnapshot : dataSnapshot.getChildren()){
                    Data_Kandang kandang = kandangSnapshot.getValue(Data_Kandang.class);

                    kandangList.add(kandang);
                }

                Kandang_List adapter = new Kandang_List(ActivityBeranda.this, kandangList);
                //listViewKandang.setAdapter(adapter);

                //registerForContextMenu(listViewKandang);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    } */

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
        getMenuInflater().inflate(R.menu.activity_beranda, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){

            case R.id.nav_beranda:
                Intent h = new Intent(ActivityBeranda.this, ActivityBeranda.class);
                startActivity(h);
                break;
            case R.id.nav_petenak:
                Intent peternak = new Intent(ActivityBeranda.this, ActivityPeternak.class);
                startActivity(peternak);
                break;
            case R.id.nav_pakan:
                Intent p = new Intent(ActivityBeranda.this, ActivityPakan.class);
                startActivity(p);
                break;
            case R.id.nav_penyakit:
                Intent j = new Intent(ActivityBeranda.this, ActivityPenyakit.class);
                startActivity(j);
                break;
            case R.id.nav_riwayat:
                Intent k = new Intent(ActivityBeranda.this, ActivityPotong.class);
                startActivity(k);
                break;
            case R.id.nav_keluar:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
