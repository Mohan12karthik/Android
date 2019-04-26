package ericjoseph.com.exaltproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EventViewerActivity extends AppCompatActivity {
    public int erpos = 0, pos = 0;
    public Button prev, next, participate;
    public String names_array[],topic_array[],date_array[],from_time_array[],to_time_array[],event_fee_array[],team_type_array[];
    public String locations_array[],headnames[],mem1names[],mem2names[],headphones[],mem1phones[],mem2phones[],imageurls[],organizer_array[], id_array[];

    public TextView eventnametv, eventtopictv, eventdatetv, eventfromtimetv, eventtotimetv, eventfeestv, eventlocationtv, eventteamtypetv, eventheadnametv, eventheadphonetv;
    public TextView eventmem1nametv, eventmem1phonetv, eventmem2nametv, eventmem2phonetv, eventorgtv, eventidtv;

    public ImageView event_img;
    public Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_viewer);

        context = this;

        id_array = new String[10];
        names_array = new String[10];
        topic_array = new String[10];
        date_array = new String[10];
        from_time_array = new String[10];
        to_time_array = new String[10];
        event_fee_array = new String[10];
        team_type_array = new String[10];
        locations_array = new String[10];
        headnames = new String[10];
        headphones = new String[10];
        mem1names = new String[10];
        mem1phones = new String[10];
        mem2names = new String[10];
        mem2phones = new String[10];
        imageurls = new String[10];
        organizer_array = new String[10];

        eventnametv = (TextView)findViewById(R.id.patient_name_textview);
        eventidtv = (TextView)findViewById(R.id.doctor_password_textview);
        eventtopictv = (TextView)findViewById(R.id.patient_userid_textview);
        eventfromtimetv = (TextView)findViewById(R.id.event_from_time_textview);
        eventtotimetv = (TextView)findViewById(R.id.event_to_time_textview);
        eventdatetv = (TextView)findViewById(R.id.doctor_city_textview);
        eventlocationtv = (TextView)findViewById(R.id.event_location_textview);
        eventteamtypetv = (TextView)findViewById(R.id.teamtypetv);
        eventfeestv = (TextView)findViewById(R.id.doctor_specialization_textview);
        eventheadnametv = (TextView)findViewById(R.id.patient_phone_textview);
        eventheadphonetv = (TextView)findViewById(R.id.doctor_email_textview);
        eventmem1nametv = (TextView)findViewById(R.id.doctor_clinic_name_textview);
        eventmem1phonetv = (TextView)findViewById(R.id.mem1phonetv);
        eventmem2nametv = (TextView)findViewById(R.id.doctor_work_address_textview);
        eventmem2phonetv = (TextView)findViewById(R.id.mem2phonetv);
        eventorgtv = (TextView)findViewById(R.id.event_org_comp_tv);

        event_img = (ImageView)findViewById(R.id.medicine_imageview);

        prev = (Button)findViewById(R.id.prev);
        next = (Button)findViewById(R.id.next);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos<=0) {
                    Toast.makeText(getApplicationContext(),"Start of List",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    pos-=1;
                    fillTextViews(pos);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos >= erpos-1) {
                    Toast.makeText(getApplicationContext(),"End of List",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    pos+=1;
                    fillTextViews(pos);
                    //Toast.makeText(getApplicationContext(),names_array[pos]+topic_array[pos],Toast.LENGTH_SHORT).show();
                    //records[pos].display();
                }
            }
        });

        // Getting values from the Firebase database
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            DatabaseReference myref = database.getReference("Event Records");
            myref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    erpos = 0;
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    while (children.iterator().hasNext()) {
                        DataSnapshot o = children.iterator().next();
                        names_array[erpos] = o.child("Event Name").getValue().toString();
                        id_array[erpos] = o.child("Event ID").getValue().toString();
                        topic_array[erpos] = o.child("Event Topic").getValue().toString();
                        date_array[erpos] = o.child("Event Date").getValue().toString();
                        from_time_array[erpos] = o.child("Event From Time").getValue().toString();
                        to_time_array[erpos] = o.child("Event To Time").getValue().toString();
                        locations_array[erpos] = o.child("Event Location").getValue().toString();
                        headnames[erpos] = o.child("Event Head Name").getValue().toString();
                        headphones[erpos] = o.child("Event Head Phone").getValue().toString();
                        mem1names[erpos] = o.child("Event Member 1 Name").getValue().toString();
                        mem1phones[erpos] = o.child("Event Member 1 Phone").getValue().toString();
                        mem2names[erpos] = o.child("Event Member 2 Name").getValue().toString();
                        mem2phones[erpos] = o.child("Event Member 2 Phone").getValue().toString();
                        imageurls[erpos] = o.child("Event Image URL").getValue().toString();
                        event_fee_array[erpos] = o.child("Event Fees").getValue().toString();
                        team_type_array[erpos] = o.child("Event Team Type").getValue().toString();
                        organizer_array[erpos] = o.child("Event Organizer").getValue().toString();

                        ++erpos;
                    }
                    Toast.makeText(getApplicationContext(), "Values Populated and available for viewing", Toast.LENGTH_SHORT).show();
                    fillTextViews(0);
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

        participate = (Button)findViewById(R.id.participate_event_button);
        participate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(context,ParticipationConfirmation.class);
                go.putExtra("Event Name",names_array[pos]);
                go.putExtra("Event ID",id_array[pos]);
                startActivity(go);
            }
        });
    }

    public void fillTextViews(int pos){
        eventnametv.setText("Event Name : " + names_array[pos]);
        eventidtv.setText("Event ID : " + id_array[pos]);
        eventtopictv.setText("Event Topic : " + topic_array[pos]);
        eventfromtimetv.setText("From : " + from_time_array[pos]);
        eventtotimetv.setText("To : " + to_time_array[pos]);
        eventdatetv.setText("Date : "+ date_array[pos]);
        eventlocationtv.setText("Event Location : " + locations_array[pos]);
        eventteamtypetv.setText("Team Type : " + team_type_array[pos]);
        eventfeestv.setText("Event Fee : " + event_fee_array[pos]);
        eventheadnametv.setText("Event Head : " + headnames[pos]);
        eventheadphonetv.setText("Event Head Contact : " + headphones[pos]);
        eventmem1nametv.setText("Event Member 1 : " + mem1names[pos]);
        eventmem1phonetv.setText("Event Member 1 Contact : " + mem1phones[pos]);
        eventmem2nametv.setText("Event Member 2 : " + mem2names[pos]);
        eventmem2phonetv.setText("Event Member 2 Contact : " + mem2phones[pos]);
        eventorgtv.setText("Event Organizer Company : " + organizer_array[pos]);

        Picasso.get().load(imageurls[pos]).placeholder(R.drawable.loading_icon).into(event_img);
    }
}
