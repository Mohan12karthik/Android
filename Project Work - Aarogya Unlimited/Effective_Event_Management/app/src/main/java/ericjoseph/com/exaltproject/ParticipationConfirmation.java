package ericjoseph.com.exaltproject;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ParticipationConfirmation extends AppCompatActivity {
    String[] participation_reasons_array;
    Double[] participation_reasons_values_array;
    Spinner pr_spinner;
    ArrayAdapter<String> pr_adapter;
    ListView selected_reasons_lv;
    Button confirm_participation;
    EditText student_id_et, org_rating_et;
    RadioGroup rg;
    TextView title_of_event, event_id_textview;
    int rb_choice = 1;
    String no_val = "No";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participation_confirmation);

        participation_reasons_array = new String[]{"Choose Reasons to attend","Learn a new skill","Just to add to resume","My current project requires this skill",
                "To earn a certification","To develop interset in a new topic","Awareness of current trends","Friends are involved","To socialize and meet new people, " +
                "company or organization"};

        participation_reasons_values_array = new Double[]{0.0,0.7,0.75,0.7,0.55,0.5,0.45,0.4,0.65};

        title_of_event = (TextView)findViewById(R.id.event_name_title);
        event_id_textview = (TextView)findViewById(R.id.participated_eventid_tv);

        final Bundle bundle = getIntent().getExtras();
        title_of_event.setText(bundle.getString("Event Name"));
        event_id_textview.setText(bundle.getString("Event ID"));

        rg = (RadioGroup)findViewById(R.id.radioGroup2);

        student_id_et = (EditText) findViewById(R.id.student_id_et);
        org_rating_et = (EditText) findViewById(R.id.org_rating_et);

        selected_reasons_lv = (ListView)findViewById(R.id.selected_reasons_list);
        //selected_reasons_lv.refreshDrawableState();

        final ArrayList<String> temp_reasons = new ArrayList<String>();
        final ArrayList<Double> temp_reasons_values = new ArrayList<Double>();

        final ArrayAdapter<String> temp_reasons_adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,temp_reasons);

        pr_spinner = (Spinner) findViewById(R.id.reasons_spinner);
        pr_spinner.refreshDrawableState();
        pr_adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,participation_reasons_array);
        pr_spinner.setAdapter(pr_adapter);

        pr_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView temp = (TextView) view;
                if(!temp_reasons.contains(temp.getText()) && temp.getText().toString() != "Choose Reasons to attend") {
                    temp_reasons.add(participation_reasons_array[i]);
                    temp_reasons_values.add(participation_reasons_values_array[i]);

                    Toast.makeText(getApplicationContext(), "Reason Selected", Toast.LENGTH_SHORT).show();
                    selected_reasons_lv.refreshDrawableState();
                    selected_reasons_lv.setAdapter(temp_reasons_adapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        selected_reasons_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                temp_reasons.remove(i);
                temp_reasons_values.remove(i);
                Toast.makeText(getApplicationContext(),"Reason removed",Toast.LENGTH_SHORT).show();
                selected_reasons_lv.refreshDrawableState();
                selected_reasons_lv.setAdapter(temp_reasons_adapter);
                return true;
            }
        });

        confirm_participation = (Button)findViewById(R.id.registration);
        confirm_participation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton rb = (RadioButton)findViewById(rg.getCheckedRadioButtonId());
                RadioButton yes = (RadioButton)findViewById(R.id.radioButton);
                RadioButton no = (RadioButton)findViewById(R.id.radioButton2);

                if (rb.getText().toString() == yes.getText().toString())
                    rb_choice = 1;
                else if (rb.getText().toString() == no.getText().toString())
                    rb_choice = 0;

                Double d = getMaxValue(temp_reasons_values);

                String str = "Event ID : " + event_id_textview.getText().toString() + "\nEvent Name : " + title_of_event.getText().toString() + "\nStudent ID : "
                        +  student_id_et.getText().toString() + "\nRecommendation : " + rb.getText().toString() + "\nRating : " + org_rating_et.getText().toString() + "/10" ;
                Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Participation Records");

                try {
                    myRef.child(student_id_et.getText().toString()+ event_id_textview.getText().toString()).setValue("0");
                    myRef.child(student_id_et.getText().toString()+ event_id_textview.getText().toString()).child("Event ID").setValue(event_id_textview.getText().toString());
                    myRef.child(student_id_et.getText().toString()+ event_id_textview.getText().toString()).child("Event Name").setValue(title_of_event.getText().toString());
                    myRef.child(student_id_et.getText().toString()+ event_id_textview.getText().toString()).child("Student ID").setValue( student_id_et.getText().toString());
                    myRef.child(student_id_et.getText().toString()+ event_id_textview.getText().toString()).child("Reasons").setValue(d);
                    myRef.child(student_id_et.getText().toString()+ event_id_textview.getText().toString()).child("Recommendation").setValue(rb_choice);
                    myRef.child(student_id_et.getText().toString()+ event_id_textview.getText().toString()).child("Rating").setValue(org_rating_et.getText().toString());
                    myRef.child(student_id_et.getText().toString()+ event_id_textview.getText().toString()).child("Check_In").setValue("No");

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("+9020420886",null,"Registration for event done",null,null);
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Error...Student Details not saved",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public Double getMaxValue(ArrayList<Double> list){
        Double max = 0.0;
        max = list.get(0);
        for(int i = 1; i < list.size(); i++)
            if(list.get(i) > max)
                max = list.get(i);
        return max;
    }
}