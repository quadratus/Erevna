package com.xxyoxx.erevna;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.sql.Blob;

public class ngo_screen extends Activity {
    private RecyclerView mList;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_screen);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mList = (RecyclerView) findViewById(R.id.list);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<blog, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<blog, BlogViewHolder>(
                blog.class,
                R.layout.list_row,
                BlogViewHolder.class,
                mDatabase
        ) {
            @NonNull
            @Override
            public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            protected void onBindViewHolder(@NonNull BlogViewHolder holder, int position, @NonNull blog model) {
                final String post_key = getRef(position).getKey();
                holder.setTitle(model.getName());
                holder.setDesc(model.getEmail());
                holder.setImage(getApplicationContext(), model.getImage());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent casevu = new Intent(ngo_screen.this,case_view.class);
                        casevu.putExtra("case_id",post_key);
                        startActivity(casevu);
                    }
                });
            }

//            @Override
//            protected void populateViewHolder(BlogViewHolder blogViewHolder, blog blog, int i) {
//                final String post_key = getRef(i).getKey();
//                blogViewHolder.setTitle(blog.getName());
//                blogViewHolder.setDesc(blog.getEmail());
//                blogViewHolder.setImage(getApplicationContext(), blog.getImage());
//
//                blogViewHolder.mView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent casevu = new Intent(ngo_screen.this,case_view.class);
//                        casevu.putExtra("case_id",post_key);
//                        startActivity(casevu);
//                    }
//                });
//            }
        };

        mList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setTitle(String title) {
            TextView post_title = (TextView) mView.findViewById(R.id.post_title);
            post_title.setText(title);

        }

        public void setDesc(String desc) {
            TextView post_desc = (TextView) mView.findViewById(R.id.post_text);
            post_desc.setText(desc);
        }

        public void setImage(Context ct, String image) {
            ImageView postImage = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ct).load(image).into(postImage);
        }

    }
}
