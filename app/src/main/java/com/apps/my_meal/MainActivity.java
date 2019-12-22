package com.apps.my_meal;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText input_email;
    private EditText input_password;
    private Button btn_login;
    public static String USER_ID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login= (Button) findViewById(R.id.btn_login);
        input_email=(EditText)findViewById(R.id.input_email);
        input_password=(EditText)findViewById(R.id.input_password);
        ProgressBar progressBar=(ProgressBar)findViewById(R.id.progressBar);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
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
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Query query= FirebaseDatabase.getInstance().getReference("Users").orderByChild("user_email").equalTo(email);
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot post:dataSnapshot.getChildren()){
                                        USER_ID=post.getValue(Users.class).User_ID;

                                    }
                                    Toast.makeText(MainActivity.this,USER_ID,Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            Toast.makeText(MainActivity.this,"Successfully",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            input_email.setText("");
                            input_password.setText("");
                            Intent intent=new Intent(MainActivity.this, Categories.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(MainActivity.this,"createUserWithEmail:failure",Toast.LENGTH_LONG).show();
                            Log.w("", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this,"your Email or password is incorrect or not Exist.",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


            }
        });

    }
    public void singup (View view){
        Intent intent=new Intent(this, signup.class);
        startActivity(intent);
    }


}
