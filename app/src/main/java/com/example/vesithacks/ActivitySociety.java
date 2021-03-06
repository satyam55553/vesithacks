package com.example.vesithacks;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ActivitySociety extends AppCompatActivity {
    private AdminAdapter.RecyclerViewClickListener listener;
    String[] names = {"1", "2", "3", "4", "5", "6", "7", "8"};
    String societyName;
    TextView societyNameTxt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.society_editor);
        societyNameTxt=(TextView) findViewById(R.id.text_society_name) ;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_society_editor);
        setOnClickListener();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recyclerView.setAdapter(new AdminAdapter(names, listener));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            societyName = extras.getString("societyName");
            societyNameTxt.setText(""+societyName);
        }
    }

    private void setOnClickListener() {
        listener = new AdminAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };
    }
}
