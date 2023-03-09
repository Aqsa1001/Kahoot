package com.example.kahoot;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class profileFragment extends Fragment {
    TextView accountSetting,report,logout;
    String userName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_profile, container, false);
        accountSetting= view.findViewById(R.id.accountSetting);
        report= view.findViewById(R.id.report);
        logout= view.findViewById(R.id.logout);

        if(this.getArguments() != null) {
            Log.i("ok kr", "hmmmmmmmk");
            userName = this.getArguments().getString("userName", "");
            Log.i("ok kr", userName);

        }

        accountSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ok", userName);
                Intent intent=new Intent(getActivity(),accountSetting.class);
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pending
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}