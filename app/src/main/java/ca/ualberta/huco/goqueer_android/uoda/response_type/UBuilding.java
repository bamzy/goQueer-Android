package ca.ualberta.huco.goqueer_android.uoda.response_type;

import com.google.gson.JsonObject;

import java.io.Serializable;

import ca.ualberta.dev.myualberta.config.Constants;

/**
 * Created by bamdad on 8/14/2016.
 */
public class UBuilding extends DataObject implements Serializable {
    private String buildingName = "";
    private String placeID = "";
    private String longitude = "";
    private String latitude = "";

    public UBuilding() {
    }

    public UBuilding(JsonObject attributes) {
            super(attributes);
            this.buildingName = optGetString(attributes, Constants.UODAResponse.MyCourses.NAME);
            this.placeID = optGetString(attributes, Constants.UODAResponse.MyCourses.PLACE_ID);
            this.longitude= optGetString(attributes, Constants.UODAResponse.MyCourses.LONGITUDE);
            this.latitude= optGetString(attributes, Constants.UODAResponse.MyCourses.LATITUDE);
    }

    public String getBuildingName() {
        return buildingName;
    }

    public String getPlaceID() {
        return placeID;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }
}
