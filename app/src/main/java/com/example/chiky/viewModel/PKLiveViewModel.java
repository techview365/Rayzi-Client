package com.example.chiky.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chiky.liveStreamming.FilterAdapter2;
import com.example.chiky.liveStreamming.FilterAdapter_tt;
import com.example.chiky.liveStreamming.HostLiveActivity;
import com.example.chiky.liveStreamming.LiveViewUserAdapter;
import com.example.chiky.liveStreamming.StickerAdapter;
import com.example.chiky.modelclass.StickerRoot;
import com.example.chiky.modelclass.UserRoot;
import com.example.chiky.utils.Filters.FilterRoot;

import org.json.JSONObject;

public class PKLiveViewModel extends ViewModel {

    public FilterAdapter_tt filterAdapter_tt = new FilterAdapter_tt();
    public FilterAdapter2 filterAdapter2 = new FilterAdapter2();
    public StickerAdapter stickerAdapter = new StickerAdapter();

    public LiveViewUserAdapter liveViewUserAdapter = new LiveViewUserAdapter();
    public MutableLiveData<Boolean> isShowFilterSheet = new MutableLiveData<>(false);
    public MutableLiveData<FilterRoot> selectedFilter = new MutableLiveData<>();
    public MutableLiveData<FilterRoot> selectedFilter2 = new MutableLiveData<>();
    public MutableLiveData<StickerRoot.StickerItem> selectedSticker = new MutableLiveData<StickerRoot.StickerItem>();

    public MutableLiveData<UserRoot.User> clickedComment = new MutableLiveData<UserRoot.User>();
    public MutableLiveData<JSONObject> clickedUser = new MutableLiveData<>();
    public boolean isMuted = false;
    public MutableLiveData<Boolean> switchCamera = new MutableLiveData<>();


    public void onClickSheetClose() {
        isShowFilterSheet.setValue(false);
    }

    public void initLister() {
        filterAdapter_tt.setOnFilterClickListnear(filterRoot -> {
            Log.d(HostLiveActivity.TAG + " viewmodel", "onBindViewHolder: ===========" + filterRoot.getTitle());
            selectedFilter.setValue(filterRoot);
        });
        filterAdapter2.setOnFilterClickListnear(filterRoot -> {
            Log.d(HostLiveActivity.TAG + " viewmodel", "onBindViewHolder: ===========" + filterRoot.getTitle());
            selectedFilter.setValue(filterRoot);
        });
        stickerAdapter.setOnStickerClickListner(filterRoot -> {
            Log.d(HostLiveActivity.TAG + " viewmodel", "onBindViewHolder: ===========" + filterRoot.getSticker());
            selectedSticker.setValue(filterRoot);
        });
        liveViewUserAdapter.setOnLiveUserAdapterClickLisnter((JSONObject userDummy) -> clickedUser.setValue(userDummy));
    }


}
