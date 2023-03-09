package com.example.kahoot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.logging.LogManager;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        if(getIntent().hasExtra("userName")){
            userName = getIntent().getStringExtra("userName");
        }
        else{
            Log.i("oye", "Data ni aya");
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_home:
                homeFragment homeFragment= new homeFragment();
                FragmentManager fragManager= getSupportFragmentManager();
                FragmentTransaction fragTransaction= fragManager.beginTransaction();
                fragTransaction.replace(R.id.container,homeFragment).commit();
                return true;

            case R.id.navigation_create:
                pinCreateFragment pinCreatequizFragment= new pinCreateFragment();
                FragmentManager fragmentManager= getSupportFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                Bundle bundle= new Bundle();
                bundle.putString("userName",userName);
                pinCreatequizFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container,pinCreatequizFragment).commit();
                return true;

            case R.id.navigation_join:
                joinFragment quizJoinFrag= new joinFragment();
                FragmentManager fragMan= getSupportFragmentManager();
                FragmentTransaction fragTran= fragMan.beginTransaction();
                Bundle Bundles= new Bundle();
                Bundles.putString("userName",userName);
                quizJoinFrag.setArguments(Bundles);
                fragTran.replace(R.id.container,quizJoinFrag).commit();
                return true;
            case R.id.navigation_profile:
                profileFragment ProfileFragment= new profileFragment();
                FragmentManager FragmentManager= getSupportFragmentManager();
                FragmentTransaction FragmentTransaction= FragmentManager.beginTransaction();
                Bundle bundle1= new Bundle();
                bundle1.putString("userName",userName);
                ProfileFragment.setArguments(bundle1);
                FragmentTransaction.replace(R.id.container,ProfileFragment).commit();
                return true;
        }
        return false;
    }
}