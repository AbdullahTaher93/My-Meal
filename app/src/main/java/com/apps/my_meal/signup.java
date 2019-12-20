package com.apps.my_meal;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class signup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText input_email;
    private EditText input_password;
    private Button btn_signup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        btn_signup= (Button) findViewById(R.id.btn_signup);
        input_email=(EditText)findViewById(R.id.input_email);
        input_password=(EditText)findViewById(R.id.input_password);

        ProgressBar progressBar=(ProgressBar)findViewById(R.id.progressBar);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=input_email.getText().toString().trim();
                String password=input_password.getText().toString().trim();


                if(TextUtils.isEmpty(email)){
                    input_email.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    input_password.setError("Email is required");
                    return;
                }
                if(password.length()<6){
                    input_password.setError("Password invalid input at least 6 chars.");


                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(signup.this,"Successfully",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            input_email.setText("");
                            input_password.setText("");


                            Intent intent=new Intent(signup.this,MainActivity.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(signup.this,"createUserWithEmail:failure",Toast.LENGTH_LONG).show();
                            Log.w("", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(signup.this,"your Email incorrect or Exist.",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


            }
        });

    }
}