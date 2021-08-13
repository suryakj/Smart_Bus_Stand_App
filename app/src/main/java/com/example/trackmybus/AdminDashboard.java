package com.example.trackmybus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class AdminDashboard extends AppCompatActivity {
    TextView name,bookcount,bookcancelled,admintext;
    String nameAdmin;
    FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        name = findViewById(R.id.texname);
        bookcount = findViewById(R.id.bookingcount);
        bookcancelled = findViewById(R.id.bookingcancel);
        Intent getdata = getIntent();
        nameAdmin = getdata.getStringExtra("name");
        name.setText("Welcome, "+nameAdmin);
        fstore = FirebaseFirestore.getInstance();
        DocumentReference count =  fstore.collection("BookingDetails").document("BookingCount");
        count.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Long suc = (Long) documentSnapshot.get("SuccessCount");
                Long canc = (Long) documentSnapshot.get(("CancelCount"));
                bookcount.setText("No of bookings - "+suc);
                bookcancelled.setText("No of booking Cancelled - "+canc);
            }
        });
    }
    public void gotoadmin(View view)
    {
        startActivity(new Intent(getApplicationContext(),AdminPage.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
    public void slotstatus(View view)
    {
        startActivity(new Intent(getApplicationContext(),SlotManage.class));
        finish();
    }
    public void adminlogout(View view)
    {
        startActivity(new Intent(getApplicationContext(),AdminPage.class));
        finish();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),ParkingDash.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}