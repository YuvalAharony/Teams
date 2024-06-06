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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerAdapter extends ArrayAdapter<PlayerItem> {
    private Set<PlayerItem> selectedPlayers;
    private boolean enableClick;
    public PlayerAdapter(@NonNull Context context, List<PlayerItem> playerList) {
        super(context, 0, playerList);
        this.selectedPlayers = new HashSet<>();
        this.enableClick = true;
    }

    public  void disableClick(){
        this.enableClick = false;
    }
    public Set<PlayerItem> getSelectedPlayers(){
        return selectedPlayers;
    }
    @Override
    public View getView(int index, View contentView, ViewGroup group){
        PlayerItem player = getItem(index);
        if(contentView == null){
            contentView = LayoutInflater.from(getContext()).inflate(R.layout.player_item, group, false);
        }
        TextView name = contentView.findViewById(R.id.name);
        TextView grade = contentView.findViewById(R.id.grade1);
        CheckBox chkSelect = contentView.findViewById(R.id.chkSelect);

        name.setText(player.getName());
        grade.setText(player.getGrade()+"");

        if(!enableClick){
            chkSelect.setVisibility(View.INVISIBLE);
        }
        if(chkSelect.isChecked()){
            selectedPlayers.add(player);
        }else{
            selectedPlayers.remove(player);
        }

        chkSelect.setOnClickListener(new View.OnClickListener(){
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