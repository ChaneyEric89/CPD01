package com.userdata;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by eric chaney on 1/5/16.
 */
public class SignUp extends Activity {

   private String userNameCreated, passCreated, emailCreated;
    EditText userCreate, passCreate, emailCreate;
    Button doneBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        doneBtn = (Button) findViewById(R.id.done_btn);

        userCreate = (EditText) findViewById(R.id.username_field);
        passCreate = (EditText) findViewById(R.id.password_field);
        emailCreate = (EditText) findViewById(R.id.email_field);

         doneBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 userNameCreated = userCreate.getText().toString();
                 passCreated = passCreate.getText().toString();
                 emailCreated = emailCreate.getText().toString();


                 if (userNameCreated.equals("") && passCreated.equals("")) {
                     Toast.makeText(getApplicationContext(),
                             "Please complete the sign up form",
                             Toast.LENGTH_LONG).show();

                 } else {

                     ParseUser user = new ParseUser();
                     user.setUsername(userNameCreated);
                     user.setPassword(passCreated);
                     user.setEmail(emailCreated);
                     user.signUpInBackground(new SignUpCallback() {
                         public void done(ParseException e) {
                             if (e == null) {
                                 // Show a simple Toast message upon successful registration
                                 Toast.makeText(getApplicationContext(),
                                         "Successfully Signed up, please log in.",
                                         Toast.LENGTH_LONG).show();
                             } else {
                                 Toast.makeText(getApplicationContext(),
                                         "Sign up Error", Toast.LENGTH_LONG)
                                         .show();
                             }
                         }
                     });
                 }


             }
         });



    }


}
