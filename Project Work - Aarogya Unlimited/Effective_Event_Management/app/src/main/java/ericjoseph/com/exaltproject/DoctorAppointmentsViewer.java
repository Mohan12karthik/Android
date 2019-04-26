package ericjoseph.com.exaltproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorAppointmentsViewer extends AppCompatActivity {

    public int erpos = 0, erpos1 = 0;
    public String names_array[],userid_array[],password_array[], doctor_name;

    public String[] app_doctor_name_array, app_city_array, app_doctor_fee_array, app_doctor_hospital_array, app_doctor_spec_array,
    app_date_array, app_time_array, app_reason_array, temp_title;

    public EditText userName, passWord;
    public ListView lv;

    public Button prev, next;

    public TextView date_tv, time_tv, reason_tv, fee_tv, app_index_tv;

    public ArrayAdapter<String> adapter;

    public int pos;

    public String[] abc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointments_viewer);

        names_array = new String[100];
        userid_array = new String[100];
        password_array = new String[100];

        app_city_array = new String[100];
        app_reason_array = new String[100];
        app_date_array = new String[100];
        app_time_array = new String[100];
        app_doctor_fee_array = new String[100];
        app_doctor_hospital_array = new String[100];
        app_doctor_name_array = new String[100];
        app_doctor_spec_array = new String[100];
        temp_title = new String[100];

        date_tv = (TextView)findViewById(R.id.app_date_tv);
        time_tv = (TextView)findViewById(R.id.app_time_tv);
        reason_tv = (TextView)findViewById(R.id.app_reason_tv);
        fee_tv = (TextView)findViewById(R.id.app_fee_tv);
        app_index_tv = (TextView)findViewById(R.id.app_index);

        abc = new String[]{"ABC", "XYZ", "PQR"};

        prev = (Button)findViewById(R.id.prev);
        next = (Button)findViewById(R.id.next);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos > 0) {
                    pos--;
                    loadValuestoView(pos);
                }

                else{
                    Toast.makeText(getApplicationContext(),"Reached begiining of list",Toast.LENGTH_SHORT).show();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos < (erpos1-1)) {
                    pos++;
                    loadValuestoView(pos);
                }

                else{
                    Toast.makeText(getApplicationContext(),"Reached end of list",Toast.LENGTH_SHORT).show();
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

                        ++erpos;
                    }
                    Toast.makeText(getApplicationContext(), "Values Populated and available for viewing", Toast.LENGTH_SHORT).show();
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

        userName = (EditText)findViewById(R.id.enter_username);
        passWord = (EditText)findViewById(R.id.enter_password);

        TextView loadDetails = (TextView)findViewById(R.id.load_doc_appointments);

        loadDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findValues(userName.getText().toString(), passWord.getText().toString());
            }
        });

        TextView viewDetails = (TextView)findViewById(R.id.view_doc_appointments);
        viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadValuestoView(0);
            }
        });
    }

    public void findValues(String username, String password){
        int i, flag = 0;
        for(i = 0; i < erpos; i++){
            if(userid_array[i].equals(username) && password_array[i].equals(password)){
                flag = 1;
                doctor_name = names_array[i];
                Toast.makeText(getApplicationContext(),"Welcome " + doctor_name,Toast.LENGTH_SHORT).show();
                RetrieveDoctorAppointments();
            }
        }

        if(flag == 0){
            Toast.makeText(getApplicationContext(),"Sorry wrong username or password. Please try again",Toast.LENGTH_SHORT).show();
        }
    }

    public void RetrieveDoctorAppointments() {

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://aarogya-unlimited-database.firebaseio.com/");
        DatabaseReference myref = database.getReference("Doctor Appointments/"+doctor_name);

        try {
            myref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    erpos1 = 0;
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    while (children.iterator().hasNext()) {
                        Toast.makeText(getApplicationContext(), "Please wait data values are loading", Toast.LENGTH_SHORT).show();
                        DataSnapshot o = children.iterator().next();

                        app_doctor_name_array[erpos1] = o.child("Doctor Name").getValue().toString();
                        app_city_array[erpos1] = o.child("Doctor City").getValue().toString();
                        app_doctor_fee_array[erpos1] = o.child("Doctor Fee").getValue().toString();
                        app_doctor_hospital_array[erpos1] = o.child("Doctor Hospital").getValue().toString();
                        app_doctor_spec_array[erpos1] = o.child("Doctor Specialization").getValue().toString();
                        app_date_array[erpos1] = o.child("Appointment Date").getValue().toString();
                        app_time_array[erpos1] = o.child("Appointment Time").getValue().toString();
                        app_reason_array[erpos1] = o.child("Appointment Reason").getValue().toString();

                        temp_title[erpos1] = app_date_array[erpos1] + " " + app_time_array[erpos1];
                        Toast.makeText(getApplicationContext(),temp_title[erpos1],Toast.LENGTH_SHORT).show();

                        ++erpos1;
                    }
                    Toast.makeText(getApplicationContext(), "Doctor Appointments of " + doctor_name + " are populated and available for viewing", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Error in populating values", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error...doctor details not saved",Toast.LENGTH_SHORT).show();
        }
    }

    public void loadValuestoView(int pos) {
        if (pos >= 0 && pos < erpos1) {
            app_index_tv.setText("Appointment " + (pos+1));
            date_tv.setText("Appointment Date :  " + app_date_array[pos]);
            time_tv.setText("Appointment Time :  " + app_time_array[pos]);
            reason_tv.setText("Appointment Reason :  " + app_reason_array[pos]);
            fee_tv.setText("Appointment Fee :  " + app_doctor_fee_array[pos]);
        }
    }
}