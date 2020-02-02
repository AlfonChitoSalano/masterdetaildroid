package com.haiyangrpdev.apptmasterdetail.model;

public class AppITunes {

    private String TrackName;

    public String getTrackName() {
        return TrackName;
    }

    private String Artwork;

    public String getArtwork() {
        return Artwork;
    }

    private String Genre;

    public String getGenre() { return Genre; }

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
