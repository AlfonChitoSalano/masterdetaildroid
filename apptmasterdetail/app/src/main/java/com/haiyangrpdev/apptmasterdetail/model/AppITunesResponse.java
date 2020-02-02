package com.haiyangrpdev.apptmasterdetail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AppITunesResponse {

    @Expose
    @SerializedName("resultCount")
    private int ResultCount;

    public int getResultCount() { return ResultCount; }

    @Expose
    @SerializedName("results")
    private List<AppITunes> Songs;

    public List<AppITunes> getSongs() { return Songs; }
}
