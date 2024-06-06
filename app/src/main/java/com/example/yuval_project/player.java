package com.example.yuval_project;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import com.example.yuval_project.data.AppData;
import com.example.yuval_project.data.model.PlayerItem;
import com.example.yuval_project.data.model.TeamItem;

public class player extends AppCompatActivity {

    TeamItem teamItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        AppData data = AppData.getInstance();

        String teamId = getIntent().getStringExtra("teamId");
        teamItem = data.findTeamById(teamId);
        EditText playerName = findViewById(R.id.editTextTextPersonName);
        EditText grade = findViewById(R.id.editTextTextPersonName2);
        Button btnAdd = findViewById(R.id.btn_save_player);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intGrade = -1;
                try{
                    intGrade = Integer.parseInt(grade.getText().toString());
                }catch(NumberFormatException e){
                    Toast.makeText(player.this, "Grade must be a number", Toast.LENGTH_LONG).show();
                    return;
                }

                PlayerItem player = new PlayerItem(playerName.getText().toString(), intGrade);
                teamItem.getPlayerList().add(player);
                data.addPlayer("Hapoel", player);
                player.this.finish();
            }
        });

    }


}