package com.theevilbees.vclass;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class phoneAuh extends Fragment {
    public interface  phoneAuhListener{
        void  onInput(CharSequence input);
    }

    public phoneAuh() {
        // Required empty public constructor
    }

    protected EditText editTextMobile;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_phone_auh, container, false);

        editTextMobile = view.findViewById(R.id.editTextMobile);
        Button bt= view.findViewById(R.id.buttonContinue);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = editTextMobile.getText().toString().trim();
                if (mobile.isEmpty() || mobile.length() < 10) {
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("key",editTextMobile.getText().toString());
                FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction t = fm.beginTransaction();
                Fragment fr = new phoneAuth2();
                t.replace(R.id.frag_block, fr);
                fr.setArguments(bundle);
                t.addToBackStack(null);

                t.commit();
            }
        });
        return view;
    }



}