package com.example.root.owner;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;

/**
 * Created by root on 15/09/17.
 */

public class Pakan_List extends RecyclerView.Adapter<Pakan_List.ViewHolder> {

    private List<Data_Pakan> pakanList;
    private Context context;
    private int rowLayout;

    public Pakan_List(List<Data_Pakan> pakanList, int rowLayout, Context context) {
        this.pakanList = pakanList;
        this.context = context;
        this.rowLayout = rowLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        //ViewHolder viewHolder = new ViewHolder(v);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Data_Pakan pakan = pakanList.get(position);

        holder.textViewNama.setText(pakan.getNamaPakan());
        holder.textViewDate.setText(pakan.getDatePakan());
        holder.textViewKapasitas.setText(pakan.getKapasitasPakan());

        String banyak = pakan.getKapasitasPakan();
        float byk = Float.parseFloat(banyak);
        if(byk < 5){
            holder.imageViewNotif.setImageResource(R.drawable.ic_warning);
        }
    }

    @Override
    public int getItemCount() {
        return pakanList == null ? 0 : pakanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNama;
        public TextView textViewDate;
        public TextView textViewKapasitas;
        public ImageView imageViewNotif;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewNama = (TextView) itemView.findViewById(R.id.textViewNama);
            textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
            textViewKapasitas = (TextView) itemView.findViewById(R.id.textViewKapasitas);
            imageViewNotif = (ImageView) itemView.findViewById(R.id.imageViewNotif);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Context context = v.getContext();
                    Data_Pakan pakan = pakanList.get(position);

                    String idP = pakan.getIdPakan();
                    String nama = pakan.getNamaPakan();
                    String banyak = pakan.getKapasitasPakan();

                    updatePakan(idP, nama, banyak);
                }
            });
        }
    }

    private void updatePakan(final String idP, final String nama, final String banyak){
        AlertDialog.Builder pakanBuilder = new AlertDialog.Builder(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View pakanView = inflater.inflate(R.layout.dialog_update_pakan, null);

        pakanBuilder.setView(pakanView);

        final EditText editTextBanyak = (EditText) pakanView.findViewById(R.id.editTextBanyak);
        final Button buttonTambah = (Button) pakanView.findViewById(R.id.buttonTambah);
        pakanBuilder.setTitle("Tambahkan pakan");

        final AlertDialog alertDialog = pakanBuilder.create();
        alertDialog.show();

        buttonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();

                String idPakan = idP;
                String NamaPakan = nama;
                String Banyak = editTextBanyak.getText().toString().trim();
                String BanyakL = banyak;

                float lama = Float.parseFloat(BanyakL);
                float baru = Float.parseFloat(Banyak);
                float fix = lama + baru;
                String jumlah = Float.toString(fix);

                int day = c.get(Calendar.DAY_OF_MONTH);
                int mounth = c.get(Calendar.MONTH)+1;
                int year = c.get(Calendar.YEAR);
                String date = day+"-"+mounth+"-"+year;

                if (TextUtils.isEmpty(Banyak)){
                    editTextBanyak.setError("Required");
                    return;
                }

                update(idPakan, NamaPakan, jumlah, date);
                alertDialog.dismiss();

            }
        });

    }

    private boolean update(String id, String nama, String banyak, String date){

        DatabaseReference databasePakan = FirebaseDatabase.getInstance().getReference("pakan");

        Data_Pakan pakan = new Data_Pakan(id, nama, banyak, date);

        databasePakan.child(id).setValue(pakan);
        Toast.makeText(context, "Data Pakan berhasil diUpdate", Toast.LENGTH_LONG).show();

        return true;
    }

}
