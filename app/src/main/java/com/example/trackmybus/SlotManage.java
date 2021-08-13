package com.example.trackmybus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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
import com.google.firebase.firestore.Source;

public class SlotManage extends AppCompatActivity {
    FirebaseFirestore fstore;
    DocumentReference getdata,bookdata,delslot,deleteRef,slotavail;
    AlertDialog.Builder builder;
    TextView ph;
    String status = "not changed";
    String slot1,slot2,slot3,slot4,slot5,slot6,slot7,slot8,slot9,phone,phoneno;
    ConstraintLayout t1,t2,t3,t4,t5,t6,t7,t8,t9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_manage);
        t1 = findViewById(R.id.s1);
        t2 = findViewById(R.id.s2);
        t3 = findViewById(R.id.s3);
        t4 = findViewById(R.id.s4);
        t5 = findViewById(R.id.s5);
        t6 = findViewById(R.id.s6);
        t7 = findViewById(R.id.s7);
        t8 = findViewById(R.id.s8);
        t9 = findViewById(R.id.s9);
        ph = findViewById(R.id.textph);
        fstore = FirebaseFirestore.getInstance();
        t1.setClickable(false);
        t2.setClickable(false);
        t3.setClickable(false);
        t4.setClickable(false);
        t5.setClickable(false);
        t6.setClickable(false);
        t7.setClickable(false);
        t8.setClickable(false);
        t9.setClickable(false);
        DocumentReference documentReference = fstore.collection("Parking").document("SlotAvailability");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    slot1 = documentSnapshot.getString("Slot1");
                    slot2 = documentSnapshot.getString("Slot2");
                    slot3 = documentSnapshot.getString("Slot3");
                    slot4 = documentSnapshot.getString("Slot4");
                    slot5 = documentSnapshot.getString("Slot5");
                    slot6 = documentSnapshot.getString("Slot6");
                    slot7 = documentSnapshot.getString("Slot7");
                    slot8 = documentSnapshot.getString("Slot8");
                    slot9 = documentSnapshot.getString("Slot9");
                    if (slot1.equals("UnAvailable")) {
                        t1.setClickable(true);
                        t1.setBackgroundColor(Color.RED);
                    }
                    if (slot2.equals("UnAvailable")) {
                        t2.setClickable(true);
                        t2.setBackgroundColor(Color.RED);
                    }
                    if (slot3.equals("UnAvailable")) {
                        t3.setClickable(true);
                        t3.setBackgroundColor(Color.RED);
                    }
                    if (slot4.equals("UnAvailable")) {
                        t4.setClickable(true);
                        t4.setBackgroundColor(Color.RED);
                    }
                    if (slot5.equals("UnAvailable")) {
                        t5.setClickable(true);
                        t5.setBackgroundColor(Color.RED);
                    }
                    if (slot6.equals("UnAvailable")) {
                        t6.setClickable(true);
                        t6.setBackgroundColor(Color.RED);
                    }
                    if (slot7.equals("UnAvailable")) {
                        t7.setClickable(true);
                        t7.setBackgroundColor(Color.RED);
                    }
                    if (slot8.equals("UnAvailable")) {
                        t8.setClickable(true);
                        t8.setBackgroundColor(Color.RED);
                    }
                    if (slot9.equals("UnAvailable")) {
                        t9.setClickable(true);
                        t9.setBackgroundColor(Color.RED);
                    }
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),AdminDashboard.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
    public void slot1del(View view)
    {
        getdata = fstore.collection("ForAdmin").document("Slot1");
        getdata.addSnapshotListener(SlotManage.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e == null){
                    phoneno = documentSnapshot.getString("phone");
                ph.setText(phoneno);
                bookdata = fstore.collection("BookingDetails").document(ph.getText().toString());
                bookdata.addSnapshotListener(SlotManage.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        String name = documentSnapshot.getString("name");
                        phone = documentSnapshot.getString("phone");
                        String durtion = documentSnapshot.getString("duration");
                        String CancelMsg = "Do you want to Cancel this booking?";
                        String Data = "Name : " + name + "\n" + "Phone : " + phone + "\n" + "Duration : " + durtion + "\n\t\t" + CancelMsg;
                        builder = new AlertDialog.Builder(SlotManage.this);
                        builder.setMessage(Data)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        delslot = fstore.collection("ForAdmin").document("Slot1");
                                        delslot.update("phone", "");
                                        slotavail = fstore.collection("Parking").document("SlotAvailability");
                                        slotavail.update("Slot1", "Available");
                                        deleteRef = fstore.collection("BookingDetails").document(phone);
                                        status = "changed";
                                        Toast.makeText(SlotManage.this, "Cancelled Successfully", Toast.LENGTH_SHORT).show();
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
            }
        });
        if(status.equals("changed")){
            DocumentReference delete1 = fstore.collection("BookingDetails").document(ph.getText().toString());
            delete1.delete()
                    .addOnCompleteListener(SlotManage.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(getApplicationContext(),SlotManage.class));
                            finish();
                        }
                    });
        }
    }

    public void deleteData(){
        deleteRef.delete().addOnCompleteListener(SlotManage.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(SlotManage.this, "Cancelled Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void slot2del(View view)
    {
        getdata = fstore.collection("ForAdmin").document("Slot2");
        getdata.addSnapshotListener(SlotManage.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e == null){
                phoneno = documentSnapshot.getString("phone");
                ph.setText(phoneno);
                bookdata = fstore.collection("BookingDetails").document(ph.getText().toString());
                bookdata.addSnapshotListener(SlotManage.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        String name = documentSnapshot.getString("name");
                        phone = documentSnapshot.getString("phone");
                        String durtion = documentSnapshot.getString("duration");
                        String CancelMsg = "Do you want to Cancel this booking?";
                        String Data = "Name : " + name + "\n" + "Phone : " + phone + "\n" + "Duration : " + durtion + "\n\t\t" + CancelMsg;
                        builder = new AlertDialog.Builder(SlotManage.this);
                        builder.setMessage(Data)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        delslot = fstore.collection("ForAdmin").document("Slot2");
                                        delslot.update("phone", "");
                                        slotavail = fstore.collection("Parking").document("SlotAvailability");
                                        slotavail.update("Slot2", "Available");
                                        status = "changed";
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
            }
        });
    }
    public void slot3del(View view)
    {
        getdata = fstore.collection("ForAdmin").document("Slot3");
        getdata.addSnapshotListener(SlotManage.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e == null) {
                    phoneno = documentSnapshot.getString("phone").toString();
                    ph.setText(phoneno);
                    bookdata = fstore.collection("BookingDetails").document(ph.getText().toString());
                    bookdata.addSnapshotListener(SlotManage.this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            String name = documentSnapshot.getString("name");
                            phone = documentSnapshot.getString("phone");
                            String durtion = documentSnapshot.getString("duration");
                            String CancelMsg = "Do you want to Cancel this booking?";
                            String Data = "Name : " + name + "\n" + "Phone : " + phone + "\n" + "Duration : " + durtion + "\n\t\t" + CancelMsg;
                            builder = new AlertDialog.Builder(SlotManage.this);
                            builder.setMessage(Data)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            delslot = fstore.collection("ForAdmin").document("Slot3");
                                            delslot.update("phone", "");
                                            slotavail = fstore.collection("Parking").document("SlotAvailability");
                                            slotavail.update("Slot3", "Available");
                                            status = "changed";
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
            }
        });
    }
    public void slot4del(View view)
    {
        getdata = fstore.collection("ForAdmin").document("Slot4");
        getdata.addSnapshotListener(SlotManage.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e == null) {
                    phoneno = documentSnapshot.getString("phone");
                    ph.setText(phoneno);
                    bookdata = fstore.collection("BookingDetails").document(ph.getText().toString());
                    bookdata.addSnapshotListener(SlotManage.this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            String name = documentSnapshot.getString("name");
                            phone = documentSnapshot.getString("phone");
                            String durtion = documentSnapshot.getString("duration");
                            String CancelMsg = "Do you want to Cancel this booking?";
                            String Data = "Name : " + name + "\n" + "Phone : " + phone + "\n" + "Duration : " + durtion + "\n\t\t" + CancelMsg;
                            builder = new AlertDialog.Builder(SlotManage.this);
                            builder.setMessage(Data)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            delslot = fstore.collection("ForAdmin").document("Slot4");
                                            delslot.update("phone", "");
                                            slotavail = fstore.collection("Parking").document("SlotAvailability");
                                            slotavail.update("Slot4", "Available");
                                            status = "changed";
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
            }
        });
    }
    public void slot5del(View view)
    {
        getdata = fstore.collection("ForAdmin").document("Slot5");
        getdata.addSnapshotListener(SlotManage.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e == null) {
                    phoneno = documentSnapshot.getString("phone");
                    ph.setText(phoneno);
                    bookdata = fstore.collection("BookingDetails").document(ph.getText().toString());
                    bookdata.addSnapshotListener(SlotManage.this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            String name = documentSnapshot.getString("name");
                            phone = documentSnapshot.getString("phone");
                            String durtion = documentSnapshot.getString("duration");
                            String CancelMsg = "Do you want to Cancel this booking?";
                            String Data = "Name : " + name + "\n" + "Phone : " + phone + "\n" + "Duration : " + durtion + "\n\t\t" + CancelMsg;
                            builder = new AlertDialog.Builder(SlotManage.this);
                            builder.setMessage(Data)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            delslot = fstore.collection("ForAdmin").document("Slot5");
                                            delslot.update("phone", "");
                                            slotavail = fstore.collection("Parking").document("SlotAvailability");
                                            slotavail.update("Slot5", "Available");
                                            status = "changed";
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
            }
        });
    }
    public void slot6del(View view)
    {
        getdata = fstore.collection("ForAdmin").document("Slot6");
        getdata.addSnapshotListener(SlotManage.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e == null) {
                    phoneno = documentSnapshot.getString("phone");
                    ph.setText(phoneno);
                    bookdata = fstore.collection("BookingDetails").document(ph.getText().toString());
                    bookdata.addSnapshotListener(SlotManage.this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            String name = documentSnapshot.getString("name");
                            phone = documentSnapshot.getString("phone");
                            String durtion = documentSnapshot.getString("duration");
                            String CancelMsg = "Do you want to Cancel this booking?";
                            String Data = "Name : " + name + "\n" + "Phone : " + phone + "\n" + "Duration : " + durtion + "\n\t\t" + CancelMsg;
                            builder = new AlertDialog.Builder(SlotManage.this);
                            builder.setMessage(Data)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            delslot = fstore.collection("ForAdmin").document("Slot6");
                                            delslot.update("phone", "");
                                            slotavail = fstore.collection("Parking").document("SlotAvailability");
                                            slotavail.update("Slot6", "Available");
                                            status = "changed";
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
            }
        });
    }
    public void slot7del(View view)
    {
        getdata = fstore.collection("ForAdmin").document("Slot7");
        getdata.addSnapshotListener(SlotManage.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e == null) {
                    phoneno = documentSnapshot.getString("phone");
                    ph.setText(phoneno);
                    bookdata = fstore.collection("BookingDetails").document(ph.getText().toString());
                    bookdata.addSnapshotListener(SlotManage.this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            String name = documentSnapshot.getString("name");
                            phone = documentSnapshot.getString("phone");
                            String durtion = documentSnapshot.getString("duration");
                            String CancelMsg = "Do you want to Cancel this booking?";
                            String Data = "Name : " + name + "\n" + "Phone : " + phone + "\n" + "Duration : " + durtion + "\n\t\t" + CancelMsg;
                            builder = new AlertDialog.Builder(SlotManage.this);
                            builder.setMessage(Data)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            delslot = fstore.collection("ForAdmin").document("Slot7");
                                            delslot.update("phone", "");
                                            slotavail = fstore.collection("Parking").document("SlotAvailability");
                                            slotavail.update("Slot7", "Available");
                                            status = "changed";
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
            }
        });
    }
    public void slot8del(View view)
    {
        getdata = fstore.collection("ForAdmin").document("Slot8");
        getdata.addSnapshotListener(SlotManage.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e == null) {
                    phoneno = documentSnapshot.getString("phone");
                    ph.setText(phoneno);
                    bookdata = fstore.collection("BookingDetails").document(ph.getText().toString());
                    bookdata.addSnapshotListener(SlotManage.this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            String name = documentSnapshot.getString("name");
                            phone = documentSnapshot.getString("phone");
                            String durtion = documentSnapshot.getString("duration");
                            String CancelMsg = "Do you want to Cancel this booking?";
                            String Data = "Name : " + name + "\n" + "Phone : " + phone + "\n" + "Duration : " + durtion + "\n\t\t" + CancelMsg;
                            builder = new AlertDialog.Builder(SlotManage.this);
                            builder.setMessage(Data)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            delslot = fstore.collection("ForAdmin").document("Slot8");
                                            delslot.update("phone", "");
                                            slotavail = fstore.collection("Parking").document("SlotAvailability");
                                            slotavail.update("Slot8", "Available");
                                            status = "changed";
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
            }
        });
    }
    public void slot9del(View view)
    {
        getdata = fstore.collection("ForAdmin").document("Slot9");
        getdata.addSnapshotListener(SlotManage.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e == null) {
                    phoneno = documentSnapshot.getString("phone");
                    ph.setText(phoneno);
                    bookdata = fstore.collection("BookingDetails").document(ph.getText().toString());
                    bookdata.addSnapshotListener(SlotManage.this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            String name = documentSnapshot.getString("name");
                            phone = documentSnapshot.getString("phone");
                            String durtion = documentSnapshot.getString("duration");
                            String CancelMsg = "Do you want to Cancel this booking?";
                            String Data = "Name : " + name + "\n" + "Phone : " + phone + "\n" + "Duration : " + durtion + "\n\t\t" + CancelMsg;
                            builder = new AlertDialog.Builder(SlotManage.this);
                            builder.setMessage(Data)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            delslot = fstore.collection("ForAdmin").document("Slot9");
                                            delslot.update("phone", "");
                                            slotavail = fstore.collection("Parking").document("SlotAvailability");
                                            slotavail.update("Slot9", "Available");
                                            status = "changed";
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
            }
        });
    }
}
