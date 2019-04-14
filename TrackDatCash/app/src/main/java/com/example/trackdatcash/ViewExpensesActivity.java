package com.example.trackdatcash;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewExpensesActivity extends AppCompatActivity {
    private static final String TAG = "VPE";
    TableLayout tableLayout;
    ArrayList<expenseDataObject> expenseObjs;
    private String userID;
    private static String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expenses);

        Button btnRtoMMfVE = (Button) findViewById(R.id.btnRtoMMfVE);
        Button filter = (Button) findViewById(R.id.btnFilterExpenses);

        //Pull the url from the activity change intent
        Bundle bundle = getIntent().getExtras();
        String urlToUse = bundle.getString("url");
        userID = LoginActivity.userIDused;


        if (urlToUse.equals("NoChange"))
        {
            //No change to the table
        }
        else
        {
            //Call table create function upon activity create
            URL = urlToUse;

        }
        initViews();

        //Return to the Main Menu
        btnRtoMMfVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todoIntent = new Intent(ViewExpensesActivity.this, MainMenuActivity.class);
                ViewExpensesActivity.this.startActivity(todoIntent);
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todoIntent = new Intent(ViewExpensesActivity.this, FilterViewActivity.class);
                ViewExpensesActivity.this.startActivity(todoIntent);
            }
        });
    }

    public void initViews()
    {
        //Initialize using space designated on app xml file
        tableLayout = (TableLayout) findViewById(R.id.chart_layout);

        //Clear any previous data
        if (tableLayout!=null)
            tableLayout.removeAllViews();

//Grab data here normally
        //Go transform the data from JSON to an array list
        fetchedDataToArray();

        //For testing purposes
        //createTestData();

        //Once array list is populated, create table
        addRows();
    }

    public void fetchedDataToArray()
    {
        String retVal = ReturnExpense.getAllExpenses(URL, userID);
        if (retVal == null)
        {
            Log.e(TAG, "I hate you all");
            createTestData();
            return;
        }
        //Print to error log what the result was
        //Log.e(TAG, longString);

        expenseObjs = new ArrayList<>();

        String longCopy = retVal;

        //Formatting string for use and init of variables used
        longCopy = longCopy.replaceAll("\"", "");
        if (longCopy.length()<10)
        {
            //Exit, there's no data to use
            Context context = getApplicationContext();
            CharSequence textFailed = "No data";
            final int duration = Toast.LENGTH_LONG;
            Toast toastLoginFail = Toast.makeText(context, textFailed, duration);
            toastLoginFail.show();
            return;
        }
        longCopy = longCopy.substring(1,longCopy.length()-2);
        //Log.e(TAG, longCopy);
        int nextComma, indexOfDesc, indexOfAmount, indexOfMonth, indexOfYear, indexOfDay, indexOfCategory;
        int nextCurly = longCopy.indexOf("}");
        int lastCurly = 0;


        while(true)
        {
            //Find next curly, make sure we haven't gone too far
            nextCurly = longCopy.indexOf("}", lastCurly+1);
            if (nextCurly < 0)
            {
                nextCurly = longCopy.length()-1;
            }
            expenseDataObject test = new expenseDataObject();

            //Find description after last end curly
            indexOfDesc = longCopy.indexOf("description", lastCurly);
            nextComma = longCopy.indexOf(",", indexOfDesc);
            test.setDescription(longCopy.substring(indexOfDesc+12,nextComma));

            //Find amount after last end curly
            indexOfAmount = longCopy.indexOf("amount", lastCurly);
            nextComma = longCopy.indexOf(",", indexOfAmount);
            test.setAmount(longCopy.substring(indexOfAmount+7,nextComma));

            //Find Category
            indexOfCategory = longCopy.indexOf("category", lastCurly);
            nextComma = longCopy.indexOf(",", indexOfCategory);
            if (nextComma<nextCurly && nextComma>0)
                test.setCategory(longCopy.substring(indexOfCategory+9,nextComma));
            else
                test.setCategory(longCopy.substring(indexOfCategory+9,nextCurly));


            //Find month after last end curly
            indexOfMonth = longCopy.indexOf("month", lastCurly);
            nextComma = longCopy.indexOf(",", indexOfMonth);
            //Log.e(TAG, longCopy.substring(indexOfMonth+6,nextComma));
            if (nextComma<nextCurly && nextComma>0)
                test.setMonth( longCopy.substring(indexOfMonth+6,nextComma) );
            else
                test.setMonth(longCopy.substring(indexOfMonth+6,nextCurly));

            //Find day after last end curly
            indexOfDay = longCopy.indexOf("day", lastCurly);
            nextComma = longCopy.indexOf(",", indexOfDay);
            if (indexOfDay>nextCurly || indexOfDay<0)
            {
                //Day was not included in this entry
                test.setDay("");
            }
            else
            {
                test.setDay((nextComma < nextCurly) ? longCopy.substring(indexOfDay + 4, nextComma) :
                        longCopy.substring(indexOfDay + 4, nextCurly));
            }

            //Find year after last end curly
            indexOfYear = longCopy.indexOf("year", lastCurly);
            nextComma = longCopy.indexOf(",", indexOfYear);
            test.setYear((nextComma<nextCurly && nextComma>0) ? longCopy.substring(indexOfYear+5,nextComma) :
                    longCopy.substring(indexOfYear+5,nextCurly));

            //Add to arraylist
            expenseObjs.add(test);

            //See if we've gone too far
            if (longCopy.indexOf("}", lastCurly+1) < 0)
            {
                break;
            }

            //Log.e(TAG, longCopy.substring(indexOfYear+5,nextComma));

            //Get the next end curly
            lastCurly = longCopy.indexOf("}", lastCurly+1);

        }

    }
    public void createTestData()
    {
        expenseObjs = new ArrayList<>();
        for (int i = 1; i < 50; i ++)
        {
            expenseDataObject test = new expenseDataObject();
            test.setDescription("He was number " + Integer.toString(i));
            test.setAmount(Integer.toString(i));
            test.setYear("1999");
            test.setMonth("May");
            test.setDay(Integer.toString(i));
            test.setCategory("Entertainment");
            expenseObjs.add(test);
        }

    }

    public void addRows()
    {
        //Create header first
        TableRow header = new TableRow(ViewExpensesActivity.this);
        header.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        //Add headers using formatting function
        header.addView(getRowsTextView(0, "Date", Color.BLACK, Typeface.BOLD, R.layout.cell_shape_dark));
        header.addView(getRowsTextView(0, "Description", Color.BLACK, Typeface.BOLD, R.layout.cell_shape_dark));
        header.addView(getRowsTextView(0, "Amount", Color.BLACK, Typeface.BOLD, R.layout.cell_shape_dark));
        header.addView(getRowsTextView(0, "Category", Color.BLACK, Typeface.BOLD, R.layout.cell_shape_dark));

        //Add the header row to the table
        tableLayout.addView(header, new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        for (int i = 0; i < expenseObjs.size(); i ++)
        {
            //Begin row setup
            TableRow row = new TableRow(ViewExpensesActivity.this);
            row.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            //Grab current objects month/day/year and format to string
            String day = expenseObjs.get(i).getDay();
            String month = expenseObjs.get(i).getMonth();
            String year = expenseObjs.get(i).getYear();
            String date;
            if (day.equals(""))
            {
                date = month + " " + year;
            }
            else
            {
                date = month + " " + day + ", " + year;
            }

            //Add the data to the row - alternating between row colours
            if ((i % 2) == 0 && i != 1)
            {
                row.addView(getRowsTextView(0, date, Color.BLACK, Typeface.NORMAL, R.layout.cell_shape_light));
                row.addView(getRowsTextView(0, expenseObjs.get(i).getDescription(), Color.BLACK, Typeface.NORMAL, R.layout.cell_shape_light));
                row.addView(getRowsTextView(0, expenseObjs.get(i).getAmount(), Color.BLACK, Typeface.NORMAL, R.layout.cell_shape_light));
                row.addView(getRowsTextView(0, expenseObjs.get(i).getCategory(), Color.BLACK, Typeface.NORMAL, R.layout.cell_shape_light));
            }
            else
            {
                row.addView(getRowsTextView(0, date, Color.BLACK, Typeface.NORMAL, R.layout.cell_shape_dark));
                row.addView(getRowsTextView(0, expenseObjs.get(i).getDescription(), Color.BLACK, Typeface.NORMAL, R.layout.cell_shape_dark));
                row.addView(getRowsTextView(0, expenseObjs.get(i).getAmount(), Color.BLACK, Typeface.NORMAL, R.layout.cell_shape_dark));
                row.addView(getRowsTextView(0, expenseObjs.get(i).getCategory(), Color.BLACK, Typeface.NORMAL, R.layout.cell_shape_dark));
            }

            //Add to table
            tableLayout.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
        }
    }

    private TextView getRowsTextView(int id, String title, int color, int typeface, int bgColor)
    {
        //Formatting for row cells
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title);
        tv.setTextColor(color);
        tv.setPadding(10, 10, 10, 10);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundResource(bgColor);
        tv.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        return tv;
    }


}
