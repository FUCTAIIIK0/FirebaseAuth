package com.example.testauth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText login;
    EditText password;
    Button signIn;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.loginText);
        password = findViewById(R.id.passwordText);
        signIn = findViewById(R.id.buttonSignIn);
        signUp = findViewById(R.id.buttonSignUp);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"Sign In",Toast.LENGTH_SHORT).show();


            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Sign Up",Toast.LENGTH_SHORT).show();

            }
        });




    }
}
