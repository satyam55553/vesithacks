package com.example.vesithacks;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ActivitySocieties extends AppCompatActivity {
    private AdminAdapter.RecyclerViewClickListener listener;
    private List<String> list;
    private  AdminAdapter adminAdapter;
    Button addBtn;
    RecyclerView recyclerView;
    private FirebaseDatabase mFirebasedatabase;
    private DatabaseReference mMessagesDatabaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_societies);
        mFirebasedatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebasedatabase.getReference().child("societies");
        addBtn = (Button) findViewById(R.id.button_society_add);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_societies);
        setOnClickListener();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogDemo();
            }
        });

        readDB();
    }
    private void setOnClickListener() {
        listener = new AdminAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                startActivity(new Intent(ActivitySocieties.this,
                        ActivitySociety.class));
            }
        };
    }
    void alertDialogDemo() {
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ADD SOCIETY");

        // set the custom layout
        final View customLayout
                = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        builder.setView(customLayout);

        // add a button
        builder.setPositiveButton(
                "ADD",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // send data from the
                        // AlertDialog to the Activity
                        EditText editText = customLayout.findViewById(R.id.etUserInput);
                        String fieldName = editText.getText().toString();
                        if(!TextUtils.isEmpty(fieldName)){
                            addFieldtoDB(fieldName);
                        }else{
                            editText.setError("Enter society's name");
                        }
                    }
                });
        builder.setNegativeButton("CLOSE",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        // create and show
        // the alert dialog
        AlertDialog dialog
                = builder.create();
        dialog.show();
    }

    public void addFieldtoDB(String data) {
        mMessagesDatabaseReference.push().setValue(data);
        Toast.makeText(ActivitySocieties.this,
                "Society Added :)", Toast.LENGTH_SHORT).show();
        list.clear();
        readDB();
    }
    public void readDB(){
        //Read from database
        list = new ArrayList<>();
        final DatabaseReference nm = FirebaseDatabase.getInstance().getReference("societies");
        nm.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        String l = (String) snap.getValue();
                        list.add(l);
                    }
                    adminAdapter = new AdminAdapter(list,listener);
                    recyclerView.setAdapter(adminAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivitySocieties.this,
                        "Error Fetching Events", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
