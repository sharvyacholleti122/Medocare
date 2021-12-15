package uk.ac.tees.aad.W9515674;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    EditText email;
    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email =findViewById(R.id.email);
        pass = findViewById(R.id.password);

        Button in  = findViewById(R.id.login_btn_signup);
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        Button create_acc = findViewById(R.id.createacc);
        create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup(email.getText().toString(),pass.getText().toString());
            }
        });




    }

    public void signup(String email, String password)
    {
        if(email.length()<=4|| password.length()<=6)
        {
            Toast.makeText(getApplicationContext(),"Enter Correct Inputs",Toast.LENGTH_LONG).show();
            return;
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(),Services.class));
                        } else {
                            Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
