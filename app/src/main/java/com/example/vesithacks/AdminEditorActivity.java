package com.example.vesithacks;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdminEditorActivity extends AppCompatActivity {
    private AdminAdapter.RecyclerViewClickListener listener;
    String[] names = {"1", "2", "3", "4", "5", "6", "7", "8"};
    Button addBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_editor);
        addBtn = (Button) findViewById(R.id.button_admine_add);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_admin_editor);
        setOnClickListener();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new AdminAdapter(names, listener));

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogDemo();
            }
        });
    }

    private void setOnClickListener() {
        listener = new AdminAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };
    }

    void alertDialogDemo() {
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ADD EVENT");

        // set the custom layout
        final View customLayout
                = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        builder.setView(customLayout);

        // add a button
        builder.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // send data from the
                                // AlertDialog to the Activity
                                EditText editText = customLayout.findViewById(R.id.etUserInput);
                            }
                        });
        // create and show
        // the alert dialog
        AlertDialog dialog
                = builder.create();
        dialog.show();
    }
}
