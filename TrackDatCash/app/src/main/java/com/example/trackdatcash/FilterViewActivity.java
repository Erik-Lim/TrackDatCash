package com.example.trackdatcash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.channels.spi.SelectorProvider;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FilterViewActivity extends AppCompatActivity {
//private Spinner sprGroupAdd, sprMonthAdd, sprCategoriesAdd;
    private static final String TAG = "FilterView";
    private Spinner sprPrimaryFilter, sprSecondaryFilter;
    private String[] catArray = {"Bills", "Dining Out", "Education", "Entertainment",
            "Groceries", "Health", "Shopping", "Transportation", "Other"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_view);

        Button btnFilterFilter = (Button) findViewById(R.id.btnFilterFilter);
        Button btnRtoVEfFE = (Button) findViewById(R.id.btnRtoVEfFE);

        final EditText etYearFV = (EditText) findViewById(R.id.etYearFV);
        Date date = new Date();
        String strDateFormat = "yyyy";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        final String formattedDate= dateFormat.format(date);
        etYearFV.setEnabled(false);

        addToPrimarySpinner();
        addToSecondarySpinner(2);

        sprPrimaryFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sprPrimaryFilter.getSelectedItemPosition()==1)
                {
                    addToSecondarySpinner(1);
                    etYearFV.setText(formattedDate.substring(0,4));
                    etYearFV.setEnabled(true);
                }
                else if (sprPrimaryFilter.getSelectedItemPosition()==2)
                {
                    addToSecondarySpinner(0);
                    etYearFV.setText(formattedDate.substring(0,4));
                    etYearFV.setEnabled(true);
                }
                else if (sprPrimaryFilter.getSelectedItemPosition()==0)
                {
                    addToSecondarySpinner(2);
                    etYearFV.setText("");
                    etYearFV.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnRtoVEfFE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //No change to data
                String urlToSend = "NoChange";

                //Using the selected value, create route & send to View Expenses Activity
                Intent todoIntent = new Intent(FilterViewActivity.this, ViewExpensesActivity.class);
                todoIntent.putExtra("url", urlToSend);
                JSONObject payload = new JSONObject();
                try{
                    payload.put("id", LoginActivity.userIDused);
                    payload.put("month", "");
                    payload.put("year", "");
                    payload.put("category", "");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String payToSend = payload.toString();
                todoIntent.putExtra("payToSend",payToSend);
                FilterViewActivity.this.startActivity(todoIntent);
            }
        });



        btnFilterFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String primary = sprPrimaryFilter.getSelectedItem().toString();
                String secondary;
                String urlToSend = "";
                String payToSend = "";
                String baseURL = "https://trackdatcash.herokuapp.com/expenses/";
                if (!primary.equals("All"))
                {
                    //Pull in the data from the selected drop down
                    secondary = sprSecondaryFilter.getSelectedItem().toString();

                    if(sprPrimaryFilter.getSelectedItemPosition()==1)
                    {
                        //User choosing to filter by month
                        urlToSend = baseURL;
                        urlToSend = urlToSend + "month";
                        JSONObject payload = new JSONObject();
                        try{
                            payload.put("id", LoginActivity.userIDused);
                            payload.put("newMonth", secondary);
                            payload.put("newYear", etYearFV.getText().toString());
                            payload.put("newCategory", "");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        payToSend = payload.toString();
                    }

                    else
                    {
                        //User choosing to filter by category
                        urlToSend = baseURL;
                        urlToSend = urlToSend + "category";
                        JSONObject payload = new JSONObject();
                        try{
                            payload.put("id", LoginActivity.userIDused);
                            payload.put("newMonth", secondary);
                            payload.put("newYear", etYearFV.getText().toString());
                            payload.put("newCategory", "");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        payToSend = payload.toString();
                    }

                }
                else
                {
                    //Send the string to send for all expenses
                    urlToSend = "https://trackdatcash.herokuapp.com/expenses/getAllExpenses";
                    JSONObject payload = new JSONObject();
                    try{
                        payload.put("id", LoginActivity.userIDused);
                        payload.put("month", "");
                        payload.put("year", "");
                        payload.put("category", "");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    payToSend = payload.toString();

                }

                //Using data from spinners, create the URL to use


                //Using the selected value, create route & send to View Expenses Activity
                Intent todoIntent = new Intent(FilterViewActivity.this, ViewExpensesActivity.class);
                todoIntent.putExtra("url", urlToSend);
                todoIntent.putExtra("payToSend",payToSend);
                FilterViewActivity.this.startActivity(todoIntent);
            }
        });


    }


    public void addToPrimarySpinner() {
        sprPrimaryFilter = (Spinner) findViewById(R.id.sprPrimaryFilter);
        List<String> primaryList = new ArrayList<String>();

        primaryList.add("All");
        primaryList.add("Month");
        primaryList.add("Category");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, primaryList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        sprPrimaryFilter.setAdapter(dataAdapter);
    }

    public void addToSecondarySpinner(int mOc) {
        sprSecondaryFilter = (Spinner) findViewById(R.id.sprSecondaryFilter);
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
        sprSecondaryFilter.setAdapter(dataAdapter);
    }

}
