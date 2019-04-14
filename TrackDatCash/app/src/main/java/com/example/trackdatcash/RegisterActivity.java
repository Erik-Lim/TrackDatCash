package com.example.trackdatcash;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Initialize buttons
        Button loginReturnBtn = (Button) findViewById(R.id.loginReturnBtn);
        Button registerBtn = (Button) findViewById(R.id.registerBtn);

        //Toast popup extras
        final Context context = getApplicationContext();
        final CharSequence textRegSuccess = "Registration Successful";
        final int duration = Toast.LENGTH_LONG;

        //Send user to the LoginActivity if they touch the back button
        loginReturnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todoIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(todoIntent);
            }
        });

        //Registration process
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Registration Process
                //Init all EditText fields
                EditText etUsernameReg = (EditText) findViewById(R.id.etUsernameReg);
                EditText etFirstNameReg = (EditText) findViewById(R.id.etFirstNameReg);
                EditText etLastNameReg = (EditText) findViewById(R.id.etLastNameReg);
                EditText etEmailReg = (EditText) findViewById(R.id.etEmailReg);
                EditText etPasswordReg = (EditText) findViewById(R.id.etPasswordReg);
                EditText etPasswordConfirmReg = (EditText) findViewById(R.id.etPasswordConfirmReg);
                String username = etUsernameReg.getText().toString();
                String firstName = etFirstNameReg.getText().toString();
                String lastName = etLastNameReg.getText().toString();
                String email = etEmailReg.getText().toString();
                String password = etPasswordReg.getText().toString();

/*
                if (!etPasswordReg.getText().equals(etPasswordConfirmReg.getText()))
                {
                    CharSequence textRegFailed = "Registration Failed - Password Mismatch";
                    Toast toastRegFail = Toast.makeText(context, textRegFailed, duration);
                    toastRegFail.show();
                }

                if (username.length()==0 || firstName.length()==0 || lastName.getText().length()==0 || email.length()==0)
                {
                    CharSequence textRegFailed = "Registration Failed - All fields required";
                    Toast toastRegFail = Toast.makeText(context, textRegFailed, duration);
                    toastRegFail.show();
                }
*/


//Temporary success only for registration button
                //Registration Successful - send user to login page with toast message
                //Show Toast message
                Toast toastRegSuccess = Toast.makeText(context, textRegSuccess, duration);
                toastRegSuccess.show();
                Intent todoIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(todoIntent);

                //Registration failed - show toast message, no changes to input data
                //Show Toast message
//Need to tell user why registration was failed here
                CharSequence textRegFailed = "Registration Failed" /* + Error Message*/;
                Toast toastRegFail = Toast.makeText(context, textRegFailed, duration);
                toastRegFail.show();
            }
        });

    }
}
