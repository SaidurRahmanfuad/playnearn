package com.devsaidur.saidur.playnearn.ui.home;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.devsaidur.saidur.playnearn.Const;
import com.devsaidur.saidur.playnearn.R;
import com.devsaidur.saidur.playnearn.databinding.FragmentHomeBinding;
import com.devsaidur.saidur.playnearn.ui.home.adapters.TopernrAdapter;
import com.devsaidur.saidur.playnearn.ui.home.datamodel.TopernrModel;
import com.devsaidur.saidur.playnearn.ui.home.datamodel.UserModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding FhomeBind;
    List<TopernrModel> topernrsList = new ArrayList<>();
    TopernrAdapter topernrAdapter;
    Dialog pop_ldokngtype;
    LinearLayout lltype1v1, lltype1vm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        //  View root = inflater.inflate(R.layout.fragment_home, container, false);
        FhomeBind = FragmentHomeBinding.inflate(inflater, container, false);
        View v = FhomeBind.getRoot();
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        FhomeBind.profpic.setOnClickListener(v1 -> {
            Navigation.findNavController(v).navigate(R.id.home_to_profile);
        });
        TopEarnerData();
        iniPopup();
        FhomeBind.btnLdostr.setOnClickListener(v1 -> {
            pop_ldokngtype.show();
        });
        return v;
    }

    private void TopEarnerData() {
        UserModel um = new UserModel();
        um.setId("1");
        um.setName("Dev");
        um.setImage("https://media-exp1.licdn.com/dms/image/C5603AQEmFYeteDGxcg/profile-displayphoto-shrink_200_200/0/1617116156230?e=1635984000&v=beta&t=Z1Qs4aKFrl6HU1DVeebloik7Q_oL_NgOICcx3eXzgdU");
        um.setEarnamount("20,000");
        um.setJoindate("2-5-2021");

        UserModel um1 = new UserModel();
        um1.setId("2");
        um1.setName("Alom");
        um1.setImage("https://pbs.twimg.com/profile_images/1147198595654795264/srF7C_Dl_400x400.jpg");
        um.setEarnamount("17,000");
        um1.setJoindate("2-5-2021");

        UserModel um2 = new UserModel();
        um2.setId("3");
        um2.setName("Noyon");
        um2.setImage("https://ssl.du.ac.bd/fontView/assets/faculty_image/fib_sajibfin06.jpg");
        um.setEarnamount("11,000");
        um2.setJoindate("2-5-2021");

        UserModel um3 = new UserModel();
        um3.setId("4");
        um3.setImage("https://thedailynewnation.com/library/1506266622_9.jpg");
        um3.setName("Kamal");
        um3.setEarnamount("6,300");
        um3.setJoindate("2-5-2021");
        topernrsList.add(new TopernrModel("1", um));
        topernrsList.add(new TopernrModel("2", um1));
        topernrsList.add(new TopernrModel("3", um2));
        topernrsList.add(new TopernrModel("4", um3));

        topernrAdapter = new TopernrAdapter(getActivity(), topernrsList);
        FhomeBind.rvTopearn.setAdapter(topernrAdapter);
        FhomeBind.rvTopearn.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        FhomeBind.rvTopearn.smoothScrollToPosition(FhomeBind.rvTopearn.getAdapter().getItemCount());
    }

    private void iniPopup() {
        pop_ldokngtype = new Dialog(getContext());
        pop_ldokngtype.setContentView(R.layout.popup_ldokngms);
        //popAddQty.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop_ldokngtype.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        pop_ldokngtype.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        pop_ldokngtype.getWindow().getAttributes().gravity = Gravity.CENTER;

        // ini popup widgets
        lltype1v1 = pop_ldokngtype.findViewById(R.id.lltype1v1);
        lltype1vm = pop_ldokngtype.findViewById(R.id.lltype1vm);


        lltype1v1.setOnClickListener(v -> {
            pop_ldokngtype.dismiss();
            final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.navigation_ldokngs);
            Const.MatchVal="Ludo_King";
        });
        lltype1vm.setOnClickListener(v -> {
            Const.MatchVal="Ludo_Star";
        });


    }
}