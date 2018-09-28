package com.example.android.sqlite;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    TextView t1;
    EditText name,surname,marks,id;
    Button add,view,update,del;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb=new DatabaseHelper(this);

        t1=(TextView)findViewById(R.id.student);
        name=(EditText)findViewById(R.id.editText);
        surname=(EditText)findViewById(R.id.editText2);
        marks=(EditText)findViewById(R.id.editText3);
        id=(EditText)findViewById(R.id.editText4);
        add=(Button)findViewById(R.id.button);
        view=(Button)findViewById(R.id.button2);
        update=(Button)findViewById(R.id.button3);
        del=(Button)findViewById(R.id.button4);
        AddData();
        ViewAll();
        UpdateData();
        DeleteData();
    }

    public void AddData(){

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(name.getText().toString(),surname.getText().toString(),marks.getText().toString());
                if(isInserted==true){
                    Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Data NOT Inserted",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    public void ViewAll(){

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res=myDb.getAllData();
                if(res.getCount()==0){
                    showMessage("Error","Nothing found");
                    return;
                }

                StringBuffer buffer=new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Id :"+res.getString(0)+"\n");
                    buffer.append("Name :"+res.getString(1)+"\n");
                    buffer.append("Surname :"+res.getString(2)+"\n");
                    buffer.append("Marks :"+res.getString(3)+"\n\n");
                }

                showMessage("Data ",buffer.toString());
            }
        });
    }

public void  UpdateData(){
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated=myDb.updateData(id.getText().toString(),name.getText().toString(),surname.getText().toString(),marks.getText().toString());

                if(isUpdated==true){
                    Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_SHORT).show();
                    }
                    else {
                    Toast.makeText(MainActivity.this,"Data NOT Updated",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    public  void  DeleteData(){

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows=myDb.deleteData(id.getText().toString());
                if(deletedRows > 0){
                    Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Data NOT Deleted",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void  showMessage(String title,String Message){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }

}
