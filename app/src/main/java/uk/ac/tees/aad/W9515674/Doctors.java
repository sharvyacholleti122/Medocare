package uk.ac.tees.aad.W9515674;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Doctors extends AppCompatActivity {

    ListView listView;
    String heading[] ;
    String subheading[];
    String images[];
    CustomAdapter adapter;

    ArrayList<Doctor> doctors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);


        TextView hosName = findViewById(R.id.hos);
        doctors = new ArrayList<Doctor>();

        hosName.setText(getIntent().getStringExtra("name"));
        listView = findViewById(R.id.list_view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long idd) {
                Intent intent = new Intent(getApplicationContext(),Appointment.class);
                intent.putExtra("doctor",doctors.get(position).getName());
                intent.putExtra("name",getIntent().getStringExtra("name"));
                intent.putExtra("number",getIntent().getStringExtra("number"));
                startActivity(intent);
            }
        });


        FirebaseDatabase.getInstance().getReference("Doctors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot da:dataSnapshot.getChildren()) {
                    Doctor doc = da.getValue(Doctor.class);
                    if(doc.getHospital().equals(getIntent().getStringExtra("name")))
                    {
                        doctors.add(doc);
                    }
                }

                heading = new String[doctors.size()];
                subheading= new String[doctors.size()];
                images = new String[doctors.size()];

                for (int i=0;i<doctors.size();i++)
                {
                    heading[i] = doctors.get(i).getName();
                    subheading[i] = doctors.get(i).getSpl();
                    images[i]= doctors.get(i).getImage();
                }

                adapter = new CustomAdapter(getApplicationContext(), heading, subheading, images);
                listView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_LONG).show();
            }
        });


    }
}

class Doctor{

    String Hospital;
    String name;
    String spl;
    String image;

    public String getHospital() {
        return Hospital;
    }

    public void setHospital(String hospital) {
        Hospital = hospital;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpl() {
        return spl;
    }

    public void setSpl(String spl) {
        this.spl = spl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
