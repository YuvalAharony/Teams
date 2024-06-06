package com.example.yuval_project;

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

    private List<PlayerItem> playerList;
    private EditText txtNumOfGroups;
    private EditText txtGroupName;
    private PlayerAdapter playerAdapter;

    // firebase
    private FirebaseDatabase database;
    private DatabaseReference firebaseTeams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        // initialize connection to firebase realtime
        database= FirebaseDatabase.getInstance();

        ListView playerListView = findViewById(R.id.playerList);
        playerList = AppData.getInstance().getPlayerList("Hapoel");

        //TODO get list of playes from intent or database / singletone

        playerAdapter = new PlayerAdapter(this, playerList);
        playerListView.setAdapter(playerAdapter);

        Button btnAddPlayer = findViewById(R.id.btn_add_player);
        btnAddPlayer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(team.this, player.class);
                startActivity(intent);
            }
        });

        txtNumOfGroups = findViewById(R.id.txtNumOfGroups);
        txtGroupName = findViewById(R.id.txtGroupName);


        Button btnCreateGroups = findViewById(R.id.btnCreateGroups);
        btnCreateGroups.setOnClickListener(this);

        Button btnSaveGroup = findViewById(R.id.btnSaveGroup);
        btnSaveGroup.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnCreateGroups){
            if(txtNumOfGroups.getText().length() == 0){
                Toast.makeText(team.this, "Please add number of groups", Toast.LENGTH_LONG);
                return;
            }
            int numOfGroups = Integer.parseInt(txtNumOfGroups.getText().toString());
            if(playerAdapter.getSelectedPlayers().size() % numOfGroups != 0){
                Toast.makeText(team.this, "Groups don't split equally", Toast.LENGTH_LONG);
                return;
            }
            AppData.getInstance().createTeams(playerAdapter.getSelectedPlayers(), numOfGroups);
            //TODO create activity to show the groups
            //Intent intent = new Intent(team.this, player.class);
            //startActivity(intent);
        }else if(view.getId() == R.id.btnSaveGroup){
            String name = txtGroupName.getText().toString();
            firebaseTeams = database.getReference();
            String userId = AppData.getInstance().getUserId();
            DatabaseReference t = firebaseTeams.child("users").child(userId).child("teams").push();

            TeamItem team = new TeamItem(name, t.getKey());
            t.setValue(team);
        }
    }
}
