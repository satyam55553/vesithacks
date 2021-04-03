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

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    Button signUpBtn;
    EditText emailSignUp, passwordSignUp;
    String userEmail, userPass, role = "Role";
    ;
    TextView alreadyUser, roleText;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase mFirebasedatabase;
    private DatabaseReference mMessagesDatabaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        mFirebasedatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebasedatabase.getReference().child("users");

        roleText = (TextView) findViewById(R.id.text_role);
        alreadyUser = (TextView) findViewById(R.id.already_user_txt);
        signUpBtn = (Button) findViewById(R.id.button_sign_up);
        emailSignUp = (EditText) findViewById(R.id.email_signup);
        passwordSignUp = (EditText) findViewById(R.id.newPassword);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = extras.getString("role");
        }
        roleText.setText("Role = " + role);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail = emailSignUp.getText().toString();
                userPass = passwordSignUp.getText().toString();

                if (TextUtils.isEmpty(userEmail)) {
                    emailSignUp.setError("Please enter email id");
                    emailSignUp.requestFocus();
                }
                if (TextUtils.isEmpty(userPass)) {
                    passwordSignUp.setError("Please enter password");
                    passwordSignUp.requestFocus();
                }
                if (!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPass)) {
                    if(role.equals("admin")){
                        queryFirebase(userEmail);
                    }else if(role.equals("student")){
                        signUp(userEmail, userPass);
                    }

                }
            }
        });

        alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                intent.putExtra("role", role);
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

    private void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SignUpActivity.this", "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
                            addUserToDB(email, role);

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

    private void addUserToDB(String email, String mRole) {
        UserData userData = new UserData(email, mRole, false);
        mMessagesDatabaseReference.push().setValue(userData);
        Toast.makeText(SignUpActivity.this,
                "User Added :)", Toast.LENGTH_SHORT).show();
    }

    private void queryFirebase(String email) {
        Query query = FirebaseDatabase.getInstance().getReference("users")
                .orderByChild("email")
                .equalTo(email);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                UserData userData = dataSnapshot.getValue(UserData.class);
                if (userData == null) {
                    signUp(userEmail, userPass);
                }else{
                    Toast.makeText(SignUpActivity.this,
                            "Invalid Access",Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
}
