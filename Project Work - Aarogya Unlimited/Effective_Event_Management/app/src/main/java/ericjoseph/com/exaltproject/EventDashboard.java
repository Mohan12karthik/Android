package ericjoseph.com.exaltproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventDashboard extends AppCompatActivity {
    TextView num_participants, revenue;
    Spinner org_spinner, event_spinner;
    ArrayList<String> org_names, names_array, id_array, event_fee_array;
    ArrayAdapter<String> org_names_adapter, event_names_adapter;
    public int location, location2, num_participants_value, num_revenue;
    public int event_size = 0;
    ArrayList<String> final_names_list, final_id_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_dashboard);

        org_spinner = (Spinner)findViewById(R.id.org_spinner);
        event_spinner = (Spinner)findViewById(R.id.event_spinner);

        populateOrganizations();

        org_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                populateEvents();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        event_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //getRegisteredList(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        }

        public void populateOrganizations(){
            org_names = new ArrayList<String>();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference org_ref = database.getReference("Organization Records");

            try {
                org_ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                        while (children.iterator().hasNext()) {
                            DataSnapshot o = children.iterator().next();
                            org_names.add(o.child("Org Name").getValue().toString());
                        }
                        Toast.makeText(getApplicationContext(), "Organizations Populated and available for viewing", Toast.LENGTH_SHORT).show();
                        org_spinner.refreshDrawableState();
                        org_spinner.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        org_spinner.setPopupBackgroundResource(R.color.colorPrimary);
                        org_names_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, org_names);
                        org_spinner.setAdapter(org_names_adapter);

                        populateEvents();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Error in populating values", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        }

    public void populateEvents(){
        final_names_list = new ArrayList<String>();
        final_id_list = new ArrayList<String>();

        final ArrayList<String> event_names = new ArrayList<String>();
        final ArrayList<String> event_organizers = new ArrayList<String>();
        final ArrayList<String> event_id = new ArrayList<String>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference event_under_org_ref = database.getReference("Event Records");

        final String org_cmp = org_spinner.getSelectedItem().toString();

        try {
            event_under_org_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    while (children.iterator().hasNext()) {
                        DataSnapshot o = children.iterator().next();
                        event_organizers.add(o.child("Event Organizer").getValue().toString());
                        event_names.add(o.child("Event Name").getValue().toString());
                        event_id.add(o.child("Event ID").getValue().toString());
                    }

                    for(int l =0;l < event_names.size(); l++){
                        if(event_organizers.get(l) == org_spinner.getSelectedItem().toString()) {
                                final_names_list.add(event_names.get(l));
                                final_id_list.add(event_id.get(l));
                        }
                    }


                    Toast.makeText(getApplicationContext(), "Event Populated and available for viewing", Toast.LENGTH_SHORT).show();

                    event_spinner.refreshDrawableState();
                    event_spinner.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    event_spinner.setPopupBackgroundResource(R.color.colorPrimary);

                    if(final_names_list.size() > 0) {
                        event_names_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, final_names_list);
                        event_spinner.setAdapter(event_names_adapter);
                    }
                    else {
                        String[] nullvalues = new String[]{"No events under this Organizer"};
                        event_names_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, nullvalues);
                        event_spinner.setAdapter(event_names_adapter);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Error in populating values", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
        }
    }

    public void getRegisteredList(int g){

        final ArrayList<String> part_event_ids = new ArrayList<String>();
        final ArrayList<String> part_temp = new ArrayList<String>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference part_ref = database.getReference("Participation Records");

        try {
           part_ref.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                   int f = 0;
                   while (children.iterator().hasNext()) {
                       DataSnapshot o = children.iterator().next();
                       part_event_ids.add(o.child("Event ID").getValue().toString());

                       if(final_id_list.contains(o.child("Event ID").getValue().toString()))
                           f = f + 1;
                   }
                   Toast.makeText(getApplicationContext(),f,Toast.LENGTH_SHORT).show();
               }
               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
        }
    }
}