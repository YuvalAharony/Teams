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

import com.example.yuval_project.data.AppData;
import com.example.yuval_project.data.model.PlayerItem;

public class player extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        AppData data = AppData.getInstance();

        EditText playerName = findViewById(R.id.editTextTextPersonName);
        EditText grade = findViewById(R.id.editTextTextPersonName2);
        Button btnAdd = findViewById(R.id.btn_save_player);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intGrade = Integer.parseInt(grade.getText().toString());
                PlayerItem player = new PlayerItem(playerName.getText().toString(), intGrade);
                data.addPlayer("Hapoel", player);
                player.this.finish();
            }
        });

    }


}