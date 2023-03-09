package com.example.kahoot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://kahoot-92aef-default-rtdb.firebaseio.com");
    Button loginBtn,lSignUpBtn;
    EditText loginUserNameText,loginPasswordText,emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn= findViewById(R.id.loginBtn);
        lSignUpBtn=findViewById(R.id.lSignUpBtn);
        loginUserNameText= findViewById(R.id.loginUserNameText);
        loginPasswordText= findViewById(R.id.loginPasswordText);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginUserNameText.getText().toString().isEmpty()|| loginPasswordText.getText().toString().isEmpty()){
                    Toast.makeText(loginActivity.this,"Empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("userData").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(loginUserNameText.getText().toString())){
                                final String password= snapshot.child(loginUserNameText.getText().toString()).child("password").getValue(String.class);
                                if(loginPasswordText.getText().toString().equals(password)){
                                    Toast.makeText(loginActivity.this,"Ok!",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    intent.putExtra("userName", loginUserNameText.getText().toString());

                                    Intent intent2 = new Intent(getApplicationContext(), accountSetting.class);
                                    intent2.putExtra("userName", loginUserNameText.getText().toString());

                                    startActivity(intent);

                                    loginUserNameText.setText("");
                                    loginPasswordText.setText("");
                                }
                                else{
                                    Toast.makeText(loginActivity.this,"Wrong Password!",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(loginActivity.this,"User Not Found. Register First!",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });

        lSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginActivity.this,signUpActivity.class));
            }
        });

    }
}