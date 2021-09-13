package com.devsaidur.saidur.playnearn.ui.logsing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.devsaidur.saidur.playnearn.Const;
import com.devsaidur.saidur.playnearn.MainActivity;
import com.devsaidur.saidur.playnearn.database.Spref;
import com.devsaidur.saidur.playnearn.databinding.ActivityLoginBinding;
import com.devsaidur.saidur.playnearn.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class Login extends AppCompatActivity {
    private ActivityLoginBinding ActLBind;
    BlurView blurView;
    private FirebaseAuth mAuth;
    Spref sessionManagement;
    private HashMap<String, String> getUserData;
    ProgressDialog pd;
    String savedMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_login);
        ActLBind = ActivityLoginBinding.inflate(getLayoutInflater());
        sessionManagement = new Spref(Login.this);
        getUserData = sessionManagement.getUserInfo();
        savedMobile = getUserData.get(sessionManagement.UserMobile);
        setContentView(ActLBind.getRoot());
        mAuth = FirebaseAuth.getInstance();
        /*ActionBar actionBar=getSupportActionBar();
        actionBar.hide();*/
        BlurBack();
        ActLBind.loginBtn.setOnClickListener(v -> {
            signinfun();
        });
        ActLBind.gotoregi.setOnClickListener(v -> {
            Intent gotoregi = new Intent(Login.this, Signup.class);
            startActivity(gotoregi);
        });
    }

    private void signinfun() {
        pd = new ProgressDialog(Login.this);
        pd.setMessage("Checking credential...");
        mAuth.signInWithEmailAndPassword(ActLBind.loginEmail.getText().toString().trim(), ActLBind.loginPassword.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("log", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            sessionManagement.saveSessaionUid(user.getUid());
                           // String uid = mAuth.getCurrentUser().getUid();
                           // access(user);
                            Intent gotomain = new Intent(Login.this, MainActivity.class);
                            startActivity(gotomain);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("log", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    private void access(FirebaseUser user) {
        if (user != null) {
            // Name, email address, and profile photo Url
          /*  String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();*/

            // Check if user's email is verified
            // boolean emailVerified = user.isEmailVerified();
            //user.getUid();

            /*Toast.makeText(Login.this,"Befor "+Const.userPhone, Toast.LENGTH_SHORT).show();
            Toast.makeText(Login.this,"Befor "+Const.userDash, Toast.LENGTH_SHORT).show();
            Toast.makeText(Login.this,"Befor "+Const.userPhone, Toast.LENGTH_SHORT).show();*/

            DatabaseReference dbr = FirebaseDatabase.getInstance().getReference(Const.userDash);
            dbr.child("Users").child(savedMobile).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    /*User user_data=new User();
                    user_data=snapshot.getValue(User.class);*/
                    User user_data = snapshot.getValue(User.class);
                    //Toast.makeText(Login.this, user_data.getId(), Toast.LENGTH_SHORT).show();
                   /* Toast.makeText(Login.this, user_data.getUserEmail(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Login.this, user_data.getUserName(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Login.this, user_data.getUserPhone(), Toast.LENGTH_SHORT).show();*/

                  /*  //for arry list type data
                    for(DataSnapshot ds:snapshot.getChildren())
                    {
                        list.add(ds.getValue());
                    }*/
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void BlurBack() {
        float radius = 2f;

        View decorView = getWindow().getDecorView();
        //ViewGroup you want to start blur from. Choose root as close to BlurView in hierarchy as possible.
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();

/*        blurView.setupWith(ActLBind.getRoot())
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(this))
                .setBlurRadius(radius)
                .setBlurAutoUpdate(true)
                .setHasFixedTransformationMatrix(true); // Or false if it's in a scrolling container or might be animated*/

        ActLBind.blurView.setupWith(ActLBind.getRoot())
                .setFrameClearDrawable(ActLBind.getRoot().getBackground())//;windowBackground
                .setBlurAlgorithm(new RenderScriptBlur(this))
                .setBlurRadius(radius)
                .setBlurAutoUpdate(true)
                .setHasFixedTransformationMatrix(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //reload();
            Intent gotomain = new Intent(Login.this, MainActivity.class);
            startActivity(gotomain);
            finish();
        }
       // mAuth.signOut();
    }
}