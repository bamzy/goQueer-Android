package ca.ualberta.huco.goqueer_android.uoda.response_type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;

import ca.ualberta.dev.myualberta.config.Constants;

/**
 * Created by bamdad on 2016-08-12.
 */
public class UIScheduleShare extends DataObject implements Serializable,Comparable {
    public int id;
    public String type;
    public String source;
    public String termId;
    public String approval;
    public String[] classIds;
    public ArrayList<UClass> uClasses = new ArrayList<>();
    public DateTime createdAt;

    public UIScheduleShare(JsonObject attributes) {
        this.id = optGetInt(attributes, Constants.UODAResponse.ID);
        this.source = optGetString(attributes, Constants.UODAResponse.ScheduleShare.SOURCE);
        this.termId = optGetString(attributes, Constants.UODAResponse.ScheduleShare.TERM_ID);
        this.approval = optGetString(attributes, Constants.UODAResponse.ScheduleShare.APPROVAL);
        this.type = optGetString(attributes, Constants.UODAResponse.ScheduleShare.UI_TYPE);

        this.createdAt = optGetDateString(attributes, Constants.UODAResponse.ScheduleShare.CREATED_AT, new DateTime(0));

        JsonElement classIdsElement = attributes.get(Constants.UODAResponse.MyCourses.CLASSES);

        if (classIdsElement != null && classIdsElement.isJsonArray()) {
            ArrayList<String> classIdList = new ArrayList<>(classIdsElement.getAsJsonArray().size());
            for (int i=0; i<classIdsElement.getAsJsonArray().size(); i++) {
                classIdList.add(classIdsElement.getAsJsonArray().get(i).getAsString());
            }
            this.classIds = classIdList.toArray(new String[classIdList.size()]);
        }
    }

    @Override
    public int compareTo(Object o) {
        return Integer.parseInt(((UIScheduleShare)o).termId) - Integer.parseInt(this.termId);

    }
}
