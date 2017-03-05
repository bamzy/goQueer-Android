package ca.ualberta.huco.goqueer_android.uoda.response_type;

import com.google.gson.JsonObject;

import java.io.Serializable;

import ca.ualberta.dev.myualberta.config.Constants;

/**
 * Created by bamdad on 8/24/16.
 */
public class UFaculty extends DataObject implements Serializable {
    private String uName;
    private String shortName;

    public UFaculty(JsonObject attributes) {
        super(attributes);
        this.uName = optGetString(attributes, Constants.UODAResponse.Faculty.NAME);
        this.shortName = optGetString(attributes, Constants.UODAResponse.Faculty.SHORT_NAME);


    }

    public String getuName() {
        return uName;
    }

    public String getShortName() {
        return shortName;
    }
}
