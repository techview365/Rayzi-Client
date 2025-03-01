package com.example.chiky.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.chiky.chat.MessageFragment;
import com.example.chiky.liveStreamming.LiveListFragment;
import com.example.chiky.posts.FeedFragmentMain;
import com.example.chiky.videocall.OneToOneFragment;

public class ScreenSlidePagerAdapter extends FragmentStateAdapter {
    public ScreenSlidePagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new LiveListFragment();
        } else if (position == 1) {
            return new OneToOneFragment();
        } else if (position == 2) {
            return new FeedFragmentMain();
        } else if (position == 3) {
            return new MessageFragment();
        } else {
            return new LiveListFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}