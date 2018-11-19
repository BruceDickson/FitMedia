package com.fitmedia.fitmedia;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchableActivity extends ListActivity {

    public static final String KEY_USER = "key_user";
    private ValueEventListener follow_listener;
    private ValueEventListener user_follow_listener;

    private List<String> users = new ArrayList<String>();
    private List<String> following = new ArrayList<String>();
    private List<Usuario> users_obj = new ArrayList<Usuario>();

    private ListView list;

    private String key_user;

    private SearchAdapter search_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        list = (ListView) findViewById(android.R.id.list);

        handleIntent(getIntent());


        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            follow_listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    if (dataSnapshot.exists()) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            String temp_user = data.getKey();
                            if(!temp_user.equals(key_user)){
                                users.add(temp_user);
                                users_obj.add(data.getValue(Usuario.class));
                            }
                        }

                    } else {
                        // Toast.makeText(TimelineActivity.this, "Fudeo menô!", Toast.LENGTH_SHORT).show();
                    }
                    System.out.println(users.toString());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            };
            ReferencesHelper.getDatabaseReference().child("users").addValueEventListener(follow_listener);


            Bundle appData = getIntent().getBundleExtra(SearchManager.APP_DATA);
            if (appData != null) {
                key_user = appData.getString(SearchableActivity.KEY_USER);
            }

            user_follow_listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    if (dataSnapshot.exists()) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                            String temp_user = data.getKey();
                            following.add(temp_user);
                        }

                    } else {
                        //Toast.makeText(TimelineActivity.this, "Fudeo menô!", Toast.LENGTH_SHORT).show();
                    }

                    System.out.println(following.toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            };
            ReferencesHelper.getDatabaseReference().child("users").child(key_user).child("following").addValueEventListener(user_follow_listener);



            search_adapter = new SearchAdapter(key_user, following, users, users_obj , SearchableActivity.this);
            list.setAdapter(search_adapter);

        }
    }



    public void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        // call the appropriate detail activity
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    private void doSearch(String queryStr) {
        Toast.makeText(getApplicationContext(), queryStr, Toast.LENGTH_LONG).show();
    }



}
