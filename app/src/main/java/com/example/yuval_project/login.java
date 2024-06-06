package com.example.yuval_project;

import android.content.Intent;
import android.os.Bundle;

import com.example.yuval_project.data.AppData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.example.yuval_project.databinding.ActivityLogin3Binding;

public class login extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    //private ActivityLogin3Binding binding;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        //binding = ActivityLogin3Binding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

        //setSupportActionBar(binding.toolbar);

        auth = FirebaseAuth.getInstance();
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_login);
       // appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        /*
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
        Button btnLogin = findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                // Login
                String email = username.getText().toString();
                String pass = password.getText().toString();
                auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                AppData.getInstance().setUserId(auth.getUid());
                                Intent intent = new Intent(login.this, teams.class);
                                login.this.startActivity(intent);
                                login.this.finish();
                            }else{
                                System.out.println(task.getException());
                                Toast.makeText(login.this, "Login failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

            }
        });

        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // signup
                String email = username.getText().toString();
                String pass = password.getText().toString();

                if(email.length() == 0){
                    Toast.makeText(login.this, "Please fill email", Toast.LENGTH_LONG).show();
                    return ;
                }
                if(pass.length() == 0){
                    Toast.makeText(login.this, "Please fill password", Toast.LENGTH_LONG).show();
                    return ;
                }
                if(pass.length() < 6){
                    Toast.makeText(login.this, "password mist have a least 6 characters", Toast.LENGTH_LONG).show();
                    return ;
                }
                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                AppData.getInstance().setUserId(auth.getUid());
                                Intent intent = new Intent(login.this, teams.class);
                                login.this.startActivity(intent);
                                login.this.finish();
                            }else{
                                System.out.println(task.getException());
                                Toast.makeText(login.this, "register failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_login);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}