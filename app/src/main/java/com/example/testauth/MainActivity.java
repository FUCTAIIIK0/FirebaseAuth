package com.example.testauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    EditText login;
    EditText password;
    Button signIn;
    Button signUp;

    Button email;
    Button phone;
    Button google;
    Button fingerprint;
    TextView mode;

    enum mode {Email,Phone,Google,Fingerprint};
    Boolean isModeEmail;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        login = findViewById(R.id.loginText);
        password = findViewById(R.id.passwordText);
        signIn = findViewById(R.id.buttonSignIn);
        signUp = findViewById(R.id.buttonSignUp);

        email = findViewById(R.id.buttonEmail);
        phone = findViewById(R.id.buttonPhone);
        google = findViewById(R.id.buttonGoogle);
        fingerprint = findViewById(R.id.buttonFingerprint);
        mode = findViewById(R.id.textViewMode);



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Toast.makeText(getApplicationContext(),"Sign In = "+user.getUid(),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Sign out",Toast.LENGTH_SHORT).show();
                }
            }
        };


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(login.getText().toString(),password.getText().toString());
                    //Toast.makeText(getApplicationContext(),"Sign In Button",Toast.LENGTH_SHORT).show();


            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp(login.getText().toString(),password.getText().toString());
                //Toast.makeText(getApplicationContext(),"Sign Up Button",Toast.LENGTH_SHORT).show();
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isModeEmail = true;
                mode.setText("Email Mode");

            }
        });

    }












        public void signIn(String login,String password ){
        if (isModeEmail){
            mAuth.signInWithEmailAndPassword(login,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Login success",Toast.LENGTH_SHORT).show();
                }else {
                        Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_SHORT).show();
                    }
                }
            });}else {

                    }
        }
        public void SignUp(String login,String password){
        if (isModeEmail){
            mAuth.createUserWithEmailAndPassword(login,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Registration success",Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(getApplicationContext(),"Registration failed",Toast.LENGTH_SHORT).show();

                    }

                }
            });}else {

                    }
        }
}
