package com.fitmedia.fitmedia;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class SearchAdapter extends BaseAdapter {

    List<String> following;
    List<String> users;
    List<Usuario> users_obj;
    private String key_user;
    private final Activity activity;
    private String[] types = {"Atleta", "Nutricionista", "Ed Físico"};

    public SearchAdapter(String key_user, List<String> following, List<String> users, List<Usuario> users_obj ,Activity activity){
        this.key_user = key_user;
        this.following = following;
        this.users = users;
        this.users_obj = users_obj;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return this.users_obj.size();
    }

    @Override
    public Object getItem(int position) {
        return this.users_obj.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String current_user = users.get(position);
        View v = activity.getLayoutInflater().inflate(R.layout.item_follow, null);
        TextView txt_follow = (TextView) v.findViewById(R.id.txt_follow);
        TextView txt_type_follow = (TextView) v.findViewById(R.id.txt_type_follow);
        final Button btn_follow = (Button) v.findViewById(R.id.btn_follow);
        Log.e("ADAPT", users_obj.get(position).getFullname());
        txt_follow.setText(users_obj.get(position).getFullname());
        txt_type_follow.setText(types[users_obj.get(position).getType()]);

        for(String str : following){
            if(str.trim().contains(users.get(position))){
                btn_follow.setText("SEGUINDO");
                btn_follow.setActivated(false);
            }
        }

        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReferencesHelper.getDatabaseReference().child("users").child(key_user).child("following").child(current_user).setValue(0);
                btn_follow.setText("SEGUINDO");
                btn_follow.setActivated(false);
            }
        });

        return v;
    }
}
