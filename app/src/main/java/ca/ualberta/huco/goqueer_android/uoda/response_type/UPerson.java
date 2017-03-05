package ca.ualberta.huco.goqueer_android.uoda.response_type;


import android.util.Log;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import ca.ualberta.dev.myualberta.config.Constants;


/**
 * Created by bamdad on 7/26/2016.
 */
public class UPerson extends DataObject implements Serializable {

    private String ccid;
    private String prefix;
    private String firstName;
    private String lastName;
    private String displayName;
    private String mail;

    private UEnrollment[] enrollments;
    private UContact[] contacts;
    private Long[] roomIds;
    private Boolean isStaff = true;


    public String getCcid() {
        return ccid;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getMail() {
        return mail;
    }

    public UContact[] getContacts() {
        return contacts;
    }

    public Boolean getIsStaff() { return  isStaff; }

    // Should this be here? Should be in MCSchedule or something.
    public Long[] getRoomIds() {
        ArrayList<Long> result = new ArrayList<>();
        for (int i=0;i<enrollments.length;i++){
            UClassTime[] uClassTimes = enrollments[i].getuClass().getClassTimes();
            if (uClassTimes != null ){
                for (int j=0;j<uClassTimes.length;j++){
                    if (!result.contains(uClassTimes[j].getRoomId()))
                        result.add(uClassTimes[j].getRoomId());

                }
            }
        }
        roomIds = new Long[result.size()];
        roomIds = result.toArray(roomIds);
        return roomIds;
    }

    public UPerson(JsonObject jsonObject) {

        super(jsonObject);

        ccid = optGetString(jsonObject, Constants.UODAResponse.Person.CCID_TAG);
        prefix = optGetString(jsonObject, Constants.UODAResponse.Person.PREFIX);
        firstName = optGetString(jsonObject,Constants.UODAResponse.Person.FIRST_NAME);
        lastName = optGetString(jsonObject, Constants.UODAResponse.Person.LAST_NAME);
        isStaff = optGetBool(jsonObject, "role_staff");


        mail = optGetString(jsonObject, Constants.UODAResponse.Person.MAIL);

        if (mail == null || mail.isEmpty()) {
            mail = optGetString(jsonObject, "email");
        }

        displayName = optGetString(jsonObject, Constants.UODAResponse.Person.DISPLAY_NAME);

        // Enrollments are stored in 'enrollments'(json object) -> 'data' (array).
        enrollments = (UEnrollment[]) DataObject.subObjectFromData(jsonObject, Constants.UODAResponse.MyCourses.ENROLLMENTS, UEnrollment.class);
        //this.contacts = (UContact[]) DataObject.subObjectFromData(jsonObject, Constants.UODAResponse.MyCourses.CONTACTS, UContact.class);

        UContact newContact = new UContact(jsonObject);
        if (newContact.hasValidPhoneNumber()) {
            contacts = new UContact[]{newContact};
        } else {
            contacts = new UContact[]{ };
        }
    }

    public UPerson(String ccid, String prefix, String firstName, String lastName, String displayName, String mail, UContact[] contacts) {
        this.ccid = ccid;
        this.prefix = prefix;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.mail = mail;
        this.contacts = contacts;
    }

    public UEnrollment[] getEnrollments() {
        return enrollments;
    }
}
