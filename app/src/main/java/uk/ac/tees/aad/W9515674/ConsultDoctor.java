package uk.ac.tees.aad.W9515674;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConsultDoctor extends AppCompatActivity {

    ArrayList<String> list;
    Spinner spinner;
    String text="";
    TextView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_doctor);
        list =  new ArrayList<String>();

        message = findViewById(R.id.message);


         spinner  = findViewById(R.id.spinner);
         spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 if(position!=0){
                 text = list.get(position-1);
                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

        Button consult = findViewById(R.id.quickConsult);
        consult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Select Option",Toast.LENGTH_LONG).show();
                    return;
                }
                if(message.getText().toString().length()<20)
                {
                    Toast.makeText(getApplicationContext(),"Message must be 20 letters long",Toast.LENGTH_LONG).show();
                    return;
                }
                callService();
            }

        });


        FirebaseDatabase.getInstance().getReference("Doctors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot da:dataSnapshot.getChildren()) {
                    Doctor hos = da.getValue(Doctor.class);
                    if(!list.contains(hos.getSpl()))
                    {
                        list.add(hos.getSpl());
                    }
                    String[] spl = new String[list.size()+1];
                    for(int i=1;i<=list.size();i++)
                    {
                        spl[i] = list.get(i-1);
                    }
                    spl[0] = "Select Specilization";
                    spinner.setAdapter(new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,spl));
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void callService()
    {
        Enquire en = new Enquire();
        en.setMessage(message.getText().toString());
        en.setType(text);
        FirebaseDatabase.getInstance().getReference("consult").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(en).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Requested Successfully",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(),Services.class));
                    }
                }
        );

    }
}
class  Enquire{
    String type;
    String message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
