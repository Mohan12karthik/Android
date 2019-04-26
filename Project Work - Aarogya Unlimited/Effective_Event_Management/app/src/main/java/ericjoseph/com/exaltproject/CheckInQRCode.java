package ericjoseph.com.exaltproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CheckInQRCode extends AppCompatActivity {
    ArrayList<String> medicineName_arrayList, medicineMfd_arrayList, medicinePrescription_arrayList, medicineImage_arrayList;
    ArrayList<String> medicineQuantity_arrayList, medicinePrice_arrayList, medicineDescription_arrayList;

    Button camera;

    int erpos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_qrcode);

        Toast.makeText(getApplicationContext(), "Please scan only after values are populated", Toast.LENGTH_SHORT).show();

        camera = (Button)findViewById(R.id.scanbutton);

        // Getting values from the Firebase database
        try {
            medicineName_arrayList = new ArrayList<String>();
            medicineMfd_arrayList = new ArrayList<String>();
            medicinePrescription_arrayList = new ArrayList<String>();
            medicineImage_arrayList = new ArrayList<String>();
            medicineQuantity_arrayList = new ArrayList<String>();
            medicinePrice_arrayList = new ArrayList<String>();
            medicineDescription_arrayList = new ArrayList<String>();

            FirebaseDatabase database = FirebaseDatabase.getInstance("https://aarogya-unlimited-database.firebaseio.com/");
            DatabaseReference p_ref = database.getReference("Medicine Records");

            p_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int pos = 0;
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    while (children.iterator().hasNext()) {
                        DataSnapshot o = children.iterator().next();

                        medicineName_arrayList.add(o.child("Medicine Name").getValue().toString());
                        medicineMfd_arrayList.add(o.child("Medicine Manufacturer").getValue().toString());
                        medicinePrescription_arrayList.add(o.child("Medicine Prescription").getValue().toString());
                        medicineImage_arrayList.add(o.child("Medicine Image URL").getValue().toString());
                        medicineQuantity_arrayList.add(o.child("Medicine Quantity").getValue().toString());
                        medicinePrice_arrayList.add(o.child("Medicine Price").getValue().toString());
                        medicineDescription_arrayList.add(o.child("Medicine Description").getValue().toString());

                        ++pos;
                    }
                    Toast.makeText(getApplicationContext(), "Values Populated and available for viewing", Toast.LENGTH_SHORT).show();
                    for(int j = 0; j < medicineName_arrayList.size(); j++){
                        Toast.makeText(getApplicationContext(),"Medicine " + j + " : " + medicineName_arrayList.get(j),Toast.LENGTH_SHORT).show();
                    }
                    //populateStudentList();
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

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Boolean b = checkCredentials();
               // if(b == true) {
               //     Toast.makeText(getApplicationContext(),"Credentials Verified",Toast.LENGTH_SHORT).show();

                    Intent scan = new Intent(getApplicationContext(), ScanActivity.class);
                    scan.putExtra("Med name Array", medicineName_arrayList);
                    scan.putExtra("Med mfd Array", medicineMfd_arrayList);
                    scan.putExtra("Med prescription Array", medicinePrescription_arrayList);
                    scan.putExtra("Med quantity Array", medicineQuantity_arrayList);
                    scan.putExtra("Med description Array", medicineDescription_arrayList);
                    scan.putExtra("Med image Array", medicineImage_arrayList);
                    scan.putExtra("Med price Array", medicinePrice_arrayList);
                    startActivity(scan);
               // }

                /*else{
                    Toast.makeText(getApplicationContext(),"Credentials are wrong....please try again",Toast.LENGTH_SHORT).show();
                    return;
                }
*/
            }
        });
    }
}
