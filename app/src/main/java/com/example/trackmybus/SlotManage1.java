package com.example.trackmybus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class SlotManage1 extends AppCompatActivity {
    FirebaseFirestore fstore;
    String slot10,slot11;
    ConstraintLayout t10,t11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_manage1);
        t10 = findViewById(R.id.s10);
        t11 = findViewById(R.id.s11);
        fstore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = fstore.collection("Parking").document("SlotAvailability");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                slot10 = documentSnapshot.getString("Slot10");
                if(slot10.equals("UnAvailable"))
                {
                    t10.setClickable(false);
                    t10.setBackgroundColor(Color.RED);
                }

            }
        });
        DocumentReference documentReference1 = fstore.collection("Parking").document("ExtraSlot");
        documentReference1.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                slot11 = documentSnapshot.getString("Slot11");
                if(slot11.equals("UnAvailable"))
                {
                    t11.setClickable(false);
                    t11.setBackgroundColor(Color.RED);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),SlotManage.class));
        finish();
    }
}