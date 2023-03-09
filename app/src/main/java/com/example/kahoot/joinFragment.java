package com.example.kahoot;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class joinFragment extends Fragment {
    EditText teacherUserNameText,pinText,quizTitleText;
    Button submissionButton;
    LinearLayout rectangle;
    String userName;
    FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
    DatabaseReference databaseReference= firebaseDatabase.getReferenceFromUrl("https://kahoot-92aef-default-rtdb.firebaseio.com/");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_join, container, false);
        teacherUserNameText= view.findViewById(R.id.teacherUserNameText);
        pinText= view.findViewById(R.id.pinText);
        quizTitleText= view.findViewById(R.id.quizTitleText);
        submissionButton= view.findViewById(R.id.submissionButton);
        rectangle= view.findViewById(R.id.rectangle);

        if(this.getArguments() != null) {
            userName = this.getArguments().getString("userName", "");
        }

        submissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teacherUserNameText.getText().toString().isEmpty() || pinText.getText().toString().isEmpty() ||
                        quizTitleText.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"Field Can't be Empty!",Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("Hosted").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.hasChild(teacherUserNameText.getText().toString())){
                                Toast.makeText(getActivity(),"Teacher User Name Doesn't Exist!",Toast.LENGTH_SHORT).show();
                            }

                            if(snapshot.hasChild(teacherUserNameText.getText().toString())){
                                if (!snapshot.child(teacherUserNameText.getText().toString()).hasChild(pinText.getText().toString())){
                                    Toast.makeText(getActivity(),"Pin is Wrong!",Toast.LENGTH_SHORT).show();
                                }
                            }

                            if(snapshot.hasChild(teacherUserNameText.getText().toString())){
                                if (snapshot.child(teacherUserNameText.getText().toString()).hasChild(pinText.getText().toString())){
                                    if(!snapshot.child(teacherUserNameText.getText().toString()).child(pinText.getText().toString()).hasChild(quizTitleText.getText().toString())){
                                        Toast.makeText(getActivity(),"No Quiz Against Pin Exists! Recheck Title!",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            if(snapshot.hasChild(teacherUserNameText.getText().toString())){
                                if (snapshot.child(teacherUserNameText.getText().toString()).hasChild(pinText.getText().toString())){
                                    if(snapshot.child(teacherUserNameText.getText().toString()).child(pinText.getText().toString()).hasChild(quizTitleText.getText().toString())){
                                        rectangle.setVisibility(View.GONE);
                                        AttemptQuiz attemptQuiz= new AttemptQuiz();
                                        FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
                                        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                                        Bundle bundle= new Bundle();
                                        bundle.putString("userName",userName);
                                        bundle.putString("teacherUserName",teacherUserNameText.getText().toString());
                                        bundle.putString("pin",pinText.getText().toString());
                                        bundle.putString("quizTitle",quizTitleText.getText().toString());
                                        attemptQuiz.setArguments(bundle);
                                        fragmentTransaction.replace(R.id.container,attemptQuiz).commit();
                                        Toast.makeText(getActivity(),"Start Your Quiz!",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        return view;
    }
}