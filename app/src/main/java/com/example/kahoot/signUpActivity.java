package com.example.kahoot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signUpActivity extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://kahoot-92aef-default-rtdb.firebaseio.com");
    Button goBtn,sLoginBtn;
    EditText userNameText,emailText,passwordText,numberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        goBtn= findViewById(R.id.goBtn);
        sLoginBtn= findViewById(R.id.sLoginBtn);
        userNameText= findViewById(R.id.userNameText);
        emailText= findViewById(R.id.emailText);
        passwordText= findViewById(R.id.passwordText);
        numberText= findViewById(R.id.numberText);

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userNameText.getText().toString().isEmpty()|| emailText.getText().toString().isEmpty()|| passwordText.getText().toString().isEmpty()||
                numberText.getText().toString().isEmpty()){
                    Toast.makeText(signUpActivity.this,"Empty",Toast.LENGTH_SHORT).show();
                }
                else if ((Integer.parseInt(numberText.getText().toString())) <7)
                {
                    Toast.makeText(signUpActivity.this,"Age should be greater than 7",Toast.LENGTH_SHORT).show();
                }
                else if ( !(Patterns.EMAIL_ADDRESS.matcher(emailText.getText().toString()).matches())) {
                    Toast.makeText(signUpActivity.this, "Wrong Email Format!", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("userData").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
/*
                            final String email= snapshot.child(userNameText.getText().toString()).child("email").getValue(String.class);
*/

                            if(snapshot.hasChild(userNameText.getText().toString().trim())){
                                Toast.makeText(signUpActivity.this,"Username Already Taken!",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                databaseReference.child("userData").child(userNameText.getText().toString().trim()).child("email").setValue(emailText.getText().toString().trim());
                                databaseReference.child("userData").child(userNameText.getText().toString().trim()).child("password").setValue(passwordText.getText().toString().trim());
                                databaseReference.child("userData").child(userNameText.getText().toString().trim()).child("age").setValue(numberText.getText().toString().trim());

                                Toast.makeText(signUpActivity.this,"Sucessfully Registered!",Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(signUpActivity.this,loginActivity.class));

                                userNameText.setText("");
                                passwordText.setText("");
                                numberText.setText("");
                                emailText.setText("");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        sLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signUpActivity.this,loginActivity.class));
            }
        });
    }
}