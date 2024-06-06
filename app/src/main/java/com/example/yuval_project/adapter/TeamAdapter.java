package com.example.yuval_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

    private FirebaseDatabase database;
    private boolean enableClick;
    private boolean showPlayers;
    public TeamAdapter(@NonNull Context context, List<TeamItem> teamItemList) {
        super(context, 0, teamItemList);
        database = FirebaseDatabase.getInstance();
        enableClick = true;
        showPlayers = false;
    }
    public void disableClick(){
        enableClick = false;
    }
    public void showPlayers(){
        this.showPlayers = true;
    }

    @Override
    public View getView(int index, View contentView, ViewGroup group){
        TeamItem teamItem = getItem(index);
        if(contentView == null){
            contentView = LayoutInflater.from(getContext()).inflate(R.layout.team_item, group, false);
        }
        TextView name = contentView.findViewById(R.id.name);
        name.setText(teamItem.getName());
        if(showPlayers){
            ListView playerlist = contentView.findViewById(R.id.teamPlayerList);
            PlayerAdapter adapter = new PlayerAdapter(getContext(), teamItem.getPlayerList(),teamItem);
            if(!enableClick){
                adapter.disableClick();
            }

            playerlist.setAdapter(adapter);
            // by default listview inside a listview will not scroll -force it to scroll
            playerlist.setOnTouchListener(new View.OnTouchListener() {
                // Setting on Touch Listener for handling the touch inside ScrollView
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // Disallow the touch request for parent scroll on touch of child view
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });
        }

        contentView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(!enableClick)return;
                Intent intent = new Intent(getContext(), team.class);
                intent.putExtra("id", teamItem.getId());
                getContext().startActivity(intent);
            }
        });
        Button btnDeleteTeam = contentView.findViewById(R.id.btnDeleteTeam);
        btnDeleteTeam.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(!enableClick)return;
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
        if(!enableClick){
            btnDeleteTeam.setVisibility(View.INVISIBLE);
        }
        return contentView;
    }
}