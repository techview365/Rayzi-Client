package com.example.chiky.posts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.chiky.BuildConfig;
import com.example.chiky.R;
import com.example.chiky.activity.BaseActivity;
import com.example.chiky.comments.CommentLikeListActivity;
import com.example.chiky.databinding.ActivityFeedListBinding;
import com.example.chiky.databinding.ItemFeed1Binding;
import com.example.chiky.modelclass.PostRoot;
import com.example.chiky.retrofit.CommenApiCalling;
import com.example.chiky.retrofit.Const;
import com.example.chiky.user.guestUser.GuestActivity;
import com.example.chiky.viewModel.FeedListViewModel;
import com.example.chiky.viewModel.ViewModelFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.util.ContentMetadata;
import io.branch.referral.util.LinkProperties;

public class FeedListActivity extends BaseActivity implements FeedAdapter.OnPostClickListner {
    ActivityFeedListBinding binding;
    private CommenApiCalling commenApiCalling;
    private FeedListViewModel viewModel;
    private String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feed_list);

        commenApiCalling = new CommenApiCalling(this);
        viewModel = ViewModelProviders.of(this, new ViewModelFactory(new FeedListViewModel()).createFor()).get(FeedListViewModel.class);
        viewModel.init(this, Const.USER);
        binding.setViewModel(viewModel);
        Intent intent = getIntent();
        int position = intent.getIntExtra(Const.POSITION, 0);
        String data = intent.getStringExtra(Const.DATA);
        userId = intent.getStringExtra(Const.USERID);
        if (data != null && !data.isEmpty()) {
            List<PostRoot.PostItem> postItems = new Gson().fromJson(data, new TypeToken<ArrayList<PostRoot.PostItem>>() {

            }.getType());
            viewModel.start = postItems.size() - Const.LIMIT;  // isloadmore always increment limit in start feild

            viewModel.feedAdapter.addData(postItems);
            binding.rvFeed.scrollToPosition(position);
            if (userId == null || userId.isEmpty()) {
                userId = postItems.get(0).getUserId();
            }
        }

        viewModel.feedAdapter.setOnPostClickListner(this);

        binding.swipeRefresh.setOnRefreshListener((refreshLayout) -> {
            viewModel.getPostData(false, userId);
        });

        binding.swipeRefresh.setOnLoadMoreListener(refreshLayout -> {
            viewModel.getPostData(true, userId);
        });

        viewModel.isLoadCompleted.observe(this, aBoolean -> {
            binding.swipeRefresh.finishRefresh();
            binding.swipeRefresh.finishLoadMore();
            viewModel.isFirstTimeLoading.set(false);
            viewModel.isLoadMoreLoading.set(false);
        });

    }

    @Override
    public void onLikeClick(PostRoot.PostItem postDummy, int position, ItemFeed1Binding binding) {

        boolean isLiked1 = postDummy.isIsLike();
        // binding.likeButton.setLiked(isLiked1);
        int like1;
        if (!isLiked1) {
            like1 = postDummy.getLike() + 1;
        } else {
            like1 = postDummy.getLike() - 1;
        }
        postDummy.setLike(like1);
        postDummy.setLike(!isLiked1);
        binding.tvLikes.setText(String.valueOf(like1));
        viewModel.feedAdapter.notifyItemChanged(position, postDummy);

        commenApiCalling.toggleLikePost(postDummy.getId(), isLiked -> {
            viewModel.feedAdapter.notifyItemChanged(position, postDummy);
        });

      /*  commenApiCalling.toggleLikePost(postDummy.getId(), isLiked -> {

            binding.likeButton.setLiked(isLiked);
            int like;
            if (isLiked) {
                like = postDummy.getLike() + 1;
            } else {
                like = postDummy.getLike() - 1;
            }
            postDummy.setLike(like);
            postDummy.setLike(isLiked);
            binding.tvLikes.setText(String.valueOf(like));
            viewModel.feedAdapter.notifyItemChanged(position, postDummy);
        });*/

    }

    @Override
    public void onCommentListClick(PostRoot.PostItem postDummy) {
        startActivity(new Intent(this, CommentLikeListActivity.class)
                .putExtra(Const.TYPE, CommentLikeListActivity.COMMENTS)
                .putExtra(Const.POSTDATA, new Gson().toJson(postDummy)));
        doTransition(Const.BOTTOM_TO_UP);


    }

    @Override
    public void onLikeListClick(PostRoot.PostItem postDummy) {
        startActivity(new Intent(this, CommentLikeListActivity.class)
                .putExtra(Const.TYPE, CommentLikeListActivity.LIKES)
                .putExtra(Const.POSTDATA, new Gson().toJson(postDummy)));
        doTransition(Const.BOTTOM_TO_UP);


    }

    @Override
    public void onShareClick(PostRoot.PostItem postDummy) {

        BranchUniversalObject buo = new BranchUniversalObject()
                .setCanonicalIdentifier("content/12345")
                .setTitle(postDummy.getCaption())
                .setContentDescription("By : " + postDummy.getName())
                .setContentImageUrl(BuildConfig.BASE_URL + postDummy.getPost())
                .setContentMetadata(new ContentMetadata().addCustomMetadata("type", "POST").addCustomMetadata(Const.DATA, new Gson().toJson(postDummy)));

        LinkProperties lp = new LinkProperties()
                .setChannel("facebook")
                .setFeature("sharing")
                .setCampaign("content 123 launch")
                .setStage("new user")

                .addControlParameter("", "")
                .addControlParameter("", Long.toString(Calendar.getInstance().getTimeInMillis()));

        buo.generateShortUrl(this, lp, (url, error) -> {
            Log.d("TAG", "initListnear: branch url" + url);
            try {
                Log.d("TAG", "initListnear: share");
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareMessage = url;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                Log.d("TAG", "initListnear: " + e.getMessage());
                //e.toString();
            }
        });

    }

    @Override
    public void onMentionClick(String userName) {
        startActivity(new Intent(this, GuestActivity.class).putExtra(Const.USERNAME, userName));
    }


}