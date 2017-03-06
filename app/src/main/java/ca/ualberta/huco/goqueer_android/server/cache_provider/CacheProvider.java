package ca.ualberta.huco.goqueer_android.server.cache_provider;

import android.content.Context;

import ca.ualberta.huco.goqueer_android.config.Constants;


/**
 * Created by bamdad on 8/18/16.
 */
public class CacheProvider {

    // UCacheSeries are a collection of UCacheObjects with different parameters
    private UCacheSeries faculties;
    private UCacheSeries subjects;
    private UCacheSeries courses;
    private UCacheSeries courseWithClasses;
    private UCacheSeries rooms;
    private UCacheSeries uClassById;

    private UCacheObject terms;
    private UCacheObject userClasses;
    private UCacheObject incomingShares;


    // All CacheHolder objects.
    private CacheHolder[] cacheHolders;


    public CacheProvider(Context context) {
        faculties = new UCacheSeries("Faculties", Constants.Cache.LIFE_MED, Constants.Cache.CAP_STD, context);
        subjects = new UCacheSeries("Subjects", Constants.Cache.LIFE_MED, Constants.Cache.CAP_STD, context);
        courses = new UCacheSeries("Courses", Constants.Cache.LIFE_MED, Constants.Cache.CAP_STD, context);
        courseWithClasses = new UCacheSeries("Course_Classes", Constants.Cache.LIFE_MED, Constants.Cache.CAP_STD, context);
        rooms = new UCacheSeries("Rooms", Constants.Cache.LIFE_LONG, Constants.Cache.CAP_LONG, context);
        uClassById = new UCacheSeries("ClassById", Constants.Cache.LIFE_SHORT, Constants.Cache.CAP_LONG, context);

        terms = new UCacheObject("Terms", Constants.Cache.LIFE_MED, context);
        userClasses = new UCacheObject("UPerson", Constants.Cache.LIFE_SHORT, context);
        incomingShares = new UCacheObject("IncomingSharing", 0, context);

        cacheHolders = new CacheHolder[]{
                faculties,
                subjects,
                terms,
                courses,
                courseWithClasses,
                rooms,
                uClassById,
                terms,
                userClasses,
                incomingShares
        };
    }

    public void clearCaches() {
        for (CacheHolder cacheHolder : cacheHolders) {
            cacheHolder.clear();
        }
    }

    public void expireCaches() {
        for (CacheHolder cacheHolder : cacheHolders) {
            cacheHolder.expire();
        }
    }

    public UCacheObject getIncomingShares() {
        return incomingShares;
    }

    public void expireHappeningsCaches() {
        userClasses.expire();
    }

    public void expireCatalogueCaches() {
        terms.expire();
        faculties.expire();
        subjects.expire();
        courses.expire();
        courseWithClasses.expire();
    }

    public UCacheSeries getFaculties() {
        return faculties;
    }

    public UCacheSeries getSubjects() {
        return subjects;
    }

    public UCacheObject getTerms() {
        return terms;
    }

    public UCacheSeries getCourses() {
        return courses;
    }

    public UCacheSeries getCourseWithClasses() {
        return courseWithClasses;
    }

    public UCacheSeries getRooms() {
        return rooms;
    }

    public UCacheObject getUserClasses() {
        return userClasses;
    }

    public UCacheSeries getUClassById() {
        return uClassById;
    }

    public String cacheReport() {
        String report = "===================================================================\n";
        int count = 0;
        long sizeSum = 0;

        for (CacheHolder holder: cacheHolders) {
            long fileSize = holder.getFileSize() / 1024;
            sizeSum += fileSize;

            if (holder instanceof  UCacheSeries) {
                int itemCount = ((UCacheSeries) holder).getObjectCount();
                report += String.format("%s: UCacheSeries with %d Objects - %d KB\n", holder.getName(), itemCount, fileSize);
            } else {
                report += String.format("%s: UCacheObject - %d KB\n", holder.getName(), fileSize);
            }
            count++;
        }

        report += "=====================================================================\n" +
                String.format("%d cache files.  Total: %d KB.", count, sizeSum);

        return report;
    }
}
