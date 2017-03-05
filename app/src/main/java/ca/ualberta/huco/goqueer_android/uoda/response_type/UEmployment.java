package ca.ualberta.huco.goqueer_android.uoda.response_type;

import com.google.gson.JsonObject;

import org.joda.time.DateTime;

import java.io.Serializable;

import ca.ualberta.dev.myualberta.config.Constants;

/**
 * Created by bamdad on 8/14/2016.
 */
public class UEmployment extends DataObject implements Serializable {
    private String jobTitle;

    private String businessTitle;
    private String extendedTitle;
    private DateTime startDate;
    private DateTime endDate;
    private UDepartment department;
    public UEmployment(JsonObject attributes) {
        this.jobTitle = optGetString(attributes, Constants.UODAResponse.MyCourses.JOB_TITLE);
        this.businessTitle = optGetString(attributes, Constants.UODAResponse.MyCourses.BUSINESS_TITLE);
        this.extendedTitle = optGetString(attributes, Constants.UODAResponse.MyCourses.EXTENDED_TITLE);
        this.startDate = optGetDateString(attributes, Constants.UODAResponse.START_DATE);
        this.endDate = optGetDateString(attributes, Constants.UODAResponse.END_DATE);

        UDepartment[] departments = (UDepartment[]) subObjectFromData(attributes, Constants.UODAResponse.MyCourses.DEPARTMENT, UDepartment.class);

        if (departments.length > 0) {
            this.department = departments[0];
        }
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getBusinessTitle() {
        return businessTitle;
    }

    public String getExtendedTitle() {
        return extendedTitle;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public UDepartment getDepartment() {
        return department;
    }
}
