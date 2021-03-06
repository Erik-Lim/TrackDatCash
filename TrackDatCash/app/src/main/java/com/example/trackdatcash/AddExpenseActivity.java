package com.example.trackdatcash;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddExpenseActivity extends AppCompatActivity {
    private static String TAG = "Add Expense";
    private Spinner sprDayAdd, sprMonthAdd, sprCategoriesAdd;
    private String userId;
    private String[] catArray = {"Bills", "Dining", "Education", "Entertainment",
            "Groceries", "Health", "Shopping", "Transportation", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        // initialize user ID
        userId = LoginActivity.userIDused;

        // initialize spinners
        addToCategorySpinner();
        addToMonthSpinner();
        addToDaySpinner();

        //Automatically set the month, day and year to the current day
        Date date = new Date();
        String strDateFormat = "yyyyMMdd";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        final EditText etYearAdd = findViewById(R.id.etYearAdd);
        sprDayAdd.setSelection(Integer.parseInt(formattedDate.substring(6,8))-1);
        etYearAdd.setText(formattedDate.substring(0,4));
        sprMonthAdd.setSelection(Integer.parseInt(formattedDate.substring(4,6))-1);

        //Find the group code of current user, and set the edit text field
        String url = "https://trackdatcash.herokuapp.com/expenses/codeMount";
        String retVal = ReturnExpense.getUser(url, LoginActivity.userIDused);
        int indexOfGC = retVal.indexOf("groupCode");
        int indexOfGCEnd = retVal.indexOf(",", indexOfGC);
        String retValCopy = retVal.substring(indexOfGC+12, indexOfGCEnd-1);
        final EditText etGroupAdd = (EditText) findViewById(R.id.etGroupAdd);
        etGroupAdd.setText(retValCopy);

        //Init buttons
        Button btnExpenseAdd = (Button) findViewById(R.id.btnExpenseAdd);
        Button btnRtoMMfAE = (Button) findViewById(R.id.btnRtoMMfAE);

        //Init checkbox
        final CheckBox cbGroupCode = (CheckBox) findViewById(R.id.cbGroupCode);

        //Toast popup extras
        final Context context = getApplicationContext();
        final CharSequence textAddSuccess = "Add Successful";
        final int duration = Toast.LENGTH_LONG;

        btnRtoMMfAE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todoIntent = new Intent(AddExpenseActivity.this, MainMenuActivity.class);
                AddExpenseActivity.this.startActivity(todoIntent);
            }
        });

        btnExpenseAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Take in all data fields
                EditText etDescriptionAdd = (EditText) findViewById(R.id.etDescriptionAdd);
                EditText etAmountAdd = (EditText) findViewById(R.id.etAmountAdd);

                String description = etDescriptionAdd.getText().toString();
                String amount = etAmountAdd.getText().toString();
                String month = sprMonthAdd.getSelectedItem().toString();
                String day = sprDayAdd.getSelectedItem().toString();
                String year = etYearAdd.getText().toString();
                String category = sprCategoriesAdd.getSelectedItem().toString();
                String group = etGroupAdd.getText().toString();

                // check if the amount is a valid number
                if (!isValidAmount(amount))
                {
                    etAmountAdd.setError("Invalid amount, please enter a valid number");
                    Toast toastAddFail = Toast.makeText(context, "Invalid amount input.", duration);
                    toastAddFail.show();
                    return;
                }

                if (!isValidYear(year))
                {
                    etYearAdd.setError("Invalid year, please enter a valid integer");
                    return;
                }

                amount = roundAmount(amount);

                if (cbGroupCode.isChecked())
                {
                    group = "none";
                }

                if (description.length()==0 || amount.length()==0 || day.length()==0 || year.length()==0)
                {
                    CharSequence textAddFail = "Add Failed - All fields required";
                    Toast toastRegFail = Toast.makeText(context, textAddFail, duration);
                    toastRegFail.show();
                    return;
                }

                String addExpense = AddUpdate.add(userId, description, amount, category, month, day, year, group);

                if ( addExpense == "error" )
                {
                    Toast toastLoginFail = Toast.makeText(context, "Error when adding expense", duration);
                    toastLoginFail.show();
                    return;
                }
                else {
                    //Temporary success only for add button
                    //Add was successful, return to main menu
                    Toast toastRegSuccess = Toast.makeText(context, textAddSuccess, duration);
                    toastRegSuccess.show();
                    Intent todoIntent = new Intent(AddExpenseActivity.this, MainMenuActivity.class);
                    AddExpenseActivity.this.startActivity(todoIntent);
                }
            }
        });

    }

    public void addToMonthSpinner() {
        sprMonthAdd = (Spinner) findViewById(R.id.sprMonthAdd);
        List<String> monthList = new ArrayList<String>();

        monthList.add("Jan");
        monthList.add("Feb");
        monthList.add("Mar");
        monthList.add("Apr");
        monthList.add("May");
        monthList.add("Jun");
        monthList.add("Jul");
        monthList.add("Aug");
        monthList.add("Sep");
        monthList.add("Oct");
        monthList.add("Nov");
        monthList.add("Dec");

        ArrayAdapter<String> dataAdapterM = new ArrayAdapter<String>(this,
                R.layout.spinner_item, monthList);
        dataAdapterM.setDropDownViewResource(R.layout.spinner_item);
        sprMonthAdd.setAdapter(dataAdapterM);
    }

    public void addToDaySpinner() {
        sprDayAdd = (Spinner) findViewById(R.id.sprDayAdd);
        List<String> dayList = new ArrayList<String>();

        for (int i = 1; i < 32; i++)
        {
            dayList.add(Integer.toString(i));
        }

        ArrayAdapter<String> dataAdapterM = new ArrayAdapter<String>(this,
                R.layout.spinner_item, dayList);
        dataAdapterM.setDropDownViewResource(R.layout.spinner_item);
        sprDayAdd.setAdapter(dataAdapterM);
    }

    public void addToCategorySpinner() {
        sprCategoriesAdd = (Spinner) findViewById(R.id.sprCategoriesAdd);
        List<String> catList = new ArrayList<String>();

        for (int i = 0; i<catArray.length; i++)
        {
            catList.add(catArray[i]);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, catList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        sprCategoriesAdd.setAdapter(dataAdapter);
    }

    // checks if the amount is valid or not
    // returns true for valid amount
    // returns false for invalid amount
    public boolean isValidAmount (String amount)
    {
        int length = amount.length();
        boolean valid = true;

        for (int i = 0; i < length; i++)
        {
            // check character is digit or decimal
            if ((amount.charAt(i) - '0' >= 0 && amount.charAt(i) - '9' <= 9) || amount.charAt(i) == 46)
            {
                valid = true;
            }

            else
            {
                valid = false;
                break;
            }
        }

        return valid;
    }

    // check if year is a valid integer
    public boolean isValidYear (String year)
    {
        try
        {
            Integer.parseInt(year);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    // method for rounding to two decimal places
    public String roundAmount (String amount)
    {
        if(amount.equals("") || amount == null)
        {
            return "";
        }

        Double expense = null;

        try
        {
            expense = new Double(amount);
        }
        catch (Exception e)
        {

        }

        Double finalvalue =  Math.round(expense * 100.00 ) / 100.0;

        return finalvalue.toString();
    }

}