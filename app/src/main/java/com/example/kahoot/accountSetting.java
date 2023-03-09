package com.example.kahoot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class accountSetting extends AppCompatActivity {
    EditText emailText,passwordText,numberText;
    TextView userNameText;
    Button delBtn,updateBtn;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://kahoot-92aef-default-rtdb.firebaseio.com");
    String userName;
    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        userNameText= findViewById(R.id.userNameText);
        emailText= findViewById(R.id.emailText);
        passwordText= findViewById(R.id.passwordText);
        numberText= findViewById(R.id.numberText);
        delBtn= findViewById(R.id.delbtn);
        updateBtn= findViewById(R.id.updatebtn);
        backArrow= findViewById(R.id.backArrow);

        if(getIntent().hasExtra("userName")){
            userName = getIntent().getStringExtra("userName");
            Log.i("TAG", userName);
        }
        else{
            Log.i("oye", "Data ni aya");
        }
        userNameText.setText(userName);
        emailText.setFocusable(true);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        databaseReference.child("userData").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String password= snapshot.child(userName).child("password").getValue(String.class);
                final String age= snapshot.child(userName).child("age").getValue(String.class);
                final String email= snapshot.child(userName).child("email").getValue(String.class);

                passwordText.setText(password);
                numberText.setText(age);
                emailText.setText(email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailText.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty()|| numberText.getText().toString().isEmpty()){
                    Toast.makeText(accountSetting.this,"Empty",Toast.LENGTH_SHORT).show();
                }
                else if ((Integer.parseInt(numberText.getText().toString())) <7)
                {
                    Toast.makeText(accountSetting.this,"Age should be greater than 7",Toast.LENGTH_SHORT).show();
                }
                else if ( !(Patterns.EMAIL_ADDRESS.matcher(emailText.getText().toString()).matches())) {
                    Toast.makeText(accountSetting.this, "Wrong Email Format!", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("userData").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(userName)){
                                databaseReference.child("userData").child(userName).child("email").setValue(emailText.getText().toString().trim());
                                databaseReference.child("userData").child(userName).child("password").setValue(passwordText.getText().toString().trim());
                                databaseReference.child("userData").child(userName).child("age").setValue(numberText.getText().toString().trim());
                                finish();
/*
                                startActivity(new Intent(accountSetting.this,HomeActivity.class));
*/
/*
                                getSupportFragmentManager().beginTransaction().replace(R.id.main, new profileFragment()).commit();
*/
                                Toast.makeText(accountSetting.this,"Your Data has been updated!",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference db= FirebaseDatabase.getInstance().getReference("userData").child(userName);

                Task<Void> mTask= db.removeValue();
                mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent= new Intent(accountSetting.this,chooseActivity.class);
                        startActivity(intent);
                        Toast.makeText(accountSetting.this,"Account Deleted Successfully",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }
}