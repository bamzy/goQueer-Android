package ca.ualberta.huco.goqueer_android.uoda.response_type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.Serializable;

import ca.ualberta.dev.myualberta.config.Constants;

/**
 * Created by bamdad on 8/14/2016.
 */
public class UDepartment extends DataObject implements Serializable {
    public String name;
    public String parentName;

    public UDepartment(JsonObject attributes) {
        this.name = optGetString(attributes, Constants.UODAResponse.MyCourses.NAME);

        this.parentName = "";
        JsonElement parentElement = attributes.get(Constants.UODAResponse.MyCourses.PARENT);
        if (parentElement != null && parentElement.isJsonObject()) {
            JsonElement dataElement = parentElement.getAsJsonObject().get(Constants.UODAResponse.DATA_TAG);

            if (dataElement != null && dataElement.isJsonObject()) {
                this.parentName = optGetString(dataElement.getAsJsonObject(), Constants.UODAResponse.MyCourses.NAME);
            }
        }
    }
}
