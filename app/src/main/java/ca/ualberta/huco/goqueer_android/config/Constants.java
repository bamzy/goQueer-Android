package ca.ualberta.huco.goqueer_android.config;

import android.text.TextUtils;

import java.util.ArrayList;


/**
 * Created by bamdad on 7/15/2016.
 */
public class Constants {
    //the ID of the configuration values stored in myualberta.properties

    public static final String PROPERTIES_FILE_NAME = "myualberta.properties";
    public static final String PREFERENCES_NAME = "MyUalbertaPrefs";
    public static final String LOG_TAG = "goqueer";
    public static final String GO_QUEER_BASE_SERVER_URL = "http://142.244.51.54:8000/";
    public static final String OAUTH_LOGIN_SERVER_URL = "oauthTokenServerUrl";
    public static final String OAUTH_TOKEN_SERVER_URL = "oauthTokenServerUrl";
    public static final String OAUTH_TOKEN_LOGOUT_URL = "oauthTokenLogoutUrl";
    public static final String OAUTH_CHECK_SERVER_URL = "oauthCheckServerUrl";
    public static final String CALLBACK_URL = "AppURI";
    public static final String REDIRECT_URL = "redirectUrl";
    public static final String API_SERVER_URL = "apiServerUrl";
    public static final String CLIENT_ID = "clientId";
    public static final String CLIENT_SECRET = "clientSecret";
    public static final String SCOPES = "scopes";
    public static final String GRANT_TYPE = "grantType";
    public static final String GUEST_GRANT_TYPE = "guestGrantType";
    public static final String GUEST_ID = "guestId";
    public static final String GUEST_SECRET = "guestSecret";
    public static final String DEMO_ID = "demoUser";

    //Error messages shown in the app
    public static final String BROWSER_NOT_FOUND = "No application can handle this request. Please install a web browser";
    public static final String NAV_MENU_TITLE = "GUEST_MENU_TITLE";
    public static final String GUEST_NAV_MENU_VALUE = "University Guest";
    public static final String DEMO_NAV_MENU_VALUE = "Demo Account";
    public static final String USER_NAV_MENU_VALUE = "Ualberta User";
    public static final String GUEST_USER_TYPE = "University Guest";
    public static final String AUTHENTICATED_USER_TYPE = "Authenticated";


    //Keys used to pass data between activities
    public static final String AUTHORIZATION_CODE = "authorization_code";
    public static final String TRUE = "true" ;
    public static int TransitionDelay = 400;
    public static int DoubleClickDelay = 1000;

    public static class Emergency {
        public static final int NOTIFICATION_AGE_CUTOFF_HRS = 24;

        public static final String NOTIFICATION_DATE_TIME_FORMAT = "EEEE, MMMM d yyyy hh:mm a";

    }

    public static class StorageKeys {
        public static class Credential {
            public static final String CCID = "token_ccid";
            public static final String TOKEN = "token_token";
            public static final String REFRESH = "token_refresh";
            public static final String PERSON_ID = "token_id";
            public static final String EXPIRES_IN = "token_expires_in";
            public static final String EXPIRY = "token_expiry";
        }

        public static class Personal {
            public static final String CCID = "person_ccid";
            public static final String FIRST = "person_first_name";
            public static final String LAST = "person_last_name";
            public static final String DISPLAY = "person_display_name";
            public static final String MAIL = "person_mail";
            public static final String PREFIX = "person_prefix";

        }
    }

    public static class UODAResponse {
        public static final String DATA_TAG = "data";
        public static final String VALID_TAG = "valid";
        public static final String START_DATE = "start_date";
        public static final String END_DATE = "end_date";
        public static final String ID = "id";

        public static class Faculty {
            public static final String NAME = "name";
            public static final String SHORT_NAME = "short_name";
        }

        public static class Person {
            //UODA Response keys
            public static final String CCID_TAG = "ccid";
            public static final String PERSON_ID_TAG = "person_id";
            public static final String DISPLAY_NAME = "display_name";
            public static final String REFRESH_TOKEN = "refresh_token";
            public static final String EXPIRES_IN = "expires_in";
            public static final String TOKEN_TYPE = "token_type";
            public static final String ACCESS_TOKEN = "access_token";
            public static final String AUTH_EXPIRATION = "auth_expiriation";
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";
            public static final String MAIL = "mail";
            public static final String PREFIX = "prefix";
            public static final String SUFFIX = "suffix";
            public static final String SUCCESSFUL_ACCESS_TOKEN_PATTERN = "ualbertalogin://?code=";
        }

        public static class Exams {
            public static final String EXAMS = "class_exams";
            public static final String EXAM_START = "start";
            public static final String EXAM_END = "end";
            // Typo in UODA no here
            public static final String IS_TENTATIVE = "is_tenative";
            public static final String IS_COMBINED = "is_combined";
        }

        public static class Contact {
            //UODA Response keys
            public static final String JOB_TITLE = "job_title";
            public static final String PHONE = "phone";
            public static final String DEPARTMENT_NAME = "department_name";
            public static final String BUILDING_NAME = "building_name";
            public static final String ROOM_NUMBER = "room_number";
            public static final String CITY = "city";
            public static final String PROVINCE = "province";
            public static final String COUNTRY = "Canada";
            public static final String POSTAL_CODE = "postal_code";
            public static final String STREET = "street";
        }

        public static class MyCourses {
            //TODO Separate into different DataObjects

            public static final String ENROLLMENTS = "enrollments";
            public static final String EMPLOYMENTS = "employments";
            public static final String UCLASS = "class";
            public static final String CLASS_ID = "class_id";
            public static final String COURSE_ID = "course_id";
            public static final String COURSE = "course";
            public static final String ROLE = "role";
            public static final String SECTION = "section";
            public static final String COMPONENT = "component";
            public static final String TYPE = "requestType";
            public static final String STATUS = "status";
            public static final String ENROLL_STATUS = "enroll_status";
            public static final String CAPACITY = "capacity";
            public static final String SESSION = "session";
            public static final String CAMPUS = "campus";
            public static final String LOCATION = "location";
            public static final String AUTO_ENROLL = "auto_enroll";
            public static final String CLASS_TOPIC = "class_topic";
            public static final String CLASS_NOTES = "class_notes";
            public static final String CONSENT = "consent";
            public static final String GRADING_BASIS = "grading_basis";
            public static final String INSTRUCTION_MODE = "instruction_mode";
            public static final String UNITS = "units";
            public static final String CLASS_RULE = "class_rule";
            public static final String INSTRUCTOR_CCID = "instructor_ccid";
            public static final String CLASS_TIMES = "class_times";
            public static final String INSTRUCTORS = "instructors";
            public static final String ACADEMIC_ORGANIZATION_ID = "academic_organization_id";
            public static final String FACULTY_ID = "faculty_id";
            public static final String SUBJECT_ID = "subject_id";
            public static final String TITLE = "title";
            public static final String CATALOG = "catalog";
            public static final String DESCRIPTION = "description";
            public static final String CAREER = "career";
            public static final String TERM = "term";
            public static final String FULL_SEMESTER = "fullSemester";
            public static final String U_CLASSES = "uClasses";
            public static final String MC_CLASSES = "mcClasses";
            public static final String PARENT_TYPE = "parentType";
            public static final String ROOM_ID = "room_id";
            public static final String START_TIME = "start_time";
            public static final String END_TIME = "end_time";
            public static final String MONDAY = "monday";
            public static final String TUESDAY = "tuesday";
            public static final String WEDNESDAY = "wednesday";
            public static final String THURSDAY = "thursday";
            public static final String FRIDAY = "friday";
            public static final String SATURDAY = "saturday";
            public static final String SUNDAY = "sunday";
            public static final String NUMBER = "number";
            public static final String BUILDING = "building";
            public static final String BUILDING_ID = "building_id";
            public static final String NAME = "name";
            public static final String PLACE_ID = "place_id";
            public static final String LONGITUDE = "longitude";
            public static final String LATITUDE = "latitude";
            public static final String INSTRUCTION_ROLE_ID = "instruction_role_id";
            public static final String JOB_TITLE = "job_title";
            public static final String BUSINESS_TITLE = "business_title";
            public static final String EXTENDED_TITLE = "extended_title";
            public static final String DEPARTMENT = "department";
            public static final String PARENT = "parent";
            public static final String CONTACTS = "contacts";
            public static final String PHONE = "phone";
            public static final String ROOM_NUMBER = "room_number";
            public static final String BUILDING_NAME = "building_name";
            public static final String CAMPUS_NAME = "campus_name";
            public static final String STREET = "street";
            public static final String CITY = "city";
            public static final String PROVINCE = "province";
            public static final String POSTAL_CODE = "postal_code";
            public static final String CLASSES = "classes";
            public static final String ROOM_TAG = "room";
        }

        public static class ScheduleShare {
            public static final String SOURCE = "source";
            public static final String UI_TYPE = "requestType";
            public static final String US_TYPE = "type";
            public static final String TERM_ID = "term_id";
            public static final String REQUEST_ID = "requestId";
            public static final String TERM_TITLE = "term_title";
            public static final String APPROVAL_FROM = "approval_from";
            public static final String APPROVAL = "approval";
            public static final String APPROVAL_TO = "approval_to";
            public static final String SUCCESS = "Success";
            public static final String FAILURE = "Failure";
            public static final String CREATED_AT = "created_at";

        }

    }
    public static class Titles {
        public static final String CLASS_DETAILS = "Class Details";
        public static String MY_COURSES = "My Courses";
        public static String SCHEDULE_SHARING = "Schedule Sharing";

        public static String HAPPENINGS = "Happenings";
        public static String MY_ACCOUNT = "My Account";
        public static String RESOURCES = "Resources";
    }

    public static final String CONTACT_DETAILS = "contact_details";
    public static final String UCONTACT = "contact";
    public static final String UCONTACT_DETAILS = "uContactDetails";
    public static final String UOFA_EMERGENCY_TEST_URL = "uofaEmergencyTestUrl";
    public static final String UOFA_EMERGENCY_URL = "uofaEmergencyUrl";

    public static final String UOFA_CONTACT_PROTECTIVE_SERVICES_URL = "uofaContactProtectiveServicesUrl";
    public static final String UOFA_EMERGENCY_PHONE_LOCATION_URL = "uofaEmergencyPhoneLocationsUrl";
    public static final String UOFA_LOST_AND_FOUND_URL = "uofaLostAndFoundUrl";
    public static final String UOFA_UNIVERSITY_SUPPORT_SERVICES_URL = "uofaUniversitySupportServicesUrl";
    public static final String UOFA_OFF_CAMPUS_TRAVEL_URL = "uofaOffCampusTravelUrl";
    public static final String UOFA_DETAILED_EMERGENCY_NOTIFICATIONS_URL = "uofaEmergencyNotificationDetails";

    public static final String UOFA_GENERAL_EMERGENCY_PHONE_NUMBER = "emergencyPhoneNumber";
    public static final String UOFA_SECURITY_ISSUE_PHONE_NUMBER = "securityIssuesPhoneNumber";

    public static final String UOFA_NOTIFICATION_RESPONSE_CONTENT_KEY = "content";
    public static final String UOFA_NOTIFICATION_RESPONSE_AFFECTED_LOCATION_KEY = "affectedLocation";
    public static final String UOFA_NOTIFICATION_RESPONSE_SENT_DATE_KEY = "sentDate";
    public static final String UOFA_NOTIFICATION_RESPONSE_HEADLINE_KEY = "headline";
    public static final String UOFA_NOTIFICATION_RESPONSE_ID_KEY = "id";
    public static final String UOFA_NOTIFICATION_ALERT_MESSAGE = "Emergency Alert";

    public static final String SELECTED_SEMESTER_INDEX= "selectedSemesterIndex";
    public static final String SELECTED_DAY= "selectedDay";
    public static final String ASSOCIATED_UPERSON= "associatedUPerson";
    public static final String SELECTED_CLASSTIME_INDEX= "selectedClasstimeIndex";
    public static final String SEMESTER_FRAG_TAG= "semesterFragment";

    //Time related Constants
    public static class Time {
        public static final int MINUTES_IN_HOUR = 60;
        public static final int DEFAULT_EARLIEST_HOUR_OF_DAY = 8;
        public static final int DEFAULT_LATEST_HOUR_OF_DAY = 22;
        public static final int ACTUAL_LATEST_HOUR_OF_DAY = 23;
        public static final int START_BUFFER_HOURS = 1;
        public static final int MIDDLE_OF_THE_DAY = 12;
        public static final String TIME_SEPARATOR = ":";
        public static final String SEPARATOR = " - ";
        public static final String PM_SUFFIX = " PM";
        public static final String AM_SUFFIX = " AM";
        public static final double MINIMUM_BLOCK_HEIGHT_TO_SHOW_FULL_TIMETABLE_TEXT = 0.8;
        public static final int USE_SHORTHAND_THRESHOLD = 3;
        public static final int TIME_TABLE_BLOCK_TEXT_SIZE = 12;
        public static final int WEEK_TIMETABLE_LEFT_SPACING = 75;
        public static final int WEEK_TIMETABLE_RIGHT_SPACING = 8;
        public static final int DAY_TIMETABLE_LEFT_SPACING = 95;
        public static final int DAY_TIMETABLE_RIGHT_SPACING = 30;

        public static final int PADDING_BETWEEN_COURSE_BLOCKS = 20;
        public static final int BLOCKS_LEFT_PADDING = 10;

        public static final int VIEW_PAGER_HEIGHT = 240;
        public static final int MAX_NUMBER_OF_WEEKDAYS = 7;

        public static final int HAPPENINGS_UPDATE_DELAY_MILLIS = 5000;

        public static final int HAPPENINGS_CLASS_DATE_PADDING_DAYS = 30;

        // Time in seconds before token expiration to the token consider expired.
        public static final int TOKEN_EXPIRATION_PADDING_SEC = 60;

    }

    public static class BundleKeys {
        public static final String MCCLASS_KEY = "mcclass_key";
    }

    public enum Weekday {
        Sunday,
        Monday,
        Tuesday,
        Wednesday,
        Thursday,
        Friday,
        Saturday;


        public int getJodaVal() {
            return this.ordinal();
        }

        // Joda weekdays are Mon-Sun 1-7
        public static Weekday getByJodaWeekday(int jodaVal) {
            jodaVal = jodaVal == Weekday.values().length ? 0 : jodaVal;

            return Weekday.values()[jodaVal];
        }

        private static String[] superShort = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
        private static String[] shortStrings = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        private static String[] longStrings = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        public String getSuperShort() {
            return superShort[this.ordinal()];
        }

        public String getShortString() {
            return shortStrings[this.ordinal()];
        }

        public String getLongString() {
            return longStrings[this.ordinal()];
        }

        public static String weekdaysCombined(Weekday[] weekdays) {
            if (weekdays.length == 0) {
                return "";
            }

            ArrayList<String> weekStrings = new ArrayList<>(weekdays.length);

            for(Weekday weekday: weekdays) {
                if (weekdays.length == 1) {
                    weekStrings.add(weekday.getLongString());
                } else if(weekdays.length < 3 ) {
                    weekStrings.add(weekday.getShortString());
                } else {
                    weekStrings.add(weekday.getSuperShort());
                }
            }
            return TextUtils.join(" | ", weekStrings);
        }
    }

    public static class Cache {
        static final int MIN_HOUR = 60;
        static final int MIN_DAY = 24 * MIN_HOUR;
        static final int MIN_WEEK = 7 * MIN_DAY;
        static final int MIN_MONTH = 4 * MIN_WEEK;

        public static final int LIFE_SHORT = MIN_HOUR;
        public static final int LIFE_MED = MIN_WEEK;
        public static final int LIFE_LONG = 6 * MIN_MONTH;

        public static final int CAP_STD = 32;
        public static final int CAP_LONG = 720;
    }

    public static class Notifications {
        public static final String FIRST_RUN_MY_COURSES = "uan_first_run_my_courses";
        public static final String FIRST_RUN_HAPPENINGS = "uan_first_run_happenings";
        public static final String FIRST_RUN_MY_FRIENDS_SCHEDULE = "uan_first_run_my_friends_schedules";
    }

    public static final String BUG_REPORT = "BUG REPORT";
    public static final String APP_REVIEW = "APP REVIEW";
    public static final String ETS_PACKAGE_NAME = "ca.edmonton.etslive";
    public static final String GOOGLE_PLAY_DIRECT_URL = "https://play.google.com/store/apps/details?id=";
    public static final String GOOGLE_PLAY_SEARCH_URL = "market://details?id=";
    public static final int SERVER_TIMEOUT_MILLIS = 8000;


    public static final String USE_TEST_DATA_FLAG = "useTestDataEnabled";
    public static final String MY_COURSE_TEST_DATA_FILE_NAME = "courseData.json";

    public static final String CACHE_BASE_DIRECTORY = "UAN_Caching";

    public static final String CONTACT_INFO = "Contact Information";

}

