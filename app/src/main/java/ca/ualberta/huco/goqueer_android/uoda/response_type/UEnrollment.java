package ca.ualberta.huco.goqueer_android.uoda.response_type;

import com.google.gson.JsonObject;

import java.io.Serializable;

import ca.ualberta.dev.myualberta.config.Constants;

/**
 * Created by bamdad on 8/14/2016.
 */
public class UEnrollment extends DataObject implements Serializable {

    private String enrollmentId;
    private String personId;
    private String classId;
    private String role;
    private UClass uClass;

    public UEnrollment(JsonObject jsonObject) {
        this.enrollmentId = optGetString(jsonObject, Constants.UODAResponse.ID);
        this.personId = optGetString(jsonObject, Constants.UODAResponse.Person.PERSON_ID_TAG);
        this.classId = optGetString(jsonObject, Constants.UODAResponse.MyCourses.CLASS_ID);
        this.role = optGetString(jsonObject, Constants.UODAResponse.MyCourses.ROLE);

        if (jsonObject.has(Constants.UODAResponse.MyCourses.UCLASS)) {
            JsonObject classElement = jsonObject.get(Constants.UODAResponse.MyCourses.UCLASS).getAsJsonObject();

            if (classElement.has(Constants.UODAResponse.DATA_TAG)) {
                this.uClass = (UClass) instantiateSubclass(UClass.class, classElement.get(Constants.UODAResponse.DATA_TAG).getAsJsonObject());
            }
        }
    }

    public UClass getuClass() {
        return uClass;
    }
}
