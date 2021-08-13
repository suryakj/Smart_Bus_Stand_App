package com.example.trackmybus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {
    TextView name;
    FirebaseAuth auth;
    FirebaseFirestore fstore;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.Name);
        auth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = auth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
                name.setText(value.getString("fName"));
            }
        });
    }
    public void gotoProfile(View view)
    {
        finish();
        startActivity(new Intent(getApplicationContext(), Profile.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
    public void gotoTrack(View view)
    {
        finish();
        startActivity(new Intent(getApplicationContext(), TrackBus.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
    public void gotoParking(View view)
    {
        finish();
        startActivity(new Intent(getApplicationContext(), ParkingDash.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

}