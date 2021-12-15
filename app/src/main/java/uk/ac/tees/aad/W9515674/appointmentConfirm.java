package uk.ac.tees.aad.W9515674;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class appointmentConfirm extends AppCompatActivity {

    TextView ctext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_confirm);

        ctext = findViewById(R.id.ctext);

        AppointmentRequest req = new AppointmentRequest();
        req.setDate(getIntent().getStringExtra("date"));
        req.setDoctor(getIntent().getStringExtra("doctor"));
        req.setHospital(getIntent().getStringExtra("name"));
        FirebaseDatabase.getInstance().getReference("appointment").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getIntent().getStringExtra("date")).setValue(req).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
               ctext.setText("Appointment confirmed");
               ctext.setTextColor(Color.GREEN);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ctext.setText("Failed to book appointment");
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this,Services.class));
    }
}

class AppointmentRequest {

    String date;
    String doctor;
    String hospital;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }
}
