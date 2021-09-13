package com.devsaidur.saidur.playnearn.ui.ongoingmtch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devsaidur.saidur.playnearn.R;
import com.devsaidur.saidur.playnearn.databinding.FragmentOngoingmtchBinding;

public class Ongoingmtch extends Fragment {

    private OngoingViewModel ongoingViewModel;
    FragmentOngoingmtchBinding fragmentOngoingmtchBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ongoingViewModel =
                new ViewModelProvider(this).get(OngoingViewModel.class);
        fragmentOngoingmtchBinding = FragmentOngoingmtchBinding.inflate(getLayoutInflater());
        View v = fragmentOngoingmtchBinding.getRoot();

      /*  ongoingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });*/




        return v;
    }
}