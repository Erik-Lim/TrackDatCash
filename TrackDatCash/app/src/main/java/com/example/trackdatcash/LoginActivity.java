package com.example.trackdatcash;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

//This is the main activity (ie, first activity on app startup)
public class LoginActivity extends AppCompatActivity {

    String success = "";
    String token = "";
    public static String userIDused;
    private static String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBtn = (Button) findViewById(R.id.loginBtn);

        TextView tvRegister = (TextView) findViewById(R.id.tvRegister);

        //Toast popup extras
        final Context context = getApplicationContext();
        final CharSequence textLoginFailed = "Login Failed";
        final CharSequence textLoginSuccess = "Login Successful";
        final int duration = Toast.LENGTH_LONG;

        //Send user to the RegisterActivity if they touch the register text
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todoIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(todoIntent);
            }
        });

        //Begin login process if the login button is pressed
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Login Process
                //The two Edit Text fields
                EditText etUsername = (EditText) findViewById(R.id.etUsername);
                EditText etPassword = (EditText) findViewById(R.id.etPassword);

                // Erik add information from edit text to send as json object
                // using Randy's methods for login
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                String tryLogin = Authentication.login("https://trackdatcash.herokuapp.com/expenses/login", username, password);

                try {
                    JSONObject loginRet = new JSONObject(tryLogin);
                    success = loginRet.get("success").toString();
                    token = loginRet.get("token").toString();
                    token = token.substring(7);
                    userIDused = (Authentication.getUserId(token, "this-is-a-really-long-secret-key-yeehaw"));
                    Log.e(TAG, userIDused);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                
                if(tryLogin == "error")
                {
                    //Login failed
                    //Show Toast message (leaving data fields as they are)
                    Toast toastLoginFail = Toast.makeText(context, textLoginFailed, duration);
                    toastLoginFail.show();
                }
                else
                {
                    //Temporary success only for login button
                    //Login was successful
                    //Show Toast message
                    Toast toastLoginSuccess = Toast.makeText(context, textLoginSuccess, duration);
                    toastLoginSuccess.show();
                    //Send user to menu
                    Intent todoIntent = new Intent(LoginActivity.this, MainMenuActivity.class);
                    LoginActivity.this.startActivity(todoIntent);
                }
            }
        });



    }

}
