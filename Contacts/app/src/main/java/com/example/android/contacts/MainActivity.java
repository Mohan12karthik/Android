package com.example.android.contacts;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton imgButton1,imgButton2;
    Spinner s1;
    String songs[]={"Ringtone","Aquila","Andromeda","Atria","Backroad","Bell Phone","Boots","cairo","Centaurus","Chimey Phone","Dione","Digital Phone","Eridani","Flutey Phone","Free Flight","Growl","Hydra","Insert coin","Luna","Mildly Alarming","Orion","Rockin","World"};
    CheckBox c1;
    Button b1,b2;
    int flag=0,m=0,h=0,s=0;
    private EditText e1,e2;
    private Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgButton1 =(ImageButton)findViewById(R.id.imageButton4);
        imgButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et1=(EditText)findViewById(R.id.edit_text1) ;
                et1.setText("");
                Toast.makeText(getApplicationContext(),"Removed number",Toast.LENGTH_LONG).show();
                m=1;
            }
        });

        imgButton2 =(ImageButton)findViewById(R.id.imageButton5);
        imgButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et2=(EditText)findViewById(R.id.edit_text2) ;
                et2.setText("");
                Toast.makeText(getApplicationContext(),"Removed email",Toast.LENGTH_LONG).show();
                h=1;
            }
        });


        s1=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String>adapter1= new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,songs);
        s1.setAdapter(adapter1);
        s1.setOnItemSelectedListener(new SongOnItemSelectedListener());

        c1 = (CheckBox) findViewById(R.id.checkBox);
        c1.setOnClickListener( new CheckItemSelectedListener());

        addListenerOnButton();
    }



    public class SongOnItemSelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            Toast.makeText(parent.getContext(),"SongOnItemSelectedListener:"+parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();;
            s=1;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public class CheckItemSelectedListener implements AdapterView.OnItemSelectedListener, View.OnClickListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(parent.getContext(),"CheckItemSelectedListener"+parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();;

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

        @Override
        public void onClick(View v) {
            CheckBox t = (CheckBox) v;

            if (t.isChecked()) {
                Toast.makeText(getApplicationContext(), t.getText()+"Voicemail is selected", Toast.LENGTH_SHORT).show();
                flag=1;
                }


        }
    }

    public void Clickinfo(View v){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Confirm Info...");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure your information is correct!?");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_launcher_background);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You clicked on YES :   "+which, Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                EditText et3=(EditText)findViewById(R.id.edit_text1) ;
                EditText et4=(EditText)findViewById(R.id.edit_text2) ;
                et3.setText("");
                et4.setText("");
                c1.setChecked(false);
                Toast.makeText(getApplicationContext(), "You clicked on NO :   "+which, Toast.LENGTH_SHORT).show();

                // dialog.cancel();
            }
        });
        // Setting Netural "Cancel" Button
        alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // User pressed Cancel button. Write Logic Here
                Toast.makeText(getApplicationContext(), "You clicked on Cancel :   "+which,
                        Toast.LENGTH_SHORT).show();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

   /* public void Clicksave(View v) {
        /*if (m == 1) {
            Toast.makeText(getApplicationContext(), "Home,Ringtone,Voicemail are saved", Toast.LENGTH_SHORT).show();
            }
            else{
            Toast.makeText(getApplicationContext(),  "Mobile,Home,Ringtone,Voicemail are saved", Toast.LENGTH_SHORT).show();
            }

        if (h == 1) {
            Toast.makeText(getApplicationContext(), "Mobile,Ringtone,Voicemail are saved", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),  "Mobile,Home,Ringtone,Voicemail are saved", Toast.LENGTH_SHORT).show();
        }

        if (s == 1) {
            Toast.makeText(getApplicationContext(), "Mobile,Home,Voicemail are saved", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),  "Mobile,Home,Ringtone,Voicemail are saved", Toast.LENGTH_SHORT).show();
        }


        if (flag == 1) {

            Toast.makeText(getApplicationContext(), "Mobile,Home,Ringtone,Voicemail are saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Mobile,Home,Ringtone are saved", Toast.LENGTH_SHORT).show();
        }
    }*/

    public void addListenerOnButton() {

        e1= (EditText) findViewById(R.id.edit_text1);
        e2 = (EditText) findViewById(R.id.edit_text2);
        save = (Button) findViewById(R.id.button2);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.FILL_VERTICAL|Gravity.FILL, 0, 0);
// Toast toast = Toast.makeText(context, text, duration);
               // Toast toast2 = new Toast(getApplicationContext());
               // toast2.setGravity(Gravity.FILL_VERTICAL|Gravity.FILL, 0, 0);
// Toast toast = Toast.makeText(context, text, duration);

                if((e1.getText().toString().isEmpty() | e2.getText().toString().isEmpty())){
                    toast.makeText(MainActivity.this, "All fields should be Entered", toast.LENGTH_LONG).show();

                }
                else if(c1.isChecked()) {
                    toast.makeText(MainActivity.this, e1.getText().toString() + " , " + e2.getText().toString()+" "+s1.getSelectedItem().toString()+","+"Voicemail", toast.LENGTH_LONG).show();
                }
                else{
                    toast.makeText(MainActivity.this, e1.getText().toString() + " , " + e2.getText().toString()+" , "+s1.getSelectedItem().toString(), toast.LENGTH_LONG).show();

                }

               /* if((e1.getText().toString().isEmpty() | e2.getText().toString().isEmpty()) | ( c1.isChecked())){
                    toast.makeText(MainActivity.this, "All fields should be Entered", toast.LENGTH_LONG).show();

                }*/
                //toast2.makeText(MainActivity.this, e2.getText(), toast2.LENGTH_SHORT).show();

            }

        });

    }
public void Clickexit(View v){
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
}


}


