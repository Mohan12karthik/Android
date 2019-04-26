package ericjoseph.com.exaltproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class doctorAdapter extends RecyclerView.Adapter<doctorAdapter.doctorViewHolder>{

    private Context mctx;
    private List<doctor_details> doctor_detailsList;

    public doctorAdapter(Context mctx, List<doctor_details> doctor_detailsList) {
        this.mctx = mctx;
        this.doctor_detailsList = doctor_detailsList;
    }

    @NonNull
    @Override
    public doctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mctx);
        View view=inflater.inflate(R.layout.layout_list,null);
        doctorViewHolder holder = new doctorViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull doctorViewHolder holder, int position) {

        final doctor_details doctor=doctor_detailsList.get(position);

        holder.textViewName.setText(doctor.getName());
        holder.textViewSpecialist.setText(doctor.getSpec());

        holder.textViewHospital.setText(String.valueOf(doctor.getHospital()));
        holder.textViewPrice.setText(String.valueOf(doctor.getFee()));

        holder.textViewCity.setText(String.valueOf(doctor.getCity()));
        Picasso.get().load(String.valueOf(doctor.getImage())).placeholder(R.drawable.loading_icon).into(holder.imageView);
        //holder.imageView.setImageDrawable(mctx.getResources().getDrawable(doctor.getImage()));

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mctx,"Selected a good doctor...", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mctx,DoctorAppointmentsConfirmation.class);
//                intent.putExtra("imageView",product.getImage());
                Bundle extras = new Bundle();
                intent.putExtras(extras);
                intent.putExtra("imageView",doctor.getImage());
                intent.putExtra("textViewName",doctor.getName());
                intent.putExtra("textViewSpecialist",doctor.getSpec());
                intent.putExtras(extras);
                intent.putExtra("textViewHospital",doctor.getHospital());
                intent.putExtra("textViewFee",doctor.getFee());
                intent.putExtra("textViewCity",doctor.getCity());
                intent.putExtras(extras);
                mctx.startActivity(intent);
            }
        });

        holder.bookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mctx,"Selected a good doctor...", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mctx,DoctorAppointmentsConfirmation.class);
//                intent.putExtra("imageView",product.getImage());
                Bundle extras = new Bundle();
                intent.putExtras(extras);
                intent.putExtra("imageView",doctor.getImage());
                intent.putExtra("textViewName",doctor.getName());
                intent.putExtra("textViewSpecialist",doctor.getSpec());
                intent.putExtras(extras);
                intent.putExtra("textViewHospital",doctor.getHospital());
                intent.putExtra("textViewFee",doctor.getFee());
                intent.putExtra("textViewCity",doctor.getCity());
                intent.putExtras(extras);
                mctx.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return doctor_detailsList.size();
    }


    class doctorViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textViewName,textViewSpecialist,textViewHospital,textViewPrice,textViewCity;
        Button bookAppointment;

        public doctorViewHolder(View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageView);
            textViewName=itemView.findViewById(R.id.textViewName);
            textViewSpecialist=itemView.findViewById(R.id.textViewSpecialist);
            textViewHospital=itemView.findViewById(R.id.textViewHospital);
            textViewPrice=itemView.findViewById(R.id.textViewPrice);
            textViewCity=itemView.findViewById(R.id.textViewCity);
            bookAppointment = itemView.findViewById(R.id.button);
        }
    }
}
