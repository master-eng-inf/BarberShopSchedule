package com.udl.bss.barbershopschedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.udl.bss.barbershopschedule.database.Users.UsersSQLiteManager;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button login_btn = findViewById(R.id.login_button);
        Button guest_btn = findViewById(R.id.guest_btn);
        Button register_btn = findViewById(R.id.register_button);

        final EditText username_et = findViewById(R.id.username_et);
        final EditText password_et = findViewById(R.id.password_et);

        final UsersSQLiteManager usm = new UsersSQLiteManager(getApplicationContext());


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usm.isBarberRegistered(username_et.getText().toString(),
                        password_et.getText().toString())) {
                    startActivityMode("Barber");
                } else if (usm.isUserRegistered(username_et.getText().toString(),
                        password_et.getText().toString())) {
                    startActivityMode("User");
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid user or password",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        guest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("user", "");
                startActivity(intent);
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

    private void startActivityMode(String mode) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra("user", mode);
        startActivity(intent);
        finish();
    }
}
