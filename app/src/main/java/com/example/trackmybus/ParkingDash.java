package com.example.trackmybus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ParkingDash extends AppCompatActivity {
    FirebaseAuth fauth;
    TextView t1;
    FirebaseFirestore db;
    String userId;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fauth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_parking_dash);
        image = findViewById(R.id.imageView1);
        db = FirebaseFirestore.getInstance();
        userId = fauth.getCurrentUser().getUid();
    }
    public void adminpage(View view)
    {
        DocumentReference checkadmin = db.collection("users").document(userId);
        checkadmin.addSnapshotListener(ParkingDash.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.getString("email").equals("suryak@student.tce.edu"))
                {
                    startActivity(new Intent(getApplicationContext(),AdminPage.class));
                    finish();
                }
                else
                {
                    return;
                }
            }
        });
    }
    public void ParkingBooking(View view)
    {
        startActivity(new Intent(getApplicationContext(), ParkingBook.class));
        finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }


    public void bookingdetails(View view)
    {
        DocumentReference curuser = db.collection("users").document(userId);
        curuser.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String ph = documentSnapshot.getString("phone");
                DocumentReference check = db.collection("BookingDetails").document(ph);
                check.addSnapshotListener(ParkingDash.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if(documentSnapshot.exists())
                        {
                            startActivity(new Intent(getApplicationContext(), BookingDetails.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(ParkingDash.this, "You Have No Booking Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}