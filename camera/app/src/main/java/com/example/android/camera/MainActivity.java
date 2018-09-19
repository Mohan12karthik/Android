package com.example.android.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView img;
    Button b,b2;
    int CAMERA_RESQUEST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img=(ImageView)findViewById(R.id.imageview);
        b=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        b.setOnClickListener(this);
        b2.setOnClickListener(this);
    }

    public void onClick(View view){
       Intent camera =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera,CAMERA_RESQUEST);
        Intent gal=new Intent();
        gal.setType("image/*");
        gal.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gal,"Select Pictire"),3);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_RESQUEST){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(photo);
        }

        if(requestCode==3 && resultCode==RESULT_OK && data != null && data.getData() !=null){
            Uri uri = data.getData();
            try{
                Bitmap bitmap2=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                ImageView imageView = (ImageView)findViewById(R.id.imageview);
                imageView.setImageBitmap(bitmap2);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
