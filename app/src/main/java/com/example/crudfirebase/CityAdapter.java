package com.example.crudfirebase;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.viewholder> {
    private final Context context;
    private final ArrayList<City> list;


    public CityAdapter(Context context, ArrayList<City> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CityAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_city, null);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.viewholder holder, int position) {

        holder.txtname.setText(list.get(position).getName());
        holder.txtcapital.setText(list.get(position).getCapital());
        holder.txtcountry.setText(list.get(position).getCountry());
        holder.txtregion.setText(list.get(position).getRegions());
        holder.txtpopula.setText(list.get(position).getPopulation());
        holder.txtstate.setText(list.get(position).getState());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        // khai bao bien
        TextView txtname, txtstate, txtcountry, txtcapital,txtpopula,txtregion;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            // anh xa
            txtname = itemView.findViewById(R.id.showname);
            txtstate = itemView.findViewById(R.id.showstate);
            txtcountry = itemView.findViewById(R.id.showcountry);
            txtcapital = itemView.findViewById(R.id.showcapital);
            txtpopula = itemView.findViewById(R.id.showpopulation);
            txtregion = itemView.findViewById(R.id.showregions);
        }


    }

}
