package com.example.android.timepicker;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import java.util.Calendar;
public class MainActivity extends AppCompatActivity {

    EditText choose;
    EditText choose2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        choose=(EditText)findViewById(R.id.et);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               Calendar calendar = Calendar.getInstance();
               int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
               int currentMinute = calendar.get(Calendar.MINUTE);

//above lines for getting the current time

                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {

                       String amPm;   //AM - PM format
                       if (hourOfDay >= 12) {
                           amPm = "PM";
                       } else {
                           amPm = "AM";
                       }
                       choose.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);


                        choose.setText(hourOfDay+":" +minutes);

                    }
                }

                , 0, 0, false);  //time picker starts with 12 o clock
// use currentHour and currentMinute in the above line makes the time picker starts with current time
                timePickerDialog.show();
            }


        });

        choose2=(EditText)findViewById(R.id.et2);
        choose2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
               int year = c.get(Calendar.YEAR);
               int month = c.get(Calendar.MONTH);
               int day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        choose2.setText(String.format("%04d-%02d-%02d",year,month,dayOfMonth));
                        choose2.setText(year+"-"+month+"-"+dayOfMonth);
                    }
                },0,0,0);
                datePickerDialog.show();
            }
        });
    }
}


