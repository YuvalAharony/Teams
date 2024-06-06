package com.example.yuval_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.yuval_project.R;
import com.example.yuval_project.data.model.PlayerItem;
import com.example.yuval_project.data.model.TeamItem;

import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends ArrayAdapter<TeamItem> {

    private List<PlayerItem> playerList;
    public TeamAdapter(@NonNull Context context, List<TeamItem> teamItemList) {
        super(context, 0, teamItemList);
        playerList = new ArrayList<PlayerItem>();
        playerList.add(new PlayerItem("Avi Cohen", 8));
        playerList.add(new PlayerItem("Beni Cohen", 8));
        playerList.add(new PlayerItem("Asaf Cohen", 7));
        playerList.add(new PlayerItem("Assaf Cohen", 8));
        playerList.add(new PlayerItem("Rami Cohen", 8));
        playerList.add(new PlayerItem("Guy Cohen", 8));
    }

    @Override
    public View getView(int index, View contentView, ViewGroup group){
        TeamItem team = getItem(index);
        if(contentView == null){
            contentView = LayoutInflater.from(getContext()).inflate(R.layout.team_item, group, false);
        }
        TextView name = contentView.findViewById(R.id.name);
        ListView playerItemList = contentView.findViewById(R.id.playerlist);
        name.setText(team.getName());
        PlayerAdapter playerAdapter = new PlayerAdapter(getContext(), playerList);
        playerItemList.setAdapter(playerAdapter);
        return contentView;
    }
}