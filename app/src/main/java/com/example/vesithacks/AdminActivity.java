package com.example.vesithacks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {
    TextView txtSociety,txtDepartment,txtCommittee;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard);
        txtSociety=(TextView) findViewById(R.id.text_admin_societies);
        txtDepartment=(TextView) findViewById(R.id.text_admin_departments);
        txtCommittee=(TextView) findViewById(R.id.text_admin_committees);

        txtSociety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivity.this,ActivitySociety.class);
                startActivity(intent);
            }
        });
        txtDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivity.this,AdminEditorActivity.class);
                startActivity(intent);
            }
        });
        txtCommittee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivity.this,AdminEditorActivity.class);
                startActivity(intent);
            }
        });
    }
}
