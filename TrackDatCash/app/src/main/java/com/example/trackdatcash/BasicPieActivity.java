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
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BasicPieActivity extends AppCompatActivity {

    private static String TAG = "BasicPieActivity";
    private double[] catTotals;
    private String[] catArray = {"Food", "Bills", "Entertainment", "Other/Misc."};
    PieChart pieChart;
    private String userID;
    private static String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_pie);
        //Log.d(TAG, "OnCreate");

        //Init activity clickables
        Button btnRtoMMfVP = (Button) findViewById(R.id.btnRtoMMfVP);
        Button btnFilterPie = (Button) findViewById(R.id.btnFilterPie);
        pieChart = (PieChart) findViewById(R.id.chPieChart);

        //Add things to the pie chart, Start non-data related setup
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(30f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Total Spending");
        pieChart.setCenterTextSize(10);
        pieChart.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
        pieChart.getDescription().setText("Spending per Category");
        pieChart.setDrawEntryLabels(true);

        //Pull the url from the activity change intent
        Bundle bundle = getIntent().getExtras();
        String urlToUse = bundle.getString("url");
        userID = LoginActivity.userIDused;

        if (urlToUse.equals("NoChange"))
        {
            //No change to the pie chart, redraw with previous URL
        }
        else
        {
            //Use the new URL to draw or redraw the pie chart
            URL = urlToUse;
        }

        //Go pull the data for the chart
        addDataSet(pieChart);

        //This listener allows the pie chart slices to be clickable, and a toast pops up
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //Find the selected slice and output the found value to screen using Toast
                int pos1 = h.toString().indexOf("x: ");
                int pos2 = h.toString().indexOf(".",pos1);
                String cat = h.toString().substring(pos1+3, pos2);
                String catType = catArray[Integer.parseInt(cat)];
                Integer val = (int)catTotals[Integer.parseInt(cat)];
                Toast.makeText(BasicPieActivity.this, catType+"\nTotal $"+val.toString(),Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected()
            {
                //Do nothing
            }
        });

        //Return to the Main Menu
        btnRtoMMfVP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todoIntent = new Intent(BasicPieActivity.this, MainMenuActivity.class);
                BasicPieActivity.this.startActivity(todoIntent);
            }
        });

        //Go to the filter activity to choose the time frame for the data in the chart
        btnFilterPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todoIntent = new Intent(BasicPieActivity.this, PieFilterActivity.class);
                BasicPieActivity.this.startActivity(todoIntent);
            }
        });
    }

    private void addDataSet(PieChart pieChart) {
        //Call the function that will go pull the data using the URL given
        fetchedDataToArray();

        //Init the ArrayLists used in the data for the pie chart
        ArrayList<PieEntry> totals = new ArrayList<>();
        ArrayList<String> catNames = new ArrayList<>();

        //Add the values to the arrays used in data set
        for (int i = 0; i<catTotals.length; i++)
        {
            totals.add(new PieEntry((int)catTotals[i], i));
            catNames.add(catArray[i]);
        }

        //Data set creation
        PieDataSet pieDataSet = new PieDataSet(totals, "Categories");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);


        //Adding colors for the pie chart
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.colorPie1));
        colors.add(getResources().getColor(R.color.colorPie2));
        colors.add(getResources().getColor(R.color.colorPie3));
        colors.add(getResources().getColor(R.color.colorPie4));

        pieDataSet.setColors(colors);

        //Create a legend
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setTextColor(getResources().getColor(R.color.colorPie5));

        LegendEntry[] legendEntry = new LegendEntry[catTotals.length];

        //Iterate through legend entries to be added using the category names
        for (int i = 0; i<catTotals.length; i++)
        {
            LegendEntry entry = new LegendEntry();
            entry.formColor = colors.get(i);
            entry.label = String.valueOf(catArray[i]);
            legendEntry[i] = entry;
        }

        legend.setCustom(legendEntry);

        //Create the object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }

    public void fetchedDataToArray()
    {
        String retVal = ReturnExpense.getAllExpenses(URL, userID);
        if (retVal == null)
        {
            Log.e(TAG, "Pie chart data grab failed");
            return;
        }

        //Init the totals array
        catTotals = new double[4];

        String longCopy = retVal;

        //Formatting string for use and init of variables used
        longCopy = longCopy.replaceAll("\"", "");

        //Due to a filter or new registration, there is no data
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

        //Begin necessities for parsing
        longCopy = longCopy.substring(1,longCopy.length()-2);
        int nextComma, indexOfAmount, indexOfCategory;
        int nextCurly = longCopy.indexOf("}");
        int lastCurly = 0;
        double currAmount;

        //Begin to go through the returned string of data
        while(true)
        {
            //Find next curly, make sure we haven't gone too far
            nextCurly = longCopy.indexOf("}", lastCurly+1);
            if (nextCurly < 0)
            {
                nextCurly = longCopy.length()-1;
            }

            //Find amount after last end curly
            indexOfAmount = longCopy.indexOf("amount", lastCurly);
            nextComma = longCopy.indexOf(",", indexOfAmount);
            currAmount = Double.parseDouble(longCopy.substring(indexOfAmount+7,nextComma));

            //Find Category
            indexOfCategory = longCopy.indexOf("category", lastCurly);
            nextComma = longCopy.indexOf(",", indexOfCategory);
            String cat;
            if (nextComma<nextCurly && nextComma>0)
                cat = (longCopy.substring(indexOfCategory+9,nextComma));
            else
                cat = (longCopy.substring(indexOfCategory+9,nextCurly));

            //Find the category, add to the current expense total of that category
            for (int i = 0; i <4; i++)
            {
                if (cat.equals(catArray[i]))
                {
                    catTotals[i]+=currAmount;
                }
            }

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
}
