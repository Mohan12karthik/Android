package ericjoseph.com.exaltproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;
    ArrayList<String> nameList, mfdList, presList, priceList, quantityList, imageList, descList;
    String student_ref_string;
    public Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        b = getIntent().getExtras();

        nameList = b.getStringArrayList("Med name Array");
        mfdList = b.getStringArrayList("Med mfd Array");
        presList = b.getStringArrayList("Med prescription Array");
        priceList = b.getStringArrayList("Med price Array");
        quantityList = b.getStringArrayList("Med quantity Array");
        imageList = b.getStringArrayList("Med image Array");
        descList = b.getStringArrayList("Med description Array");


        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                       // Set the scanner view as the content view
    }
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        String eventScanString = rawResult.toString();
        //Toast.makeText(getApplicationContext(),eventScanString,Toast.LENGTH_SHORT).show();
        int j;

        int flag = 0;
        for(j = 0; j < nameList.size(); j++){
            String raw_string = nameList.get(j) + " " + mfdList.get(j);
            if(raw_string.equals(eventScanString) == true){
                Toast.makeText(getApplicationContext(),"Medicine name found : " + nameList.get(j),Toast.LENGTH_SHORT).show();
                flag = 1;
                break;
            }
        }

        if(flag == 0)
            Toast.makeText(getApplicationContext(),"No such medicine in database ", Toast.LENGTH_LONG).show();
        else{
            Intent i = new Intent(getApplicationContext(),BuyMedicinePage.class);
            i.putExtra("Med name", nameList.get(j));
            i.putExtra("Med mfd", mfdList.get(j));
            i.putExtra("Med prescription", presList.get(j));

            i.putExtra("Med price", priceList.get(j));
            i.putExtra("Med quantity", quantityList.get(j));
            i.putExtra("Med image", imageList.get(j));
            i.putExtra("Med description", descList.get(j));
            startActivity(i);
        }

        onBackPressed();
    }

    void registerCheckIn(final String x){
        String temp;
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            final DatabaseReference myref = database.getReference("Participation Records");
            myref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    while (children.iterator().hasNext()) {
                        DataSnapshot o = children.iterator().next();
                        String temp = o.getValue().toString();
                        if(temp.equals(x)) {
                            myref.child(temp).child("Check_In").setValue("Yes");
                            Toast.makeText(getApplicationContext(), "Check-In done", Toast.LENGTH_SHORT).show();
                            break;
                        }
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
    }
}
