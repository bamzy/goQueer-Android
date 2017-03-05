package ca.ualberta.huco.goqueer_android.uoda.response_type;

import com.google.gson.JsonObject;

import java.io.Serializable;

import ca.ualberta.dev.myualberta.config.Constants;

/**
 * Created by bamdad on 8/14/2016.
 */
public class UInstructor extends DataObject implements Serializable {
    private String instructionRoleId;
    private String ccid;
    private String uName;

    public UInstructor(JsonObject attributes) {
        this.instructionRoleId = optGetString(attributes, Constants.UODAResponse.MyCourses.BUILDING_ID);
        this.ccid = optGetString(attributes, Constants.UODAResponse.Person.CCID_TAG);
        this.uName = optGetString(attributes, Constants.UODAResponse.MyCourses.NAME);
    }

    public String getInstructionRoleId() {
        return instructionRoleId;
    }

    public String getCcid() {
        return ccid;
    }

    public String getuName() {
        return uName;
    }
}

