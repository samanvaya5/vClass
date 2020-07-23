package com.theevilbees.vclass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;


public class signIn extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 22;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
        SignInButton b=(SignInButton) findViewById(R.id.sign_in_button);
        b.setOnClickListener(this);

    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.sign_in_button)
            signIn();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Exception exception = task.getException();
            if (task.isSuccessful()) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d("signin", "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("signin", "Google sign in failed", e);
                    // ...
                }
            }
            else
            {
                Log.w("signin",exception.toString());
            }
        }
    }
    int getType()
    {
        return 0;
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("signin", "signInWithCredential:success");
                            // FirebaseUser user = mAuth.getCurrentUser();
                            boolean isNew = Objects.requireNonNull(task.getResult()).getAdditionalUserInfo().isNewUser();
                           Intent i;
                            if(isNew)
                            {
                               i= new Intent(getApplicationContext(),details.class);
                            }
                            else
                            {
                                if(getType()==-1)
                                {
                                    i=new Intent(getApplicationContext(),details.class);
                                }
                                else if(getType()==1)
                                {
                                    i= new Intent(getApplicationContext(),student.class);
                                }
                                else
                                {
                                    i= new Intent(getApplicationContext(),teacher.class);
                                }
                            }

                            startActivity(i);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("signin", "signInWithCredential:failure", task.getException());

                        }

                        // ...
                    }
                });
    }


}
