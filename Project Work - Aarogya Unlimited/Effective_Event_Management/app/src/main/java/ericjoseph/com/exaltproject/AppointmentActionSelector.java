package ericjoseph.com.exaltproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AppointmentActionSelector extends AppCompatActivity {
    TextView click1, click2;
    public Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_action_selector);

        click1 = (TextView)findViewById(R.id.Click1);
        click2 = (TextView)findViewById(R.id.Click2);



        click1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getApplicationContext(),DoctorAppointment.class);
                startActivity(i);
            }
        });

        click2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getApplicationContext(),DoctorAppointmentsViewer.class);
                startActivity(i);
            }
        });
    }
}
