package com.example.trackmybus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.firestore.local.LocalWriteResult;
import com.google.firestore.v1.WriteResult;

import java.util.HashMap;
import java.util.Map;

public class Booking extends AppCompatActivity {
    FirebaseFirestore fstore;
    Button confirmButton;
    TextView fname,phoneno,slotselected,duration,datetime;
    FirebaseAuth fauth;
    String name,phone,dura,slot,da,ye,mo,ti,mi,dateandtime;
    String userId;
    public boolean bookingok = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        confirmButton = findViewById(R.id.confirm);
        fname = findViewById(R.id.name);
        phoneno = findViewById(R.id.phone);
        slotselected = findViewById(R.id.selectedslot);
        duration = findViewById(R.id.duration);
        datetime = findViewById(R.id.dateandtime);
        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = fauth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                name = documentSnapshot.getString("fName");
                phone = documentSnapshot.getString("phone");
                fname.setText(name);
                phoneno.setText(phone);
            }
        });
        Intent getData = getIntent();
        dura = getData.getStringExtra("Duration");
        slot = getData.getStringExtra("slot");
        da = getData.getStringExtra("Date");
        mo = getData.getStringExtra("Month");
        ye = getData.getStringExtra("Year");
        ti = getData.getStringExtra("Hour");
        mi = getData.getStringExtra("Minute");
        dateandtime = da+"/"+mo+"/"+ye+"--"+ti+":"+mi;
        slotselected.setText(slot);
        duration.setText(dura);
        datetime.setText(dateandtime);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> userData = new HashMap<String, Object>();
                userData.put("name", name);
                userData.put("phone", phone);
                userData.put("slot", slot);
                userData.put("date", dateandtime);
                userData.put("duration", dura);
                userData.put("status", "Booked");
                DocumentReference newRef = fstore.collection("BookingDetails").document(phone);
                newRef.set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            String DocID = "";
                            if(slot.equals("Slot11"))
                            {
                                DocID = "ExtraSlot";
                            }
                            else
                            {
                                DocID = "SlotAvailability";
                            }
                            DocumentReference slotref = fstore.collection("Parking").document(DocID);
                            slotref.update(slot, "UnAvailable").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Booking.this, "Slot Booked Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Booking.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(Booking.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                updateCount();
                DocumentReference foradmin = fstore.collection("ForAdmin").document(slotselected.getText().toString());
                foradmin.update("phone",phoneno.getText().toString());
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),ParkingBook.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
    public void updateCount()
    {
        DocumentReference countsuc = fstore.collection("BookingDetails").document("BookingCount");
        countsuc.update("SuccessCount", FieldValue.increment(1));
    }
}