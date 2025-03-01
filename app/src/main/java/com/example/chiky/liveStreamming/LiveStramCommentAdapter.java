package com.example.chiky.liveStreamming;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chiky.BuildConfig;
import com.example.chiky.MainApplication;
import com.example.chiky.R;
import com.example.chiky.databinding.ItemLivestramCommentBinding;
import com.example.chiky.modelclass.LiveStramComment;
import com.example.chiky.modelclass.UserRoot;

import java.util.ArrayList;
import java.util.List;

public class LiveStramCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW1 = 1;
    private static final int VIEW2 = 2;
    List<LiveStramComment> comments = new ArrayList<>();
    OnCommentClickListener onCommentClickListener;
    private Context context;

    @Override
    public int getItemViewType(int position) {
        //  if (position==0) return VIEW1;
        return VIEW2;
    }

    public OnCommentClickListener getOnCommentClickListner() {
        return onCommentClickListener;
    }

    public void setOnCommentClickListner(OnCommentClickListener onCommentClickListener) {
        this.onCommentClickListener = onCommentClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        if (viewType == VIEW1) {
            return new NoticeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_livestream_comment_1, parent, false));
        }
        return new CommentViewHOlder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_livestram_comment, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof CommentViewHOlder) {
            ((CommentViewHOlder) holder).setCommentData(position);
        }

    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void addSingleComment(LiveStramComment liveStramCommentDummy) {
        this.comments.add(0, liveStramCommentDummy);
        notifyItemInserted(0);
    }

    public interface OnCommentClickListener {
        void onClickComment(UserRoot.User userDummy);
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder {

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class CommentViewHOlder extends RecyclerView.ViewHolder {
        ItemLivestramCommentBinding binding;

        public CommentViewHOlder(@NonNull View itemView) {
            super(itemView);
            binding = ItemLivestramCommentBinding.bind(itemView);

        }

        public void setCommentData(int position) {
            LiveStramComment comment = comments.get(position);

            if (!comment.getUser().isFake()) {
                setUserLevel(comment.getUser().getLevel().getImage(), binding.buttomLevel);
            }
            if (comment.isJoined()) {
                binding.tvComment.setText(comment.getUser().getName());
                binding.tvJoined.setVisibility(View.VISIBLE);
                binding.tvComment.setVisibility(View.GONE);
            } else {
                binding.tvJoined.setVisibility(View.GONE);
                binding.tvComment.setVisibility(View.VISIBLE);
                binding.tvComment.setText(comment.getComment());
            }
            binding.tvName.setText(comment.getUser().getName());

            Log.e("TAG", "setCommentData: >>>>>>>>>>>>>> " + comment.getUser().getImage());
            binding.imgUser.setUserImage(comment.getUser().getImage(), comment.getUser().isIsVIP(), context, 10);

            binding.getRoot().setOnClickListener(v -> onCommentClickListener.onClickComment(comment.getUser()));
        }

        private void setUserLevel(String image, ImageView buttomLevel) {
            Glide.with(context).load(BuildConfig.BASE_URL + image)
                    .apply(MainApplication.requestOptions)
                    .into(buttomLevel);
        }


    }


}
