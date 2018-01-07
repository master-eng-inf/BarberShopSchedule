package com.udl.bss.barbershopschedule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.udl.bss.barbershopschedule.database.Users.UsersSQLiteManager;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.Client;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button login_btn = findViewById(R.id.login_button);
        Button register_btn = findViewById(R.id.register_button);

        final EditText username_et = findViewById(R.id.username_et);
        final EditText password_et = findViewById(R.id.password_et);

        mPrefs = getSharedPreferences("USER", MODE_PRIVATE);

        if (mPrefs.contains("user") && mPrefs.contains("mode")) {
            Gson gson = new Gson();
            String json = mPrefs.getString("user", "");
            String mode = mPrefs.getString("mode", "");
            if (mode.equals("Barber")) {
                Barber barber = gson.fromJson(json, Barber.class);
                logIn(barber.getName(), barber.getPassword());
            } else {
                Client client = gson.fromJson(json, Client.class);
                logIn(client.getName(), client.getPassword());
            }

        }


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn(username_et.getText().toString(), password_et.getText().toString());
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void logIn (String username, String password) {
        APIController.getInstance().logInUser(username, password)
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        try {
                            if (task.getResult() != null) {
                                Gson gson = new Gson();
                                JSONObject json = new JSONObject(task.getResult());
                                if (json.has("places_id")) {
                                    Barber barber = new Barber(
                                            json.getInt("id"),
                                            json.getString("name"),
                                            json.getString("email"),
                                            json.getString("places_id"),
                                            json.getString("password"),
                                            json.getString("telephone"),
                                            json.getString("gender"),
                                            json.getString("description"),
                                            json.getString("address"),
                                            json.getString("city"),
                                            null
                                    );
                                    barber.setToken(json.getString("token"));

                                    startActivityMode("Barber", gson.toJson(barber));
                                } else {
                                    Client client = new Client(
                                            json.getInt("id"),
                                            json.getString("name"),
                                            json.getString("email"),
                                            json.getString("password"),
                                            json.getString("telephone"),
                                            json.getInt("gender"),
                                            json.getInt("age"),
                                            null
                                    );
                                    client.setToken(json.getString("token"));

                                    startActivityMode("User", gson.toJson(client));
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid user or password",
                                        Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    private void startActivityMode(String mode, String user) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        //intent.putExtra("user", mode);

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("user", user);
        prefsEditor.putString("mode", mode);
        prefsEditor.apply();

        startActivity(intent);
        finish();
    }

}
