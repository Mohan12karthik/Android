package ericjoseph.com.exaltproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView patient_image, org_image, doctor_image, patient_viewer_image, doctor_viewer_image, appointment_image, scanner_image;
    ImageView pharmacy_view_image, med_reg_image;
    Intent new_activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        patient_image = (ImageView)findViewById(R.id.patient_img);
        patient_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_activity = new Intent(getApplicationContext(),PatientRegistration.class);
                startActivity(new_activity);
            }
        });

        pharmacy_view_image = (ImageView)findViewById(R.id.org_img2);
        pharmacy_view_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_activity = new Intent(getApplicationContext(),PharmacyViewer.class);
                startActivity(new_activity);
            }
        });

        org_image = (ImageView)findViewById(R.id.org_img);
        org_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_activity = new Intent(getApplicationContext(),PharmacyRegistration.class);
                startActivity(new_activity);
            }
        });

        doctor_image = (ImageView)findViewById(R.id.doctor_img);
        doctor_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_activity = new Intent(getApplicationContext(),DoctorRegistration.class);
                startActivity(new_activity);
            }
        });

        patient_viewer_image = (ImageView) findViewById(R.id.patient_img2);
        patient_viewer_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_activity = new Intent(getApplicationContext(),PatientViewerActivity.class);
                startActivity(new_activity);
            }
        });

        doctor_viewer_image = (ImageView) findViewById(R.id.doctor_img2);
        doctor_viewer_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_activity = new Intent(getApplicationContext(),DoctorViewerActivity.class);
                startActivity(new_activity);
            }
        });

        appointment_image = (ImageView) findViewById(R.id.doctor_appointment_img);
        appointment_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_activity = new Intent(getApplicationContext(),AppointmentActionSelector.class);
                startActivity(new_activity);
            }
        });

        med_reg_image = (ImageView) findViewById(R.id.transport_img);
        med_reg_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_activity = new Intent(getApplicationContext(),MedicineRegistration.class);
                startActivity(new_activity);
            }
        });

        scanner_image = (ImageView) findViewById(R.id.farmer_img);
        scanner_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_activity = new Intent(getApplicationContext(),CheckInQRCode.class);
                startActivity(new_activity);
            }
        });
    }
}
