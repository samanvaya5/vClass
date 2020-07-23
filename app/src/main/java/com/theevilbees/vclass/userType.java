package com.theevilbees.vclass;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class userType extends Fragment {

    FirebaseFirestore db;
    GoogleSignInAccount ur;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    final Map<String,Object> da= new HashMap<>();
    String email;CollectionReference users;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_user_type, container, false);
        db= FirebaseFirestore.getInstance();
        //ur= GoogleSignIn.getLastSignedInAccount(getActivity());
        email= ((Global)getActivity().getApplication()).getemail();
        //da.put("uid",uid);
        users = db.collection("users");

        ImageButton bt = (ImageButton) view.findViewById(R.id.studentb);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                da.put("type","student");
                users.document(email).set(da);

                Intent intent = new Intent();
                intent.setClass(getActivity(),student.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
        ImageButton btt = (ImageButton) view.findViewById(R.id.teacherb);
        btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   da.put("type","teacher");
               // users.document(email).set(da);
                FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction t = fm.beginTransaction();
                Fragment fr = new phoneAuh();
                t.replace(R.id.frag_block, fr);
                t.addToBackStack(null);
                t.commit();
            }
        });




        return view;
    }
}