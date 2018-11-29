package com.fitmedia.fitmedia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Post> posts;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Log.e("TESTE", posts.get(i).getContent()+"");

        ((Item)viewHolder).editText.setText(posts.get(i).getContent());
        ((Item)viewHolder).txt_nome.setText(posts.get(i).getId_user());
        ((Item)viewHolder).txt_data.setText(new Date(posts.get(i).getDate()).toString());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class Item extends RecyclerView.ViewHolder{
        EditText editText;
        TextView txt_nome;
        TextView txt_data;
        public Item(@NonNull View itemView) {
            super(itemView);
            editText = (EditText) itemView.findViewById(R.id.edt_texto);
            txt_nome = (TextView) itemView.findViewById(R.id.txt_nome);
            txt_data = (TextView) itemView.findViewById(R.id.txt_data2);
        }
    }
}
