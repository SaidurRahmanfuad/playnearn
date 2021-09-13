package com.devsaidur.saidur.playnearn.ui.home.ldokng;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.devsaidur.saidur.playnearn.Const;
import com.devsaidur.saidur.playnearn.R;
import com.devsaidur.saidur.playnearn.database.Spref;
import com.devsaidur.saidur.playnearn.databinding.FragmentLdoKngmsBinding;
import com.devsaidur.saidur.playnearn.models.Match;
import com.devsaidur.saidur.playnearn.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LdoKngms extends Fragment implements LdoKngIf{
    Spref sessionManagement;
    private HashMap<String, String> getUserData;
    String savedMobile;

    FirebaseDatabase fdb;
    DatabaseReference dbr;
    String cld;

    LdomatchAdapter ldomatchAdapter;
    FragmentLdoKngmsBinding fragmentLdoKngmsBinding;
    List<Match> matchList=new ArrayList<>();

    public LdoKngms() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentLdoKngmsBinding = FragmentLdoKngmsBinding.inflate(getLayoutInflater());
        View v = fragmentLdoKngmsBinding.getRoot();
        sessionManagement = new Spref(getActivity());
        getUserData = sessionManagement.getUserInfo();
        savedMobile = getUserData.get(sessionManagement.UserMobile);
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_ldo_kngms, container, false);
        fdb=FirebaseDatabase.getInstance();
        dbr=fdb.getReference(Const.adminDash);
        if(Const.MatchVal.equals("Ludo_King"))
        {
            cld="Ludo_King";
        }
        getAllMatchData();

        //  .
        return v;
    }

    private void getAllMatchData() {

        Toast.makeText(getActivity(), cld, Toast.LENGTH_SHORT).show();
        dbr.child("Matches").child(cld).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot!=null)
                {
                    for (DataSnapshot ds:snapshot.getChildren()){
                        Match am=ds.getValue(Match.class);
                        //alluserList.add(ds.getValue().toString());
                        matchList.add(am);
                        ldomatchAdapter = new LdomatchAdapter(getActivity(), matchList,LdoKngms.this::checkbal);
                        fragmentLdoKngmsBinding.rvLdokngmtch.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                        fragmentLdoKngmsBinding.rvLdokngmtch.setAdapter(ldomatchAdapter);
                        //am.account.getCurrent_bal();
                    }

                }
                else {
                    Toast.makeText(getActivity(), "No Match Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void checkbal(int pos, String mfee,String mcat,String mid,String seat) {
        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference(Const.userDash);
        dbr.child("Users").child(savedMobile).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user_data = snapshot.getValue(User.class);
               boolean low= Integer.parseInt(mfee)>Integer.parseInt(user_data.getAccount().getCurrent_bal());
                if(!low)
                {
                    //Check is balance avilabel
                    int upval= Integer.parseInt(user_data.getAccount().getCurrent_bal())-Integer.parseInt(mfee);
                    dbr.child("Users").child(savedMobile).child("account").child("current_bal").setValue(String.valueOf(upval))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), "After pay your balance is : "+String.valueOf(upval), Toast.LENGTH_SHORT).show();

                                    updateSeat();

                                    //Update seat
                                    int st=Integer.parseInt(seat)-1;
                                    DatabaseReference admindash = FirebaseDatabase.getInstance().getReference(Const.userDash);
                                    admindash.child("Matches").child(mcat).child(mid).child("match_seat").setValue(String.valueOf(st)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            ldomatchAdapter.notifyDataSetChanged();
                                            showRoomId();
                                        }
                                    });

                                    buttonChange();

                                }
                            });

                }
                else {
                    Toast.makeText(getActivity(), "Not Enough Balance", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void updateSeat() {


    }
}