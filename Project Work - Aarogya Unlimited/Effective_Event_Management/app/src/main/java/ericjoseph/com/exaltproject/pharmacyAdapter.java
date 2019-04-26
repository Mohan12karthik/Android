package ericjoseph.com.exaltproject;

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

import com.squareup.picasso.Picasso;

import java.util.List;

public class pharmacyAdapter extends RecyclerView.Adapter<pharmacyAdapter.pharmacyViewHolder>{

    public Context pharma_context;
    private List<pharmacy_details> pharmacy_detailsList;
    public pharmacyViewHolder holder;

    public pharmacyAdapter(Context mcontext, List<pharmacy_details> p_detailsList) {
        pharma_context = mcontext;
        pharmacy_detailsList = p_detailsList;
    }

    @NonNull
    @Override
    public pharmacyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(pharma_context);
        View view = inflater.inflate(R.layout.pharm_list,null);
        holder = new pharmacyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull pharmacyViewHolder holder, int position) {

        final pharmacy_details pharmacy=pharmacy_detailsList.get(position);

        holder.PharmName.setText(String.valueOf(pharmacy.getPharmname()));
        holder.Address.setText(String.valueOf(pharmacy.getAddress()));

        holder.Phone.setText(String.valueOf(pharmacy.getPhone()));
        Picasso.get().load(String.valueOf(pharmacy.getLogo())).placeholder(R.drawable.loading_icon).into(holder.img_logo);
        //holder.img_logo.setImageDrawable(pharma_context.getResources().getDrawable(pharmacy.getLogo()));

        holder.img_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(pharma_context,"Selected a good pharmacy...", Toast.LENGTH_SHORT).show();
                Intent pharm_med=new Intent(pharma_context,buy_medicine.class);
                pharma_context.startActivity(pharm_med);*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return pharmacy_detailsList.size();
    }

    class pharmacyViewHolder extends RecyclerView.ViewHolder{

        ImageView img_logo;
        TextView PharmName,Address,Phone;

        public pharmacyViewHolder(View itemView) {
            super(itemView);

            img_logo = itemView.findViewById(R.id.logo);
            //img_logo.setImageResource(R.drawable.pharmacy_logo_img);
            PharmName=itemView.findViewById(R.id.PharmName);
            Address=itemView.findViewById(R.id.Address);
            Phone=itemView.findViewById(R.id.Phone);
        }
    }
}
