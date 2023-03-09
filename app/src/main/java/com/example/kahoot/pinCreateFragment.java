package com.example.kahoot;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class pinCreateFragment extends Fragment {
    String userName;
    EditText quizTitleText,pinText;
    Button submissionButton;
    LinearLayout rectangle;
    FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
    DatabaseReference databaseReference= firebaseDatabase.getReferenceFromUrl("https://kahoot-92aef-default-rtdb.firebaseio.com/");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_pin_create, container, false);
        quizTitleText= view.findViewById(R.id.quizTitleText);
        pinText= view.findViewById(R.id.pinText);
        submissionButton= view.findViewById(R.id.submissionButton);
        rectangle= view.findViewById(R.id.rectangle);

/*         FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
         DatabaseReference mDatabaseReference = mDatabase.getReferenceFromUrl("https://kahoot-92aef-default-rtdb.firebaseio.com");
        mDatabaseReference = mDatabase.getReference().child("name");
        mDatabaseReference.setValue("Donald Duck");*/

        if(this.getArguments() != null) {
            userName = this.getArguments().getString("userName", "");
        }
        if(this.getArguments()== null){
            Log.i("User Name haven't", "recieve in pinCreateFragment");
        }

        submissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pinText.getText().toString().isEmpty() || quizTitleText.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"Field can't be Empty!!",Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("Hosted").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(userName)){
                                if(snapshot.child(userName).hasChild(pinText.getText().toString())){
                                    Toast.makeText(getActivity(),"Pin Already Exist!",Toast.LENGTH_SHORT).show();
                                }
                            }

                            if(!(snapshot.hasChild(userName))){
                                databaseReference= firebaseDatabase.getReference("Hosted/"+userName+"/"+pinText.getText().toString()+"/");
                                rectangle.setVisibility(View.GONE);
                                createFragment createquizFragment= new createFragment();
                                FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                                Bundle bundle= new Bundle();
                                bundle.putString("userName",userName);
                                bundle.putString("pin",pinText.getText().toString());
                                bundle.putString("quizTitle",quizTitleText.getText().toString());
                                createquizFragment.setArguments(bundle);
                                fragmentTransaction.replace(R.id.container,createquizFragment).commit();
                            }
                            else if(snapshot.hasChild(userName)){
                                if (!snapshot.child(userName).hasChild(pinText.getText().toString())){
                                    databaseReference= firebaseDatabase.getReference("Hosted/"+userName+"/"+pinText.getText().toString()+"/");
                                    rectangle.setVisibility(View.GONE);
                                    createFragment createquizFragment= new createFragment();
                                    FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                                    Bundle bundle= new Bundle();
                                    bundle.putString("userName",userName);
                                    bundle.putString("pin",pinText.getText().toString());
                                    bundle.putString("quizTitle",quizTitleText.getText().toString());
                                    createquizFragment.setArguments(bundle);
                                    fragmentTransaction.replace(R.id.container,createquizFragment).commit();
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