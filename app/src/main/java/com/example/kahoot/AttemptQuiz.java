package com.example.kahoot;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AttemptQuiz extends Fragment {
    String pin,quizTitle,teacherUserName,userName;
    RecyclerView recyclerView;
    attemptQuizAdapter attemptQuizAdapter;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    ArrayList<model> list;
    ArrayList<UpdatedModel> myList;
    Button submissionButton;
    float gainMarks=0,totalMarks=0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_attempt_quiz, container, false);
        submissionButton= view.findViewById(R.id.submissionButton);

        if(this.getArguments()!=null){
            userName= this.getArguments().getString("userName");
            pin= this.getArguments().getString("pin");
            quizTitle= this.getArguments().getString("quizTitle");
            teacherUserName= this.getArguments().getString("teacherUserName");
        }

        DatabaseReference root= firebaseDatabase.getReference().child("Hosted").child(teacherUserName).child(pin).child(quizTitle);
        DatabaseReference databaseReference= firebaseDatabase.getReferenceFromUrl("https://kahoot-92aef-default-rtdb.firebaseio.com/");

        recyclerView= view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        list= new ArrayList<>();
        myList = new ArrayList<>();
        attemptQuizAdapter= new attemptQuizAdapter(myList,list, view.getContext(),teacherUserName,quizTitle,pin);

        recyclerView.setAdapter(attemptQuizAdapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    model myModel= dataSnapshot.getValue(model.class);
                    list.add(myModel);

                    UpdatedModel model= dataSnapshot.getValue(UpdatedModel.class);
                    model.setGainMarks("NAN");
                    model.setUserAnswer("NAN");
                    myList.add(model);
                    Log.i("aya","idr");
                }
                attemptQuizAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Ye error aya ha: ", error.toException());
            }
        });

        submissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptQuizAdapter.notifyDataSetChanged();
                databaseReference.child("Attempted").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(userName)){
                            if(snapshot.child(userName).hasChild(pin)){
                                Intent intent=new Intent(getActivity(),HomeActivity.class);
                                startActivity(intent);
                                Toast.makeText(getActivity(),"You can't submit because you have attempt this quiz before!",Toast.LENGTH_SHORT).show();
                            }
                        }

                        if(!(snapshot.hasChild(userName))){
                            for (UpdatedModel data : myList) {
                                float temp= Float.parseFloat(data.getGainMarks());
                                gainMarks+=temp;

                                float temp1 =Float.parseFloat(data.getQuestionMarks());
                                totalMarks+=temp1;

                                // Save data to Firebase
                                databaseReference.child("Attempted").child(userName).child(pin).child(quizTitle).child(data.getQuestionNo()).setValue(data);

                                //Adding Student UserName to Hosted Tree in order to keep in record which student attempted specific quiz
                                DatabaseReference root2= firebaseDatabase.getReference();
                                root2.child("Hosted").child(teacherUserName).child("Student UserNames").push().child("userName").setValue(userName);

                                Intent intent=new Intent(getActivity(),CongoActivity.class);
                                intent.putExtra("gainMarks",gainMarks);
                                intent.putExtra("totalMarks",totalMarks);
                                startActivity(intent);
                                Toast.makeText(getActivity(),"Submit Successfully",Toast.LENGTH_SHORT).show();

                            }

                        }
                        else if(snapshot.hasChild(userName)){
                            if(!(snapshot.child(userName).hasChild(pin))){
                                for (UpdatedModel data : myList) {
                                    float temp= Float.parseFloat(data.getGainMarks());
                                    gainMarks+=temp;

                                    float temp1 =Float.parseFloat(data.getQuestionMarks());
                                    totalMarks+=temp1;

                                    // Save data to Firebase
                                    databaseReference.child("Attempted").child(userName).child(pin).child(quizTitle).child(data.getQuestionNo()).setValue(data);

                                    //Adding Student UserName to Hosted Tree in order to keep in record which student attempted specific quiz
                                    DatabaseReference root2= firebaseDatabase.getReference();
                                    root2.child("Hosted").child(teacherUserName).child("Student UserNames").push().child("userName").setValue(userName);

                                    Intent intent=new Intent(getActivity(),CongoActivity.class);
                                    intent.putExtra("gainMarks",gainMarks);
                                    intent.putExtra("totalMarks",totalMarks);
                                    startActivity(intent);
                                    Toast.makeText(getActivity(),"Submit Successfully",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });






        return view;
    }
}