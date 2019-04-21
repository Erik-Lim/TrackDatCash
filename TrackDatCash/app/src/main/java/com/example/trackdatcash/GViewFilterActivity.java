package com.example.trackdatcash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GViewFilterActivity extends AppCompatActivity {
    private static final String TAG = "FilterView";
    private Spinner sprPrimaryGFilter, sprSecondaryGFilter;
    private String[] catArray = {"Bills", "Dining", "Education", "Entertainment",
            "Groceries", "Health", "Shopping", "Transportation", "Other"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gview_filter);

        Button btnGFilter = (Button) findViewById(R.id.btnGFilter);
        Button btnRtoGEfGF = (Button) findViewById(R.id.btnRtoGEfGF);

        final EditText etYearGFV = (EditText) findViewById(R.id.etYearGFV);
        etYearGFV.setEnabled(false);
        Date date = new Date();
        String strDateFormat = "yyyy";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        final String formattedDate= dateFormat.format(date);

        addToPrimarySpinner();
        addToSecondarySpinner(2);

        sprPrimaryGFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sprPrimaryGFilter.getSelectedItemPosition()==1)
                {
                    addToSecondarySpinner(1);
                    etYearGFV.setText(formattedDate.substring(0,4));
                    etYearGFV.setEnabled(true);
                }
                else if (sprPrimaryGFilter.getSelectedItemPosition()==2)
                {
                    addToSecondarySpinner(0);
                    etYearGFV.setText(formattedDate.substring(0,4));
                    etYearGFV.setEnabled(true);
                }
                else if (sprPrimaryGFilter.getSelectedItemPosition()==0)
                {
                    addToSecondarySpinner(2);
                    etYearGFV.setText("");
                    etYearGFV.setHint("");
                    etYearGFV.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnRtoGEfGF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Using the selected value, create route & send to View Expenses Activity
                Intent todoIntent = new Intent(GViewFilterActivity.this, GView2Activity.class);
                todoIntent.putExtra("groupCode", GroupViewActivity.groupCode);
                todoIntent.putExtra("filter", "none");
                GViewFilterActivity.this.startActivity(todoIntent);
            }
        });

        btnGFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String primary = sprPrimaryGFilter.getSelectedItem().toString();
                String secondary;
                String filterExtra = "";
                if (!primary.equals("All"))
                {
                    //Pull in the data from the selected drop down
                    secondary = sprSecondaryGFilter.getSelectedItem().toString();
                    filterExtra = primary + secondary + etYearGFV.getText().toString();
                }
                else
                {
                    filterExtra = "none";
                }

                //Using the selected value, create route & send to View Expenses Activity
                Intent todoIntent = new Intent(GViewFilterActivity.this, GView2Activity.class);
                todoIntent.putExtra("groupCode", GroupViewActivity.groupCode);
                todoIntent.putExtra("filter", filterExtra);
                GViewFilterActivity.this.startActivity(todoIntent);
            }
        });

    }

    public void addToPrimarySpinner() {
        sprPrimaryGFilter = (Spinner) findViewById(R.id.sprPrimaryGFilter);
        List<String> primaryList = new ArrayList<String>();

        primaryList.add("All");
        primaryList.add("Month");
        primaryList.add("Category");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, primaryList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        sprPrimaryGFilter.setAdapter(dataAdapter);
    }

    public void addToSecondarySpinner(int mOc) {
        sprSecondaryGFilter = (Spinner) findViewById(R.id.sprSecondaryGFilter);
        List<String> secondaryList = new ArrayList<String>();

        //If primary filter is month, set secondary to months
        if (mOc == 1)
        {
            secondaryList.add("Jan");
            secondaryList.add("Feb");
            secondaryList.add("Mar");
            secondaryList.add("Apr");
            secondaryList.add("May");
            secondaryList.add("Jun");
            secondaryList.add("Jul");
            secondaryList.add("Aug");
            secondaryList.add("Sep");
            secondaryList.add("Oct");
            secondaryList.add("Nov");
            secondaryList.add("Dec");
        }

        //Categories were chosen in the primary filter, populate the fields
        else if (mOc==0)
        {
            for (int i = 0; i<catArray.length; i++)
            {
                secondaryList.add(catArray[i]);
            }
        }
        else
        {
            secondaryList.add("");
        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, secondaryList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        sprSecondaryGFilter.setAdapter(dataAdapter);
    }

}
