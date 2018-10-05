package com.example.android.googleform;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    EditText name,rollno;
    Button submit;

    public static  final MediaType FORM_DATA_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    public static final  String URL="https://docs.google.com/forms/d/e/1FAIpQLSfNRFROK_IngFDwlmxxR1-gSZG9Hwr0tTXAd-dBrC29I-Cb0w/formResponse";
    public static  final String NAME_KEY="entry.1752970434";
    public static  final String Roll_KEY="entry.181296046";

    public Context context=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=(EditText)findViewById(R.id.editText);
        rollno=(EditText)findViewById(R.id.editText2);
        submit=(Button)findViewById(R.id.button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postdata postdata = new postdata();
                postdata.execute(URL,name.getText().toString(),rollno.getText().toString());
            }
        });
    }

    public class postdata extends AsyncTask<String,Void ,Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean result= true;
            String url_1 = strings[0];
            String name  = strings[1];
            String  roll = strings[2];
            String postbody="";
            try {
                postbody = NAME_KEY+"="+URLEncoder.encode(name,"UTF-8") + "&" + Roll_KEY+"="+URLEncoder.encode(roll,"UTF-8");
            }catch (UnsupportedEncodingException ex){
                result =  false;
            }
            try{
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(FORM_DATA_TYPE,postbody);
                Request requestBody = new Request.Builder().url(URL).post(body).build();
                Response response = client.newCall(requestBody).execute();
            }catch (Exception ex){
                result = false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
            super.onPostExecute(aBoolean);
        }
    }
}

