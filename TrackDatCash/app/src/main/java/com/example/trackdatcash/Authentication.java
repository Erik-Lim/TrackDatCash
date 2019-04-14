package com.example.trackdatcash;

import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class Authentication {

    // pass in url, token (from login method), secret and returns userId
    // No url because this is the jjwt. MUST USE LOGIN METHOD FIRST TO GET JWT
    public static String getUserId(String token, String secret){
        String encodedSecret = Base64.encodeToString(secret.getBytes(), Base64.DEFAULT);

        // return the UserID using the jwt
        final String userId;
        Jws<Claims> jws;
        try {
            jws = Jwts.parser()         // (1)
                    .setSigningKey(encodedSecret)         // (2)
                    .parseClaimsJws(token); // (3)
            userId = jws.getBody().get("id").toString();
            return userId;
            // we can safely trust the JWT
        }
        catch (JwtException ex) {       // (4)
            // we *cannot* use the JWT as intended by its creator
            return "error";
        }
    }

    // login returns jwt, use jwt in getUserId to get user id
    // returns "error" on error, and jwt on
    // trackdatcash.herokuapp.com/login
    public static String login(String url, String email, String password){
        JSONObject payload = new JSONObject();
        String token;

        JSONArray temp = new JSONArray();

        // create payload
        try{
            payload.put("email", email);
            payload.put("password", password);

        }
        catch(Exception ex){
            return "error";
        }

        // grab jwt from login, otherwise return error
        try{
            token = JsonIo.doJsonIo(url, payload.toString()).toString();

            return token;
        }
        catch(Exception ex){
            return "error";
        }
    }

    // pass in url, and name/email/password as EditText.getText().toString()
    // on success, will return string of true and string of false on error
    public static String register(String url, String name, String email, String password){
        JSONObject payload = new JSONObject();
        JSONObject temp;
        String register;

        try{
            payload.put("name", name);
            payload.put("email", email);
            payload.put("password", password);
        }
        catch(Exception ex){
            return "false";
        }

        try{
            temp = JsonIo.doJsonIo(url, payload.toString());
            register = temp.toString();
            if(register == "error")
                return register;

            return "true";
        }
        catch(Exception ex){
            return "errorr";
        }
    }
}
