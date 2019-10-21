package com.xxyoxx.erevna;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.widget.TextView;

public class splash extends Activity {
    private static int SPLASH_DISPLAY_LENGTH = 3000;
    TextView t1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                SharedPreferences sharedPr = getSharedPreferences("mypref",MODE_PRIVATE);
                String checkMail = sharedPr.getString("email",null);

                if(checkMail != null && !checkMail.isEmpty()) {
                    Intent mainIntent = new Intent( splash.this, userscreen.class);
                    splash.this.startActivity(mainIntent);
                    splash.this.finish();
                }
                else {
                    Intent login_intent = new Intent(splash.this, login.class);
                    Intent el = new Intent("com.xxyoxx.erevna.login");
                    startActivity(login_intent);
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
        
    }
}
