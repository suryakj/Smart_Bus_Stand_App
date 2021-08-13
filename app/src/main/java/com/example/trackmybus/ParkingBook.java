package com.example.trackmybus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ParkingBook extends AppCompatActivity {
    public Spinner spinner;
    TextView spin;
    public String slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, slot10, slot11;
    public String[] durationSpinner;
    Button t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11;
    FirebaseFirestore fstore;
    int year1;
    int month1;
    int date1;
    int HOUR;
    String MINUTE;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    AlertDialog.Builder builder;
    public String spinnervalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_book);
        spinner = findViewById(R.id.spinner1);
        spin = findViewById(R.id.DateTime);
        t1 = findViewById(R.id.buttonSlot1);
        t2 = findViewById(R.id.buttonSlot2);
        t3 = findViewById(R.id.buttonSlot3);
        t4 = findViewById(R.id.buttonSlot4);
        t5 = findViewById(R.id.buttonSlot5);
        t6 = findViewById(R.id.buttonSlot6);
        t7 = findViewById(R.id.buttonSlot7);
        t8 = findViewById(R.id.buttonSlot8);
        t9 = findViewById(R.id.buttonSlot9);
        t10 = findViewById(R.id.buttonSlot10);
        t11 = findViewById(R.id.buttonSlot11);
        fstore = FirebaseFirestore.getInstance();
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
                slot10 = documentSnapshot.getString("Slot10");
                slot11 = documentSnapshot.getString("Slot11");
                if (slot1.equals("UnAvailable")) {
                    t1.setEnabled(false);
                    t1.setBackgroundColor(Color.RED);
                }
                if (slot2.equals("UnAvailable")) {
                    t2.setEnabled(false);
                    t2.setBackgroundColor(Color.RED);
                }
                if (slot3.equals("UnAvailable")) {
                    t3.setEnabled(false);
                    t3.setBackgroundColor(Color.RED);
                }
                if (slot4.equals("UnAvailable")) {
                    t4.setEnabled(false);
                    t4.setBackgroundColor(Color.RED);
                }
                if (slot5.equals("UnAvailable")) {
                    t5.setEnabled(false);
                    t5.setBackgroundColor(Color.RED);
                }
                if (slot6.equals("UnAvailable")) {
                    t6.setEnabled(false);
                    t6.setBackgroundColor(Color.RED);
                }
                if (slot7.equals("UnAvailable")) {
                    t7.setEnabled(false);
                    t7.setBackgroundColor(Color.RED);
                }
                if (slot8.equals("UnAvailable")) {
                    t8.setEnabled(false);
                    t8.setBackgroundColor(Color.RED);
                }
                if (slot9.equals("UnAvailable")) {
                    t9.setEnabled(false);
                    t9.setBackgroundColor(Color.RED);
                }
                if (slot10.equals("UnAvailable")) {
                    t10.setEnabled(false);
                    t10.setBackgroundColor(Color.RED);
                }
                if (slot11.equals("UnAvailable")) {
                    t11.setEnabled(false);
                    t11.setBackgroundColor(Color.RED);
                }
            }
        });
        durationSpinner = getResources().getStringArray(R.array.parkingslot);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.parkingslot, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnervalue = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(ParkingBook.this);
                builder.setMessage("Are sure want to Book this slot?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ParkingBook.this, Booking.class);
                                intent.putExtra("slot", "Slot1");
                                intent.putExtra("Duration", "" + spinnervalue);
                                intent.putExtra("Date", "" + date1);
                                intent.putExtra("Month", "" + month1);
                                intent.putExtra("Year", "" + year1);
                                intent.putExtra("Hour", "" + HOUR);
                                intent.putExtra("Minute", "" + MINUTE);
                                startActivity(intent);
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
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(ParkingBook.this);
                builder.setMessage("Are sure want to Book this slot?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ParkingBook.this, Booking.class);
                                intent.putExtra("slot", "Slot2");
                                intent.putExtra("Duration", "" + spinnervalue);
                                intent.putExtra("Date", "" + date1);
                                intent.putExtra("Month", "" + month1);
                                intent.putExtra("Year", "" + year1);
                                intent.putExtra("Hour", "" + HOUR);
                                intent.putExtra("Minute", "" + MINUTE);
                                startActivity(intent);
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
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(ParkingBook.this);
                builder.setMessage("Are sure want to Book this slot?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ParkingBook.this, Booking.class);
                                intent.putExtra("slot", "Slot3");
                                intent.putExtra("Duration", "" + spinnervalue);
                                intent.putExtra("Date", "" + date1);
                                intent.putExtra("Month", "" + month1);
                                intent.putExtra("Year", "" + year1);
                                intent.putExtra("Hour", "" + HOUR);
                                intent.putExtra("Minute", "" + MINUTE);
                                startActivity(intent);
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
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(ParkingBook.this);
                builder.setMessage("Are sure want to Book this slot?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ParkingBook.this, Booking.class);
                                intent.putExtra("slot", "Slot4");
                                intent.putExtra("Duration", "" + spinnervalue);
                                intent.putExtra("Date", "" + date1);
                                intent.putExtra("Month", "" + month1);
                                intent.putExtra("Year", "" + year1);
                                intent.putExtra("Hour", "" + HOUR);
                                intent.putExtra("Minute", "" + MINUTE);
                                startActivity(intent);
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
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(ParkingBook.this);
                builder.setMessage("Are sure want to Book this slot?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ParkingBook.this, Booking.class);
                                intent.putExtra("slot", "Slot5");
                                intent.putExtra("Duration", "" + spinnervalue);
                                intent.putExtra("Date", "" + date1);
                                intent.putExtra("Month", "" + month1);
                                intent.putExtra("Year", "" + year1);
                                intent.putExtra("Hour", "" + HOUR);
                                intent.putExtra("Minute", "" + MINUTE);
                                startActivity(intent);
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
        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(ParkingBook.this);
                builder.setMessage("Are sure want to Book this slot?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ParkingBook.this, Booking.class);
                                intent.putExtra("slot", "Slot6");
                                intent.putExtra("Duration", "" + spinnervalue);
                                intent.putExtra("Date", "" + date1);
                                intent.putExtra("Month", "" + month1);
                                intent.putExtra("Year", "" + year1);
                                intent.putExtra("Hour", "" + HOUR);
                                intent.putExtra("Minute", "" + MINUTE);
                                startActivity(intent);
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
        t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(ParkingBook.this);
                builder.setMessage("Are sure want to Book this slot?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ParkingBook.this, Booking.class);
                                intent.putExtra("slot", "Slot7");
                                intent.putExtra("Duration", "" + spinnervalue);
                                intent.putExtra("Date", "" + date1);
                                intent.putExtra("Month", "" + month1);
                                intent.putExtra("Year", "" + year1);
                                intent.putExtra("Hour", "" + HOUR);
                                intent.putExtra("Minute", "" + MINUTE);
                                startActivity(intent);
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
        t8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(ParkingBook.this);
                builder.setMessage("Are sure want to Book this slot?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ParkingBook.this, Booking.class);
                                intent.putExtra("slot", "Slot8");
                                intent.putExtra("Duration", "" + spinnervalue);
                                intent.putExtra("Date", "" + date1);
                                intent.putExtra("Month", "" + month1);
                                intent.putExtra("Year", "" + year1);
                                intent.putExtra("Hour", "" + HOUR);
                                intent.putExtra("Minute", "" + MINUTE);
                                startActivity(intent);
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
        t9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(ParkingBook.this);
                builder.setMessage("Are sure want to Book this slot?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ParkingBook.this, Booking.class);
                                intent.putExtra("slot", "Slot9");
                                intent.putExtra("Duration", "" + spinnervalue);
                                intent.putExtra("Date", "" + date1);
                                intent.putExtra("Month", "" + month1);
                                intent.putExtra("Year", "" + year1);
                                intent.putExtra("Hour", "" + HOUR);
                                intent.putExtra("Minute", "" + MINUTE);
                                startActivity(intent);
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
        t10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(ParkingBook.this);
                builder.setMessage("Are sure want to Book this slot?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ParkingBook.this, Booking.class);
                                intent.putExtra("slot", "Slot10");
                                intent.putExtra("Duration", "" + spinnervalue);
                                intent.putExtra("Date", "" + date1);
                                intent.putExtra("Month", "" + month1);
                                intent.putExtra("Year", "" + year1);
                                intent.putExtra("Hour", "" + HOUR);
                                intent.putExtra("Minute", "" + MINUTE);
                                startActivity(intent);
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
        t11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(ParkingBook.this);
                builder.setMessage("Are sure want to Book this slot?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ParkingBook.this, Booking.class);
                                intent.putExtra("slot", "Slot11");
                                intent.putExtra("Duration", "" + spinnervalue);
                                intent.putExtra("Date", "" + date1);
                                intent.putExtra("Month", "" + month1);
                                intent.putExtra("Year", "" + year1);
                                intent.putExtra("Hour", "" + HOUR);
                                intent.putExtra("Minute", "" + MINUTE);
                                startActivity(intent);
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

    public void DateandTime(View view) {
        int minute,hour,year,month,date;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        date = c.get(Calendar.DATE);
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                HOUR = hourOfDay ;
                MINUTE = (String.format("%02d %s", minute,hourOfDay < 12 ? "am" : "pm"));
                spin.setText(date1 + "/" + month1 + "/" + year1 + "--" + HOUR % 12 + ":" + MINUTE);
            }
        }, hour, minute,true);
        timePickerDialog.show();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                year1 = year;
                month1 = month;
                date1 = dayOfMonth;
            }
        }, year, month, date);
        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), ParkingDash.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
