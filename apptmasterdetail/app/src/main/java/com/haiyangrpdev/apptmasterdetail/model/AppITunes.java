package com.haiyangrpdev.apptmasterdetail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class AppITunes {

    @Expose
    @SerializedName("trackName")
    private String TrackName;

    public String getTrackName() {
        return TrackName;
    }

    @Expose
    @SerializedName("artworkUrl100")
    private String Artwork;

    public String getArtwork() {
        return Artwork;
    }

    @Expose
    @SerializedName("primaryGenreName")
    private String Genre;

    public String getGenre() { return Genre; }

    @Expose
    @SerializedName("trackPrice")
    private double Price;

    public double getPrice() {
        return Price;
    }

    @Expose
    @SerializedName("trackNumber")
    private int TrackNumber;

    public int getTrackNumber() {
        return TrackNumber;
    }

    public void setTrackName(String trackName){
        TrackName = trackName;
    }

    public void setArtwork(String artWork){
        Artwork = artWork;
    }

    public void setGenre(String genre){
        Genre = genre;
    }

    public void setPrice(double price){
        Price = price;
    }
}
