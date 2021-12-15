package uk.ac.tees.aad.W9515674;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Appointment extends AppCompatActivity {

    TextView call;
    TextView text;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        getIntent().getStringExtra("doctor");

        text = findViewById(R.id.textView5);


        Button selectDate = findViewById(R.id.date);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(text);
            }
        });

        TextView name = findViewById(R.id.docname);
        name.setText(getIntent().getStringExtra("doctor"));

        TextView na = findViewById(R.id.hosname3);
        na.setText(getIntent().getStringExtra("name"));

         call = findViewById(R.id.con);
        call.setText(getIntent().getStringExtra("number"));

        Button confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),appointmentConfirm.class);
                intent.putExtra("doctor",getIntent().getStringExtra("doctor"));
                intent.putExtra("name",getIntent().getStringExtra("name"));
                intent.putExtra("date",text.getText().toString());
                startActivity(intent);
            }
        });


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        text.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}

