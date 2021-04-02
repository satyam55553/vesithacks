package com.example.vesithacks;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    TextView roleText;
    String role = "Role";
    Button signInBtn;
    String userEmail, userPass;
    UserData userData;
    ArrayList<UserData> list;
    EditText userName, password;
    Button signIn;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        signInBtn = (Button) findViewById(R.id.button_sign_in);
        roleText = (TextView) findViewById(R.id.text_role);
        userName = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = extras.getString("role");
        }
        roleText.setText("Role = " + role);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail = userName.getText().toString();
                userPass = password.getText().toString();
                if (TextUtils.isEmpty(userEmail)) {
                    userName.setError("Please enter email id");
                    userName.requestFocus();
                }
                if (TextUtils.isEmpty(userPass)) {
                    password.setError("Please enter email id");
                    password.requestFocus();
                }
                if (!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPass)) {
                    signIn(userEmail, userPass);
                }
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthStateListener);
////        mFirebaseAuth.getCurrentUser();
//    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            reload();
//        }
//    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LoginActivity.this", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (role.equals("admin")) {
                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(intent);
                            } else if (role.equals("student")) {
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LoginActivity.this", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
