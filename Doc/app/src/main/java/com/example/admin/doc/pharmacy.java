package com.example.admin.doc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class pharmacy extends AppCompatActivity {

    public RecyclerView pharm_recyclerView;

    List<pharmacy_details> pharmacy_detailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);


        pharmacy_detailsList=new ArrayList<>();

        pharmacy_detailsList.add(
                new pharmacy_details(
                        1,
                        R.drawable.logo,
                        "Thulasi Pharmacy",
                        "No 225, 2nd Street, Gandhipuram, Coimbatore - 641012",
                        "+91 9843977730"
                )
        );

        pharmacy_detailsList.add(
                new pharmacy_details(
                        2,
                        R.drawable.logo,
                        "Thulasi Pharmacy",
                        "No 225, 2nd Street, Gandhipuram, Coimbatore - 641012",
                        "+91 9843977730"
                )
        );

        pharmacy_detailsList.add(
                new pharmacy_details(
                        3,
                        R.drawable.logo,
                        "Thulasi Pharmacy",
                        "No 225, 2nd Street, Gandhipuram, Coimbatore - 641012",
                        "+91 9843977730"
                )
        );

        pharmacy_detailsList.add(
                new pharmacy_details(
                        4,
                        R.drawable.logo,
                        "Thulasi Pharmacy",
                        "No 225, 2nd Street, Gandhipuram, Coimbatore - 641012",
                        "+91 9843977730"
                )
        );

        pharmacy_detailsList.add(
                new pharmacy_details(
                        5,
                        R.drawable.logo,
                        "Thulasi Pharmacy",
                        "No 225, 2nd Street, Gandhipuram, Coimbatore - 641012",
                        "+91 9843977730"
                )
        );

        pharmacy_detailsList.add(
                new pharmacy_details(
                        6,
                        R.drawable.logo,
                        "Thulasi Pharmacy",
                        "No 225, 2nd Street, Gandhipuram, Coimbatore - 641012",
                        "+91 9843977730"
                )
        );



        pharm_recyclerView=(RecyclerView)findViewById(R.id.pharm_recyclerview);
        pharmacyAdapter adapter = new pharmacyAdapter(this,pharmacy_detailsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pharm_recyclerView.setLayoutManager(layoutManager);
        pharm_recyclerView.setAdapter(adapter);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pharm_recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(pharmacy.this, "selected", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
