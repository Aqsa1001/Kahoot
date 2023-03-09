package com.example.kahoot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    ArrayList<model> myList;
    private Context context;
    String userName,quizTitle,pin;

    public CustomAdapter(Context context, ArrayList<model> myList,String userName,String quizTitle,String pin) {
        this.myList = myList;
        this.context = context;
        this.userName = userName;
        this.quizTitle = quizTitle;
        this.pin = pin;
    }

    /*
    private ArrayList questionNo, question, questionMarks, option1, option2, option3, option4, correctOption;*/

    /*CustomAdapter(Context context, ArrayList questionNo, ArrayList question, ArrayList questionMarks,
                  ArrayList option1, ArrayList option2, ArrayList option3, ArrayList option4,
                  ArrayList correctOption) {

        this.context = context;
        this.question = question;
        this.questionNo = questionNo;
        this.questionMarks = questionMarks;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctOption = correctOption;
    }*/

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        /*holder.questionNo Wali side saari wo hain jo My row me bni hui hain*/
        /*question.getPostion wali side saari array list ha jo upr bnae ha*/

        model myModel= myList.get(position);


        holder.questionNo.setText(myModel.getQuestionNo());
        holder.question.setText(myModel.getQuestion());
        holder.questionMarks.setText(myModel.getQuestionMarks());
        holder.option1.setText(myModel.getOption1());
        holder.option2.setText(myModel.getOption2());
        holder.option3.setText(myModel.getOption3());
        holder.option4.setText(myModel.getOption4());
        holder.correctOption.setText(myModel.getCorrectOption());


        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, update_delete_Actitvity.class);
                intent.putExtra("questionNo", myModel.getQuestionNo());
                intent.putExtra("question", myModel.getQuestion());
                intent.putExtra("questionMarks", myModel.getQuestionMarks());
                intent.putExtra("option1", myModel.getOption1());
                intent.putExtra("option2", myModel.getOption2());
                intent.putExtra("option3", myModel.getOption3());
                intent.putExtra("option4", myModel.getOption4());
                intent.putExtra("correctOption", myModel.getCorrectOption());
                intent.putExtra("userName",userName);
                intent.putExtra("quizTitle",quizTitle);
                intent.putExtra("pin",pin);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView question, questionNo, questionMarks, option1, option2, option3, option4, correctOption;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            questionNo = itemView.findViewById(R.id.questionNo);
            questionMarks = itemView.findViewById(R.id.questionMarks);
            option1 = itemView.findViewById(R.id.option1);
            option2 = itemView.findViewById(R.id.option2);
            option3 = itemView.findViewById(R.id.option3);
            option4 = itemView.findViewById(R.id.option4);
            correctOption = itemView.findViewById(R.id.correctOption);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
