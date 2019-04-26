package ericjoseph.com.exaltproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PatientViewerActivity extends AppCompatActivity {
    public int erpos = 0, pos = 0;
    public Button prev, next;
    public String names_array[],userid_array[],dob_array[],password_array[],ailment_array[],address_array[],blood_type_array[],phone_array[],email_array[],image_url_array[];

    public TextView patientnametv, patientuseridtv, patientpasswordtv, patientDOBtv, patientphonenumtv, patientbloodtypetv, patientemailtv, patientaddresstv, patientailmentstv;

    public ImageView patient_img;
    public Activity context;
    public EditText patient_search_bar;
    public Button patient_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_viewer);

        context = this;

        names_array = new String[10];
        userid_array = new String[10];
        address_array = new String[10];
        password_array = new String[10];
        dob_array = new String[10];
        ailment_array = new String[10];
        blood_type_array = new String[10];
        phone_array = new String[10];
        email_array = new String[10];
        image_url_array = new String[10];

        patientnametv = (TextView)findViewById(R.id.patient_name_textview);
        patientuseridtv = (TextView)findViewById(R.id.patient_userid_textview);
        patientpasswordtv = (TextView)findViewById(R.id.doctor_password_textview);
        patientDOBtv = (TextView)findViewById(R.id.doctor_specialization_textview);
        patientphonenumtv = (TextView)findViewById(R.id.patient_phone_textview);
        patientemailtv = (TextView)findViewById(R.id.doctor_email_textview);
        patientbloodtypetv = (TextView)findViewById(R.id.doctor_city_textview);
        patientaddresstv = (TextView)findViewById(R.id.doctor_clinic_name_textview);
        patientailmentstv = (TextView)findViewById(R.id.doctor_work_address_textview);

        patient_search_bar = (EditText) findViewById(R.id.patient_searchbar);

        patient_img = (ImageView)findViewById(R.id.medicine_imageview);

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

            DatabaseReference myref = database.getReference("Patient Records");
            myref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    erpos = 0;
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    while (children.iterator().hasNext()) {
                        DataSnapshot o = children.iterator().next();
                        names_array[erpos] = o.child("Patient Name").getValue().toString();
                        userid_array[erpos] = o.child("Patient User ID").getValue().toString();
                        password_array[erpos] = o.child("Patient Password").getValue().toString();
                        address_array[erpos] = o.child("Patient Address").getValue().toString();
                        dob_array[erpos] = o.child("Patient Date of Birth").getValue().toString();
                        blood_type_array[erpos] = o.child("Patient Blood Type").getValue().toString();

                        image_url_array[erpos] = o.child("Patient Image URL").getValue().toString();
                        ailment_array[erpos] = o.child("Patient Ailment").getValue().toString();
                        phone_array[erpos] = o.child("Patient Phone").getValue().toString();
                        email_array[erpos] = o.child("Patient Email").getValue().toString();

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

        patient_search = (Button)findViewById(R.id.patient_searchbutton);
        patient_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 0 , flag = 0;
                for(i = 0; i < names_array.length ;i++){
                    if(names_array[i].equals(patient_search_bar.getText().toString())){
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
        patientnametv.setText("Patient Name : " + names_array[pos]);
        patientuseridtv.setText("User ID : " + userid_array[pos]);

        patientpasswordtv.setText("Password : " + password_array[pos]);
        patientDOBtv.setText("DOB : " + dob_array[pos]);

        patientemailtv.setText("Patient Email : " + email_array[pos]);
        patientphonenumtv.setText("Patient Phone : " + phone_array[pos]);

        patientaddresstv.setText("Patient Address : " + address_array[pos]);
        patientailmentstv.setText("Patient Ailment : " + ailment_array[pos]);

        patientbloodtypetv.setText("Patient Blood Type : " + blood_type_array[pos]);
        Picasso.get().load(image_url_array[pos]).placeholder(R.drawable.loading_icon).into(patient_img);
    }
}
