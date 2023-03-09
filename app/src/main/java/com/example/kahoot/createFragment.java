package com.example.kahoot;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class createFragment extends Fragment {
    FloatingActionButton add_button;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
    CustomAdapter customAdapter;
    ArrayList<model> list;
    String userName,quizTitle,pin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_create, container, false);
        if(this.getArguments()!=null){
            pin= this.getArguments().getString("pin");
            quizTitle= this.getArguments().getString("quizTitle");
            userName= this.getArguments().getString("userName");
        }

        DatabaseReference root= firebaseDatabase.getReference().child("Hosted").child(userName).child(pin).child(quizTitle);

        add_button= view.findViewById(R.id.addButton);

       recyclerView= view.findViewById(R.id.recyclerView);
       recyclerView.setHasFixedSize(false);
       recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

       list= new ArrayList<>();
       customAdapter= new CustomAdapter(view.getContext(),list,userName,quizTitle,pin);

       recyclerView.setAdapter(customAdapter);

       root.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                   model myModel= dataSnapshot.getValue(model.class);
                   list.add(myModel);
                   Log.i("aya","idr");
               }
               customAdapter.notifyDataSetChanged();
           }
           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Log.w("TAG", "Ye error aya ha: ", error.toException());
           }
       });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),addActivity.class);
                intent.putExtra("userName",userName);
                intent.putExtra("quizTitle",quizTitle);
                intent.putExtra("pin",pin);
                startActivity(intent);
            }
        });
        return view;
    }
}