package com.example.kahoot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class chooseActivity extends AppCompatActivity {
Button teacherBtn,stdBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        teacherBtn=findViewById(R.id.teacherBtn);
        stdBtn=findViewById(R.id.stdbtn);

        teacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(chooseActivity.this,loginActivity.class);
                startActivity(intent);
            }
        });
        stdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(chooseActivity.this,loginActivity.class);
                startActivity(intent);
            }
        });
    }
}