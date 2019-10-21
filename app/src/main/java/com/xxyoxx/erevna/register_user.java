package com.xxyoxx.erevna;

import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.util.ArrayList;

public class register_user extends Activity {

    private EditText name,password,address,mail;
    private RadioGroup sex;
    private Button register;
    private InputStream is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);
        setContentView(R.layout.activity_login_two);

        name = findViewById(R.id.name);
        password =  findViewById(R.id.pass);
        address = findViewById(R.id.addr);
        mail = findViewById(R.id.email);
        register = findViewById(R.id.reg);

        sex =  findViewById(R.id.rg);




        //TODO Separate from onCreate.

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(register.isPressed()){
                    int r = sex.getCheckedRadioButtonId();
                    RadioButton rb = findViewById(r);
                    String gender = rb.getText().toString();
                    String uname = name.getText().toString();
                    String pass = password.getText().toString();
                    String addr = address.getText().toString();
                    String eaddr = mail.getText().toString();

                    ArrayList<NameValuePair> regg = new ArrayList<NameValuePair>();
                    regg.add(new BasicNameValuePair("uname",uname));
                    regg.add(new BasicNameValuePair("gender",gender));
                    regg.add(new BasicNameValuePair("pass",pass));
                    regg.add(new BasicNameValuePair("address",addr));
                    regg.add(new BasicNameValuePair("email",eaddr));

                    try{
                        HttpClient htc = new DefaultHttpClient();
                        HttpPost hp = new HttpPost("http://www.xxyoxx.esy.es/register.php");
                        hp.setEntity(new UrlEncodedFormEntity(regg));
                        HttpResponse hr = htc.execute(hp);
                        HttpEntity ent = hr.getEntity();
                        is = ent.getContent();
                        Snackbar.make(v,"Successful!",Snackbar.LENGTH_SHORT).show();
                    }

                    catch (Exception reg){
                        Toast.makeText(getApplicationContext(),reg+"",Toast.LENGTH_LONG).show();
                    }

                    Intent regd = new Intent("com.xxyoxx.erevna.login");
                    startActivity(regd);

                }

            }
        });





    }


}
