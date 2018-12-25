package com.example.admin.doc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class con_appoint extends AppCompatActivity {

    TextView p_date,p_time,p_name,p_fee;
    EditText reason;
    Button book;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_appoint);

        p_date=(TextView)findViewById(R.id.p_date);
        p_time=(TextView)findViewById(R.id.p_time);
        p_name=(TextView)findViewById(R.id.p_name);
        p_fee=(TextView)findViewById(R.id.p_fee);




//        String fee=getIntent().getStringExtra("textViewFee");
//        p_fee.setText(String.valueOf(fee));
//
//
//        Intent in=getIntent();
//        String b_date=in.getStringExtra("s_date");
//        p_date.setText(b_date);
//        String b_time=in.getStringExtra("s_time");
//        p_time.setText(b_time);

    }



}
