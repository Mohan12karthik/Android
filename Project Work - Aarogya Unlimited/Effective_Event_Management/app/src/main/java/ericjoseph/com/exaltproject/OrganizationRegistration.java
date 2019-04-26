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

public class OrganizationRegistration extends AppCompatActivity {

    EditText org_name, org_address, org_mem1_name, org_mem1_phone, org_mem1_email, org_mem2_name, org_mem2_phone, org_mem2_email, org_mem3_name, org_mem3_phone, org_mem3_email;
    TextView register_org;
    ImageView org_image;
    Uri filePath;
    String downloadUrl;
    int PICK_IMAGE_REQUEST = 9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_registration);

        org_name = (EditText)findViewById(R.id.pharmacy_name);
        org_address = (EditText)findViewById(R.id.pharmacy_address);
        org_mem1_name = (EditText)findViewById(R.id.pharmacy_id);
        org_mem1_phone = (EditText)findViewById(R.id.pharmacy_phone);
        org_mem1_email = (EditText)findViewById(R.id.org_mem1_email);
        org_mem2_name = (EditText)findViewById(R.id.org_mem2_name);
        org_mem2_phone = (EditText)findViewById(R.id.org_mem2_phone);
        org_mem2_email = (EditText)findViewById(R.id.org_mem2_email);
        org_mem3_name = (EditText)findViewById(R.id.org_mem3_name);
        org_mem3_phone = (EditText)findViewById(R.id.org_mem3_phone);
        org_mem3_email = (EditText)findViewById(R.id.org_mem3_email);

        org_image = (ImageView)findViewById(R.id.org_imageview);
        org_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });

        register_org = (TextView)findViewById(R.id.register_org);
        register_org.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImagestoFirebase();
            }
        });
    }

    public void uploadImagestoFirebase(){
        try {
            //Storing Image now
            StorageReference storageRef = FirebaseStorage.getInstance().getReference("Organization Images");
            StorageReference childRef = storageRef.child(org_name.getText().toString() + "_image");
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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Organization Records");

        try {
            myRef.child(org_name.getText().toString()).setValue("0");
            myRef.child(org_name.getText().toString()).child("Org Name").setValue(org_name.getText().toString());
            myRef.child(org_name.getText().toString()).child("Org Address").setValue(org_address.getText().toString());
            myRef.child(org_name.getText().toString()).child("Org Member 1 Name").setValue(org_mem1_name.getText().toString());
            myRef.child(org_name.getText().toString()).child("Org Member 1 Phone").setValue(org_mem1_phone.getText().toString());
            myRef.child(org_name.getText().toString()).child("Org Member 1 eMail").setValue(org_mem1_email.getText().toString());
            myRef.child(org_name.getText().toString()).child("Org Member 2 Name").setValue(org_mem2_name.getText().toString());
            myRef.child(org_name.getText().toString()).child("Org Member 2 Phone").setValue(org_mem2_phone.getText().toString());
            myRef.child(org_name.getText().toString()).child("Org Member 2 eMail").setValue(org_mem2_email.getText().toString());
            myRef.child(org_name.getText().toString()).child("Org Member 3 Name").setValue(org_mem3_name.getText().toString());
            myRef.child(org_name.getText().toString()).child("Org Member 3 Phone").setValue(org_mem3_phone.getText().toString());
            myRef.child(org_name.getText().toString()).child("Org Member 3 eMail").setValue(org_mem3_email.getText().toString());
            myRef.child(org_name.getText().toString()).child("Org Image URL").setValue(downloadUrl);

            Toast.makeText(getApplicationContext(),"Organization Registered Successfully",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error...Student Details not saved",Toast.LENGTH_SHORT).show();
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
                org_image.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
