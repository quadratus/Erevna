package com.xxyoxx.erevna;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.StrictMode;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class upload extends Activity implements LocationListener {

    Button location, submit, image;
    String lat;
    String lon;
    TextView longitude, latitude;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private static final int GALLERY_INTENT = 1;
    private ProgressDialog mProgressDialog;
    FirebaseStorage storage;
    ImageView iv;

    String myJSON;
    InputStream is;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADD = "address";

    Task<Uri> downloadUri;
    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    TextView nm, addr, mail;

    String id = "", name = "", address = "";
    String uname;

    LocationManager lm;
    // TODO: Fix map location fetch. Code should probably go into onProviderEnabled
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);
        storage = FirebaseStorage.getInstance();
        mStorage = storage.getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");

        personList = new ArrayList<HashMap<String, String>>();


        nm = findViewById(R.id.namec);
        addr =  findViewById(R.id.addressc);
        mail =  findViewById(R.id.emailc);
        iv =  findViewById(R.id.imagev);
        image = findViewById(R.id.selectimage);
        location = findViewById(R.id.loc);
        longitude = findViewById(R.id.longc);
        latitude =  findViewById(R.id.latc);


        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        /*if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 100, this);*/


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_INTENT);
            }
        });
    }

    public void getData(View gd) {

        SharedPreferences my = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        uname = my.getString("email", null);

        final ArrayList<NameValuePair> arr = new ArrayList<NameValuePair>();
        arr.add(new BasicNameValuePair("email", uname));

        try {

            DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
            HttpPost httppost = new HttpPost("http://xxyoxx.esy.es/getDetails.php");
            httppost.setEntity(new UrlEncodedFormEntity(arr));
            HttpResponse hr = httpclient.execute(httppost);
            HttpEntity ent = hr.getEntity();
            is = ent.getContent();
                    /*Toast.makeText(getApplicationContext(),"1 wrk ",Toast.LENGTH_LONG).show();*/


        } catch (Exception fl) {
                    /*Toast.makeText(getApplicationContext(),"First Try error "+fl,Toast.LENGTH_LONG).show();*/
            Log.e("TRY$", fl.toString());
        }
                /*// Depends on your web service
                httppost.setHeader("Content-type", "application/json");*/

        String result = null;
        try {
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
                    /*Toast.makeText(getApplicationContext(),"2 str\n "+result,Toast.LENGTH_LONG).show();*/
        } catch (Exception sl) {
            sl.printStackTrace();
            Log.e("Tatti", sl.toString());
                    /*Toast.makeText(getApplicationContext(),"2 err\n "+sl,Toast.LENGTH_LONG).show();*/
        }

        try {
            String aa = "", b = "", c = "";
            JSONArray ar = new JSONArray(result);
            for (int i = 0; i < ar.length(); i++) {
                JSONObject jo = ar.getJSONObject(i);
                aa = jo.getString("uname");
                b = jo.getString("address");
                c = jo.getString("email");
            }
            nm.setText(aa);
            addr.setText(b);
            mail.setText(c);
                   /* Toast.makeText(getApplicationContext(),"3 wrk"+result,Toast.LENGTH_LONG).show();*/
        } catch (Exception tl) {
                    /*Toast.makeText(getApplicationContext(),"3 err "+tl,Toast.LENGTH_LONG).show();*/
        }

        LocationManager locationManager =
                (LocationManager) upload.this.getSystemService(Context.LOCATION_SERVICE);
        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                lat = Double.toString(location.getLatitude());
                lon = Double.toString(location.getLongitude());
                longitude.setText(lon);
                latitude.setText(lat);

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        //del


        //del

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            /*if (resultCode == GALLERY_INTENT && resultCode == RESULT_OK) {*/

            final Uri path = data.getData();

            StorageReference filepath = mStorage.child("Photos").child(path.getLastPathSegment());

            filepath.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    iv.setImageURI(path);
//TODO: Consider separating text data upload logic from image upload logic, text should be uploaded when
//                    the user clicks upload, not when they select an image (Done, needs review)
                    downloadUri= taskSnapshot.getStorage().getDownloadUrl();

                }
            });
            /*}*/
        } catch (Exception upl) {
            upl.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void upload_data(View view) {
        DatabaseReference newList = mDatabase.push();
        newList.child("Name").setValue(nm.getText());
        newList.child("Address").setValue(addr.getText());
        newList.child("Email").setValue(uname);
        newList.child("Image").setValue(downloadUri.toString());
        newList.child("Lat").setValue(latitude.getText());
        newList.child("Lon").setValue(longitude.getText());
        Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_LONG).show();
    }
}
