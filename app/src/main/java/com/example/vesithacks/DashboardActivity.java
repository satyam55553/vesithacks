package com.example.vesithacks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DashboardActivity extends AppCompatActivity {
    private myAdapter.RecyclerViewClickListener listener;
    String[] eventNames = {"1", "2", "3", "4", "5", "6", "7", "8"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_dashboard);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_student_dashboard);
        setOnClickListener();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new myAdapter(eventNames, eventNames, eventNames, eventNames, listener));
    }

    private void setOnClickListener() {
        listener = new myAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };
    }
}
