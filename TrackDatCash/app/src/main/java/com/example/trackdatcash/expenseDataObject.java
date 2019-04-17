package com.example.trackdatcash;

public class expenseDataObject implements Comparable<expenseDataObject>
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
            this.setMonthAsInt(1);
        else if (month.toLowerCase().equals("feb"))
            this.setMonthAsInt(2);
        else if (month.toLowerCase().equals("mar"))
            this.setMonthAsInt(3);
        else if (month.toLowerCase().equals("apr"))
            this.setMonthAsInt(4);
        else if (month.toLowerCase().equals("may"))
            this.setMonthAsInt(5);
        else if (month.toLowerCase().equals("june"))
            this.setMonthAsInt(6);
        else if (month.toLowerCase().equals("july"))
            this.setMonthAsInt(7);
        else if (month.toLowerCase().equals("aug"))
            this.setMonthAsInt(8);
        else if (month.toLowerCase().equals("sept"))
            this.setMonthAsInt(9);
        else if (month.toLowerCase().equals("oct"))
            this.setMonthAsInt(10);
        else if (month.toLowerCase().equals("nov"))
            this.setMonthAsInt(11);
        else if (month.toLowerCase().equals("dec"))
            this.setMonthAsInt(12);

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

    @Override
    public int compareTo(expenseDataObject o) {
        if (Integer.parseInt(this.getYear())-Integer.parseInt(o.getYear())==0)
        {
            if (this.getMonthAsInt()-o.getMonthAsInt()==0)
            {
                return Integer.parseInt(this.getDay())<=Integer.parseInt(o.getDay()) ? 1 : -1;
            }
            else
            {
                return this.getMonthAsInt()<o.getMonthAsInt() ? 1 : -1;
            }
        }
        else
        {
            return Integer.parseInt(this.getYear())<Integer.parseInt(o.getYear()) ? 1 : -1;
        }

    }

    public int getMonthAsInt() {
        return monthAsInt;
    }

    public void setMonthAsInt(int monthAsInt) {
        this.monthAsInt = monthAsInt;
    }
}
