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

public class PharmacyViewer extends AppCompatActivity {

    public List<pharmacy_details> pharmacy_detailsList;

    public int erpos = 0;
    public String[] name_array, address_array, phone_array, userid_array, password_array, image_url_array, email_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_viewer);

        Toast.makeText(getApplicationContext(), "Loading...please wait", Toast.LENGTH_SHORT).show();

        pharmacy_detailsList=new ArrayList<>();

        name_array = new String[10];
        userid_array = new String[10];
        address_array = new String[10];
        password_array = new String[10];
        phone_array = new String[10];
        email_array = new String[10];
        image_url_array = new String[10];

        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://aarogya-unlimited-database.firebaseio.com/");
            DatabaseReference myref = database.getReference("Pharmacy Records");
            erpos = 0;
            myref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    while (children.iterator().hasNext()) {
                        DataSnapshot o = children.iterator().next();
                        name_array[erpos] = o.child("Pharmacy Name").getValue().toString();
                        userid_array[erpos] = o.child("Pharmacy User ID").getValue().toString();
                        password_array[erpos] = o.child("Pharmacy Password").getValue().toString();
                        address_array[erpos] = o.child("Pharmacy Address").getValue().toString();
                        image_url_array[erpos] = o.child("Pharmacy Image URL").getValue().toString();
                        phone_array[erpos] = o.child("Pharmacy Phone number").getValue().toString();
                        email_array[erpos] = o.child("Pharmacy Email").getValue().toString();

                        Toast.makeText(getApplicationContext(), "Loading...please wait", Toast.LENGTH_LONG).show();
                        ++erpos;
                    }
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), "Values populated", Toast.LENGTH_LONG).show();
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

       /* pharmacy_detailsList.add(
                new pharmacy_details(
                        1,
                        R.drawable.pharmacy_logo_img,
                        "Thulasi Pharmacy",
                        "No 225, 2nd Street, Gandhipuram, Coimbatore - 641012",
                        "+91 9843977730"
                )
        );

        pharmacy_detailsList.add(
                new pharmacy_details(
                        2,
                        R.drawable.pharmacy_logo_img,
                        "Eric Pharmacy",
                        "No 225, 2nd Street, Gandhipuram, Coimbatore - 641012",
                        "+91 9843977730"
                )
        );

        pharmacy_detailsList.add(
                new pharmacy_details(
                        3,
                        R.drawable.pharmacy_logo_img,
                        "Ebbe Pharmacy",
                        "No 225, 2nd Street, Gandhipuram, Coimbatore - 641012",
                        "+91 9843977730"
                )
        );

        pharmacy_detailsList.add(
                new pharmacy_details(
                        4,
                        R.drawable.pharmacy_logo_img,
                        "Maverick Pharmacy",
                        "No 225, 2nd Street, Gandhipuram, Coimbatore - 641012",
                        "+91 9020420886"
                )
        );

        pharmacy_detailsList.add(
                new pharmacy_details(
                        5,
                        R.drawable.pharmacy_logo_img,
                        "Evlyn Pharmacy",
                        "No 225, 2nd Street, Gandhipuram, Coimbatore - 641012",
                        "+91 9843977730"
                )
        );

        pharmacy_detailsList.add(
                new pharmacy_details(
                        6,
                        R.drawable.pharmacy_logo_img,
                        "Thulasi Pharmacy",
                        "No 225, 2nd Street, Gandhipuram, Coimbatore - 641012",
                        "+91 9843977730"
                )
        );*/

     /*   recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
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
        });*/
    }

    public void doRemainingWork(){

        for (int i = 0; i < erpos; i++) {
            pharmacy_detailsList.add(new pharmacy_details(userid_array[i],image_url_array[i]
                    ,name_array[i], address_array[i],phone_array[i],email_array[i]));
        }

        RecyclerView pharmRecyclerView=(RecyclerView)findViewById(R.id.pharm_recyclerview);
        pharmacyAdapter pharmacyAdapter = new pharmacyAdapter(this,pharmacy_detailsList);
        LinearLayoutManager pharmacylayoutManager = new LinearLayoutManager(this);
        pharmacylayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pharmRecyclerView.setLayoutManager(pharmacylayoutManager);
        pharmRecyclerView.setAdapter(pharmacyAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pharmRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

