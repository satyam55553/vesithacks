package com.example.vesithacks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button studentBtn,adminBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentBtn=(Button) findViewById(R.id.button_student);
        adminBtn=(Button) findViewById(R.id.button_Admin);

        studentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SignUpActivity.class);
                intent.putExtra("role","student");
                startActivity(intent);
            }
        });
        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SignUpActivity.class);
                intent.putExtra("role","admin");
                startActivity(intent);
            }
        });
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
            Intent intent = new Intent(MainActivity.this, AdminEditorActivity.class);
            startActivity(intent);
            return true;
        }else if (item.getItemId() == R.id.event_adder_menu) {
            Intent intent = new Intent(MainActivity.this, ActivityEventAdder.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }
}