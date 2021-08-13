package com.example.trackmybus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class BookingDetails extends AppCompatActivity {
    FirebaseFirestore fstore;
    FirebaseAuth fauth;
    Button cancelButton;
    String userId,finalCancel;
    TextView fname, phoneno, slotselected, duration, datetime, status, nodata,na,ph1,ss,du,st,timeanddate,texttime;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        fstore = FirebaseFirestore.getInstance();
        fauth = FirebaseAuth.getInstance();
        fname = findViewById(R.id.name);
        cancelButton = findViewById(R.id.cancel);
        phoneno = findViewById(R.id.phone);
        slotselected = findViewById(R.id.selectedslot);
        duration = findViewById(R.id.duration);
        nodata = findViewById(R.id.textView12);
        na = findViewById(R.id.na);
        ph1 = findViewById(R.id.ph);
        ss = findViewById(R.id.ss);
        du = findViewById(R.id.du);
        st = findViewById(R.id.st);
        texttime = findViewById(R.id.textTime);
        timeanddate = findViewById(R.id.time1);
        datetime = findViewById(R.id.DateTime);
        status = findViewById(R.id.status);
        userId = fauth.getCurrentUser().getUid();

        DocumentReference reference = fstore.collection("users").document(userId);
        reference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String ph = documentSnapshot.getString("phone");
                DocumentReference book = fstore.collection("BookingDetails").document(ph);
                book.addSnapshotListener(BookingDetails.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        fname.setText(documentSnapshot.getString("name"));
                        phoneno.setText(documentSnapshot.getString("phone"));
                        slotselected.setText(documentSnapshot.getString("slot"));
                        duration.setText(documentSnapshot.getString("duration"));
                        timeanddate.setText(documentSnapshot.getString("date"));
                        status.setText(documentSnapshot.getString("status"));
                    }
                });
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(BookingDetails.this);
                builder.setMessage("Are sure want to Cancel the Booking?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String slotse = slotselected.getText().toString();
                                DocumentReference upd = fstore.collection("Parking").document("SlotAvailability");
                                upd.update(slotse, "Available")
                                        .addOnCompleteListener(BookingDetails.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                DocumentReference user = fstore.collection("users").document(userId);
                                                user.addSnapshotListener(BookingDetails.this, new EventListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                                        String ph = documentSnapshot.getString("phone");
                                                        DocumentReference delete1 = fstore.collection("BookingDetails").document(ph);
                                                        delete1.delete()
                                                                .addOnCompleteListener(BookingDetails.this, new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        nodata.setVisibility(View.VISIBLE);
                                                                        na.setVisibility(View.INVISIBLE);
                                                                        ph1.setVisibility(View.INVISIBLE);
                                                                        ss.setVisibility(View.INVISIBLE);
                                                                        du.setVisibility(View.INVISIBLE);
                                                                        st.setVisibility(View.INVISIBLE);
                                                                        texttime.setVisibility(View.INVISIBLE);
                                                                        cancelButton.setVisibility(View.INVISIBLE);
                                                                        Toast.makeText(BookingDetails.this, "Slot Cancelled Successfully", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    }
                                                });
                                            }
                                        });
                                updateCount();
                                DocumentReference foradmin = fstore.collection("ForAdmin").document(slotselected.getText().toString());
                                foradmin.update("phone","");
                            }

                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), ParkingDash.class));
        finish();
    }
    public void updateCount()
    {
        DocumentReference countcan = fstore.collection("BookingDetails").document("BookingCount");
        countcan.update("CancelCount", FieldValue.increment(1));
    }
}