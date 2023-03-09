package com.example.kahoot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class update_delete_Actitvity extends AppCompatActivity {
    EditText question,questionMarks,option1,option2,option3,option4,correctOption;
    Button updateButton,deleteButton;
    TextView questionNo;
    ImageView backArrow;
    String qNo,ques,qMarks,opt1,opt2,opt3,opt4,corOption,userName,pin,quizTitle;
    FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_actitvity);

        questionNo= findViewById(R.id.questionNo);
        question= findViewById(R.id.question);
        questionMarks= findViewById(R.id.questionMarks);
        option1= findViewById(R.id.option1);
        option2= findViewById(R.id.option2);
        option3= findViewById(R.id.option3);
        option4= findViewById(R.id.option4);
        correctOption= findViewById(R.id.correctOption);
        updateButton= findViewById(R.id.updateButton);
        deleteButton= findViewById(R.id.deleteButton);
        backArrow= findViewById(R.id.backArrow);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        get_setData();

        DatabaseReference root= firebaseDatabase.getReference().child("Hosted").child(userName).child(pin);

        updateButton.setOnClickListener(new View.OnClickListener() {
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
                        else{
                            Toast.makeText(getApplicationContext(),"Error in DataBase!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("TAG", "Ye error aya ha: ", error.toException());
                    }
                });
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Hosted").child("1234").child("you5").child("Science").child(questionNo.getText().toString());

                Task<Void> mTask= db.removeValue();
                mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(update_delete_Actitvity.this,"Deleted Successfully",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });



    }

    public void get_setData(){
        if(getIntent().hasExtra("question") && getIntent().hasExtra("questionNo") &&
                getIntent().hasExtra("questionMarks") && getIntent().hasExtra("option1") && getIntent().hasExtra("option2") &&
                getIntent().hasExtra("option3") && getIntent().hasExtra("option4") && getIntent().hasExtra("correctOption")){
            //Getting Data from Intent
            qNo = getIntent().getStringExtra("questionNo");
            ques = getIntent().getStringExtra("question");
            qMarks = getIntent().getStringExtra("questionMarks");
            opt1 = getIntent().getStringExtra("option1");
            opt2 = getIntent().getStringExtra("option2");
            opt3 = getIntent().getStringExtra("option3");
            opt4 = getIntent().getStringExtra("option4");
            corOption = getIntent().getStringExtra("correctOption");
            quizTitle = getIntent().getStringExtra("quizTitle");
            pin = getIntent().getStringExtra("pin");
            userName = getIntent().getStringExtra("userName");

            //Setting Intent Data
            question.setText(ques);
            questionNo.setText(qNo);
            questionMarks.setText(qMarks);
            option1.setText(opt1);
            option2.setText(opt2);
            option3.setText(opt3);
            option4.setText(opt4);
            correctOption.setText(corOption);

/*
            Log.d("stev", title+" "+author+" "+pages);
*/
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
}