package com.xxyoxx.erevna;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;

import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.FirebaseApp;

import java.util.Arrays;
import java.util.List;

//TODO: Implement authentication using Firebase
//TODO: Implement MVP architecture
//TODO: Write tests
//TODO: Implement app wide constraint layout
public class SplashScreen extends Activity  {
    private static int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                //This is where the SharedPreferences are created for the first time
                SharedPreferences sharedPr = getSharedPreferences("mypref",MODE_PRIVATE);
                String checkMail = sharedPr.getString("email",null);

                //Check if the SharePref are empty, if yes then start login activity otherwise start
                //user screen activity
                if(checkMail != null && !checkMail.isEmpty()) {
                    startActivity(new Intent(SplashScreen.this,userscreen.class));
                } else {
//                    AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout
//                            .Builder(R.layout.activity_login_screen)
//                            .setEmailButtonId(R.id.email_field)
//                            .build();
//
//                    Intent login_intent = AuthUI.getInstance(FirebaseApp.initializeApp(SplashScreen.this))
//                            .createSignInIntentBuilder()
//                            .setAuthMethodPickerLayout(customLayout)
//                            .build();
//
//                    List<AuthUI.IdpConfig> providers = Arrays.asList(
//                            new AuthUI.IdpConfig.EmailBuilder().build()
//                    );
//                    startActivityForResult(
//                            AuthUI.getInstance()
//                                    .createSignInIntentBuilder()
//                                    .setAvailableProviders(providers)
//                                    .build(),
//                            000);
//                    startActivity(login_intent);
                    startActivity(new Intent(SplashScreen.this,login.class));
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
//
    }
}