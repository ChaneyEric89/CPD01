package com.userdata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;

//Eric Chaney
public class MainActivity extends Activity {

    EditText userName, userPass;
    Button signUpBtn, logInBtn;
    String userNameTxt, passTxt;
    Sync sync;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        userName = (EditText) findViewById(R.id.username_field);
        userPass = (EditText) findViewById(R.id.password_entry);

        signUpBtn = (Button) findViewById(R.id.button);
        logInBtn = (Button) findViewById(R.id.button2);


        sync = new Sync();

        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {

        } else {

            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                //sync.SetAlarm(getApplicationContext());

                Intent intent = new Intent(MainActivity.this, EnterData.class);
                startActivity(intent);
                finish();
            } else {

            }
        }



        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userNameTxt = userName.getText().toString();
                passTxt = userPass.getText().toString();

                ParseUser.logInInBackground(userNameTxt, passTxt,
                        new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                if (isOnline() && user != null) {

                                   // sync.SetAlarm(getApplicationContext());
                                    Intent intent = new Intent(
                                            MainActivity.this,
                                            EnterData.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),
                                            "Logged in",
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                } else {

                                    if(isOnline()){
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "No such user exist, please sign up",
                                            Toast.LENGTH_LONG).show();
                                }else{
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "Check Internet Connection",
                                                Toast.LENGTH_LONG).show();

                                    }
                            }
                        }
            });


            }
        });


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        SignUp.class);
                startActivity(intent);




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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
