package com.example.yuval_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.yuval_project.adapter.TeamAdapter;
import com.example.yuval_project.data.AppData;
import com.example.yuval_project.data.model.PlayerItem;
import com.example.yuval_project.data.model.TeamItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.yuval_project.databinding.ActivityTeamsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class teams extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTeamsBinding binding;
    List<TeamItem> teamItemList;

    //firebase
    private FirebaseDatabase database;
    private DatabaseReference firebaseTeams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTeamsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_teams);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        Button btnNewTeam = findViewById(R.id.newteam);
        btnNewTeam.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(teams.this, team.class);
                teams.this.startActivity(intent);
            }
        });

        // read teams from firebase
        firebaseTeams = database.getReference();
        String userId = AppData.getInstance().getUserId();
        firebaseTeams.child("users").child(userId).child("teams").get().
            addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {

                    if(task.isSuccessful()){
                        for(DataSnapshot data :task.getResult().getChildren()){ // for each element in the list getChildren
                            TeamItem t = data.getValue(TeamItem.class);
                            teamItemList.add(t);
                        }

                    }else{
                        Toast.makeText(teams.this, "failed to access firebase", Toast.LENGTH_LONG).show();
                    }
                }
            });
        ListView list = findViewById(R.id.playerList);
        // dummy teams
        teamItemList = new ArrayList<TeamItem>();
        teamItemList.add(new TeamItem("Hapoel Ta"));
        teamItemList.add(new TeamItem("Hapoel Ramat Gan"));
        teamItemList.add(new TeamItem("Hapoel Givataim"));
        teamItemList.add(new TeamItem("Hapoel Peah Tikva"));
        teamItemList.add(new TeamItem("Hapoel Haifa"));
        teamItemList.add(new TeamItem("Hapoel Jerusalem"));

        ListView teamlist = findViewById(R.id.teamlist);
        TeamAdapter adapter = new TeamAdapter(this, teamItemList);
        teamlist.setAdapter(adapter);
        /*
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

         */
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_teams);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


}