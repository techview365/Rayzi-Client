package com.example.chiky.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chiky.R;
import com.example.chiky.SessionManager;
import com.example.chiky.retrofit.Const;
import com.google.android.exoplayer2.SimpleExoPlayer;

public abstract class BaseFragment extends Fragment {
    public SessionManager sessionManager;
    public SimpleExoPlayer player;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getActivity());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public static boolean isRTL(Context context) {

        Configuration config = context.getResources().getConfiguration();
        return config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }

    public void doTransition(int type) {

        if (getActivity() != null) {
            if (type == Const.BOTTOM_TO_UP) {

                getActivity().overridePendingTransition(R.anim.enter_from_bottom, R.anim.exit_none);
            } else if (type == Const.UP_TO_BOTTOM) {
                getActivity().overridePendingTransition(R.anim.exit_none, R.anim.enter_from_up);

            }

        }
    }
}
