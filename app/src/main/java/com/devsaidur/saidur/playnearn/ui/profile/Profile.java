package com.devsaidur.saidur.playnearn.ui.profile;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.devsaidur.saidur.playnearn.Const;
import com.devsaidur.saidur.playnearn.R;
import com.devsaidur.saidur.playnearn.database.Spref;
import com.devsaidur.saidur.playnearn.databinding.FragmentHomeBinding;
import com.devsaidur.saidur.playnearn.databinding.FragmentProfileBinding;
import com.devsaidur.saidur.playnearn.models.User;
import com.devsaidur.saidur.playnearn.ui.logsing.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Profile extends Fragment {


    public Profile() {
        // Required empty public constructor
    }

    private FragmentProfileBinding FprofBind;
    FirebaseAuth mAuth;
    Spref sessionManagement;
    private HashMap<String, String> getUserData;
    ProgressDialog pd;
    String savedMobile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //  View v = inflater.inflate(R.layout.fragment_profile, container, false);
        FprofBind = FragmentProfileBinding.inflate(inflater, container, false);
        View v = FprofBind.getRoot();

        sessionManagement = new Spref(getActivity());
        getUserData = sessionManagement.getUserInfo();
        savedMobile = getUserData.get(sessionManagement.UserMobile);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        access(user);

        //copy secrate code
        FprofBind.copySecratecode.setOnClickListener(v1 -> {
            final android.content.ClipboardManager clipboardManager = (ClipboardManager) getActivity().getApplicationContext()
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            final ClipData clipData = ClipData
                    .newPlainText("text label", FprofBind.showUserscode.getText().toString());
            clipboardManager.setPrimaryClip(clipData);

            Toast.makeText(getActivity(), "Copied!!", Toast.LENGTH_SHORT).show();
        });
        FprofBind.logout.setOnClickListener(v1 -> {
            logout();
        });
        return v;
    }

    private void access(FirebaseUser user) {
        if (user != null) {
            DatabaseReference dbr = FirebaseDatabase.getInstance().getReference(Const.userDash);
            dbr.child("Users").child(savedMobile).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user_data = snapshot.getValue(User.class);
                    FprofBind.showUsername.setText(user_data.getUserEmail());
                    FprofBind.showUseremail.setText(user_data.getUserName());
                    FprofBind.showUserphone.setText(user_data.getUserPhone());
                    FprofBind.showUserscode.setText(user_data.getId());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    private void logout()
    {
        mAuth.signOut();
        Intent in=new Intent(getActivity(),Login.class);
        startActivity(in);
        getActivity().finish();
        sessionManagement.logout();
    }
}