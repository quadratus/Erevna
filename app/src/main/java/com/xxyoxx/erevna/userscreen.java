package com.xxyoxx.erevna;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class userscreen extends Activity {

    TextView usr,logo;
    Button upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userscreen);
        //String name = getIntent().getExtras().getString("uname");
        usr = (TextView) findViewById(R.id.namee);
       // usr.setText(name);
        SharedPreferences srt = getSharedPreferences("mypref",Context.MODE_PRIVATE);
        String usedat = srt.getString("email",null);
        usr.setText(usedat);

        logo = (Button)findViewById(R.id.log_out);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences stt = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor er = stt.edit();
                er.clear();
                er.commit();
                finish();
                Intent logo = new Intent("com.xxyoxx.erevna.login");
                startActivity(logo);
            }
        });

        upload = (Button) findViewById(R.id.upl);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent up = new Intent("com.xxyoxx.erevna.upload");
                startActivity(up);
            }
        });
    }

}
