package com.theevilbees.vclass;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInAccount user = GoogleSignIn.getLastSignedInAccount(this);
        if(user==null)
        {
            Intent i= new Intent(getApplicationContext(), signIn.class);
            startActivity(i);
        }
        else
        {
            if(getType()==1)
            {
                Intent i= new Intent(getApplicationContext(),student.class);
                startActivity(i);

            }
            else
            {
                Intent i= new Intent(getApplicationContext(),teacher.class);
                startActivity(i);

            }
        }

    }
    int getType()
    {
        return 0;
    }

}