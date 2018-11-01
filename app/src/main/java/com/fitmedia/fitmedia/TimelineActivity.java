package com.fitmedia.fitmedia;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimelineActivity extends AppCompatActivity {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    private String key_user;
    private String key_post;
    private String content_post;
    private Long date_post;

    private RecyclerView recyclerView;

    private  EditText edt_post;

    private Button btn_post;

    private List<Post> posts = new ArrayList<Post>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        edt_post = (EditText) findViewById(R.id.edt_post);
        btn_post = (Button) findViewById(R.id.btn_post);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("posts");


        Intent intent = getIntent();

        key_user = intent.getStringExtra("key_user");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(new Adapter(this, posts));


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                posts = new ArrayList<Post>();
                if(dataSnapshot.exists()){
                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        Post post = data.getValue(Post.class);
                        posts.add(post);
                        //Toast.makeText(TimelineActivity.this, "Chego aqui xD!", Toast.LENGTH_SHORT).show();
                    }
                    recyclerView.setAdapter(new Adapter(TimelineActivity.this, posts));
                }
                else{
                    Toast.makeText(TimelineActivity.this, "Fudeo menô!", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });




        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                date_post = new Date().getTime();



                key_post = mDatabase.push().getKey();
                content_post = edt_post.getText().toString();
                Post post = new Post(content_post, key_user, date_post);
                mDatabase.child("posts").child(key_post).setValue(post);

            }
        });



    }
}
