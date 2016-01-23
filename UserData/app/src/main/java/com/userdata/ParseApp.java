package com.userdata;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by eric chaney on 1/8/16.
 */
public class ParseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        Parse.initialize(this, "79xWydBvGQgbL1OB8EhmpDOW03rCe8KCyCn3Pg6T", "uUdFN3Y4T9PMFgHDTt26rLqwg7RMYeTARWkJtBZH");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);


    }
}
