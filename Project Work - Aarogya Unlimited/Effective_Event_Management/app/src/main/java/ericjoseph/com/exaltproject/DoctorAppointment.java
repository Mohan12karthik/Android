package ericjoseph.com.exaltproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class DoctorAppointment extends AppCompatActivity {
    public RecyclerView recyclerView;

    public int erpos = 0;
    public String[] workname_array,names_array,city_array,specialization_array,userid_array,image_url_array, address_array
            ,password_array,phone_array,email_array,from_array,to_array;



    List<doctor_details> doctor_detailsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment);

        Toast.makeText(getApplicationContext(), "Loading...please wait", Toast.LENGTH_SHORT).show();

        doctor_detailsList=new ArrayList<>();

        names_array = new String[10];
        userid_array = new String[10];
        address_array = new String[10];
        password_array = new String[10];
        specialization_array = new String[10];
        workname_array = new String[10];
        city_array = new String[10];
        phone_array = new String[10];
        email_array = new String[10];
        image_url_array = new String[10];
        from_array = new String[10];
        to_array = new String[10];

        //getDetailsFromFirebase();

        // Firebase details collection

        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://aarogya-unlimited-database.firebaseio.com/");
            DatabaseReference myref = database.getReference("Doctor Records");
            erpos = 0;
            myref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    while (children.iterator().hasNext()) {
                        DataSnapshot o = children.iterator().next();
                        names_array[erpos] = o.child("Doctor Name").getValue().toString();
                        userid_array[erpos] = o.child("Doctor User ID").getValue().toString();
                        password_array[erpos] = o.child("Doctor Password").getValue().toString();
                        address_array[erpos] = o.child("Doctor Address").getValue().toString();
                        workname_array[erpos] = o.child("Doctor Workplace Name").getValue().toString();
                        specialization_array[erpos] = o.child("Doctor Specialization").getValue().toString();
                        image_url_array[erpos] = o.child("Doctor Image URL").getValue().toString();
                        city_array[erpos] = o.child("Doctor City").getValue().toString();
                        phone_array[erpos] = o.child("Doctor Phone").getValue().toString();
                        email_array[erpos] = o.child("Doctor Email").getValue().toString();
                        from_array[erpos] = o.child("Doctor From Time").getValue().toString();
                        to_array[erpos] = o.child("Doctor To Time").getValue().toString();

                        Toast.makeText(getApplicationContext(), "Loading...please wait", Toast.LENGTH_LONG).show();
                        ++erpos;
                    }
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    doRemainingWork();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Error in populating values", Toast.LENGTH_SHORT).show();
                }
            });
        }

        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error in populating database due to Database Errors",Toast.LENGTH_SHORT).show();
        }
    }

    public void doRemainingWork(){

        for (int i = 0; i < erpos; i++) {
            doctor_detailsList.add(new doctor_details(userid_array[i],image_url_array[i]
                    , names_array[i], specialization_array[i],
                    workname_array[i], "200", city_array[i]));
        }

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        doctorAdapter adapter = new doctorAdapter(this,doctor_detailsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DoctorAppointment.this, "selected", Toast.LENGTH_SHORT).show();
            }
        });

    }
}