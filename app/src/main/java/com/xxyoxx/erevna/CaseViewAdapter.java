package com.xxyoxx.erevna;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class CaseViewAdapter extends FirebaseRecyclerAdapter<blog, CaseViewAdapter.BlogViewHolder> {
    Context ct;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CaseViewAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BlogViewHolder holder, int position, @NonNull blog model) {
        final String post_key = getRef(position).getKey();
        holder.setTitle(model.getName());
        holder.setDesc(model.getEmail());
        holder.setImage(ct, model.getImage());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent casevu = new Intent(v.getContext(),case_view.class);
                casevu.putExtra("case_id",post_key);

            }
        });
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
            return new BlogViewHolder(v);
    }



    class BlogViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setTitle(String title) {
            TextView post_title =  mView.findViewById(R.id.post_title);
            post_title.setText(title);

        }

        public void setDesc(String desc) {
            TextView post_desc =  mView.findViewById(R.id.post_text);
            post_desc.setText(desc);
        }

        public void setImage(Context ct, String image) {
            ImageView postImage =  mView.findViewById(R.id.post_image);
            Picasso.with(ct).load(image).into(postImage);
        }

    }

}
