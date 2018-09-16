package com.example.android.sharepreference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText t1, t2;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = (EditText) findViewById(R.id.et1);
        t2 = (EditText) findViewById(R.id.et2);
        b1 = (Button) findViewById(R.id.button);



    }

    public void onClick(View view) {

        SharedPreferences sf = getSharedPreferences("myfile", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sf.edit();
        edit.putString("hello", t1.getText().toString());
        edit.putString("hai", t2.getText().toString());
        edit.commit();
        Intent i = new Intent(this, share2.class);

        startActivity(i);
    }
}