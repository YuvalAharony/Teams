package com.example.yuval_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.yuval_project.R;
import com.example.yuval_project.data.AppData;
import com.example.yuval_project.data.model.PlayerItem;
import com.example.yuval_project.data.model.TeamItem;
import com.example.yuval_project.player;
import com.example.yuval_project.team;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends ArrayAdapter<TeamItem> {

    FirebaseDatabase database;
    public TeamAdapter(@NonNull Context context, List<TeamItem> teamItemList) {
        super(context, 0, teamItemList);
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public View getView(int index, View contentView, ViewGroup group){
        TeamItem teamItem = getItem(index);
        if(contentView == null){
            contentView = LayoutInflater.from(getContext()).inflate(R.layout.team_item, group, false);
        }
        TextView name = contentView.findViewById(R.id.name);
        name.setText(teamItem.getName());
        //ListView playerItemList = contentView.findViewById(R.id.playerlist);
        //PlayerAdapter playerAdapter = new PlayerAdapter(getContext(), playerList);
        //playerItemList.setAdapter(playerAdapter);
        contentView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), team.class);
                intent.putExtra("id", teamItem.getId());
                getContext().startActivity(intent);
            }
        });
        Button btnDeleteTeam = contentView.findViewById(R.id.btnDeleteTeam);
        btnDeleteTeam.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String userId = AppData.getInstance().getUserId();
                DatabaseReference teamRef = database.getReference().child("users").child(userId).child("teams").child(teamItem.getId());
               teamRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       Toast.makeText(getContext(), "Team " + teamItem.getName() + " was deleted", Toast.LENGTH_LONG).show();
                       AppData.getInstance().removeTeam(teamItem);
                       notifyDataSetChanged();
                   }
               });
            }
        });
        return contentView;
    }
}