package com.example.admin.doc;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class medicineAdapter extends RecyclerView.Adapter<medicineAdapter.medicineViewHolder>{

    private Context mcntx;
    private List<medicine_details> medicine_detailsList;

    public medicineAdapter(Context mcntx, List<medicine_details> medicine_detailsList) {
        this.mcntx = mcntx;
        this.medicine_detailsList = medicine_detailsList;
    }

    @NonNull
    @Override
    public medicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mcntx);
        View view=inflater.inflate(R.layout.medicine_list,null);
        medicineAdapter.medicineViewHolder holder=new medicineAdapter.medicineViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull medicineViewHolder holder, int position) {

        final medicine_details medicine = medicine_detailsList.get(position);

        holder.medicinename.setText(String.valueOf(medicine.getMedicinename()));
        holder.manufacturer.setText(String.valueOf(medicine.getManufacturer()));

        holder.medinfo.setText(String.valueOf(medicine.getMedinfo()));
        holder.price.setText(String.valueOf(medicine.getPrice()));

        holder.mimg.setImageDrawable(mcntx.getResources().getDrawable(medicine.getImg()));

        holder.mimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcntx,"Selected a good medicine...", Toast.LENGTH_SHORT).show();
//                Intent med=new Intent(mcntx,buy_medicine.class);
//                mcntx.startActivity(med);
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicine_detailsList.size();
    }


    class medicineViewHolder extends RecyclerView.ViewHolder{

        ImageView mimg;
        TextView medicinename,manufacturer,medinfo,price;

        public medicineViewHolder(View itemView) {
            super(itemView);

            mimg=itemView.findViewById(R.id.medicine_img);
            medicine_detailsList=itemView.findViewById(R.id.medicine_Name);
            manufacturer=itemView.findViewById(R.id.manufacturer);
            medinfo=itemView.findViewById(R.id.quantity);
            price=itemView.findViewById(R.id.Pricetab);

        }
    }
}
