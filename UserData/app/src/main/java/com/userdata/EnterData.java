package com.userdata;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;

/**
 * Created by eric chaney on 1/5/16.
 */
public class EnterData extends Activity {

    Button logOutBtn, postBtn;
    EditText titleEdit, postEdit;

    String titleEntry, postEntry;
    private ArrayList<String> syncedDataList;

    //ParseACL defaultACL;
    ParseUser user;

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_layout);
         //defaultACL = new ParseACL();
         user = ParseUser.getCurrentUser();
        syncedDataList = new ArrayList<>();

        registerReceiver(broadcastReceiver, new IntentFilter("getSyncedData"));

        Bundle data = new Bundle();
        data.putStringArrayList("syncedData", syncedDataList);


        fragmentTransaction = EnterData.this.getFragmentManager().beginTransaction();
        ListFrag listFrag = new ListFrag();
        listFrag.setArguments(data);
        fragmentTransaction.replace(R.id.entry_container, listFrag);
        fragmentTransaction.commit();

        logOutBtn = (Button) findViewById(R.id.logout_btn);
        postBtn = (Button) findViewById(R.id.post_btn);


        titleEdit = (EditText) findViewById(R.id.title_field);
        postEdit = (EditText) findViewById(R.id.post_field);

        //DATA VALIDATION
        titleEdit.setFilters(new InputFilter[] {new InputFilter.LengthFilter(25)});

        postEdit.setFilters(new InputFilter[] {new InputFilter.LengthFilter(225)});

        /////
        
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOnline()){
                 user = ParseUser.getCurrentUser();


                    titleEntry = titleEdit.getText().toString();
                    postEntry = postEdit.getText().toString();
                    // Make a new post
                    ParseObject post = new ParseObject("Post");
                    post.put("title", titleEntry);
                    post.put("body", postEntry);
                    post.put("user", user);
                    post.setACL(new ParseACL(user));
                   // ParseACL.setDefaultACL(defaultACL, true);
                    //post.saveInBackground();
                    post.saveInBackground(new SaveCallback() {
                        public void done(ParseException e) {
                        if (e == null) {
                        // myObjectSavedSuccessfully();

                            Bundle data = new Bundle();
                            data.putStringArrayList("syncedData", syncedDataList);

                            fragmentTransaction = EnterData.this.getFragmentManager().beginTransaction();
                            ListFrag listFrag = new ListFrag();
                            listFrag.setArguments(data);
                            fragmentTransaction.replace(R.id.entry_container, listFrag);
                            fragmentTransaction.commit();

                            titleEdit.setText("");
                            postEdit.setText("");
                            syncedDataList = null;

                        } else {
                        //myObjectSaveDidNotSucceed();

                            Toast.makeText(
                                    getApplicationContext(),
                                    "Post Failed: Check Internet Connection",
                                    Toast.LENGTH_LONG).show();
                            }
                        }
                    });


//                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
//                    query.whereEqualTo("user", user);
//                    query.findInBackground(new FindCallback<ParseObject>() {
//                        @Override
//                        public void done(List<ParseObject> objects, ParseException e) {
//
//                        }
//                    });


                }else{

                    Toast.makeText(
                            getApplicationContext(),
                            "Post Failed: Check Internet Connection",
                            Toast.LENGTH_LONG).show();
                }


            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if(isOnline()) {
                  ParseUser.logOut();
                  finish();

                  Intent intent = new Intent(EnterData.this, MainActivity.class);
                  startActivity(intent);
                  finish();
              }else{

                  Toast.makeText(
                          getApplicationContext(),
                          "Logout Failed: Check Internet Connection",
                          Toast.LENGTH_LONG).show();
              }
            }
        });


    }

    protected boolean isOnline(){

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnectedOrConnecting()){

            return true;
        }else{
            return false;
        }

    }



    BroadcastReceiver broadcastReceiver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (isOnline()) {


            syncedDataList = intent.getExtras().getStringArrayList("extra");
            //String state = intent.getExtras().getString("extra");

            Bundle data = new Bundle();
            data.putStringArrayList("syncedData", syncedDataList);
            fragmentTransaction = EnterData.this.getFragmentManager().beginTransaction();
            ListFrag listFrag = new ListFrag();
            listFrag.setArguments(data);
            fragmentTransaction.replace(R.id.entry_container, listFrag);
            fragmentTransaction.commit();
        }
            //Log.e("newmesage", "" + message);
        }
    };


}
