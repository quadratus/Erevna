package com.xxyoxx.erevna;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class login extends Activity {

    private EditText email, password;
    private Button register, user_sign_in, ngo_sign_in;
    private String result;

    private InputStream is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        user_sign_in = findViewById(R.id.sign_in_user);
        ngo_sign_in = findViewById(R.id.sign_in_ngo);
        register =  findViewById(R.id.register);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent rg = new Intent("com.xxyoxx.erevna.register_user");
                Intent login_intent = new Intent(v.getContext(), register_user.class);
                startActivity(login_intent);

            }
        });

        user_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eaddr = email.getText().toString();
                String pass = password.getText().toString();

                ArrayList<NameValuePair> authentication_list = new ArrayList<NameValuePair>();
                authentication_list.add(new BasicNameValuePair("email", eaddr));
                authentication_list.add(new BasicNameValuePair("pass", pass));

                try {
                    HttpClient htc = new DefaultHttpClient();
                    HttpPost hp = new HttpPost("http://www.xxyoxx.esy.es/verify.php");
                    hp.setEntity(new UrlEncodedFormEntity(authentication_list));
                    HttpResponse hr = htc.execute(hp);
                    HttpEntity ent = hr.getEntity();
                    is = ent.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();

                    //Snackbar.make(v,"Successful!",Snackbar.LENGTH_SHORT).show();

                    String s = result.trim();
                    if (s.equalsIgnoreCase("success")) {
                        SharedPreferences sp = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = sp.edit();
                        ed.putString("email", eaddr);
                        ed.apply();

                        Intent user_screen_intent = new Intent(v.getContext(),userscreen.class);
                        user_screen_intent.putExtra("uname", eaddr);
                        startActivity(user_screen_intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid user name or password", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception reg) {
                    Toast.makeText(getApplicationContext(), reg + "", Toast.LENGTH_LONG).show();
                }


            }
        });

        ngo_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eaddr = email.getText().toString();
                String pass = password.getText().toString();

                ArrayList<NameValuePair> ver = new ArrayList<NameValuePair>();
                ver.add(new BasicNameValuePair("email", eaddr));
                ver.add(new BasicNameValuePair("pass", pass));

                try {
                    HttpClient htc = new DefaultHttpClient();
                    HttpPost hp = new HttpPost("http://www.xxyoxx.esy.es/verifyngo.php");
                    hp.setEntity(new UrlEncodedFormEntity(ver));
                    HttpResponse hr = htc.execute(hp);
                    HttpEntity ent = hr.getEntity();
                    is = ent.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();

                    //Snackbar.make(v,"Successful!",Snackbar.LENGTH_SHORT).show();

                    String s = result.trim();
                    if (s.equalsIgnoreCase("success")) {
                        SharedPreferences sp = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = sp.edit();
                        ed.putString("email", eaddr);
                        ed.apply();
//                        Intent con = new Intent("com.xxyoxx.erevna.ngoscreen");
                        Intent ngo_screen_intent = new Intent(v.getContext(), ngo_screen.class);
                        ngo_screen_intent.putExtra("uname", eaddr);
//                        con.putExtra("uname", eaddr);
                        startActivity(ngo_screen_intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid user name or password", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception reg) {
                    Toast.makeText(getApplicationContext(), reg + "", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void sign_ind(View s) {

    }

}
