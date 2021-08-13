package com.example.trackmybus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    EditText mfull, mMail, Mobileno;
    Button mSavebtn;
    ProgressBar progress;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID;
    public static final String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mfull = findViewById(R.id.inputName);
        mMail = findViewById(R.id.inputEmail);
        Mobileno = findViewById(R.id.inputMobile);
        mSavebtn = findViewById(R.id.update);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mSavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mMail.getText().toString().trim();
                String fullname = mfull.getText().toString().trim();
                String mob = Mobileno.getText().toString().trim();
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");
                userID = fAuth.getCurrentUser().getUid();
                fAuth.getCurrentUser().updateEmail(email);
                DocumentReference documentReference = fStore.collection("users").document(userID);
                Map<String, Object> user = new HashMap<>();
                user.put("fName", fullname);
                user.put("email", email);
                user.put("phone", mob);
                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditProfile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this, "Erron in Profile Updation", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: "+ e.toString());
                    }
                });
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
            startActivity(new Intent(getApplicationContext(),Profile.class));
            overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

        }
        return super.onKeyDown(keyCode, event);
    }
}