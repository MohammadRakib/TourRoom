package com.example.tourroom.Data;

import java.io.Serializable;

public class place_data implements Serializable {
    private String placeName,PlaceAddress,placeDescription,placeId,placeImage;

    public place_data()
    {

    }

    public place_data(String placeId,String placeName, String placeAddress, String placeDescription) {
        this.placeId = placeId;
        this.placeName = placeName;
        PlaceAddress = placeAddress;
        this.placeDescription = placeDescription;

    }

    public place_data( String placeId,String placeName, String placeAddress, String placeDescription, String placeImage) {
        this.placeId = placeId;
        this.placeName = placeName;
        PlaceAddress = placeAddress;
        this.placeDescription = placeDescription;
        this.placeImage = placeImage;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceAddress() {
        return PlaceAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        PlaceAddress = placeAddress;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceImage() {
        return placeImage;
    }

    public void setPlaceImage(String placeImage) {
        this.placeImage = placeImage;
    }

}
