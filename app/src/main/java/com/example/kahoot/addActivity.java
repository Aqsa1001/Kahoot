package com.example.kahoot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class addActivity extends AppCompatActivity {
    EditText questionNo,question,questionMarks,option1,option2,option3,option4,correctOption;
    Button addButton;
    String userName,quizTitle,pin;
    FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        if(getIntent().hasExtra("quizTitle") && getIntent().hasExtra("pin") && getIntent().hasExtra("" +
                "userName")){
            quizTitle = getIntent().getStringExtra("quizTitle");
            pin = getIntent().getStringExtra("pin");
            userName = getIntent().getStringExtra("userName");
        }
        DatabaseReference root= firebaseDatabase.getReference().child("Hosted").child(userName).child(pin);

        questionNo= findViewById(R.id.questionNo);
        question= findViewById(R.id.question);
        questionMarks= findViewById(R.id.questionMarks);
        option1= findViewById(R.id.option1);
        option2= findViewById(R.id.option2);
        option3= findViewById(R.id.option3);
        option4= findViewById(R.id.option4);
        correctOption= findViewById(R.id.correctOption);
        addButton= findViewById(R.id.addButton);
        backArrow= findViewById(R.id.backArrow);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             root.child(quizTitle).addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                     if(questionNo.getText().toString().isEmpty() || question.getText().toString().isEmpty() || questionMarks.getText().toString().isEmpty()
                     || option1.getText().toString().isEmpty() || option2.getText().toString().isEmpty() || option3.getText().toString().isEmpty() ||
                             option4.getText().toString().isEmpty() || correctOption.getText().toString().isEmpty()){
                         Toast.makeText(getApplicationContext(),"Any Field can't be Empty!",Toast.LENGTH_SHORT).show();
                     }
                     else if(snapshot.hasChild(questionNo.getText().toString())){
                         Toast.makeText(getApplicationContext(),"Question Number Already Exists!",Toast.LENGTH_SHORT).show();
                         /*Log.i("Idr","Ni aya");*/
                     }
                     else{
                         root.child(quizTitle).child(questionNo.getText().toString()).child("questionNo").setValue(questionNo.getText().toString());
                         root.child(quizTitle).child(questionNo.getText().toString()).child("question").setValue(question.getText().toString());
                         root.child(quizTitle).child(questionNo.getText().toString()).child("questionMarks").setValue(questionMarks.getText().toString());
                         root.child(quizTitle).child(questionNo.getText().toString()).child("option1").setValue(option1.getText().toString());
                         root.child(quizTitle).child(questionNo.getText().toString()).child("option2").setValue(option2.getText().toString());
                         root.child(quizTitle).child(questionNo.getText().toString()).child("option3").setValue(option3.getText().toString());
                         root.child(quizTitle).child(questionNo.getText().toString()).child("option4").setValue(option4.getText().toString());
                         root.child(quizTitle).child(questionNo.getText().toString()).child("correctOption").setValue(correctOption.getText().toString());
                         finish();

                     }
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {

                 }
             });

             /*   databaseReference.child("quizData").addListenerForSingleValueEvent(new ValueEventListener() {
                    int qNo=1;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String userName= snapshot.child("Amaan").child(String.valueOf(qNo)).getValue(String.class);

                        if(snapshot.child("Amaan").hasChild(String.valueOf(qNo))){
                            qNo += 1;

*//*
                            Toast.makeText(signUpActivity.this,"Username Already Taken!",Toast.LENGTH_SHORT).show();
*//*
                        }
                        else{

                            databaseReference.child("userData").child(userNameText.getText().toString().trim()).child("email").setValue(emailText.getText().toString().trim());
                            databaseReference.child("userData").child(userNameText.getText().toString().trim()).child("password").setValue(passwordText.getText().toString().trim());
                            databaseReference.child("userData").child(userNameText.getText().toString().trim()).child("age").setValue(numberText.getText().toString().trim());

                            Toast.makeText(signUpActivity.this,"Sucessfully Registered!",Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(signUpActivity.this,loginActivity.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
*/

                /*Sending Data to Home Activity after which it stores in Create Fragment Recycler */
                /*Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("questionNo", questionNo.getText().toString());
                intent.putExtra("question", question.getText().toString());
                intent.putExtra("questionMarks", questionMarks.getText().toString());
                intent.putExtra("option1", option1.getText().toString());
                intent.putExtra("option2", option2.getText().toString());
                intent.putExtra("option3", option3.getText().toString());
                intent.putExtra("option4", option4.getText().toString());
                intent.putExtra("correctOption", correctOption.getText().toString());
                startActivity(intent);*/
            }
        });


    }
}