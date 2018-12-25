package com.example.admin.doc;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class doc_appoint extends AppCompatActivity {

    Button date,confirm_appoint,time;
    int year_x,month_x,day_x,hour_x,minute_x;
    static final int DIALOG_ID=0;
    static final int TIME_DIALOG_ID=1;

    TextView s_date,s_time;


    Bundle bundle;

    ImageView imageView;
    TextView textViewName,textViewSpecialist,textViewHospital,textViewFee,textViewCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_appoint);

//        final String b_date=s_date.getText().toString();
//        final String b_time=s_time.getText().toString();

        addListenerOnButton();
        setCurrentDateOnView();
//        showTimePickerDialog();
        bundle = getIntent().getExtras();

        confirm_appoint=(Button)findViewById(R.id.confirm_book);

        confirm_appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(doc_appoint.this,"Appointment Confirm",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(doc_appoint.this,con_appoint.class);
//                i.putExtra("s_date",b_date);
//                i.putExtra("s_time",b_time);
                startActivity(i);
            }
        });

        getIncomingIntent();
        imageView = (ImageView) findViewById(R.id.img);
        int bitmap =  getIntent().getIntExtra("imageView",1);
        imageView.setImageResource(bitmap);

        textViewName= (TextView)findViewById(R.id.docName);
        String name = getIntent().getStringExtra("textViewName");
        textViewName.setText(name);

        textViewSpecialist=(TextView)findViewById(R.id.Specialist);
        String spec=getIntent().getStringExtra("textViewSpecialist");
        textViewSpecialist.setText(spec);

        textViewHospital=(TextView)findViewById(R.id.Hospital);
        String price=getIntent().getStringExtra("textViewHospital");
        textViewHospital.setText(String.valueOf(price));

        textViewFee=(TextView)findViewById(R.id.Fee);
        String fee=getIntent().getStringExtra("textViewFee");
        textViewFee.setText(String.valueOf(fee));

        textViewCity=(TextView)findViewById(R.id.City);
        String city=getIntent().getStringExtra("textViewCity");
        textViewCity.setText(String.valueOf(city));



//
//        date=(Button)findViewById(R.id.button);
//        s_date=(TextView)findViewById(R.id.currentdate);
//
//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar cal=Calendar.getInstance();
//                year_x=cal.get(Calendar.YEAR);
//                month_x=cal.get(Calendar.MONTH);
//                day_x=cal.get(Calendar.DAY_OF_MONTH);
//
//
//                DatePickerDialog datePickerDialog=new DatePickerDialog(doc_appoint.this, new DatePickerDialog.OnDateSetListener() {
//
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        view.setMinDate(System.currentTimeMillis() - 1000);
//                        s_date.setText(String.format("%04d-%02d-%02d",year,month,dayOfMonth));
//                        s_date.setText(year+"-"+month+"-"+dayOfMonth);
//                    }
//                },0,0,0);
//                datePickerDialog.show();
//            }
//        });


        time=(Button)findViewById(R.id.b_time);
        s_time=(TextView)findViewById(R.id.s_time);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal=Calendar.getInstance();
                hour_x=cal.get(Calendar.HOUR_OF_DAY);
                minute_x=cal.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog=new TimePickerDialog(doc_appoint.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {

                        String amPm;   //AM - PM format
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        s_time.setText(String.format("%02d:%02d", hourOfDay, minutes) +"%s"+ amPm);
                        s_time.setText(hourOfDay + ":" + minutes+" "+amPm);
                    }

                },0,0,false);
                timePickerDialog.show();

        }
        });
    }

    public void addListenerOnButton(){

        date=(Button)findViewById(R.id.button);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }


    public void setCurrentDateOnView(){
        final Calendar cal=Calendar.getInstance();
        year_x=cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH);
        day_x=cal.get(Calendar.DAY_OF_MONTH);


        s_date=(TextView)findViewById(R.id.currentdate);

        s_date.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day_x).append("-").append(month_x+1).append("-")
                .append(year_x).append(" "));

    }


    protected Dialog onCreateDialog(int id) {


        if (id == DIALOG_ID) {
            switch (id) {
                case DIALOG_ID:
                    // set date picker as current date
                    DatePickerDialog _date = new DatePickerDialog(this, datepickerListener, year_x, month_x,
                            day_x) {
                        @Override
                        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            if (year < year_x)
                                view.updateDate(year_x, month_x, day_x);

                            if (monthOfYear < month_x && year == year_x)
                                view.updateDate(year_x, month_x, day_x);

                            if (dayOfMonth < day_x && year == year_x && monthOfYear == month_x)
                                view.updateDate(year_x, month_x, day_x);

                        }
                    };
                    return _date;
            }
        }
        return  null;
    }

//        if(id==TIME_DIALOG_ID){
//            return new TimePickerDialog(doc_appoint.this,timepickerListener,hour_x,minute_x,false);
//        }
//        return null;
//    }

   private DatePickerDialog.OnDateSetListener datepickerListener=new DatePickerDialog.OnDateSetListener() {
       @Override
       public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

           year_x=year;
           month_x=month+1;
           day_x=dayOfMonth;

           s_date.setText(new StringBuilder()
                   // Month is 0 based, just add 1
                   .append(day_x).append("-").append(month_x+1).append("-")
                   .append(year_x).append(" "));

       }
   };


//    public void showTimePickerDialog(){
//        time=(Button)findViewById(R.id.b_time);
//        time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialog(TIME_DIALOG_ID);
//            }
//        });
//    }
//
//    protected  TimePickerDialog.OnTimeSetListener timepickerListener=
//            new TimePickerDialog.OnTimeSetListener() {
//                @Override
//                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//
//                    hour_x=hourOfDay;
//                    minute_x=minute;
//                    s_time.setText(new StringBuilder().append(hour_x).append(":").append(minute_x));
//                }
//            };
//

    private void getIncomingIntent(){

        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_name")){
            // Log.d(TAG, "getIncomingIntent: found intent extras.");

            String imageUrl = getIntent().getStringExtra("image_url");
            String imageName = getIntent().getStringExtra("image_name");

            // setImage(imageUrl, imageName);
        }

    }
}
