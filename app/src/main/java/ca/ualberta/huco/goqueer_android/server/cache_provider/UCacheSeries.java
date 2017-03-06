package ca.ualberta.huco.goqueer_android.server.cache_provider;

import android.content.Context;
import android.util.Log;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import ca.ualberta.huco.goqueer_android.config.Constants;
import ca.ualberta.huco.goqueer_android.server.call_objects.UMultiCallback;
import ca.ualberta.huco.goqueer_android.server.response_type.DataObject;


/**
 * Created by bamdad on 8/22/16.
 */
public class UCacheSeries implements CacheHolder {

    private HashMap<String, UCacheObject> pendingObjects = new HashMap<>();
    private HashMap<String, UCacheObject> cacheObjects = new HashMap<>();
    private String name;
    private int countCap;
    private int lifeTimes;
    private Context context;


    public UCacheSeries(String name, int lifeTimes, int countCap, Context context) {
        if (countCap < 1) {
            throw new InvalidParameterException("countCap too small.");
        }

        this.context = context;
        this.name = name;
        this.countCap = countCap;
        this.lifeTimes = lifeTimes;
    }

    public void getUODA(String endURI, HashMap<String, String> parameters, Class<? extends DataObject[]> returnType, final UMultiCallback callback) {
        String cacheName = makeCacheName(endURI, parameters);

        UCacheObject cacheObject = cacheObjects.get(cacheName);

        if (cacheObject == null) {
            cacheObject = pendingObjects.get(cacheName);
        }

        if (cacheObject != null) {
            cacheObject.getUODA(endURI, parameters, returnType, callback);
            return;
        }

        final UCacheObject newCacheObject = new UCacheObject(cacheName, lifeTimes, context);

        pendingObjects.put(cacheName, newCacheObject);

        newCacheObject.getUODA(endURI, parameters, returnType, new UMultiCallback() {
            @Override
            public void onGotObjects(DataObject[] uObjects) {
                if (uObjects.length > 0) {
                    addObjectToCache(newCacheObject);
                }
                pendingObjects.remove(newCacheObject);
                callback.onGotObjects(uObjects);
            }
        });
    }

    private void addObjectToCache(UCacheObject cacheObject) {
        checkCap();
        cacheObjects.put(cacheObject.name, cacheObject);
    }

    private String makeCacheName(String uri, HashMap<String, String> parameters) {
        // Remove trim '/' from beginning and end, replace internal '/' with underscore
        String cacheName = uri.replaceAll("/$|^/", "").replace("/", "_");

        // If add parameters to name that are not 'include'
        if (parameters != null) {
            for (Map.Entry<String, String> entry: parameters.entrySet()) {
                if (!entry.getKey().equals("include")) {
                    cacheName = cacheName + "_" + entry.getValue();
                }
            }
        }

        return cacheName;
    }

    /**
     * Checks if the cache series length will exceed it's cap if 1 more item is added
     */
    private void checkCap() {
        // Used before adding new value to prevent it's removal
        if (cacheObjects.size() < countCap) {
            return;
        }

        String oldestKey = getOldestCacheKey();

        Log.w(Constants.LOG_TAG, "Cache series: " + name + " removing " + oldestKey);
        UCacheObject obj = cacheObjects.get(oldestKey);
        obj.clear();

        cacheObjects.remove(oldestKey);
    }

    private String getOldestCacheKey() {

        ArrayList<UCacheObject> sortedObjs = new ArrayList<>(cacheObjects.values());
         Collections.sort(sortedObjs, new Comparator<UCacheObject>(){
            @Override
            public int compare(UCacheObject uCacheObject, UCacheObject t1) {
                return uCacheObject.birthDate.compareTo(t1.birthDate);
            }
        });

        return sortedObjs.get(0).name;
    }

    @Override
    public void expire() {
        for (UCacheObject cacheObject : cacheObjects.values()) {
            cacheObject.expire();
        }
    }

    @Override
    public void clear() {
        for (UCacheObject cacheObject : cacheObjects.values()) {
            cacheObject.clear();
        }

        for (UCacheObject cacheObject : pendingObjects.values()) {
            cacheObject.clear();
        }

        cacheObjects.clear();
        pendingObjects.clear();
    }

    @Override
    public long getFileSize() {
        long fileSize = 0;

        for (UCacheObject cacheObject : cacheObjects.values()) {
            fileSize += cacheObject.getFileSize();
        }

        for (UCacheObject pendingObject : pendingObjects.values()) {
            fileSize += pendingObject.getFileSize();
        }

        return fileSize;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getObjectCount() {
        return cacheObjects.size();
    }
}
