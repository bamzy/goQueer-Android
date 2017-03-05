package ca.ualberta.huco.goqueer_android.uoda;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import ca.ualberta.huco.goqueer_android.config.Constants;
import ca.ualberta.huco.goqueer_android.network.Singletons;
import ca.ualberta.huco.goqueer_android.uoda.call_objects.UMultiCallback;
import ca.ualberta.huco.goqueer_android.uoda.call_objects.UODACall;
import ca.ualberta.huco.goqueer_android.uoda.call_objects.UObjectCallback;
import ca.ualberta.huco.goqueer_android.uoda.response_type.DataObject;
import ca.ualberta.huco.goqueer_android.uoda.response_type.UPerson;
import ca.ualberta.huco.goqueer_android.uoda.response_type.URoom;
import ca.ualberta.huco.goqueer_android.uoda.response_type.UScheduleShare;
import ca.ualberta.huco.goqueer_android.uoda.response_type.UTerm;


/**
 * Created by bamdad on 7/26/2016.
 */
public class DataProvider {

    public static class Search {

        public static void person(String keyword, final UMultiCallback callback) {

            UODACall uodaCall = new UODACall("/contacts/search/" + keyword, null, UPerson[].class, callback);
            uodaCall.getUODANoNull();
        }
    }

//    public static class Person {
//
//        public static void getDetails(String userId, final UObjectCallback callback) {
//            UODACall uodaCall = new UODACall("/persons/" + userId, null, UPerson[].class, true, new UMultiCallback() {
//                @Override
//                public void onGotObjects(DataObject[] uObjects) {
//                    callback.onGotObject(DataObject.firstOrDefault(uObjects, null));
//                }
//            });
//            uodaCall.getUODA();
//        }
//
//
//        public static void getClasses(String personId, final UObjectCallback callback) {
//            HashMap<String, String> arguments = new HashMap<>();
//            arguments.put("include","enrollments,enrollments.class,enrollments.class.course," +
//                    "enrollments.class.class_times,enrollments.class.class_exams,enrollments.class.course.term," +
//                    "enrollments.class.instructors");
//
//            Singletons.cacheProvider.getUserClasses().getUODA("/persons/" + personId, arguments, UPerson[].class, new UMultiCallback() {
//                @Override
//                public void onGotObjects(DataObject[] uObjects) {
//                    callback.onGotObject(DataObject.firstOrDefault(uObjects, null));
//                }
//            });
//        }
//
//        public static void contactRecords(String ccid, final UMultiCallback callback) {
//            UODACall uodaCall = new UODACall("/contacts/" + ccid, null, UContact[].class, callback);
//            uodaCall.getUODANoNull();
//        }
//    }
//
//    public static class Terms {
//        public static void getAll(final UMultiCallback callback) {
//            Singletons.cacheProvider.getTerms().getUODA("/terms/", null, UTerm[].class, callback);
//        }
//
//        public static void getCurrent(final UMultiCallback callback) {
//            getAll(new UMultiCallback() {
//                @Override
//                public void onGotObjects(DataObject[] uObjects) {
//                    if (uObjects == null) {
//                        callback.onGotObjects(new UTerm[0]);
//                        return;
//                    }
//
//                    ArrayList<UTerm> currentTerms = new ArrayList<>();
//                    for (UTerm term: (UTerm[]) uObjects) {
//                        if (term.isCurrent()) {
//                            currentTerms.add(term);
//                        }
//                    }
//
//                    callback.onGotObjects(currentTerms.toArray(new UTerm[currentTerms.size()]));
//                }
//            });
//        }
//
//        // Can these be deleted? Not being used
////        public static void getAllIds(final StringCallback callback) {
////            getAll(new UMultiCallback() {
////                @Override
////                public void onGotObjects(DataObject[] uObjects) {
////                    String[] termIds = new String[uObjects.length];
////
////                    for(int i=0; i<uObjects.length; i++) {
////                        termIds[i] = uObjects[i].getId();
////                    }
////
////                    callback.onGotStrings(termIds);
////                }
////            });
////        }
////
////        public static void getCurrentIds(final StringCallback callback) {
////            getCurrent(new UMultiCallback() {
////                @Override
////                public void onGotObjects(DataObject[] uObjects) {
////                    String[] termIds = new String[uObjects.length];
////
////                    for(int i=0; i<uObjects.length; i++) {
////                        termIds[i] = uObjects[i].getId();
////                    }
////
////                    callback.onGotStrings(termIds);
////                }
////            });
////        }
//    }
//
//    public static class Faculty {
//        public static void get(String termId, final UMultiCallback callback) {
//            String endpoint = String.format("/terms/%s/faculties/", termId);
//
//            Singletons.cacheProvider.getFaculties().getUODA(endpoint, null, UFaculty[].class, callback);
//        }
//    }
//
//    public static class Subject {
//        public static void get(String termId, String facultyId, UMultiCallback callback) {
//            String endpoint = String.format("/terms/%s/faculties/%s/subjects/", termId, facultyId);
//
//            Singletons.cacheProvider.getSubjects().getUODA(endpoint, null, USubject[].class, callback);
//        }
//    }
//
//    public static class Course {
//        public static void getBySubject(String subjectId, String termId, UMultiCallback callback) {
//            // Some subjects have spaces (ex. 'CH E') that need to be replaced with '%20' in the Outgoing url.
//            String spacelessSubject = subjectId.replace(" ", "%20");
//
//            HashMap<String, String> parameters = new HashMap<>(3);
//            parameters.put("subject_id", spacelessSubject);
//            parameters.put("term_id", termId);
//            parameters.put("include", "term");
//
//            Singletons.cacheProvider.getCourses().getUODA("/courses/", parameters, UCourse[].class, callback);
//        }
//
//        public static void getWithClasses(String courseId, final UObjectCallback callback) {
//            String endpoint = String.format("/courses/%s/", courseId);
//
//            HashMap<String, String> parameters = new HashMap<>(1);
//            parameters.put("include", "classes.course.term,classes.class_times,classes.class_notes,classes.class_exams,classes.instructors");
//
//            Singletons.cacheProvider.getCourseWithClasses().getUODA(endpoint, parameters, UCourse[].class, new UMultiCallback() {
//                @Override
//                public void onGotObjects(DataObject[] uObjects) {
//                    callback.onGotObject(DataObject.firstOrDefault(uObjects, null));
//                }
//            });
//        }
//    }
//
//    public static class UClasses {
//        public static void getById(String classId,UMultiCallback callback) {
//            HashMap<String, String> parameters = new HashMap<>(1);
//            parameters.put("include", "class_times,course,instructors,class_notes,class_exams");
//            Singletons.cacheProvider.getUClassById().getUODA("/classes/" + classId, parameters, UClass[].class, callback);
//
//        }
//
//    }
//
//    public static class Schedules {
//
//        public static class Requests {
//
//            public static void getCachedIncoming(final UMultiCallback callback) {
//                Singletons.cacheProvider.getIncomingShares().getUODA("/schedule_shares/incoming/", null, UIScheduleShare[].class, callback);
//            }
//            public static void getIncoming(final UMultiCallback callback) {
//                UODACall uodaCall = new UODACall("/schedule_shares/incoming/", null, UIScheduleShare[].class, callback);
//                uodaCall.getUODA();
//            }
//
//            public static void getAll(final UMultiCallback callback) {
//                UODACall uodaCall = new UODACall("/schedule_shares/shares/", null, UScheduleShare[].class, callback);
//                uodaCall.getUODANoNull();
//            }
//
//            public static void setRequestStatus(int scheduleId, UScheduleShare.ScheduleShareStatus status, final UMultiCallback callback) {
//                UODACall uodaCall = new UODACall("/schedule_shares/set_status/" + scheduleId+ "/" +status, null, UResponse[].class, callback);
//                uodaCall.putUODA();
//            }
//
//            public static void shareSchedule(String ccid, String termId, final UMultiCallback callback) {
//                UODACall uodaCall = new UODACall("/schedule_shares/share/" + ccid + "/" + termId, null, UResponse[].class, callback);
//                uodaCall.postUODA();
//            }
//
//        }
//    }
//
//    public static class Location {
//        public static void room(long roomId, final UObjectCallback callback) {
//            if (roomId == 0) {
//                Log.e(Constants.LOG_TAG, "Blocked call to get room with id 0.");
//                callback.onGotObject(new URoom(null));
//                return;
//            }
//            String uri = String.format("/rooms/%d/", roomId);
//            HashMap<String, String> parameters = new HashMap<>();
//            parameters.put("include", "building");
//
//            Singletons.cacheProvider.getRooms().getUODA(uri, parameters, URoom[].class, new UMultiCallback() {
//                @Override
//                public void onGotObjects(DataObject[] uObjects) {
//                    callback.onGotObject(DataObject.firstOrDefault(uObjects, new URoom(null)));
//                }
//            });
//        }
//    }
//
//    public static class Emergency {
//        public static void mostRecent(UObjectCallback callback) {
//            new EmergencyCall().get(callback);
//        }
//    }
}
