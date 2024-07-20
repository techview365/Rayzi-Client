package com.example.chiky.user.wallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chiky.R;
import com.example.chiky.databinding.ItemReedemHistoryBinding;
import com.example.chiky.modelclass.ReedemListRoot;
import com.example.chiky.retrofit.Const;

import java.util.List;

public class ReedemHistoryAdapter extends RecyclerView.Adapter<ReedemHistoryAdapter.ReedemHistoryViewHolder> {

    private Context context;
    private final int selectedPos = 0;
    private final List<ReedemListRoot.RedeemItem> redeems;

    public ReedemHistoryAdapter(List<ReedemListRoot.RedeemItem> redeems) {

        this.redeems = redeems;
    }

    @Override
    public ReedemHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ReedemHistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reedem_history, parent, false));
    }

    @Override
    public void onBindViewHolder(ReedemHistoryViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return redeems.size();
    }

    public class ReedemHistoryViewHolder extends RecyclerView.ViewHolder {
        ItemReedemHistoryBinding binding;

        public ReedemHistoryViewHolder(View itemView) {
            super(itemView);
            binding = ItemReedemHistoryBinding.bind(itemView);
        }

        public void setData(int position) {
            ReedemListRoot.RedeemItem reedem = redeems.get(position);
            binding.tvcoin.setText(reedem.getRCoin() + Const.CoinName);
            binding.tvdate.setText(reedem.getCreatedAt());
            binding.tvPaymentGateway.setText(reedem.getPaymentGateway());
            binding.tvStatus.setText(reedem.getStatus());
        }

        private String getStatus(int status) {
            if (status == 0) {
                return "Pending";
            } else if (status == 1) {
                return "Accepted";
            } else {
                return "Decline";
            }

        }
    }
}
