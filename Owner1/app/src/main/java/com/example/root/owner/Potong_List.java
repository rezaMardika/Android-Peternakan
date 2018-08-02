package com.example.root.owner;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 21/09/17.
 */

public class Potong_List extends ArrayAdapter<Data_Potong> {

    private List<Data_Potong> potongList;
    private Activity context;
    private Filter filter;

    public Potong_List(Activity context, int layoutResourceId, List<Data_Potong> potongList) {
        super(context, layoutResourceId, potongList);
        this.context = context;
        this.potongList = potongList;
    }

    @Override
    public int getCount() {
        return potongList.size();
    }

    @Nullable
    @Override
    public Data_Potong getItem(int position) {
        return potongList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(potongList.get(position).getIdH());
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_potong, null, true);

        final TextView textViewId = (TextView) listViewItem.findViewById(R.id.textViewId);
        final TextView textViewBobot = (TextView) listViewItem.findViewById(R.id.textViewBobot);
        final TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDate);
        final TextView textViewKandang = (TextView) listViewItem.findViewById(R.id.textViewKandang);

        Data_Potong potong = potongList.get(position);

        textViewId.setText(potong.getCodeH());
        textViewBobot.setText(potong.getBobotH());
        textViewDate.setText(potong.getTanggalH());
        textViewKandang.setText(potong.getKandangH());

        return listViewItem;
        /*View row = convertView;
        final ViewHolder holder;

        if(row == null){
            LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
            row = layoutInflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.textViewId = (TextView) row.findViewById(R.id.textViewId);
            holder.textViewBobot = (TextView) row.findViewById(R.id.textViewBobot);
            holder.textViewDate = (TextView) row.findViewById(R.id.textViewDate);
            holder.textViewKandang = (TextView) row.findViewById(R.id.textViewKandang);

            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Data_Potong potong = potongList.get(position);

        holder.textViewId.setText(potong.getIdH());
        holder.textViewBobot.setText(potong.getBobotH());
        holder.textViewDate.setText(potong.getTanggalH());
        holder.textViewKandang.setText(potong.getKandangH());

        return row;*/
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new AppFilter<Data_Potong>(potongList);
        }

        return filter;
    }

    private class AppFilter<T> extends Filter{

        private ArrayList<T> sourceData;

        public AppFilter(List<T> potongList){
            sourceData = new ArrayList<T>();
            synchronized (this){
                sourceData.addAll(potongList);
            }
        }


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterData = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();

            if(filterData != null && filterData.length() > 0){
                ArrayList<T> filter = new ArrayList<T>();

                for (T object : sourceData){
                    if(object.toString().toLowerCase().contains(filterData))
                        filter.add(object);

                }
                results.count = filter.size();
                results.values = filter;
            }
            else {
                synchronized (this){
                    results.values = sourceData;
                    results.count = sourceData.size();
                }
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<T> filtered = (ArrayList<T>) results.values;
            notifyDataSetChanged();
            clear();
            int i, l;
            l = filtered.size();
            for (i = 0; i<l; i++)
                add((Data_Potong) filtered.get(i));
            notifyDataSetChanged();
        }
    }

    /*@NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater inflater = context.getLayoutInflater();

                View listViewItem = inflater.inflate(R.layout.list_potong, null, true);

                final TextView textViewId = (TextView) listViewItem.findViewById(R.id.textViewId);
                final TextView textViewBobot = (TextView) listViewItem.findViewById(R.id.textViewBobot);
                final TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDate);
                final TextView textViewKandang = (TextView) listViewItem.findViewById(R.id.textViewKandang);

                Data_Potong potong = potongList.get(position);

                textViewId.setText(potong.getIdH());
                textViewBobot.setText(potong.getBobotH());
                textViewDate.setText(potong.getTanggalH());
                textViewKandang.setText(potong.getKandangH());

                return listViewItem;


            }*/
    static class ViewHolder{
        TextView textViewId;
        TextView textViewBobot;
        TextView textViewDate;
        TextView textViewKandang;

    }
}
