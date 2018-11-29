package com.fitmedia.fitmedia;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Post> posts;
    private long date_post;
    private String key_comment;
    private String content_comment;
    private ArrayList<Comment> comments;


    public Adapter (Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.costume_row, viewGroup, false);
        Item item = new Item(row);

        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        Log.e("TESTE", posts.get(i).getContent()+"");
        final int position = i;
        ((Item)viewHolder).txt_content.setText(posts.get(i).getContent());
        ((Item)viewHolder).txt_nome.setText(posts.get(i).getName_user());
        DateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        ((Item)viewHolder).txt_data.setText(f.format(new Date(posts.get(i).getDate())));
        ((Item)viewHolder).card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueEventListener comment_listener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        comments = new ArrayList<>();
                        if(dataSnapshot.exists()){
                            for(DataSnapshot data : dataSnapshot.getChildren()){
                                Comment comment = data.getValue(Comment.class);
                                if(comment.getId_post() == posts.get(position).getId_post())
                                    comments.add(comment);
                            }
                            Intent intent = new Intent(context, CommentActivity.class);
                            intent.putExtra("comments", comments);
                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                ReferencesHelper.getDatabaseReference().child("comments").addValueEventListener(comment_listener);
            }
        });
        ((Item)viewHolder).btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_post = new Date().getTime();
                if(!((Item)viewHolder).edt_comment.getText().equals("")){
                    key_comment = ReferencesHelper.getDatabaseReference().push().getKey();
                    content_comment = ((Item)viewHolder).edt_comment.getText().toString();
                    Comment comment = new Comment(content_comment, posts.get(position).getId_user(), posts.get(position).getId_post(), posts.get(position).getName_user(), date_post);
                    ReferencesHelper.getDatabaseReference().child("comments").child(key_comment).setValue(comment);

                    ((Item)viewHolder).edt_comment.setText("");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class Item extends RecyclerView.ViewHolder{
        CardView card_view;
        TextView txt_content;
        TextView txt_nome;
        TextView txt_data;
        ImageButton btn_comment;
        EditText edt_comment;
        public Item(@NonNull View itemView) {
            super(itemView);
            card_view = (CardView) itemView.findViewById(R.id.card_view_posts);
            txt_content = (TextView) itemView.findViewById(R.id.txt_content);
            txt_nome = (TextView) itemView.findViewById(R.id.txt_nome);
            txt_data = (TextView) itemView.findViewById(R.id.txt_data2);
            btn_comment = (ImageButton) itemView.findViewById(R.id.btn_comment);
            edt_comment = (EditText) itemView.findViewById(R.id.edt_comment);
        }
    }
}
