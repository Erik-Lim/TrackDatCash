package com.example.trackdatcash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GroupViewActivity extends AppCompatActivity {
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_view);


        userID = LoginActivity.userIDused;

        String url = "https://trackdatcash.herokuapp.com/expenses/codeMount";
        String retVal = ReturnExpense.getUser(url, userID);
        int indexOfGC = retVal.indexOf("groupCode");
        int indexOfGCEnd = retVal.indexOf(",", indexOfGC);
        String retValCopy = retVal.substring(indexOfGC+12, indexOfGCEnd-1);
        TextView tvUserGroupCodeGV = (TextView) findViewById(R.id.tvUserGroupCodeGV);
        tvUserGroupCodeGV.setText(retValCopy);

        Button btnRtoMMfGV = (Button) findViewById(R.id.btnRtoMMfGV);
        Button btnGroupViewGV = (Button) findViewById(R.id.btnGroupViewGV);
        final EditText etGroupCodeGV = (EditText) findViewById(R.id.etGroupCodeGV);

        etGroupCodeGV.setText(retValCopy);

        //Return to the Main Menu
        btnRtoMMfGV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todoIntent = new Intent(GroupViewActivity.this, MainMenuActivity.class);
                GroupViewActivity.this.startActivity(todoIntent);
            }
        });

        btnGroupViewGV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Take in the Group code to create the url to find the expense data for that group
                String groupCode = etGroupCodeGV.getText().toString();
                Intent todoIntent = new Intent(GroupViewActivity.this, GView2Activity.class);
                todoIntent.putExtra("groupCode", groupCode);
                GroupViewActivity.this.startActivity(todoIntent);
            }
        });
    }
}
