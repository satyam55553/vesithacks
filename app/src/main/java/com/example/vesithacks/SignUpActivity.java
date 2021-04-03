package com.example.vesithacks;

import android.content.Intent;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    Button signUpBtn;
    EditText emailSignUp,passwordSignUp;
    String userEmail, userPass,role = "Role";;
    TextView alreadyUser,roleText;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        roleText = (TextView) findViewById(R.id.text_role);
        alreadyUser=(TextView) findViewById(R.id.already_user_txt);
        signUpBtn=(Button) findViewById(R.id.button_sign_up);
        emailSignUp=(EditText) findViewById(R.id.email_signup);
        passwordSignUp=(EditText) findViewById(R.id.newPassword);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = extras.getString("role");
        }
        roleText.setText("Role = " + role);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail=emailSignUp.getText().toString();
                userPass=passwordSignUp.getText().toString();

                if(TextUtils.isEmpty(userEmail)){
                    emailSignUp.setError("Please enter email id");
                    emailSignUp.requestFocus();
                }
                if(TextUtils.isEmpty(userPass)){
                    passwordSignUp.setError("Please enter email id");
                    passwordSignUp.requestFocus();
                }
                if(!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPass)){
                    signUp(userEmail,userPass);
                }
            }
        });

        alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                intent.putExtra("role",role);
                startActivity(intent);
            }
        });

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Toast.makeText(SignUpActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    if (role.equals("admin")) {
                        Intent intent = new Intent(SignUpActivity.this, AdminActivity.class);
                        startActivity(intent);
                    } else if (role.equals("student")) {
                        Intent intent = new Intent(SignUpActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "PLEASE SIGN UP", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
    private void signUp(String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SignUpActivity.this", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (role.equals("admin")) {
                                Intent intent = new Intent(SignUpActivity.this, AdminActivity.class);
                                startActivity(intent);
                            } else if (role.equals("student")) {
                                Intent intent = new Intent(SignUpActivity.this, DashboardActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SignUpActivity.this", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }
}
