package com.example.admin.doc;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    public RecyclerView recyclerView;

    Button b_appoint;
    List<doctor_details> doctor_detailsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doctor_detailsList=new ArrayList<>();

//        b_appoint=(Button)findViewById(R.id.b_appoint);

        doctor_detailsList.add(
                new doctor_details(
                        1,
                        R.drawable.doc1,
                        " Dr.Mohan Karthik",
                        " Dentist",
                        " K.G.  Hospital","₹200","Coimbatore"

                ));

        doctor_detailsList.add(
                new doctor_details(
                        2,
                        R.drawable.doc1,
                        "Dr.Mohan",
                        "ENT",
                        "K.G.  Hospital","₹300","Chennai"
                ));


        doctor_detailsList.add(
                new doctor_details(
                        3,
                        R.drawable.doc1,
                        "Dr.Karthik",
                        "Cardiologist.",
                        "K.G.  Hospital","₹500","Bangalore"
                ));


        doctor_detailsList.add(
                new doctor_details(
                        4,
                        R.drawable.doc1,
                        "Dr.Kabilan",
                        "Eye",
                        "Vasan Eye Care","₹300","Mumbai"
                ));


        doctor_detailsList.add(
                new doctor_details(
                        5,
                        R.drawable.doc1,
                        "Dr.Eric",
                        "Dermatologists ",
                        "K.G.  Hospital","₹500","Delhi"
                ));


        doctor_detailsList.add(
                new doctor_details(
                        6,
                        R.drawable.doc1,
                        "Dr.Mohan Karthik",
                        "Cardiovascular surgeon.",
                        "K.G.  Hospital","₹600","Hyderabad"
                ));


//        b_appoint.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(MainActivity.this,doc_appoint.class);
//                startActivity(intent);
//            }
//        });

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
                Toast.makeText(MainActivity.this, "selected", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
