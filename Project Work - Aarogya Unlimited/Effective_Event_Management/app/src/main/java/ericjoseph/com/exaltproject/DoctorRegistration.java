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

public class DoctorRegistration extends AppCompatActivity {
    EditText doctor_name_txt, doctor_user_id_txt, doctor_work_address_txt, doctor_mail_txt, doctor_phone_txt, doctor_password_txt;
    EditText doctor_city_name_txt, doctor_from_time_txt, doctor_to_time_txt, doctor_clinic_name_txt;
    Button register;
    Spinner spin;
    String[] doctor_specialization_list;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Uri filePath;
    String downloadUrl;
    ImageView img;
    int PICK_IMAGE_REQUEST = 9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        doctor_specialization_list = new String[]{"Pediatrics", "Gynaecology", "Cardiology", "Urology", "Neurology", "Radiology", "General Health"};

        doctor_name_txt = (EditText) findViewById(R.id.doctor_name);
        doctor_work_address_txt = (EditText) findViewById(R.id.doctor_work_address);
        doctor_user_id_txt = (EditText) findViewById(R.id.doctor_userid);
        doctor_password_txt = (EditText) findViewById(R.id.doctor_password);
        doctor_mail_txt = (EditText) findViewById(R.id.doctor_email);
        doctor_phone_txt = (EditText) findViewById(R.id.doctor_phone_num);
        doctor_clinic_name_txt = (EditText) findViewById(R.id.doctor_workplace);
        doctor_city_name_txt = (EditText) findViewById(R.id.doctor_city);
        doctor_from_time_txt = (EditText) findViewById(R.id.doctor_from_time);
        doctor_to_time_txt = (EditText) findViewById(R.id.doctor_to_timing);

        spin = (Spinner)findViewById(R.id.doctor_specialization);
        ArrayAdapter<String> spin_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,doctor_specialization_list);
        spin.setAdapter(spin_adapter);

        register = (Button)findViewById(R.id.register_doctor);
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
            StorageReference storageRef = FirebaseStorage.getInstance("gs://aarogya-unlimited-database.appspot.com").getReference("Doctor Images");
            StorageReference childRef = storageRef.child(doctor_name_txt.getText().toString() + "_image");
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
            Toast.makeText(getApplicationContext(), "Doctor Image not Uploaded due to errors", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadTextDetailstoFirebase() {
        database = FirebaseDatabase.getInstance("https://aarogya-unlimited-database.firebaseio.com/");
        myRef = database.getReference("Doctor Records");

        try {
            myRef.child(doctor_name_txt.getText().toString()).setValue(doctor_user_id_txt.getText().toString());
            myRef.child(doctor_name_txt.getText().toString()).child("Doctor Name").setValue(doctor_name_txt.getText().toString());
            myRef.child(doctor_name_txt.getText().toString()).child("Doctor Workplace Name").setValue(doctor_clinic_name_txt.getText().toString());
            myRef.child(doctor_name_txt.getText().toString()).child("Doctor Address").setValue(doctor_work_address_txt.getText().toString());
            myRef.child(doctor_name_txt.getText().toString()).child("Doctor City").setValue(doctor_city_name_txt.getText().toString());
            myRef.child(doctor_name_txt.getText().toString()).child("Doctor User ID").setValue(doctor_user_id_txt.getText().toString());
            myRef.child(doctor_name_txt.getText().toString()).child("Doctor Password").setValue(doctor_password_txt.getText().toString());
            myRef.child(doctor_name_txt.getText().toString()).child("Doctor Specialization").setValue(spin.getSelectedItem().toString());
            myRef.child(doctor_name_txt.getText().toString()).child("Doctor Phone").setValue(doctor_phone_txt.getText().toString());
            myRef.child(doctor_name_txt.getText().toString()).child("Doctor Email").setValue(doctor_mail_txt.getText().toString());
            myRef.child(doctor_name_txt.getText().toString()).child("Doctor To Time").setValue(doctor_to_time_txt.getText().toString());
            myRef.child(doctor_name_txt.getText().toString()).child("Doctor From Time").setValue(doctor_from_time_txt.getText().toString());

            myRef.child(doctor_name_txt.getText().toString()).child("Doctor Image URL").setValue(downloadUrl);

            Toast.makeText(getApplicationContext(),"Data values saved for Doctor " + doctor_name_txt.getText().toString(),Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error...doctor details not saved",Toast.LENGTH_SHORT).show();
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