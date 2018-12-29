package com.example.admin.doc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class pharm_medicine extends AppCompatActivity {

    public RecyclerView med_recyclerView;

    List<medicine_details> medicine_detailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharm_medicine);

        medicine_detailsList=new ArrayList<>();

        medicine_detailsList.add(
                new medicine_details(
                        1,
                        R.drawable.crocin,
                        "Crocin Advance Tablet",
                        "Glaxosmithkline Pharmaceuticals Ltd.",
                        "1 Strip  (10 Tablets)",
                        "Price: ₹30"
                )
        );


        medicine_detailsList.add(
                new medicine_details(
                        2,
                        R.drawable.crocin,
                        "Crocin Advance Tablet",
                        "Glaxosmithkline Pharmaceuticals Ltd.",
                        "1 Strip  (10 Tablets)",
                        "Price: ₹30"
                )
        );

        medicine_detailsList.add(
                new medicine_details(
                        3,
                        R.drawable.crocin,
                        "Crocin Advance Tablet",
                        "Glaxosmithkline Pharmaceuticals Ltd.",
                        "1 Strip  (10 Tablets)",
                        "Price: ₹30"
                )
        );

        medicine_detailsList.add(
                new medicine_details(
                        4,
                        R.drawable.crocin,
                        "Crocin Advance Tablet",
                        "Glaxosmithkline Pharmaceuticals Ltd.",
                        "1 Strip  (10 Tablets)",
                        "Price: ₹30"
                )
        );

        medicine_detailsList.add(
                new medicine_details(
                        5,
                        R.drawable.crocin,
                        "Crocin Advance Tablet",
                        "Glaxosmithkline Pharmaceuticals Ltd.",
                        "1 Strip  (10 Tablets)",
                        "Price: ₹30"
                )
        );

        medicine_detailsList.add(
                new medicine_details(
                        6,
                        R.drawable.crocin,
                        "Crocin Advance Tablet",
                        "Glaxosmithkline Pharmaceuticals Ltd.",
                        "1 Strip  (10 Tablets)",
                        "Price: ₹30"
                )
        );

        med_recyclerView=(RecyclerView)findViewById(R.id.recycler_medicine);
        medicineAdapter adapter = new medicineAdapter(this,medicine_detailsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        med_recyclerView.setLayoutManager(layoutManager);
        med_recyclerView.setAdapter(adapter);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        med_recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(pharm_medicine.this, "selected", Toast.LENGTH_SHORT).show();
                Intent med=new Intent(pharm_medicine.this,buy_medicine.class);
               startActivity(med);

            }
        });

    }


}
