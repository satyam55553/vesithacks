package com.example.vesithacks;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DashboardActivity extends AppCompatActivity {
    private myAdapter.RecyclerViewClickListener listener;
    private List<EventData> eventsList;
    private myAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_dashboard);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_student_dashboard);
        setOnClickListener();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        eventsList = new ArrayList<>();

        final DatabaseReference nm = FirebaseDatabase.getInstance().getReference("events");
        nm.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        EventData l = snap.getValue(EventData.class);
                        eventsList.add(l);
                    }
                    myAdapter = new myAdapter(eventsList, listener);
                    recyclerView.setAdapter(myAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DashboardActivity.this,
                        "Error Fetching Events", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setOnClickListener() {
        listener = new myAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(
                DashboardActivity.this,MainActivity.class
        ));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.editor_menu) {
            Intent intent = new Intent(DashboardActivity.this, AdminEditorActivity.class);
            startActivity(intent);
            return true;
        }else if (item.getItemId() == R.id.event_adder_menu) {
            Intent intent = new Intent(DashboardActivity.this, ActivityEventAdder.class);
            startActivity(intent);
            return true;
        }else if (item.getItemId() == R.id.sign_out_menu) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(DashboardActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
            finishAffinity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
