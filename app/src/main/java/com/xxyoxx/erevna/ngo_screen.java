package com.xxyoxx.erevna;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.sql.Blob;

public class ngo_screen extends Activity {
    private RecyclerView mList;
    private DatabaseReference mDatabase;
    private CaseViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_screen);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<blog>()
                .setQuery(mDatabase,blog.class)
                .build();
        adapter = new CaseViewAdapter(options);
        mList = findViewById(R.id.recycler_view);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
