package com.example.trackdatcash;

public class expenseDataObject
{
    String description;
    String amount;
    String month;
    int monthAsInt;
    String year;
    String day;
    String groupCode;
    String category;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
        if (month.toLowerCase().equals("jan"))
            this.monthAsInt = 1;
        else if (month.toLowerCase().equals("feb"))
            this.monthAsInt = 2;
        else if (month.toLowerCase().equals("mar"))
            this.monthAsInt = 3;
        else if (month.toLowerCase().equals("apr"))
            this.monthAsInt = 4;
        else if (month.toLowerCase().equals("may"))
            this.monthAsInt = 5;
        else if (month.toLowerCase().equals("june"))
            this.monthAsInt = 6;
        else if (month.toLowerCase().equals("july"))
            this.monthAsInt = 7;
        else if (month.toLowerCase().equals("aug"))
            this.monthAsInt = 8;
        else if (month.toLowerCase().equals("sept"))
            this.monthAsInt = 9;
        else if (month.toLowerCase().equals("oct"))
            this.monthAsInt = 10;
        else if (month.toLowerCase().equals("nov"))
            this.monthAsInt = 11;
        else if (month.toLowerCase().equals("dec"))
            this.monthAsInt = 12;

    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
