package ericjoseph.com.exaltproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PatientRegistration extends AppCompatActivity {
    EditText patient_name_txt, patient_user_id_txt, patient_DOB_txt, patient_address_txt, patient_mail_txt, patient_phone_txt, patient_password_txt;
    Button register;
    Spinner spin, spin2;
    String[] patient_aliments, patient_blood_type;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Uri filePath;
    String downloadUrl;
    ImageView img;
    int PICK_IMAGE_REQUEST = 9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);

        patient_blood_type = new String[]{"O+", "O-", "A+", "A-", "B+", "B-", "AB"};
        patient_aliments = new String[]{"Diabetes","Hypertension","Cholesterol","Joint pain"};

        patient_name_txt = (EditText) findViewById(R.id.doctor_name);
        patient_DOB_txt = (EditText) findViewById(R.id.doctor_city);
        patient_address_txt = (EditText) findViewById(R.id.patient_address);
        patient_user_id_txt = (EditText) findViewById(R.id.doctor_userid);
        patient_password_txt = (EditText) findViewById(R.id.doctor_password);
        patient_mail_txt = (EditText) findViewById(R.id.doctor_workplace);
        patient_phone_txt = (EditText) findViewById(R.id.doctor_email);

        spin = (Spinner)findViewById(R.id.doctor_specialization);
        ArrayAdapter<String> spin_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,patient_blood_type);
        spin.setAdapter(spin_adapter);

        spin2 = (Spinner)findViewById(R.id.patient_ailments_spinner);
        ArrayAdapter<String> spin_adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,patient_aliments);
        spin2.setAdapter(spin_adapter2);

        register = (Button)findViewById(R.id.button2);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImagestoFirebase();
            }
        });

        img = (ImageView) findViewById(R.id.doctor_image);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });
    }

    public void uploadImagestoFirebase(){
        try {
            //Storing Image now
            StorageReference storageRef = FirebaseStorage.getInstance("gs://aarogya-unlimited-database.appspot.com").getReference("Patient Images");
            StorageReference childRef = storageRef.child(patient_name_txt.getText().toString() + "_image");
            //uploading the image
            final UploadTask uploadTask = childRef.putFile(filePath);

            if (filePath != null){
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(), "Image Upload successful", Toast.LENGTH_SHORT).show();
                        downloadUrl = taskSnapshot.getDownloadUrl().toString();
                        uploadTextDetailstoFirebase();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Upload Failed -> Please try again" + e, Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(), "Uploading Image and Details...Please Wait and do not exit from page", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
                Toast.makeText(getApplicationContext(),"Please enter all the details or select Image if not done",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Patient Image not Uploaded due to errors", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadTextDetailstoFirebase() {
        database = FirebaseDatabase.getInstance("https://aarogya-unlimited-database.firebaseio.com/");
        myRef = database.getReference("Patient Records");

        try {
            myRef.child(patient_name_txt.getText().toString()).setValue(patient_user_id_txt.getText().toString());
            myRef.child(patient_name_txt.getText().toString()).child("Patient Name").setValue(patient_name_txt.getText().toString());
            myRef.child(patient_name_txt.getText().toString()).child("Patient Address").setValue(patient_address_txt.getText().toString());
            myRef.child(patient_name_txt.getText().toString()).child("Patient Date of Birth").setValue(patient_DOB_txt.getText().toString());
            myRef.child(patient_name_txt.getText().toString()).child("Patient User ID").setValue(patient_user_id_txt.getText().toString());
            myRef.child(patient_name_txt.getText().toString()).child("Patient Password").setValue(patient_password_txt.getText().toString());
            myRef.child(patient_name_txt.getText().toString()).child("Patient Blood Type").setValue(spin.getSelectedItem().toString());
            myRef.child(patient_name_txt.getText().toString()).child("Patient Ailment").setValue(spin2.getSelectedItem().toString());
            myRef.child(patient_name_txt.getText().toString()).child("Patient Phone").setValue(patient_phone_txt.getText().toString());
            myRef.child(patient_name_txt.getText().toString()).child("Patient Email").setValue(patient_mail_txt.getText().toString());
            myRef.child(patient_name_txt.getText().toString()).child("Patient Image URL").setValue(downloadUrl);

            Toast.makeText(getApplicationContext(),"Data values saved for patient " + patient_name_txt.getText().toString(),Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error...patient details not saved",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting image to ImageView
                img.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}