package ericjoseph.com.exaltproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class MedicineRegistration extends AppCompatActivity {
    public TextView register_event_textview;
    public EditText medicine_name_et, medicine_mfd_et, medicine_prescription_et, medicine_description_et, medicine_price_et, medicine_quantity_et;
    public ImageView medicine_image;
    public String downloadUrl = null;
    public int PICK_IMAGE_REQUEST = 111;
    public Uri filePath = null;
    public DatabaseReference myRef;
    public String qr_download_link = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_registration);

        medicine_name_et = (EditText) findViewById(R.id.medicine_name);
        medicine_mfd_et = (EditText) findViewById(R.id.medicine_manufacturer);
        medicine_prescription_et = (EditText) findViewById(R.id.medicine_prescription);
        medicine_description_et = (EditText) findViewById(R.id.medicine_description);
        medicine_price_et = (EditText)findViewById(R.id.medicine_price);
        medicine_quantity_et = (EditText)findViewById(R.id.medicine_quantity);

        medicine_image = (ImageView) findViewById(R.id.medicine_imageview);
        medicine_image.setOnClickListener(new View.OnClickListener() {
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
        if(medicine_description_et.getText().toString()!="" && medicine_mfd_et.getText().toString()!="" &&
                medicine_name_et.getText().toString()!="" && medicine_prescription_et.getText().toString()!="" &&
                medicine_price_et.getText().toString()!=null & medicine_quantity_et.getText().toString()!="")
            return true;

        else{
            Toast.makeText(getApplicationContext(),"Please fill in all the details to complete upload of Medicine detail",Toast.LENGTH_SHORT).show();
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
                medicine_image.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String uploadQR(String str){

        StorageReference storageRef = FirebaseStorage.getInstance("gs://aarogya-unlimited-database.appspot.com").getReference("Medicine QR Code Images");
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
                qr_download_link = taskSnapshot.getDownloadUrl().toString();
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
            if (filePath != null && checkvalues() == true){
                StorageReference storageRef = FirebaseStorage.getInstance("gs://aarogya-unlimited-database.appspot.com").getReference("Medicine Images");
                StorageReference childRef = storageRef.child(medicine_name_et.getText().toString() + "_image");

                //uploading the image
                final UploadTask uploadTask = childRef.putFile(filePath);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(), "Image Upload successful", Toast.LENGTH_SHORT).show();
                        downloadUrl = taskSnapshot.getDownloadUrl().toString();

                        qr_download_link = uploadQR(medicine_name_et.getText().toString() + " " + medicine_mfd_et.getText().toString());

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
            Toast.makeText(getApplicationContext(), "Medicine Image not uploaded due to errors", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadTextDetailstoFirebase(){
        try {
            //Storing Data Values in RealTime Database
            if (filePath != null && checkvalues() == true) {
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://aarogya-unlimited-database.firebaseio.com/");
                myRef = database.getReference("Medicine Records");

                if (filePath.getPath().toString() != "" && checkvalues()==true)
                    try {
                        myRef.child(medicine_name_et.getText().toString()).setValue(medicine_name_et.getText().toString() + " " + medicine_mfd_et.getText().toString());
                        myRef.child(medicine_name_et.getText().toString()).child("Medicine Name").setValue(medicine_name_et.getText().toString());
                        myRef.child(medicine_name_et.getText().toString()).child("Medicine Manufacturer").setValue(medicine_mfd_et.getText().toString());
                        myRef.child(medicine_name_et.getText().toString()).child("Medicine Quantity").setValue(medicine_quantity_et.getText().toString());
                        myRef.child(medicine_name_et.getText().toString()).child("Medicine Price").setValue(medicine_price_et.getText().toString());
                        myRef.child(medicine_name_et.getText().toString()).child("Medicine Description").setValue(medicine_description_et.getText().toString());
                        myRef.child(medicine_name_et.getText().toString()).child("Medicine Prescription").setValue(medicine_prescription_et.getText().toString());

                        if(downloadUrl!= null)
                            myRef.child(medicine_name_et.getText().toString()).child("Medicine Image URL").setValue(downloadUrl);
                        else
                            myRef.child(medicine_name_et.getText().toString()).child("Medicine Image URL").setValue("Null URL");
                        
                        if(qr_download_link != null)
                            myRef.child(medicine_name_et.getText().toString()).child("Medicine QR Code URL").setValue(qr_download_link);
                        else
                            myRef.child(medicine_name_et.getText().toString()).child("Medicine QR Code URL").setValue("Null URL");

                        Toast.makeText(getApplicationContext(), "Medicine details Registered Successfully", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error...Medicine details not saved", Toast.LENGTH_SHORT).show();
                    }

            } else {
                Toast.makeText(getApplicationContext(), "Select an image first", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error...Please try later" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
