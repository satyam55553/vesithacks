package com.example.vesithacks;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityEventAdder extends AppCompatActivity {
    EditText eventName,organiserName,regDate,eventDate,eventDetails;
    String name,organiser,reg,date,details="";
    Button submitEvent;
    private FirebaseDatabase mFirebasedatabase;
    private DatabaseReference mMessagesDatabaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_adder);
        mFirebasedatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebasedatabase.getReference().child("events");
        eventName= (EditText) findViewById(R.id.eventName_adder);
        organiserName=(EditText) findViewById(R.id.organiserName_adder);
        regDate=(EditText) findViewById(R.id.reg_date_adder);
        eventDate=(EditText) findViewById(R.id.event_date_adder);
        eventDetails=(EditText) findViewById(R.id.event_details_adder);
        submitEvent=(Button) findViewById(R.id.submit_event_adder);

        submitEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=eventName.getText().toString();
                organiser=organiserName.getText().toString();
                reg=regDate.getText().toString();
                date=eventDate.getText().toString();
                details=eventDetails.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    eventName.setError("Please enter event name");
                    eventName.requestFocus();
                }
                if (TextUtils.isEmpty(organiser)) {
                    organiserName.setError("Please enter organiser name");
                    organiserName.requestFocus();
                }
                if (TextUtils.isEmpty(reg)) {
                    regDate.setError("Please enter organiser name");
                    regDate.requestFocus();
                }
                if (TextUtils.isEmpty(date)) {
                    eventDate.setError("Please enter organiser name");
                    eventDate.requestFocus();
                }
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(organiser)) {
                    if(!TextUtils.isEmpty(reg) && !TextUtils.isEmpty(date)){
                        addEventToDB(name,organiser,reg,date,details);
                    }
                }
            }
        });
    }
    private void addEventToDB(String mname, String morganiser,
                              String mreg, String mdate, String mdetails){
        EventData eventData=new EventData(mname,morganiser,mreg,mdate,mdetails);
        mMessagesDatabaseReference.push().setValue(eventData);
        Toast.makeText(ActivityEventAdder.this,
                "Event Added :)", Toast.LENGTH_SHORT).show();
    }
}
