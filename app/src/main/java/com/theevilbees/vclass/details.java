package com.theevilbees.vclass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.os.Bundle;

public class details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

       userType u = new userType();
        FragmentManager fm  = getSupportFragmentManager();//FragmentManager();
        FragmentTransaction t= fm.beginTransaction();

        t.add(R.id.frag_block,u);
        t.addToBackStack(null);
        t.commit();



    }
}