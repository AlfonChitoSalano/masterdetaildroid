package com.haiyangrpdev.apptmasterdetail.ui;

import java.util.List;
import java.util.Collections;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.haiyangrpdev.apptmasterdetail.ui.base.BaseViewModel;
import com.haiyangrpdev.apptmasterdetail.model.AppITunes;
import com.haiyangrpdev.apptmasterdetail.apiservice.ITunesService;
import com.haiyangrpdev.apptmasterdetail.model.AppITunesResponse;

public class ItemListActivityViewModel extends BaseViewModel {

    private ITunesService iTunesService;
    private MutableLiveData<List<AppITunes>> Songs;

    ItemListActivityViewModel(ITunesService iTunesService) {
        this.iTunesService = iTunesService;
    }

    public void getData() {
        //load songs from api
        this.iTunesService.getSongsApi().getAllSongs().enqueue(new SongsCallback());
    }

    public MutableLiveData<List<AppITunes>> getSongs() {
        return Songs;
    }

    private void setSongs(List<AppITunes> songs) {
        try {
            this.Songs.postValue(songs);
        }
        catch (Exception e) {
            String errorMessage = e.getMessage();
        }
    }

    //callback
    private class SongsCallback implements Callback<AppITunesResponse> {

        @Override
        public void onResponse(@NonNull Call<AppITunesResponse> call, @NonNull Response<AppITunesResponse> response) {

            AppITunesResponse appITunesResponse = response.body();

            if (appITunesResponse != null) {
                setSongs(appITunesResponse.getSongs());
            } else {
                setSongs(Collections.<AppITunes>emptyList());
            }
        }

        @Override
        public void onFailure(Call<AppITunesResponse> call, Throwable t) {
            setSongs(Collections.<AppITunes>emptyList());
        }
    }
}
