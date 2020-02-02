package com.haiyangrpdev.apptmasterdetail.apiservice;

import com.haiyangrpdev.apptmasterdetail.model.AppITunesResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class ITunesService {

    private static final String URL = "https://itunes.apple.com/";
    private SongsApi mSongsApi;
    private static ITunesService instance;

    public static ITunesService getInstance() {
        if (instance == null) {
            instance = new ITunesService();
        }
        return instance;
    }

    private ITunesService() {
        try {
            Retrofit mRetrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).build();
            mSongsApi = mRetrofit.create(SongsApi.class);
        }
        catch (Exception e) {
            String errorMessage = e.getMessage();
        }
    }

    public SongsApi getSongsApi() {
        return mSongsApi;
    }

    public interface SongsApi {
        @GET("search?term=star&amp;country=au&amp;media=movie&amp;all") Call<AppITunesResponse> getAllSongs();
    }
}
