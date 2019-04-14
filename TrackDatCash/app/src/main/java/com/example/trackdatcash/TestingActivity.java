package com.example.trackdatcash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestingActivity extends AppCompatActivity {
    private String userID;
    public static TextView longtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        Button startGet = (Button) findViewById(R.id.btnStartsGet);
        longtext = (TextView) findViewById(R.id.longText);



        startGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://trackdatcash.herokuapp.com/expenses/codeMount";
                userID = LoginActivity.userIDused;

                String retVal = ReturnExpense.getUser(url, userID);
                int indexOfGC = retVal.indexOf("groupCode");
                int indexOfGCEnd = retVal.indexOf(",", indexOfGC);
                String retValCopy = retVal.substring(indexOfGC+12, indexOfGCEnd-1);

                longtext.setText(retValCopy);
            }
        });

    }
}
