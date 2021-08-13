package com.example.trackmybus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity {

    TextView fname,emailtext,phone1,changep;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String userId;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        noteRef = db.collection("users").document(userId);
        changep = findViewById(R.id.change);
        fname = findViewById(R.id.name1);
        emailtext = findViewById(R.id.mail);
        phone1 = findViewById(R.id.phone);
        database = FirebaseDatabase.getInstance();
        noteRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    fname.setText(documentSnapshot.getString("fName"));
                    emailtext.setText(documentSnapshot.getString("email"));
                    phone1.setText(documentSnapshot.getString("phone"));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to Fetch Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void Dash(View view)
    {
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
    public void logout(View view)
    {
        finish();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
    public void changep(View view)
    {
        finish();
        startActivity(new Intent(getApplicationContext(), EditProfile.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

        }
        return super.onKeyDown(keyCode, event);
    }
    public void changepass(View view)
    {
        EditText resetPass = new EditText(view.getContext());
        AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
        passwordResetDialog.setTitle("Change Password?");
        passwordResetDialog.setMessage("Enter New Password > 6 characters long.");
        passwordResetDialog.setView(resetPass);
        passwordResetDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newPassword = resetPass.getText().toString();
                FirebaseAuth.getInstance().getCurrentUser().updatePassword(newPassword);
                Toast.makeText(Profile.this, "Now You Can Login with Your New Password", Toast.LENGTH_SHORT).show();
            }
        });
        passwordResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        passwordResetDialog.create().show();
    }
}