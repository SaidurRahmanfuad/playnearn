package com.devsaidur.saidur.playnearn.ui.logsing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.devsaidur.saidur.playnearn.Const;
import com.devsaidur.saidur.playnearn.database.Spref;
import com.devsaidur.saidur.playnearn.databinding.ActivitySignupBinding;
import com.devsaidur.saidur.playnearn.models.Account;
import com.devsaidur.saidur.playnearn.models.PlayHistory;
import com.devsaidur.saidur.playnearn.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;


public class Signup extends AppCompatActivity {
    ActivitySignupBinding ActSBind;
    private FirebaseAuth mAuth;
    FirebaseDatabase fdb;
    DatabaseReference fdbr;
    Spref spref;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActSBind = ActivitySignupBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_signup);
        setContentView(ActSBind.getRoot());
        spref=new Spref(Signup.this);
        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();
        fdbr = fdb.getReference(Const.userDash);

      /*  ActionBar actionBar = getSupportActionBar();
        actionBar.hide();*/
        //  Blurry.with(Signup.this).capture(ActSBind.getRoot()).into(ActSBind.cardvRegi);
        //  Blurry.with(Signup.this).radius(25).sampling(2).onto(ActSBind.cardvRegi);

        ActSBind.signupJoinBtn.setOnClickListener(v -> {

            if (!Validate_name() && !Validate_mail() && !Validate_phone() && !Validate_pass()) {
                return;
            } else {
                signUpFun();
            }

        });


    }

    private boolean Validate_pass() {
        if (ActSBind.signupPassword.getText().toString().trim().isEmpty()) {
            ActSBind.signupPassword.setError("Field can't Empty");
            return false;
        } else {
            ActSBind.signupPassword.setError(null);
            return true;
        }
    }

    private boolean Validate_mail() {
        // if (Patterns.EMAIL_ADDRESS.matcher(ActSBind.signupEmail.getText().toString().trim()).matches) {
        if (ActSBind.signupEmail.getText().toString().trim().isEmpty()) {
            ActSBind.signupEmail.setError("Field can't Empty");
            return false;
        } else {
            ActSBind.signupEmail.setError(null);
            return true;
        }
    }

    private boolean Validate_phone() {
        if (ActSBind.signupMobile.getText().toString().trim().isEmpty()) {
            ActSBind.signupMobile.setError("Field can't Empty");
            return false;
        } else {
            ActSBind.signupMobile.setError(null);
            return true;
        }
    }

    private boolean Validate_name() {
        if (ActSBind.signupFullname.getText().toString().trim().isEmpty() || ActSBind.signupFullname.getText().toString().trim().length() < 6) {
            ActSBind.signupFullname.setError("Field can't Empty");
            ActSBind.signupFullname.requestFocus();
            return false;
        } else {
            ActSBind.signupFullname.setError(null);
            return true;
        }
    }

    private void signUpFun() {
        pd = new ProgressDialog(Signup.this);
        pd.setMessage("Loading..");
        pd.show();
        String email = ActSBind.signupEmail.getText().toString().trim();
        String password = ActSBind.signupPassword.getText().toString().trim();
        String username = ActSBind.signupFullname.getText().toString().trim();
        String userphone = ActSBind.signupMobile.getText().toString().trim();
        String useremail = ActSBind.signupEmail.getText().toString().trim();
        String userimg = "";

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "createUserWithEmail:success");
                            String uid = mAuth.getCurrentUser().getUid();
                            //save phn for get data
                            spref.saveSessaion(uid,username,userphone,useremail,"2");
                            Account account=new Account("0","0");
                            PlayHistory playHistory=new PlayHistory("","","","");
                            User userdata = new User(uid, username, userphone, useremail, userimg,account,playHistory);
                            storeUser(userdata, userphone, uid);
                            // updateUI(user);
                        } else {
                            pd.dismiss();
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(Signup.this, "You Already have an account",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Signup.this, task.getException().getMessage().toString(),
                                        Toast.LENGTH_SHORT).show();
                                Toast.makeText(Signup.this, "Authentication failed",
                                        Toast.LENGTH_SHORT).show();
                                // updateUI(null);
                            }

                        }
                    }
                });
    }

    private void storeUser(User userdata, String userphone, String uid) {

        //fdbr.child(uid).setValue(userdata).addOnSuccessListener(new OnSuccessListener<Void>() {
        fdbr.child("Users").child(userphone).setValue(userdata).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                Toast.makeText(Signup.this, "Done", Toast.LENGTH_SHORT).show();
                Intent gotologin = new Intent(Signup.this, Login.class);
                startActivity(gotologin);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Signup.this, "Done", Toast.LENGTH_SHORT).show();
            }
        });
    }
}