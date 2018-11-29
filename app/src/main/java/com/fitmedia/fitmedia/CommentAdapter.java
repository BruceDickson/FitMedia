package com.fitmedia.fitmedia;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<Comment> comments;

    public CommentAdapter(Activity activity, ArrayList<Comment> comments) {
        this.activity = activity;
        this.comments = comments;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = activity.getLayoutInflater().inflate(R.layout.item_comment, null);
        TextView name_user = (TextView) v.findViewById(R.id.txt_name_comment);
        TextView content_comment = (TextView) v.findViewById(R.id.txt_comment);
        name_user.setText(comments.get(position).getName_user());
        content_comment.setText(comments.get(position).getContent());
        return v;
    }
}
