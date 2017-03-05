package ca.ualberta.huco.goqueer_android.uoda.response_type;

import com.google.gson.JsonObject;

import java.io.Serializable;

import ca.ualberta.dev.myualberta.config.Constants;

/**
 * Created by bamdad on 8/14/2016.
 */
public class UCourse extends DataObject implements Serializable {

    private long termId=0;

    private String facultyId;

    private String academicOrganizationId;
    private String subjectId;
    private String catalog;
    private String title;
    private String courseDescription;

    private String career;

    private String units;
    private UClass[] classes;

    public void setTerm(UTerm term) {
        this.term = term;
    }

    private UTerm term;
    public UCourse(JsonObject attributes) {
        super(attributes);

        this.termId= optGetLong(attributes, Constants.UODAResponse.ScheduleShare.TERM_ID);
        this.academicOrganizationId= optGetString(attributes, Constants.UODAResponse.MyCourses.ACADEMIC_ORGANIZATION_ID);
        this.facultyId= optGetString(attributes, Constants.UODAResponse.MyCourses.FACULTY_ID);
        this.subjectId = optGetString(attributes, Constants.UODAResponse.MyCourses.SUBJECT_ID);
        this.title =  optGetString(attributes, Constants.UODAResponse.MyCourses.TITLE);
        this.catalog=  optGetString(attributes, Constants.UODAResponse.MyCourses.CATALOG);
        this.courseDescription=  optGetString(attributes, Constants.UODAResponse.MyCourses.DESCRIPTION);
        this.career = optGetString(attributes, Constants.UODAResponse.MyCourses.CAREER);
        this.units = optGetString(attributes, Constants.UODAResponse.MyCourses.UNITS);

        DataObject[] uTerms = subObjectFromData(attributes, Constants.UODAResponse.MyCourses.TERM, UTerm.class);
        this.term = (UTerm) firstOrDefault(uTerms, null);

        this.classes = (UClass[]) subObjectFromData(attributes, Constants.UODAResponse.MyCourses.CLASSES, UClass.class);
    }

    public String getFacultyId() {
        return facultyId;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public UTerm getTerm() {
        return term;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public UClass[] getClasses() {
        return classes;
    }

    public long getTermId() {
        return termId;
    }

    public String getTitle() {
        return title;
    }
}



