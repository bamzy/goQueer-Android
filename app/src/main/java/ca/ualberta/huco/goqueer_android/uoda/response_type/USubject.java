package ca.ualberta.huco.goqueer_android.uoda.response_type;

import com.google.gson.JsonObject;

import java.io.Serializable;

import ca.ualberta.dev.myualberta.config.Constants;

/**
 * Created by bamdad on 8/24/16.
 */
public class USubject extends DataObject implements Serializable {
    private String uName;

    private String academicOrg;
    public USubject(JsonObject attributes) {
        super(attributes);

        this.uName = optGetString(attributes, Constants.UODAResponse.MyCourses.NAME);
        this.academicOrg = optGetString(attributes, Constants.UODAResponse.MyCourses.ACADEMIC_ORGANIZATION_ID);
    }

    public String getuName() {
        return uName;
    }

    public String getAcademicOrg() {
        return academicOrg;
    }
}
