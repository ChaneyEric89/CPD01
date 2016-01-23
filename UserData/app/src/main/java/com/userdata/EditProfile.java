package com.userdata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by eric chaney on 1/20/16.
 */
public class EditProfile extends Activity {

    Button updateEmailBtn;
    EditText createNewEmail;
    ParseUser user;
    Sync alarm;

    String newTitle, currentTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);
        user = ParseUser.getCurrentUser();
         alarm = new Sync();
        Intent intent = getIntent();
        currentTitle = intent.getStringExtra("clickedItem");

        updateEmailBtn = (Button) findViewById(R.id.update_btn);
        createNewEmail =(EditText) findViewById(R.id.edit_email);

        updateEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTitle = createNewEmail.getText().toString();


                    ParseQuery query = ParseQuery.getQuery("Post");

                query.whereEqualTo("user", user);
                query.whereEqualTo("title", currentTitle);
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (object == null) {
                            Log.d("score", "The getFirst request failed.");
                        } else {

                            object.put("title", newTitle);
                            Log.d("score", "Retrieved the object.");
                        }
                    }
                });

                Intent intent = new Intent(EditProfile.this, EnterData.class);


                startActivity(intent);
                //alarm.SetAlarm(EditProfile.this);

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

}
