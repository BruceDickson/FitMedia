package com.fitmedia.fitmedia;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimelineActivity extends AppCompatActivity {

    private ValueEventListener post_listener;
    private ValueEventListener follow_listener;
    private DatabaseReference user_listener;


    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private String name_user;
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

            Intent intent = getIntent();
            key_user = intent.getStringExtra("key_user");
            //name_user = ReferencesHelper.getDatabaseReference().child("users").child(key_user).

            recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(new Adapter(this, posts));

            user_listener = ReferencesHelper.getDatabaseReference().child("users").child(key_user).child("fullname");

            user_listener.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    name_user = dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            follow_listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    posts = new ArrayList<Post>();
                    Integer cont = 0;
                    if(dataSnapshot.exists()){
                        for(DataSnapshot data : dataSnapshot.getChildren()){

                            String temp_user = data.getKey();
                            Log.d("ID_FOLLOW", temp_user+"");
                            posts = new ArrayList<Post>();
                            post_listener = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {


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
                            };
                            ReferencesHelper.getDatabaseReference().child("posts").orderByChild("id_user").equalTo(temp_user).limitToLast(5).addValueEventListener(post_listener);
                            cont++;

                        }
                        Collections.shuffle(posts);
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
            };
            ReferencesHelper.getDatabaseReference().child("users").child(key_user).child("following").addValueEventListener(follow_listener);








            btn_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    date_post = new Date().getTime();



                    key_post = ReferencesHelper.getDatabaseReference().push().getKey();
                    content_post = edt_post.getText().toString();
                    Post post = new Post(content_post, key_user, name_user,date_post);
                    ReferencesHelper.getDatabaseReference().child("posts").child(key_post).setValue(post);

                }
            });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search:
                onSearchRequested();
                break;
            case R.id.txt_sair:
                mAuth.signOut();
                finish();
                return true;
            case R.id.help:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public boolean onSearchRequested() {
        Log.d("ID_BUNDLE", key_user);
        Toast.makeText(TimelineActivity.this, "POS SEARCH ACT", Toast.LENGTH_SHORT).show();
        Bundle appData = new Bundle();
        appData.putString(SearchableActivity.KEY_USER, key_user);
        startSearch(null, false, appData, false);
        Toast.makeText(TimelineActivity.this, "POS SEARCH ACT", Toast.LENGTH_SHORT).show();
        return true;
    }
}
