package com.example.testauth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 7117;
    EditText login;
    EditText password;
    Button signIn;
    Button signUp;

    Button email;
    Button phone;
    Button authUI;
    Button fingerprint;
    Button logout;
    TextView mode;

    Boolean isModeEmail;


    List<AuthUI.IdpConfig> providers;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init provider
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()

        );
        //showSignInOptions();

        mAuth = FirebaseAuth.getInstance();
        login = findViewById(R.id.loginText);
        password = findViewById(R.id.passwordText);
        signIn = findViewById(R.id.buttonSignIn);
        signUp = findViewById(R.id.buttonSignUp);

        email = findViewById(R.id.buttonEmail);
        phone = findViewById(R.id.buttonPhone);
        authUI = findViewById(R.id.buttonAuthUI);
        fingerprint = findViewById(R.id.buttonFingerprint);
        mode = findViewById(R.id.textViewMode);


        logout =findViewById(R.id.buttonLogOut);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logout
                AuthUI.getInstance()
                        .signOut(MainActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                logout.setEnabled(false);
                                showSignInOptions();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this," "+e.getMessage(),Toast.LENGTH_SHORT);

                    }
                });
            }
        });
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
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivities(new Intent(MainActivity.this, ActivityTwo.class));
            }
        });
        authUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignInOptions();
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




    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.MyTheme)
                        .build(), MY_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (requestCode == RESULT_OK){
                //Get user
                FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
                //Show email toast
                Toast.makeText(this," "+user.getEmail(),Toast.LENGTH_SHORT);
                logout.setEnabled(true);
            }else {
                Toast.makeText(this," "+response.getError(),Toast.LENGTH_SHORT);
            }
        }

    }
}
