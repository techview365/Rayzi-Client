package com.example.chiky.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.chiky.R;
import com.example.chiky.activity.BaseFragment;
import com.example.chiky.databinding.FragmentHomeBinding;
import com.example.chiky.liveStreamming.LiveFragmentMain;


public class HomeFragment extends BaseFragment {

    FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        openFragmet(new LiveFragmentMain());

        return binding.getRoot();
    }

    public void openFragmet(Fragment fragment) {
        getChildFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frameHome, fragment).commit();
    }


}