package com.devsaidur.saidur.playnearn.ui.wallet;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.devsaidur.saidur.playnearn.Const;
import com.devsaidur.saidur.playnearn.database.Spref;
import com.devsaidur.saidur.playnearn.models.User;
import com.devsaidur.saidur.playnearn.models.Withdraw;
import com.devsaidur.saidur.playnearn.ui.logsing.Signup;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.devsaidur.saidur.playnearn.R;
import com.devsaidur.saidur.playnearn.databinding.FragmentWalletBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class WalletFragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseDatabase fdb;
    DatabaseReference fdr, fdr2;
    Spref spref;
    private HashMap<String, String> getUserData;
    String savedMobile, saveuid;


    private WalletViewModel walletViewModel;
    private FragmentWalletBinding FWBind;
    Dialog popup_diposit, popup_withdraw;
    //diposit
    AutoCompleteTextView diposit_amount, pay_type;
    TextInputEditText diposit_number, ref_code;
    RelativeLayout btn_diposit;

    //withdraw
    AutoCompleteTextView withdraw_amount, withdraw_type;
    TextInputEditText withdraw_number, sec_code;
    RelativeLayout btn_withdraw;

    List<String> paytype = new ArrayList<>();
    List<String> payamount = new ArrayList<>();
    ArrayAdapter<String> paytypeAdapter;
    ArrayAdapter<String> payamountAdapter;
    String typeData, dipositData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        walletViewModel =
                new ViewModelProvider(this).get(WalletViewModel.class);
        View root = inflater.inflate(R.layout.fragment_wallet, container, false);
        FWBind = FragmentWalletBinding.inflate(inflater, container, false);
        View v = FWBind.getRoot();
        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();
        fdr = fdb.getReference(Const.adminDash);
        fdr2 = fdb.getReference(Const.userDash);

        spref = new Spref(getActivity());
        getUserData = spref.getUserInfo();
        savedMobile = getUserData.get(spref.UserMobile);


     /*   walletViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });*/
     /*   FWBind.mbl.setAlpha(0.6f);
        FWBind.tops.setBackgroundColor(0);*/

        rechargeData();
        iniPopup();
        iniWitdraw();
        FWBind.cvRecharge.setOnClickListener(v1 -> {
            popup_diposit.show();
        });

        FWBind.cvWithdraw.setOnClickListener(v1 -> {
            popup_withdraw.show();
        });

        WithdrawTopData();

        return v;
    }


    private void WithdrawTopData() {
        Toast.makeText(getActivity(), "Function call", Toast.LENGTH_SHORT).show();
        //Withdraw req data
        fdr.child(Const.withdrawrequest).child(savedMobile).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Exist! Do whatever.
                    String withdrawAmount = snapshot.child("withdrawAmount").getValue().toString();
                    String withdrawDate = snapshot.child("withdrawDate").getValue().toString();
                    String withdrawStatus = snapshot.child("withdrawStatus").getValue().toString();
                    FWBind.llwithdrawstatus.setVisibility(View.VISIBLE);
                    FWBind.withdrawReqtk.setText(withdrawAmount);
                    // FWBind.withdrawdate.setText(withdrawDate);
                    if (withdrawStatus.equals("0")) {
                        FWBind.withdrawstatus.setText("Pending...");
                        FWBind.withdrawdate.setText(withdrawDate);
                    } else {
                        FWBind.withdrawstatus.setText("Approved");
                        FWBind.withdrawdate.setText(withdrawDate);
                    }


                } else {
                    // Don't exist! Do something.
                    FWBind.withdrawReqtk.setText("0");
                    FWBind.llwithdrawstatus.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        fdr2.child("Users").child(savedMobile).child("account").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    // Exist! Do whatever.
                    String currentBal = snapshot.child("current_bal").getValue().toString();
                    FWBind.currentbal.setText(currentBal);

                } else {
                    // Don't exist! Do something.
                    FWBind.currentbal.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void diposit_ReqToDB() {
        //write database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");

        HashMap<Object, String> dipositreq = new HashMap<>();
        dipositreq.put("user_name", "abc");
        dipositreq.put("user_phon", "abc");
        dipositreq.put("user_id", "abc");
        dipositreq.put("diposit_amount", dipositData);
        dipositreq.put("diposit_type", typeData);
        dipositreq.put("diposit_number", "abc");
        dipositreq.put("diposit_status", "req");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("TAG", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    private void rechargeData() {
        paytype.add("BKash");
        paytype.add("Nagad");
        paytype.add("Rocket");
        paytypeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, paytype);

        payamount.add("50 Tk");
        payamount.add("100 Tk");
        payamount.add("200 Tk");
        payamount.add("500 Tk");
        payamountAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, payamount);

    }

    private void iniPopup() {
        popup_diposit = new Dialog(getContext());
        popup_diposit.setContentView(R.layout.popup_recharge);
        //popup_diposit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // popup_diposit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popup_diposit.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        popup_diposit.getWindow().getAttributes().gravity = Gravity.CENTER;

        // ini popup widgets
        diposit_amount = popup_diposit.findViewById(R.id.actv_dipositamount);
        pay_type = popup_diposit.findViewById(R.id.actv_paytype);

        diposit_number = popup_diposit.findViewById(R.id.actv_nmbr);
        ref_code = popup_diposit.findViewById(R.id.actv_refcode);
        btn_diposit = popup_diposit.findViewById(R.id.btn_diposit);

        pay_type.setAdapter(paytypeAdapter);
        pay_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                typeData = parent.getItemAtPosition(position).toString();
            }
        });


        diposit_amount.setAdapter(payamountAdapter);
        diposit_amount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dipositData = parent.getItemAtPosition(position).toString();
            }
        });


        btn_diposit.setOnClickListener(v -> {
            popup_diposit.dismiss();
            diposit_ReqToDB();
            final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.navigation_ldokngs);
        });


    }

    private void iniWitdraw() {
        popup_withdraw = new Dialog(getContext());
        popup_withdraw.setContentView(R.layout.popup_withdraw);
        //popup_diposit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // popup_diposit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popup_withdraw.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        popup_withdraw.getWindow().getAttributes().gravity = Gravity.CENTER;


        // ini popup widgets
        withdraw_amount = popup_withdraw.findViewById(R.id.actv_wamount);
        withdraw_type = popup_withdraw.findViewById(R.id.actv_wtype);

        withdraw_number = popup_withdraw.findViewById(R.id.actv_wnmbr);
        sec_code = popup_withdraw.findViewById(R.id.actv_seccode);
        btn_withdraw = popup_withdraw.findViewById(R.id.btn_withdraw);
        // rechargeData();
        withdraw_type.setAdapter(paytypeAdapter);
        withdraw_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                typeData = parent.getItemAtPosition(position).toString();
            }
        });


        withdraw_amount.setAdapter(payamountAdapter);
        withdraw_amount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dipositData = parent.getItemAtPosition(position).toString();
            }
        });
        withdraw_ReqToDB();

    }

    private void withdraw_ReqToDB(/*Withdraw withdrawReq*/) {
        btn_withdraw.setOnClickListener(v -> {

            fdr2.child("Users").child(savedMobile).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot != null) {
                        User instant_data = snapshot.getValue(User.class);
                        String today = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        Withdraw withdrawReq = new Withdraw(savedMobile, sec_code.getText().toString(), instant_data.getId(), instant_data.getUserName(),
                                instant_data.getUserEmail(), withdraw_amount.getText().toString(), typeData, withdraw_number.getText().toString(),
                                "0", today);

                        popup_withdraw.dismiss();
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            fdr.child(Const.withdrawrequest).child(savedMobile).setValue(withdrawReq).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(), "With draw requestsend", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        }

                        Toast.makeText(getActivity(), "seccode "+sec_code.getText().toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), "withdraw_amount "+withdraw_amount.getText().toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), "typeData "+typeData, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), "withdraw_number "+withdraw_number.getText().toString(), Toast.LENGTH_SHORT).show();
                        // withdraw_ReqToDB(withdrawReq);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }
}