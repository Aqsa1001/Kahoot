package com.example.kahoot;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class attemptQuizAdapter extends RecyclerView.Adapter<attemptQuizAdapter.MyViewHolder> {
    ArrayList<model> myList;
    private Context context;
    String teacherUserName,quizTitle,pin;
    ArrayList<UpdatedModel> list;

    public attemptQuizAdapter(ArrayList<UpdatedModel> list,ArrayList<model> myList, Context context, String teacherUserName, String quizTitle, String pin) {
        this.list= list;
        this.myList = myList;
        this.context = context;
        this.teacherUserName = teacherUserName;
        this.quizTitle = quizTitle;
        this.pin = pin;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.attempt_row, parent, false);
        return new attemptQuizAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        model myModel= myList.get(position);
        UpdatedModel model= list.get(position);

        holder.questionNo.setText(myModel.getQuestionNo());
        holder.question.setText(myModel.getQuestion());
        holder.questionMarks.setText(myModel.getQuestionMarks());
        holder.option1.setText(myModel.getOption1());
        holder.option2.setText(myModel.getOption2());
        holder.option3.setText(myModel.getOption3());
        holder.option4.setText(myModel.getOption4());
        holder.correctOption.setText(myModel.getCorrectOption());
        holder.radioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.option1:
                        holder.userAnswer.setText(holder.option1.getText().toString());
                        model.setUserAnswer(holder.userAnswer.getText().toString());
                        if(holder.userAnswer.getText().toString().equals(holder.correctOption.getText().toString())){
                            holder.gainMarks.setText(holder.questionMarks.getText().toString());
                            model.setGainMarks(holder.gainMarks.getText().toString());
                        }
                        else {
                            holder.gainMarks.setText("0");
                            model.setGainMarks(holder.gainMarks.getText().toString());
                        }
                        break;
                    case R.id.option2:
                        holder.userAnswer.setText(holder.option2.getText().toString());
                        model.setUserAnswer(holder.userAnswer.getText().toString());
                        if(holder.userAnswer.getText().toString().equals(holder.correctOption.getText().toString())){
                            holder.gainMarks.setText(holder.questionMarks.getText().toString());
                            model.setGainMarks(holder.gainMarks.getText().toString());
                        }
                        else {
                            holder.gainMarks.setText("0");
                            model.setGainMarks(holder.gainMarks.getText().toString());
                        }
                        break;
                    case R.id.option3:
                        holder.userAnswer.setText(holder.option3.getText().toString());
                        model.setUserAnswer(holder.userAnswer.getText().toString());
                        if(holder.userAnswer.getText().toString().equals(holder.correctOption.getText().toString())){
                            holder.gainMarks.setText(holder.questionMarks.getText().toString());
                            model.setGainMarks(holder.gainMarks.getText().toString());
                        }
                        else {
                            holder.gainMarks.setText("0");
                            model.setGainMarks(holder.gainMarks.getText().toString());
                        }
                        break;
                    case R.id.option4:
                        holder.userAnswer.setText(holder.option4.getText().toString());
                        model.setUserAnswer(holder.userAnswer.getText().toString());
                        if(holder.userAnswer.getText().toString().equals(holder.correctOption.getText().toString())){
                            holder.gainMarks.setText(holder.questionMarks.getText().toString());
                            model.setGainMarks(holder.gainMarks.getText().toString());
                        }
                        else {
                            holder.gainMarks.setText("0");
                            model.setGainMarks(holder.gainMarks.getText().toString());
                        }
                        break;
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView question, questionNo, questionMarks, option1, option2, option3, option4, correctOption, userAnswer, gainMarks;
        LinearLayout quizRowLayout;
        RadioGroup radioGrp;


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
            quizRowLayout = itemView.findViewById(R.id.quizRowLayout);
            userAnswer= itemView.findViewById(R.id.userAnswer);
            radioGrp= itemView.findViewById(R.id.radioGrp);
            gainMarks= itemView.findViewById(R.id.gainMarks);
        }
    }
}
