package com.userdata;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by eric chaney on 1/5/16.
 */
public class EnterData extends Activity {

    Button logOutBtn, postBtn;
    EditText titleEdit, postEdit;
    String titleEntry, postEntry;

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_layout);

        fragmentTransaction = EnterData.this.getFragmentManager().beginTransaction();
        ListFrag listFrag = new ListFrag();
        fragmentTransaction.replace(R.id.entry_container, listFrag);
        fragmentTransaction.commit();

        logOutBtn = (Button) findViewById(R.id.logout_btn);
        postBtn = (Button) findViewById(R.id.post_btn);

        titleEdit = (EditText) findViewById(R.id.title_field);
        postEdit = (EditText) findViewById(R.id.post_field);

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser user = ParseUser.getCurrentUser();

                titleEntry = titleEdit.getText().toString();
                postEntry = postEdit.getText().toString();
            // Make a new post
                ParseObject post = new ParseObject("Post");
                post.put("title", titleEntry);
                post.put("body", postEntry);
                post.put("user", user);
                post.saveInBackground();


                ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
                query.whereEqualTo("user", user);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                    }
                });

                fragmentTransaction = EnterData.this.getFragmentManager().beginTransaction();
                ListFrag listFrag = new ListFrag();
                fragmentTransaction.replace(R.id.entry_container, listFrag);
                fragmentTransaction.commit();

               titleEdit.setText("");
                postEdit.setText("");

            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                finish();

                Intent intent = new Intent(EnterData.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
