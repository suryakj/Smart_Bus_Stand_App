package com.example.trackmybus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminPage extends AppCompatActivity {
    EditText user,pass;
    Button adminlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        user = findViewById(R.id.Email);
        pass = findViewById(R.id.Pass);
        adminlogin = findViewById(R.id.buttonLogin);
        adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = user.getText().toString();
                String password = pass.getText().toString();
                if(username.equals("suryakj") && password.equals("625014"))
                {
                    Intent intent = new Intent(getApplicationContext(),AdminDashboard.class);
                    intent.putExtra("name","Surya");
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),ParkingDash.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
}