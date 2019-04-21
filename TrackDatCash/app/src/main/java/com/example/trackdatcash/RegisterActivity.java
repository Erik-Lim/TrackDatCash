package com.example.trackdatcash;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
                EditText etNameReg = (EditText) findViewById(R.id.etNameReg);
                EditText etEmailReg = (EditText) findViewById(R.id.etEmailReg);
                EditText etPasswordReg = (EditText) findViewById(R.id.etPasswordReg);
                EditText etPasswordConfirmReg = (EditText) findViewById(R.id.etPasswordConfirmReg);

                String name = etNameReg.getText().toString();
                String email = etEmailReg.getText().toString();
                String password = etPasswordReg.getText().toString();
                String password2 = etPasswordConfirmReg.getText().toString();

                // send information to route
                String register = Authentication.register("https://trackdatcash.herokuapp.com/expenses/register", name, email, password, password2);

                // make sure all fields are required
                if (name.length() == 0 || email.length() == 0 || password.length() == 0 || password2.length() == 0) {
                    CharSequence textRegFailed = "All inputs are required";
                    Toast toastRegFail = Toast.makeText(context, textRegFailed, duration);
                    toastRegFail.show();
                    return;
                }

                // check if password is long enough
                else if (password.length() < 6)
                {
                    Toast toastPasswordSmall = Toast.makeText(context, "Password too short", duration);
                    toastPasswordSmall.show();
                    return;
                }

                else if (password.length() > 30)
                {
                    Toast toastPasswordLong = Toast.makeText(context, "Password too long", duration);
                    toastPasswordLong.show();
                    return;
                }

                else if (!password.equals(password2))
                {
                    Toast toastPasswordMatch = Toast.makeText(context, "Passwords do not match", duration);
                    toastPasswordMatch.show();
                    return;
                }

//                // check if email is valid email address
//                else if (!Authentication.validateEmail(email))
//                {
//                    Toast toastEmailInvalid = Toast.makeText(context, "Email not valid", duration);
//                    toastEmailInvalid.show();
//                    return;
//                }

                else if(register.equals("true"))
                {
                    //Temporary success only for registration button
                    //Registration Successful - send user to login page with toast message
                    //Show Toast message
                    Toast toastRegSuccess = Toast.makeText(context, textRegSuccess, duration);
                    toastRegSuccess.show();
                    Intent todoIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    RegisterActivity.this.startActivity(todoIntent);
                }

                else
                {
                    //Registration failed - show toast message, no changes to input data
                    //Show Toast message
                    //Need to tell user why registration was failed here
                    CharSequence textRegFailed = "Registration Failed" /* + Error Message*/;
                    Toast toastRegFail = Toast.makeText(context, textRegFailed, duration);
                    toastRegFail.show();
                    return;
                }


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
            }
        });

    }
}
