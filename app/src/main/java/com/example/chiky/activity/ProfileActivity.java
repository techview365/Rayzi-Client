package com.example.chiky.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.chiky.MainApplication;
import com.example.chiky.R;
import com.example.chiky.SessionManager;
import com.example.chiky.databinding.ActivityProfile1Binding;
import com.example.chiky.modelclass.UserRoot;
import com.example.chiky.posts.FeedGridActivity;
import com.example.chiky.reels.VideoListGridActivity;
import com.example.chiky.retrofit.Const;
import com.example.chiky.retrofit.UserApiCall;
import com.example.chiky.user.EditProfileActivity;
import com.example.chiky.user.FollowersListActivity;
import com.example.chiky.user.MyLevelListActivity;
import com.example.chiky.user.complain.ComplainListActivity;
import com.example.chiky.user.complain.CreateComplainActivity;
import com.example.chiky.user.freeCoins.FreeDiamondsActivity;
import com.example.chiky.user.vip.VipPlanActivity;
import com.example.chiky.user.wallet.MyWalletActivity;
import com.example.chiky.viewModel.ViewModelFactory;
import com.google.gson.Gson;

public class ProfileActivity extends BaseActivity {

    ActivityProfile1Binding binding;
    SessionManager sessionManager;
    UserRoot.User user;
    UserApiCall userApiCall;
    private ProfileViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_1);

        viewModel = ViewModelProviders.of(this, new ViewModelFactory(new ProfileViewModel()).createFor()).get(ProfileViewModel.class);
        binding.setViewModel(viewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        sessionManager = new SessionManager(ProfileActivity.this);
        userApiCall = new UserApiCall(ProfileActivity.this);

        viewModel.isLoading.set(true);
        user = sessionManager.getUser();
        userApiCall.getUser(user -> {
            sessionManager.saveUser(user);
            this.user = user;
            initView();

            viewModel.isLoading.set(false);
        });
        initListener();
    }

    private void initListener() {

        binding.btnSetting.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, SettingActivity.class)));
        binding.lytMyPost.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, FeedGridActivity.class).putExtra(Const.DATA, new Gson().toJson(user))));
        binding.lytMyVideos.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, VideoListGridActivity.class).putExtra(Const.DATA, new Gson().toJson(user))));
        binding.lytFollowing.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, FollowersListActivity.class).putExtra(Const.TYPE, 1).putExtra(Const.USERID, user.getId())));
        binding.lytFollowrs.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, FollowersListActivity.class).putExtra(Const.TYPE, 2).putExtra(Const.USERID, user.getId())));
        binding.btnEditProfile.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class)));
        binding.tvLevel.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, MyLevelListActivity.class)));
        binding.lytVIP.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, VipPlanActivity.class)));
        binding.lytWallet.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, MyWalletActivity.class)));
        binding.lytFreeDimonds.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, FreeDiamondsActivity.class)));
        binding.lytSupport.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, CreateComplainActivity.class)));
        binding.lytComplains.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, ComplainListActivity.class)));
        binding.lytRecord.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, RecordActivity.class)));

        binding.copy.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) ProfileActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("", user.getUniqueId());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(ProfileActivity.this, getResources().getString(R.string.Copied_successfully), Toast.LENGTH_SHORT).show();
        });

    }

    @SuppressLint("SetTextI18n")
    private void initView() {

        if (!isFinishing()) {
            if (!user.getCoverImage().isEmpty()) {
                Glide.with(this).load(user.getCoverImage())
                        .placeholder(R.drawable.default_cover)
                        .apply(MainApplication.requestOptions)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop().into(binding.imgUser1);
            } else {
                binding.imgUser1.setImageResource(R.drawable.default_cover);
            }
        binding.imgUser.setUserImage(user.getImage(), user.isIsVIP(), ProfileActivity.this, 40);
        }
        binding.tvName.setText(user.getName());
        binding.tvAge.setText(String.valueOf(user.getAge()));
        binding.tvFollowrs.setText(String.valueOf(user.getFollowers()));
        binding.tvLevel.setText(user.getLevel().getName());
        binding.tvFollowing.setText(String.valueOf(user.getFollowing()));
        binding.tvUserName.setText(String.valueOf(user.getUsername()));

        if (user.getUniqueId() == null || user.getUniqueId().isEmpty()) {
            binding.tvUserId.setVisibility(View.GONE);
            binding.copy.setVisibility(View.GONE);
        } else {
            binding.tvUserId.setVisibility(View.VISIBLE);
            binding.copy.setVisibility(View.VISIBLE);
            binding.tvUserId.setText("ID:" + user.getUniqueId());
        }

        if (user.getGender().equalsIgnoreCase(Const.MALE)) {
            binding.imgGender.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.male));
        } else {
            binding.imgGender.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.female));
        }

        if (isRTL(ProfileActivity.this)) {
            binding.tvWallet.setGravity(Gravity.END);
            binding.tvMyRelite.setGravity(Gravity.END);
            binding.tvMyPost.setGravity(Gravity.END);
            binding.tvFreeDiamond.setGravity(Gravity.END);
            binding.tvBecomeVip.setGravity(Gravity.END);
            binding.tvHaveIssue.setGravity(Gravity.END);
            binding.tvMyComplain.setGravity(Gravity.END);

            // Following box
            binding.folowingbox.setGravity(Gravity.START);

            binding.imgWallet.setScaleX(isRTL(ProfileActivity.this) ? -1 : 1);
            binding.imgMyRelites.setScaleX(isRTL(ProfileActivity.this) ? -1 : 1);
            binding.imgMyPost.setScaleX(isRTL(ProfileActivity.this) ? -1 : 1);
            binding.imgFreeDiamond.setScaleX(isRTL(ProfileActivity.this) ? -1 : 1);
            binding.imgBecomeVip.setScaleX(isRTL(ProfileActivity.this) ? -1 : 1);
            binding.imgHaveIssue.setScaleX(isRTL(ProfileActivity.this) ? -1 : 1);
            binding.imgMyComplain.setScaleX(isRTL(ProfileActivity.this) ? -1 : 1);
        }

    }

    public class ProfileViewModel extends ViewModel {
        public ObservableBoolean isLoading = new ObservableBoolean(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}