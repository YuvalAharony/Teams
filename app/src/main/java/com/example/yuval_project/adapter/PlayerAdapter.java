package com.example.yuval_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.ArrayList;

import androidx.annotation.NonNull;

import com.example.yuval_project.R;
import com.example.yuval_project.data.model.PlayerItem;

import java.util.List;

public class PlayerAdapter extends ArrayAdapter<PlayerItem> {
    private List<PlayerItem> selectedPlayers;
    public PlayerAdapter(@NonNull Context context, List<PlayerItem> playerList) {
        super(context, 0, playerList);
        this.selectedPlayers = new ArrayList<PlayerItem>();
    }
    public List<PlayerItem> getSelectedPlayers(){
        return selectedPlayers;
    }
    @Override
    public View getView(int index, View contentView, ViewGroup group){
        PlayerItem player = getItem(index);
        if(contentView == null){
            contentView = LayoutInflater.from(getContext()).inflate(R.layout.player_item, group, false);
        }
        TextView name = contentView.findViewById(R.id.name);
        TextView grade = contentView.findViewById(R.id.grade);
        CheckBox chkSelect = contentView.findViewById(R.id.chkSelect);
        name.setText(player.getName());
        grade.setText(player.getGrade());

        contentView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(chkSelect.isChecked()){
                    selectedPlayers.add(player);
                }else{
                    selectedPlayers.remove(player);
                }
            }
        });
        return contentView;
    }



    // TODO on click on checkbox add the player to a list
}