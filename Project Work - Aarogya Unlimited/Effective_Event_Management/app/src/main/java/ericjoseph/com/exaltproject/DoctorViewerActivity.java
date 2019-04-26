package ericjoseph.com.exaltproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

public class DoctorViewerActivity extends AppCompatActivity {
    public int erpos = 0, pos = 0;
    public Button prev, next;
    public String names_array[],userid_array[],city_array[],password_array[],specialization_array[],address_array[],workname_array[],phone_array[],email_array[],
            image_url_array[], from_array[], to_array[];

    public TextView doctornametv, doctoruseridtv, doctorpasswordtv, doctorcitytv, doctorphonenumtv, doctorspecializationtv,
            doctoremailtv, doctoraddresstv, doctorclinicnametv, doctorfromtimetv, doctortotimetv;

    public ImageView doctor_img;
    public Activity context;
    public AutoCompleteTextView search_bar;
    public ArrayAdapter<String> adapter;
    public Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_viewer);

        context = this;

        search_bar = (AutoCompleteTextView)findViewById(R.id.searchbar);
        search_bar.setThreshold(1);

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

        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.select_dialog_item,names_array);
        search_bar.setAdapter(adapter);


        doctornametv = (TextView)findViewById(R.id.patient_name_textview);
        doctoruseridtv = (TextView)findViewById(R.id.patient_userid_textview);
        doctorpasswordtv = (TextView)findViewById(R.id.doctor_password_textview);
        doctorspecializationtv = (TextView)findViewById(R.id.doctor_specialization_textview);
        doctorphonenumtv = (TextView)findViewById(R.id.patient_phone_textview);
        doctoremailtv = (TextView)findViewById(R.id.doctor_email_textview);
        doctorcitytv = (TextView)findViewById(R.id.doctor_city_textview);
        doctorclinicnametv = (TextView)findViewById(R.id.doctor_clinic_name_textview);
        doctoraddresstv = (TextView)findViewById(R.id.doctor_work_address_textview);
        doctorfromtimetv = (TextView)findViewById(R.id.doctor_from_timing_textview);
        doctortotimetv = (TextView)findViewById(R.id.doctor_to_timing_textview);

        doctor_img = (ImageView)findViewById(R.id.medicine_imageview);

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
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://aarogya-unlimited-database.firebaseio.com/");

            DatabaseReference myref = database.getReference("Doctor Records");
            myref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    erpos = 0;
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    while (children.iterator().hasNext()) {
                        Toast.makeText(getApplicationContext(), "Please wait data values are loading", Toast.LENGTH_SHORT).show();
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

                        ++erpos;
                    }
                    Toast.makeText(getApplicationContext(), "Values Populated and available for viewing", Toast.LENGTH_SHORT).show();
                    fillTextViews(0);
                    adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.select_dialog_item,names_array);
                    search_bar.setAdapter(adapter);
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

        search = (Button)findViewById(R.id.searchbutton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 0 , flag = 0;
                for(i = 0; i < names_array.length ;i++){
                    if(names_array[i].equals(search_bar.getText().toString())){
                        flag = 1;
                        break;
                    }
                }

                if(flag == 0){
                    Toast.makeText(getApplicationContext(), "Doctor record not found", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Doctor record found", Toast.LENGTH_SHORT).show();
                    fillTextViews(i);
                }
            }
        });
    }

    public void fillTextViews(int pos){
        doctornametv.setText("Doctor Name : " + names_array[pos]);
        doctoruseridtv.setText("User ID : " + userid_array[pos]);

        doctorpasswordtv.setText("Password : " + password_array[pos]);
        doctorcitytv.setText("City : " + city_array[pos]);

        doctoremailtv.setText("Doctor Email : " + email_array[pos]);
        doctorphonenumtv.setText("Doctor Phone : " + phone_array[pos]);

        doctoraddresstv.setText("Clinic address : " + address_array[pos]);
        doctorclinicnametv.setText("Clinic name : " + workname_array[pos]);

        doctorspecializationtv.setText("Doctor Specialization : " + specialization_array[pos]);

        doctorfromtimetv.setText("From : " + from_array[pos]);
        doctortotimetv.setText("To : " + to_array[pos]);

        Picasso.get().load(image_url_array[pos]).placeholder(R.drawable.loading_icon).into(doctor_img);
    }
}
