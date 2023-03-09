package com.example.kahoot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CongoActivity extends AppCompatActivity {
    float gainMarks,totalMarks;
    TextView gainMarksText,totalMarksText;
    Button okBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congo);
        gainMarksText= findViewById(R.id.gainMarksText);
        totalMarksText= findViewById(R.id.totalMarksText);
        okBtn= findViewById(R.id.okBtn);

        if(this.getIntent().hasExtra("gainMarks") || this.getIntent().hasExtra("totalMarks")){
            gainMarks= this.getIntent().getFloatExtra("gainMarks",0);
            totalMarks= this.getIntent().getFloatExtra("totalMarks",0);
            gainMarksText.setText(String.valueOf(gainMarks));
            totalMarksText.setText(String.valueOf(totalMarks));
        }
        else{
            Log.i("Nothing Comes!","in Congo");
        }

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(CongoActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}