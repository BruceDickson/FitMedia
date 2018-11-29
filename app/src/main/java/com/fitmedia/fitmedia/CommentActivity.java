package com.fitmedia.fitmedia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    private ArrayList<Comment> comments;
    private ListView list_view_comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        list_view_comments = (ListView) findViewById(R.id.list_view_comment);

        Intent intent = getIntent();
        comments = (ArrayList<Comment>) intent.getSerializableExtra("comments");

        list_view_comments.setAdapter(new CommentAdapter(CommentActivity.this, comments));
    }
}
