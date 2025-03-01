package com.example.chiky.liveStreamming;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.chiky.MainApplication;
import com.example.chiky.R;
import com.example.chiky.activity.BaseActivity;
import com.example.chiky.databinding.ActivityLiveSummaryBinding;
import com.example.chiky.modelclass.LiveSummaryRoot;
import com.example.chiky.retrofit.Const;
import com.example.chiky.retrofit.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveSummaryActivity extends BaseActivity {
    ActivityLiveSummaryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_live_summary);
        Intent intent = getIntent();
        String liveStreamingId = intent.getStringExtra(Const.DATA);
        if (!liveStreamingId.isEmpty()) {
            getLiveSummaryData(liveStreamingId);
        }

        if (!isFinishing()) {
            binding.imgUser.setUserImage(sessionManager.getUser().getImage(), sessionManager.getUser().isIsVIP(), LiveSummaryActivity.this, 20);
            Glide.with(this).load(R.drawable.girl)
                    .apply(MainApplication.requestOptions)
                    .centerCrop().into(binding.imageback);
        }
        binding.tvName.setText(sessionManager.getUser().getName());
        binding.btnHomePage.setOnClickListener(v -> onBackPressed());

    }

    private void getLiveSummaryData(String liveStreamingId) {
        binding.loder.setVisibility(View.VISIBLE);
        Call<LiveSummaryRoot> call = RetrofitBuilder.create().getLiveSummary(liveStreamingId);
        call.enqueue(new Callback<LiveSummaryRoot>() {
            @Override
            public void onResponse(Call<LiveSummaryRoot> call, Response<LiveSummaryRoot> response) {
                if (response.code() == 200) {
                    if (response.body().isStatus()) {
                        LiveSummaryRoot.LiveStreamingHistory summary = response.body().getLiveStreamingHistory();
                        binding.tvComments.setText(String.valueOf(summary.getComments()));
                        binding.tvDuration.setText(summary.getDuration());
                        binding.tvIncresedFans.setText("+" + summary.getFans());
                        binding.tvJoinedUsers.setText(String.valueOf(summary.getUser()));
                        binding.tvRcoins.setText("+" + summary.getRCoin());
                        binding.tvRecivedGifts.setText(String.valueOf(summary.getGifts()));
                        binding.loder.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<LiveSummaryRoot> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }
}