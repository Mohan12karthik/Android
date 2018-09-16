package com.example.android.sharepreference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class share2 extends AppCompatActivity {
    TextView t3,t4;
    Button b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share2);

        t3=(TextView) findViewById(R.id.et3);
        t4=(TextView) findViewById(R.id.et4);
        b2=(Button)findViewById(R.id.button2);


    }

    public void onClick(View view) {

        SharedPreferences sf= getSharedPreferences("myfile", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=sf.edit();
        String p=sf.getString("hello","NA");
        String q=sf.getString("hai","NA");
        t3.setText(p);
        t4.setText(q);
    }
}
