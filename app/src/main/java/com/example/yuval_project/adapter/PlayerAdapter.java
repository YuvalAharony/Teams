package com.example.yuval_project.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.yuval_project.R;
import com.example.yuval_project.data.AppData;
import com.example.yuval_project.data.model.PlayerItem;
import com.example.yuval_project.data.model.TeamItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerAdapter extends ArrayAdapter<PlayerItem> {
    private Set<PlayerItem> selectedPlayers;
    private boolean enableClick;
    private FirebaseDatabase database;
    private TeamItem team;
    public PlayerAdapter(@NonNull Context context, List<PlayerItem> playerList, TeamItem team) {
        super(context, 0, playerList);
        this.selectedPlayers = new HashSet<>();
        this.enableClick = true;
        database = FirebaseDatabase.getInstance();
        this.team = team;
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
        Button btnDeletePlayer = contentView.findViewById(R.id.btnDeletePlayer);
        chkSelect.setChecked(false);

        name.setText(player.getName());
        grade.setText(player.getGrade()+" ");

        if(!enableClick){
            chkSelect.setVisibility(View.INVISIBLE);
        }
        if(selectedPlayers.contains(player)){
            chkSelect.setChecked(true);
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

        if(!enableClick){
            btnDeletePlayer.setVisibility(View.INVISIBLE);
        }
        btnDeletePlayer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(!enableClick)return;
                String userId = AppData.getInstance().getUserId();
                DatabaseReference teamRef = database.getReference().child("users").child(userId).child("teams").child(team.getId());
                team.getPlayerList().remove(player);
                teamRef.setValue(team);
                notifyDataSetChanged();
            }

        });

        return contentView;
    }




}