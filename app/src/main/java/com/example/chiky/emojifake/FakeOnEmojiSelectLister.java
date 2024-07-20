package com.example.chiky.emojifake;

import com.example.chiky.databinding.ItemEmojiGridBinding;
import com.example.chiky.modelclass.FakeGiftRoot;


public interface FakeOnEmojiSelectLister {
    void onEmojiSelect(ItemEmojiGridBinding binding, FakeGiftRoot giftRoot, String giftCount);
}
