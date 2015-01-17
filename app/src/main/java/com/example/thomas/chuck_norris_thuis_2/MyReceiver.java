package com.example.thomas.chuck_norris_thuis_2;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by thomas on 16/01/15.
 */
public class MyReceiver extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("receive is gebeurd.");

        Bundle extras = intent.getExtras();
        if (extras != null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View view = inflater.inflate( R.layout.activity_my, null );

            Button btnJokes = (Button)view.findViewById(R.id.btnFetch);
            btnJokes.setText("test");
            System.out.println(btnJokes.getText());
            String [] ar;
            ar = getArray(extras);

            ArrayAdapter<String> lstLijst = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, ar);
            ListView lijst = (ListView)view.findViewById(R.id.lstJokes);
            lijst.setAdapter(lstLijst);

        }
    }

    private String[] getArray(Bundle extras){
        String arr[] = new String[5];
        for (int i = 0; i<5; i++){
            arr[i]  = extras.getString(String.valueOf(i));
        }
        return arr;
    }
}
