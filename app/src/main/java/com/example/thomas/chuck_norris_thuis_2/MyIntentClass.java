package com.example.thomas.chuck_norris_thuis_2;

import android.app.IntentService;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomas on 16/01/15.
 */
public class MyIntentClass extends IntentService {
    List<String> lijst = new ArrayList<String>();
    @Override
    protected void onHandleIntent(Intent intent) {
        openConnection();
    }

    public MyIntentClass(){
        super("MyIntentClass");
    }

    private void openConnection(){
        try{
            String output;
            while (lijst.size() < 5) {
                URL url = new URL("http://api.icndb.com/jokes/random?");
                //Create new HTTP URL connection
                URLConnection connection = url.openConnection();
                HttpURLConnection httpURLConnection =
                        (HttpURLConnection) connection;
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream in = httpURLConnection.getInputStream();
                    output = readAnsParseJSON(getStringFromInputStream(in));
                    if (!lijst.contains(output)){
                        lijst.add(output);
                    }
                }
            }

            Intent intent = new Intent();
            intent.setAction("MyBroadcast");
            for (int i = 0; i<5; i++){
                intent.putExtra(String.valueOf(i), lijst.get(i));
            }
            sendBroadcast(intent);
        }
        catch(MalformedURLException e){
            Log.d("MyActivity","Malformed URL exception");
            e.printStackTrace();
        }
        catch(IOException e){
            Log.d("MyActivity", "IO Exception");
            e.printStackTrace();
        }
        catch(JSONException e){
            Log.d("MyActivity", "JSON Exception");
            e.printStackTrace();
        }
    }


    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    public String readAnsParseJSON (String in) throws JSONException {
        String joke;
        JSONObject reader = new JSONObject(in);
        JSONObject value = reader.getJSONObject("value");
        joke = value.getString("joke");
        return joke;

    }
}
