package com.example.chiky.emoji;

import com.example.chiky.databinding.ItemEmojiGridBinding;
import com.example.chiky.modelclass.GiftRoot;

public interface OnEmojiSelectLister {
    void onEmojiSelect(ItemEmojiGridBinding binding, GiftRoot.GiftItem giftRootDummy);
}
