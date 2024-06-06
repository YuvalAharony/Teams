package com.example.yuval_project;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.yuval_project.adapter.PlayerAdapter;
import com.example.yuval_project.adapter.TeamAdapter;
import com.example.yuval_project.data.AppData;
import com.example.yuval_project.data.model.PlayerItem;
import com.example.yuval_project.data.model.TeamItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FinalTeamsActivity extends AppCompatActivity {

    private AppData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_final_teams);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        data = AppData.getInstance();
        Map<String, List<PlayerItem>> teamMap = data.getSelectedTeamMap();
        List<TeamItem> teams = new ArrayList<TeamItem>();
        for(String teamName : teamMap.keySet()){
            ArrayList<PlayerItem> players = (ArrayList<PlayerItem>)teamMap.get(teamName);
            TeamItem team = new TeamItem(teamName, "", players);
            teams.add(team);
        }
        ListView teamlist = findViewById(R.id.teamList);
        TeamAdapter adapter = new TeamAdapter(this,teams);
        adapter.disableClick();
        teamlist.setAdapter(adapter);

    }
}