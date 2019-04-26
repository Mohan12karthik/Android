package ericjoseph.com.exaltproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PharmacyRegistration extends AppCompatActivity {

    EditText pharmacy_name, pharmacy_address, pharmacy_userid, pharmacy_password, pharmacy_email, pharmacy_phone;
    TextView register_pharmacy;
    ImageView pharmacy_image;
    Uri filePath;
    String downloadUrl;
    int PICK_IMAGE_REQUEST = 9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_registration);

        pharmacy_name = (EditText)findViewById(R.id.pharmacy_name);
        pharmacy_address = (EditText)findViewById(R.id.pharmacy_address);
        pharmacy_userid = (EditText)findViewById(R.id.pharmacy_id);
        pharmacy_password = (EditText)findViewById(R.id.pharmacy_password);
        pharmacy_phone = (EditText)findViewById(R.id.pharmacy_phone);
        pharmacy_email = (EditText)findViewById(R.id.pharmacy_email);

        pharmacy_image = (ImageView)findViewById(R.id.org_imageview);
        pharmacy_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });

        register_pharmacy = (TextView)findViewById(R.id.register_pharmacy);
        register_pharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImagestoFirebase();
            }
        });
    }

    public void uploadImagestoFirebase(){
        try {
            //Storing Image now
            StorageReference storageRef = FirebaseStorage.getInstance("gs://aarogya-unlimited-database.appspot.com").getReference("Pharmacy Images");
            StorageReference childRef = storageRef.child(pharmacy_name.getText().toString() + "_image");
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
            Toast.makeText(getApplicationContext(), "Image not Uploaded due to errors", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadTextDetailstoFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://aarogya-unlimited-database.firebaseio.com/");
        DatabaseReference myRef = database.getReference("Pharmacy Records");

        try {
            myRef.child(pharmacy_name.getText().toString()).setValue("0");
            myRef.child(pharmacy_name.getText().toString()).child("Pharmacy Name").setValue(pharmacy_name.getText().toString());
            myRef.child(pharmacy_name.getText().toString()).child("Pharmacy Address").setValue(pharmacy_address.getText().toString());
            myRef.child(pharmacy_name.getText().toString()).child("Pharmacy User ID").setValue(pharmacy_userid.getText().toString());
            myRef.child(pharmacy_name.getText().toString()).child("Pharmacy Password").setValue(pharmacy_password.getText().toString());
            myRef.child(pharmacy_name.getText().toString()).child("Pharmacy Phone number").setValue(pharmacy_phone.getText().toString());
            myRef.child(pharmacy_name.getText().toString()).child("Pharmacy Email").setValue(pharmacy_email.getText().toString());
            myRef.child(pharmacy_name.getText().toString()).child("Pharmacy Image URL").setValue(downloadUrl);

            Toast.makeText(getApplicationContext(),"Pharmacy details registered successfully",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error...Pharmacy details not saved",Toast.LENGTH_SHORT).show();
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
                pharmacy_image.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
