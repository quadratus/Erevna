package com.xxyoxx.erevna;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;

import java.io.InputStream;
import java.util.ArrayList;


public class case_view extends Activity {

    private String mCase_key = null;
    private DatabaseReference mDatabase;
    private ImageView mCaseImage;
    private TextView mCaseName;
    private TextView mCaseMail;
    private FloatingActionButton fab;
    private TextView mLat;
    private TextView mLon;
    private TextView mAddress, mEmail;
    private String post_desc;
    private String post_title;
    Button navi;
    InputStream is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_view);
        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");

        mCase_key = getIntent().getExtras().getString("case_id");

        navi = (Button) findViewById(R.id.navigate);
        mCaseImage = (ImageView) findViewById(R.id.ivc);
        mCaseName = (TextView) findViewById(R.id.tvc1);
        mCaseMail = (TextView) findViewById(R.id.tvc2);
        mAddress = (TextView) findViewById(R.id.addressc);
        mEmail = (TextView) findViewById(R.id.emailc);
        mLat = (TextView) findViewById(R.id.latc);
        mLon = (TextView) findViewById(R.id.longc);

        mDatabase.child(mCase_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                post_title = (String) dataSnapshot.child("Name").getValue();
                post_desc = (String) dataSnapshot.child("Email").getValue();
                String post_image = (String) dataSnapshot.child("Image").getValue();
                String post_lat = (String) dataSnapshot.child("Lat").getValue();
                String post_lon = (String) dataSnapshot.child("Lon").getValue();
                String post_addr = (String) dataSnapshot.child("Address").getValue();


                mCaseName.setText(post_title);
                mEmail.setText(post_desc);
                mAddress.setText(post_addr);
                Picasso.with(case_view.this).load(post_image).into(mCaseImage);
                mLat.setText(post_lat);
                mLon.setText(post_lon);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
            }
        });

        navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + mLat + "," + mLon);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                    startActivity(mapIntent);

                } catch (Exception er) {

                }
            }
        });

    }

    public void resolved(View v){
        final ArrayList<NameValuePair> res = new ArrayList<NameValuePair>();
        res.add(new BasicNameValuePair("email",post_desc));
        res.add(new BasicNameValuePair("name",post_title));

        try {

            DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
            HttpPost httppost = new HttpPost("http://xxyoxx.esy.es/sendMail.php");
            httppost.setEntity(new UrlEncodedFormEntity(res));
            HttpResponse hr = httpclient.execute(httppost);
            HttpEntity ent = hr.getEntity();
            is = ent.getContent();
            Snackbar.make(v,"E-Mail sent successfuly",Snackbar.LENGTH_LONG).show();


        } catch (Exception fl) {
            Toast.makeText(getApplicationContext(), "First Try error " + fl, Toast.LENGTH_LONG).show();
            Log.e("Tatti", fl.toString());
        }
    }
}

