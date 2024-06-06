package com.example.yuval_project;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuval_project.adapter.PlayerAdapter;
import com.example.yuval_project.data.AppData;
import com.example.yuval_project.data.model.PlayerItem;
import com.example.yuval_project.data.model.TeamItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class team extends AppCompatActivity implements View.OnClickListener{

    //private List<PlayerItem> playerList;
    private EditText txtNumOfGroups;
    private EditText txtGroupName;
    private PlayerAdapter playerAdapter;
    private TeamItem teamItem;

    // firebase
    private FirebaseDatabase database;
    private DatabaseReference firebaseTeams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        // initialize connection to firebase realtime
        database= FirebaseDatabase.getInstance();

        // if this is existing team update
        String teamId = getIntent().getStringExtra("id");
        if(teamId != null){
            teamItem = AppData.getInstance().findTeamById(teamId);
            //playerList = teamItem.getPlayerList();
        }else{
            teamItem = new TeamItem();
        }

        ListView playerListView = findViewById(R.id.playerList);

        //TODO get list of playes from intent or database / singletone

        playerAdapter = new PlayerAdapter(this, teamItem.getPlayerList());
        playerListView.setAdapter(playerAdapter);

        ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            playerAdapter.notifyDataSetChanged();
        });

        Button btnAddPlayer = findViewById(R.id.btn_add_player);
        btnAddPlayer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                boolean teamExists = false;
                if(teamItem.getId().equals("")){    // add new team
                    teamExists = createTeam();
                }else{
                    teamExists = true;
                }
                if(!teamExists)return;
                Intent intent = new Intent(team.this, player.class);
                intent.putExtra("teamId", teamItem.getId());
                activityLauncher.launch(intent);
            }
        });


        playerAdapter.notifyDataSetChanged();

        txtNumOfGroups = findViewById(R.id.txtNumOfGroups);
        txtGroupName = findViewById(R.id.txtGroupName);
        txtGroupName.setText(teamItem.getName());

        Button btnCreateGroups = findViewById(R.id.btnCreateGroups);
        btnCreateGroups.setOnClickListener(this);

        Button btnSaveGroup = findViewById(R.id.btnSaveGroup);
        btnSaveGroup.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnCreateGroups){
            if(txtNumOfGroups.getText().length() == 0){
                Toast.makeText(team.this, "Please add number of groups", Toast.LENGTH_LONG).show();
                return;
            }
            int numOfGroups = Integer.parseInt(txtNumOfGroups.getText().toString());
            if(playerAdapter.getSelectedPlayers().size()  == 0){
                Toast.makeText(team.this, "Please selected players", Toast.LENGTH_LONG).show();
                return;
            }
            if(playerAdapter.getSelectedPlayers().size() % numOfGroups != 0){
                Toast.makeText(team.this, "Groups don't split equally", Toast.LENGTH_LONG).show();
                return;
            }
            List selectedPlayers = new ArrayList(playerAdapter.getSelectedPlayers());
            AppData.getInstance().createTeams(selectedPlayers, numOfGroups);
            //TODO create activity to show the groups
            Intent intent = new Intent(team.this, FinalTeamsActivity.class);
            startActivity(intent);
        }else if(view.getId() == R.id.btnSaveGroup){
            String name = txtGroupName.getText().toString();
            firebaseTeams = database.getReference();
            String userId = AppData.getInstance().getUserId();
            if(teamItem.getId().equals("")){    // add new team
               createTeam();
            }else{  // update existing team
                DatabaseReference t = firebaseTeams.child("users")
                        .child(userId).
                        child("teams")
                        .child(teamItem.getId());
                teamItem.setName(name);
                t.setValue(teamItem);
                finish();
            }

            AppData.getInstance().addTeam(teamItem);
        }
    }

    private boolean createTeam(){
        String name = txtGroupName.getText().toString();
        if(name == null || name.length() == 0){
            Toast.makeText(this, "please enter team name", Toast.LENGTH_LONG).show();
            return false;
        }
        firebaseTeams = database.getReference();
        String userId = AppData.getInstance().getUserId();

        DatabaseReference t = firebaseTeams.child("users").child(userId).child("teams").push();
        teamItem.setId(t.getKey());
        teamItem.setName(name);
        t.setValue(teamItem);
        AppData.getInstance().addTeam(teamItem);
        return true;
    }
}
