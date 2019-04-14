package com.example.trackdatcash;

import org.json.JSONObject;

public class AddUpdate {

    // Update id with URL and editText.getText().toString()
    // if update route returns "error", there was an error
    // if returns "true", successful
    // trackdatcash.herokuapp.com/update/:id
    // IF THERE IS NO GROUPCODE, USE STRING OF "none"
    public static String update(String url, String description, String amount, String category,
                                String month, String day, String year, String groupCode){

        JSONObject payload = new JSONObject();
        String test;
        String update = "";
        try{
            payload.put("description", description);
            payload.put("amount", amount);
            payload.put("category", category);
            payload.put("month", month);
            payload.put("day", day);
            payload.put("year", year);
            if(groupCode == "none"){

            }
            else
                payload.put("groupCode", groupCode);
        }
        catch(Exception ex){
            return "error";
        }

        try{
            test = JsonIo.doJsonIo(url, payload.toString()).toString();
            update = test.toString();

            if(update == "error"){
                return "error";
            }
            else{
                return "true";
            }
        }
        catch(Exception ex){
            return "error";
        }
    }

    // will return 'expense': 'expense added successfully' on success
    // returns "error" on error
    // trackdatcash.herokuapp.com/add
    // IF THERE IS NO GROUPCODE, JUST USE STRING OF "none"
    public static String add(String url, String userid, String description, String amount, String category,
                             String month, String day, String year, String groupCode){
        JSONObject payload = new JSONObject();
        String add;

        try{
            payload.put("userid", userid);
            payload.put("description", description);
            payload.put("amount", amount);
            payload.put("category", category);
            payload.put("month", month);
            payload.put("day", day);
            payload.put("year", year);
            if(groupCode == "none"){

            }
            else
                payload.put("groupCode", groupCode);

            add = JsonIo.doJsonIo(url, payload.toString()).toString();
            if(add == "error")
                return "error";

            return add;
        }
        catch(Exception ex){
            return "error";
        }
    }
}
