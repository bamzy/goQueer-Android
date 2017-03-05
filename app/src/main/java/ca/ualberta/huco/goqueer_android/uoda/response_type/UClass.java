package ca.ualberta.huco.goqueer_android.uoda.response_type;

import com.google.gson.JsonObject;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ca.ualberta.dev.myualberta.config.Constants;

/**
 * Created by bamdad on 8/14/2016.
 */
public class UClass extends  DataObject implements Serializable {


    private String courseId;

    private String section;
    private String component;
    private String type;
    private String status;
    private String enrollStatus;
    private int capacity;
    private DateTime startDate;
    private DateTime endDate;
    private String session;
    private String campus;
    private String location;
    private String autoEnroll;
    private String classTopic;
    private String classNotes;
    private String consent;
    private String gradingBasis;
    private String instructionMode;
    private String units;
    private String classUrl;
    private String instructorCcid;

    private UExam[] uExams;
    private UCourse uCourse;
    private UClassTime[] classTimes;
    private UInstructor[] instructors;
    private UTerm classTerm;


    public UClass(JsonObject attributes) {
        super(attributes);

        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        this.capacity= optGetInt(attributes, Constants.UODAResponse.MyCourses.CAPACITY);
        this.startDate = optGetDateString(attributes, Constants.UODAResponse.START_DATE);
        this.endDate = optGetDateString(attributes, Constants.UODAResponse.END_DATE);

        this.courseId = optGetString(attributes, Constants.UODAResponse.MyCourses.COURSE_ID);
        this.section = optGetString(attributes, Constants.UODAResponse.MyCourses.SECTION);
        this.component = optGetString(attributes, Constants.UODAResponse.MyCourses.COMPONENT);
        this.type = optGetString(attributes, Constants.UODAResponse.MyCourses.TYPE);
        this.status = optGetString(attributes, Constants.UODAResponse.MyCourses.STATUS);
        this.enrollStatus = optGetString(attributes, Constants.UODAResponse.MyCourses.ENROLL_STATUS);
        this.section = optGetString(attributes, Constants.UODAResponse.MyCourses.SECTION);
        this.session = optGetString(attributes, Constants.UODAResponse.MyCourses.SESSION);
        this.campus = optGetString(attributes, Constants.UODAResponse.MyCourses.CAMPUS);
        this.location = optGetString(attributes, Constants.UODAResponse.MyCourses.LOCATION);
        this.autoEnroll = optGetString(attributes, Constants.UODAResponse.MyCourses.AUTO_ENROLL);
        this.classTopic = optGetString(attributes, Constants.UODAResponse.MyCourses.CLASS_TOPIC);
        this.classNotes = optGetString(attributes, Constants.UODAResponse.MyCourses.CLASS_NOTES);
        this.consent = optGetString(attributes, Constants.UODAResponse.MyCourses.CONSENT);
        this.gradingBasis = optGetString(attributes, Constants.UODAResponse.MyCourses.GRADING_BASIS);
        this.instructionMode = optGetString(attributes, Constants.UODAResponse.MyCourses.INSTRUCTION_MODE);
        this.units = optGetString(attributes, Constants.UODAResponse.MyCourses.UNITS);
        this.classUrl = optGetString(attributes, Constants.UODAResponse.MyCourses.CLASS_RULE);

        this.uCourse = (UCourse) firstOrDefault(subObjectFromData(attributes, Constants.UODAResponse.MyCourses.COURSE, UCourse.class), null);

        if (uCourse != null) {
            this.classTerm = uCourse.getTerm();
        }

        this.classTimes = (UClassTime[]) subObjectFromData(attributes, Constants.UODAResponse.MyCourses.CLASS_TIMES, UClassTime.class);

        this.instructors = (UInstructor[]) subObjectFromData(attributes, Constants.UODAResponse.MyCourses.INSTRUCTORS, UInstructor.class);

        this.uExams = (UExam[]) subObjectFromData(attributes, Constants.UODAResponse.Exams.EXAMS, UExam.class);


    }

    public String getLocation() {
        return location;
    }

    public String getSection() {

        return section;
    }

    public UClassTime[] getClassTimes() {
        return classTimes;
    }

    public UCourse getuCourse() {
        return uCourse;
    }

    public String getComponent() {
        return component;
    }

    public boolean hasAssociateWeekdays(){
        if (classTimes == null) {
            return false;
        }

        for(UClassTime classTime: classTimes){
            if (classTime.hasWeekdays())
                return true;
        }

        return false;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getStatus() {
        return status;
    }

    public String getEnrollStatus() {
        return enrollStatus;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public String getClassNotes() {
        return classNotes;
    }

    public UExam[] getuExams() {
        return uExams;
    }

    public UInstructor[] getInstructors() {
        return instructors;
    }

    public UTerm getClassTerm() {
        return classTerm;
    }
}
