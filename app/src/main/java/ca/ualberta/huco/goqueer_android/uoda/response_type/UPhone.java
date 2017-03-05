package ca.ualberta.huco.goqueer_android.uoda.response_type;

import com.google.gson.JsonObject;

import java.io.Serializable;

import ca.ualberta.dev.myualberta.config.Constants;

/**
 * Created by bamdad on 8/14/2016.
 */
public class UPhone extends DataObject implements Serializable {
    private String phoneNumber;
    private String roomNumber;
    private String buildingName;
    private String campusName;
    private String street;
    private String city;
    private String province;
    private String postalCode;
    private String type;

    public UPhone(JsonObject jsonObject) {
        this.phoneNumber = optGetString(jsonObject, Constants.UODAResponse.ID);
        this.roomNumber = optGetString(jsonObject, Constants.UODAResponse.MyCourses.ROOM_NUMBER);
        this.buildingName = optGetString(jsonObject, Constants.UODAResponse.MyCourses.BUILDING_NAME);
        this.campusName = optGetString(jsonObject, Constants.UODAResponse.MyCourses.CAMPUS_NAME);
        this.street =  optGetString(jsonObject, Constants.UODAResponse.MyCourses.STREET);
        this.province=  optGetString(jsonObject, Constants.UODAResponse.MyCourses.PROVINCE);
        this.city =  optGetString(jsonObject, Constants.UODAResponse.MyCourses.CITY);
        this.postalCode =  optGetString(jsonObject, Constants.UODAResponse.MyCourses.POSTAL_CODE);
        this.type =  optGetString(jsonObject, Constants.UODAResponse.MyCourses.TYPE);
    }

    public String getType() {
        return type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public String getCampusName() {
        return campusName;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
