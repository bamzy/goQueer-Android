package ca.ualberta.huco.goqueer_android.uoda.response_type;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.Arrays;

import ca.ualberta.dev.myualberta.config.Constants;

/**
 * Created by bamdad on 8/14/2016.
 */
public class UContact extends DataObject implements Serializable {

    public String phoneNumber;
    public String roomNumber;
    public String departmentName;
    public String buildingName;
    public String city;
    public String street;
    public String province;
    public String postalCode;
    public String jobTitle;

    public UContact(JsonObject attributes) {

        this.jobTitle = optGetString(attributes, Constants.UODAResponse.Contact.JOB_TITLE);
        this.departmentName = optGetString(attributes, Constants.UODAResponse.Contact.DEPARTMENT_NAME);
        this.phoneNumber = String.valueOf(optGetLong(attributes, Constants.UODAResponse.Contact.PHONE));
        this.roomNumber = optGetString(attributes, Constants.UODAResponse.Contact.ROOM_NUMBER);
        this.buildingName = optGetString(attributes, Constants.UODAResponse.Contact.BUILDING_NAME);
        this.city = optGetString(attributes, Constants.UODAResponse.Contact.CITY);
        this.province = optGetString(attributes, Constants.UODAResponse.Contact.PROVINCE);
        this.postalCode = optGetString(attributes, Constants.UODAResponse.Contact.POSTAL_CODE);
        this.street = optGetString(attributes, Constants.UODAResponse.Contact.STREET);

    }

    public Boolean hasValidPhoneNumber() {
        return (phoneNumber != null && !phoneNumber.isEmpty() && !phoneNumber.equals("0"));
    }
}
