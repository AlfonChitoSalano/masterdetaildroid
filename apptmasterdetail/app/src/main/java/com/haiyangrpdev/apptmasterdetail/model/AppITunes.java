package com.haiyangrpdev.apptmasterdetail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppITunes {

    @Expose
    @SerializedName("trackName")
    private String TrackName;

    public String getTrackName() {
        return TrackName;
    }

    @Expose
    @SerializedName("artworkUrl30")
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
