package com.example.yuval_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.yuval_project.data.AppData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    private static final String EMAIL = "EMAIL";
    private static final String PASSWORD = "PASSWORD";
    TextView tvTimer;
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
        tvTimer = findViewById(R.id.tvTimer);
        tvTimer.setText("5");

        SharedPreferences shared = this.getSharedPreferences("com.example.yuval_project", Context.MODE_PRIVATE);
        String email = shared.getString(EMAIL, "");
        String pass = shared.getString(PASSWORD, "");
        if(email.length() > 0 && pass.length() > 0){
            username.setText(email);
            password.setText(pass);
        }

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
                                SharedPreferences shared = login.this.getSharedPreferences("com.example.yuval_project", Context.MODE_PRIVATE);
                                shared.edit().putString(EMAIL, email).apply();
                                shared.edit().putString(PASSWORD, pass).apply();
                                AppData.getInstance().setUserId(auth.getUid());
                                new CountDownTimer(5000, 1000){

                                    @Override
                                    public void onTick(long l) {
                                        tvTimer.setText(l/1000 + "");
                                    }

                                    @Override
                                    public void onFinish() {

                                        Intent intent = new Intent(login.this, teams.class);
                                        login.this.startActivity(intent);
                                        login.this.finish();
                                    }
                                }.start();

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