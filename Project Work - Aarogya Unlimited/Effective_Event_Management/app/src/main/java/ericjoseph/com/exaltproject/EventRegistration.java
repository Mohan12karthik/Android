package ericjoseph.com.exaltproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class EventRegistration extends AppCompatActivity {
    TextView register_event_textview;
    EditText event_name_et, event_topic_et, event_id_et, event_date_et, event_from_time_et, event_to_time_et, event_location_et, event_head_name_et, event_head_phone_et;
    EditText event_mem1_name_et, event_mem1_phone_et, event_mem2_name_et, event_mem2_phone_et, event_organizer_et, event_price_value_et;
    ImageView event_image;
    Uri downloadUrl = null;
    int PICK_IMAGE_REQUEST = 111;
    Uri filePath = null;
    DatabaseReference myRef;
    Bitmap bitmap;
    Spinner team_type_spinner, price_type_spinner;
    //creating reference to firebase storage
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://effectiveeventmanagement.appspot.com/Event Images");    //change the url according to your firebase app
    String[] price_types, team_types;
    Uri qr_download_link = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registration);

        price_types = new String[]{"Click to Select Price Type","Free","Paid"};
        team_types =  new String[]{"Click to Select Team Type","Individual","Multiple"};

        team_type_spinner = (Spinner)findViewById(R.id.event_team_type_spinner);
        price_type_spinner = (Spinner)findViewById(R.id.event_price_spinner);

        ArrayAdapter<String> team_adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,team_types);
        ArrayAdapter<String> price_adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,price_types);

        team_type_spinner.setAdapter(team_adapter);
        price_type_spinner.setAdapter(price_adapter);

        event_name_et = (EditText) findViewById(R.id.medicine_name);
        event_topic_et = (EditText) findViewById(R.id.medicine_manufacturer);
        event_date_et = (EditText) findViewById(R.id.medicine_prescription);
        event_from_time_et = (EditText) findViewById(R.id.event_from_time_edittext);
        event_to_time_et = (EditText) findViewById(R.id.event_to_time_edittext);
        event_location_et = (EditText) findViewById(R.id.medicine_description);
        event_head_name_et = (EditText) findViewById(R.id.event_head_name);
        event_head_phone_et = (EditText) findViewById(R.id.event_head_phone);
        event_mem1_name_et = (EditText) findViewById(R.id.event_mem1_name);
        event_mem1_phone_et = (EditText) findViewById(R.id.event_mem1_phone);
        event_mem2_name_et = (EditText) findViewById(R.id.event_mem2_name);
        event_mem2_phone_et = (EditText) findViewById(R.id.event_member2_phonenum);
        event_organizer_et = (EditText)findViewById(R.id.medicine_price);
        event_price_value_et = (EditText)findViewById(R.id.event_price);
        event_id_et = (EditText)findViewById(R.id.event_id);

        event_image = (ImageView) findViewById(R.id.medicine_imageview);
        event_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });

        register_event_textview = (TextView) findViewById(R.id.register_medicine_txt);
        register_event_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImagestoFirebase();
            }
        });
    }

    public boolean checkvalues(){
        if(event_name_et.getText().toString()!="" && event_id_et.getText().toString()!="" && event_topic_et.getText().toString()!="" && event_location_et.getText().toString()!="" && event_date_et.getText().toString()!=null &
                event_from_time_et.getText().toString()!="" && event_to_time_et.getText().toString()!= "" && event_head_name_et.getText().toString()!="" && event_head_phone_et.getText().toString()!= "" &
                event_mem1_name_et.getText().toString()!="" && event_mem1_phone_et.getText().toString()!="" && event_mem2_name_et.getText().toString()!="" && event_mem2_phone_et.getText().toString()!="" &
                event_organizer_et.getText().toString()!="" && team_type_spinner.getSelectedItem().toString()!= "Click to Select Team Type" &&
                price_type_spinner.getSelectedItem().toString()!= "Click to Select Price Type")
        return true;

        else{
            Toast.makeText(getApplicationContext(),"Please fill in all the details to complete upload of event",Toast.LENGTH_SHORT).show();
            return false;
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
                event_image.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Uri uploadQR(String str){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference("Sample QR Code");
        // Create a reference to "mountains.jpg"
        StorageReference mountainsRef = storageRef.child(str + "_qr_code.jpg");

        Bitmap bitmap = makeBitmap(str);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        final UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                qr_download_link = taskSnapshot.getDownloadUrl();
                Toast.makeText(getApplicationContext(),"QR code Uploaded",Toast.LENGTH_SHORT).show();
                uploadTextDetailstoFirebase();
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(),"Please wait for qr code to upload",Toast.LENGTH_SHORT).show();
            }
        });
        return qr_download_link;
    }

    public Bitmap makeBitmap(String STR){
        Bitmap bitmap = null;
        //ImageView imageView = (ImageView) findViewById(R.id.qr_img);
        try {
            bitmap = encodeAsBitmap(STR);
            //  imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        int WIDTH = 200;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        int width = 200;
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, w, h);
        return bitmap;
    }

    public void uploadImagestoFirebase(){
        try {
            //Storing Image now
            StorageReference childRef = storageRef.child(event_name_et.getText().toString() + "_image");
            //uploading the image
            final UploadTask uploadTask = childRef.putFile(filePath);

            if (filePath != null && checkvalues() == true){
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(), "Image Upload successful", Toast.LENGTH_SHORT).show();
                        downloadUrl = taskSnapshot.getDownloadUrl();

                        qr_download_link = uploadQR(event_id_et.getText().toString());

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
            Toast.makeText(getApplicationContext(), "Event Image not Uploaded due to errors", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadTextDetailstoFirebase(){
        try {
            //Storing Data Values in RealTime Database
            if (filePath != null && checkvalues() == true) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                myRef = database.getReference("Event Records");

                        /*
                        while(downloadUrl == null && qr_download_link == null);
                            Toast.makeText(getApplicationContext(), "Event Image and QR Code uploaded...Please Wait for remaining images to be uploaded", Toast.LENGTH_SHORT).show();
                        */

                if (filePath.getPath().toString() != "" && checkvalues()==true)
                    try {
                        myRef.child(event_name_et.getText().toString()).setValue("0");
                        myRef.child(event_name_et.getText().toString()).child("Event Member 2 Phone").setValue(event_mem2_phone_et.getText().toString());
                        myRef.child(event_name_et.getText().toString()).child("Event Name").setValue(event_name_et.getText().toString());
                        myRef.child(event_name_et.getText().toString()).child("Event ID").setValue(event_id_et.getText().toString());
                        myRef.child(event_name_et.getText().toString()).child("Event Topic").setValue(event_topic_et.getText().toString());
                        myRef.child(event_name_et.getText().toString()).child("Event Date").setValue(event_date_et.getText().toString());
                        myRef.child(event_name_et.getText().toString()).child("Event From Time").setValue(event_from_time_et.getText().toString());
                        myRef.child(event_name_et.getText().toString()).child("Event To Time").setValue(event_to_time_et.getText().toString());
                        myRef.child(event_name_et.getText().toString()).child("Event Location").setValue(event_location_et.getText().toString());
                        myRef.child(event_name_et.getText().toString()).child("Event Head Name").setValue(event_head_name_et.getText().toString());
                        myRef.child(event_name_et.getText().toString()).child("Event Head Phone").setValue(event_head_phone_et.getText().toString());
                        myRef.child(event_name_et.getText().toString()).child("Event Member 1 Name").setValue(event_mem1_name_et.getText().toString());
                        myRef.child(event_name_et.getText().toString()).child("Event Member 1 Phone").setValue(event_mem1_phone_et.getText().toString());
                        myRef.child(event_name_et.getText().toString()).child("Event Member 2 Name").setValue(event_mem2_name_et.getText().toString());
                        myRef.child(event_name_et.getText().toString()).child("Event Organizer").setValue(event_organizer_et.getText().toString());
                        myRef.child(event_name_et.getText().toString()).child("Event Team Type").setValue(team_type_spinner.getSelectedItem().toString());

                        if(downloadUrl.getPath().toString()!= null)
                            myRef.child(event_name_et.getText().toString()).child("Event Image URL").setValue(downloadUrl.getPath().toString());
                        else
                            myRef.child(event_name_et.getText().toString()).child("Event Image URL").setValue("Null URL");

                        if (price_type_spinner.getSelectedItem().toString() == "Free")
                            myRef.child(event_name_et.getText().toString()).child("Event Fees").setValue("Free");
                        else if (price_type_spinner.getSelectedItem().toString() == "Paid")
                            myRef.child(event_name_et.getText().toString()).child("Event Fees").setValue("" + event_price_value_et.getText().toString());

                        if(qr_download_link.getPath().toString()!= null)
                            myRef.child(event_name_et.getText().toString()).child("Event QRCode URL").setValue(qr_download_link.getPath().toString());
                        else
                            myRef.child(event_name_et.getText().toString()).child("Event QRCode URL").setValue("Null URL");


                        Toast.makeText(getApplicationContext(), "Event Registered Successfully", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error...Student Details not saved", Toast.LENGTH_SHORT).show();
                    }

            } else {
                Toast.makeText(getApplicationContext(), "Select an image first", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error..." + e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
