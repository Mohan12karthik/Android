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

public class pharmacyAdapter extends RecyclerView.Adapter<pharmacyAdapter.pharmacyViewHolder>{

    private Context mcontext;
    private List<pharmacy_details> pharmacy_detailsList;

    public pharmacyAdapter(Context mcontext, List<pharmacy_details> pharmacy_detailsList) {
        this.mcontext = mcontext;
        this.pharmacy_detailsList = pharmacy_detailsList;
    }

    @NonNull
    @Override
    public pharmacyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mcontext);
        View view=inflater.inflate(R.layout.pharm_list,null);
        pharmacyAdapter.pharmacyViewHolder holder=new pharmacyAdapter.pharmacyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull pharmacyViewHolder holder, int position) {

        final pharmacy_details pharmacy=pharmacy_detailsList.get(position);

        holder.PharmName.setText(String.valueOf(pharmacy.getPharmname()));
        holder.Address.setText(String.valueOf(pharmacy.getAddress()));

        holder.Phone.setText(String.valueOf(pharmacy.getPhone()));
        holder.logo.setImageDrawable(mcontext.getResources().getDrawable(pharmacy.getLogo()));

        holder.logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,"Selected a good pharmacy...", Toast.LENGTH_SHORT).show();
                Intent pharm_med=new Intent(mcontext,pharm_medicine.class);
                mcontext.startActivity(pharm_med);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pharmacy_detailsList.size();
    }

    class pharmacyViewHolder extends RecyclerView.ViewHolder{

        ImageView logo;
        TextView PharmName,Address,Phone;

        public pharmacyViewHolder(View itemView) {
            super(itemView);

            logo=itemView.findViewById(R.id.logo);
            PharmName=itemView.findViewById(R.id.PharmName);
            Address=itemView.findViewById(R.id.Address);
            Phone=itemView.findViewById(R.id.Phone);


        }
    }
}
