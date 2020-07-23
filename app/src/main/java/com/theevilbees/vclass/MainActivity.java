package com.theevilbees.vclass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
int x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkUser(new FirebaseCall() {
            @Override
            public void onCallback(GoogleSignInAccount googleSignInAccount) {
                if(googleSignInAccount==null)
                {
                    Intent i= new Intent(getApplicationContext(), signIn.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    readData(new FirebaseCallback() {

                        @Override
                        public void onCallback(String type) {
                            if(type.isEmpty())
                            {
                                Intent i = new Intent(getApplicationContext(), details.class);
                                startActivity(i);
                                finish();

                            }
                            else {
                                if (type.equalsIgnoreCase("teacher")) {
                                    Intent i = new Intent(getApplicationContext(), teacher.class);
                                    startActivity(i);
                                    finish();
                                    Log.d("\n\n type String ", type + " " + x);

                                } else if (type.equalsIgnoreCase("student")) {
                                    Intent i = new Intent(getApplicationContext(), student.class);
                                    startActivity(i);
                                    finish();

                                } else {
                                    Intent i = new Intent(getApplicationContext(), details.class);
                                    startActivity(i);
                                    finish();

                                }

                            }

                        }
                    });

                }
            }
        });


    }
    private  void  checkUser(final  FirebaseCall firebaseCall)
    {
        GoogleSignInAccount user = GoogleSignIn.getLastSignedInAccount(this);
        firebaseCall.onCallback(user);

    }
    private  void  readData(final FirebaseCallback firebaseCallback)
    {
        GoogleSignInAccount user = GoogleSignIn.getLastSignedInAccount(this);
        if(user==null)
        {
            Intent i= new Intent(getApplicationContext(), signIn.class);
            startActivity(i);
            finish();
        }
        else

        {
            //FirebaseUser ur= FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            String email=user.getEmail();
            ((Global)this.getApplication()).setemail(email);

            //String uid = ur.getUid();
            x=-1;
            FirebaseFirestore db= FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(email);
            docRef.get().addOnCompleteListener(this,new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful())
                    {
                        DocumentSnapshot document = task.getResult();
                        String type;
                        if( document.get("type")==null)
                            type="";
                        else
                        {
                         type = (String) document.get("type");}
                        firebaseCallback.onCallback(type);
                        // Log.d("\n\n type String ",type);

                        //    Log.d("Document data \n\n", "DocumentSnapshot data: " + document.getData());

                    }

                    else {
                        Log.d("", "get failed with ", task.getException());
                    }
                }
            });
            String ms=" "+x;
            Log.d("\n\n x=",ms);


        }
    }
    private interface FirebaseCallback{
        void  onCallback(String s);
    }
    private interface FirebaseCall{
        void  onCallback(GoogleSignInAccount googleSignInAccount);
    }

}