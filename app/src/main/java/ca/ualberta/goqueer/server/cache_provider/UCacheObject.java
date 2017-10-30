package ca.ualberta.goqueer.server.cache_provider;

import android.content.Context;
import android.util.Log;

import org.joda.time.DateTime;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.HashMap;

import ca.ualberta.goqueer.config.Constants;
import ca.ualberta.goqueer.server.call_objects.UMultiCallback;
import ca.ualberta.goqueer.server.call_objects.UODACall;
import ca.ualberta.goqueer.server.response_type.DataObject;


/**
 * Created by bamdad on 8/22/16.
 */
public class UCacheObject implements CacheHolder {
    String name;
    int lifeTime;
    boolean writeNullOrEmpty = false;
    boolean serveExpired = true;
    UArchive uArchive;
    Context context;
    DateTime birthDate;

    public UCacheObject(String name, int lifeTime, Context context) {
        this.context = context;
        this.name = name;
        this.lifeTime = lifeTime;
        this.uArchive = loadFromArchive();
        this.birthDate = DateTime.now();
    }

    /**
     * Returns UODAObject from memory or server
     * @param endURI  Base uri for UODACall
     * @param returnType  Return requestType to check against for return
     * @param parameters  Parameters for UODACall
     * @param callback  Return path for UODAObject
     */
    public void getUODA(String endURI, HashMap<String, String> parameters, final Class<? extends DataObject[]> returnType, final UMultiCallback callback) {
        if (uArchive == null || isExpired()) {
            fetchFromUODA(endURI, returnType, parameters, new UMultiCallback() {
                @Override
                public void onGotObjects(DataObject[] uObjects) {
                    DataObject[] returnObjects = uObjects;

                    if (returnObjects == null) {
                        returnObjects = (uArchive == null) ? emptyReturnTypeArray(returnType) : uArchive.getObjects();
                    }

                    callback.onGotObjects(returnObjects);
                }
            });
        }

        if (uArchive != null && !(serveExpired && isExpired())) {
            callback.onGotObjects(uArchive.getObjects());
        }
    }

    /**
     * Fetches data from UODA Server and updates cache on success.
     * @param endURI  Base uri for UODACall
     * @param returnType  Return requestType to check against for return
     * @param parameters  Parameters for UODACall
     * @param callback  Return path for UODAObject
     */
    protected void fetchFromUODA(final String endURI, final Class<? extends DataObject[]> returnType, HashMap<String, String> parameters, final UMultiCallback callback) {
        UODACall call = new UODACall(endURI, parameters, returnType, new UMultiCallback() {
            @Override
            public void onGotObjects(DataObject[] uObjects) {
                if(writeNullOrEmpty || (uObjects != null && uObjects.length > 0)) {
                    writeToArchive(uObjects);
                }

                callback.onGotObjects(uObjects);
            }
        });

        call.getUODA();
    }

    private DataObject[] emptyReturnTypeArray(final Class<? extends DataObject[]> returnType) {
        return (DataObject[]) Array.newInstance(returnType.getComponentType(), 0);
    }

    private void clearArchive() {
        File cacheFile = getCacheFile();

        if (!cacheFile.isFile()) {
            Log.w(Constants.LOG_TAG, "Failed to delete archive " + name + " as file doesn't exist.");
        } else if (!getCacheFile().delete()) {
            Log.e(Constants.LOG_TAG, "Failed to delete archive " + name);
        }
    }

    /**
     * @return  true if expiry is in the past or null.
     */
    public boolean isExpired() {
        return uArchive.getExpiry() == null || uArchive.getExpiry().isBefore(DateTime.now());
    }

    public DateTime getExpiry() {
        if (uArchive != null) {
            return uArchive.getExpiry();
        }

        return null;
    }

    @Override
    public void expire() {
        if (uArchive == null) {
            return;
        }

        uArchive.setExpiry(new DateTime(0));
    }

    @Override
    public void clear() {
        clearArchive();
        this.uArchive = null;
    }

    public UArchive loadFromArchive() {
        ObjectInputStream objectInputStream = null;
        FileInputStream fileInputStream = null;

        File cacheFile = getCacheFile();

        if (!cacheFile.isFile()) {
            Log.w("Caching", "Couldn't find cacheFile for " + name);
            return null;
        } else {
            Log.w("Caching", "Attempting to read cacheFile for " + name);
        }

        try {
            fileInputStream = new FileInputStream(getCacheFile());

            objectInputStream = new ObjectInputStream(fileInputStream);

        } catch (EOFException e) {
            Log.e(Constants.LOG_TAG, "UCacheObject Read EOF.");
        } catch (IOException e) {
            if (!(e instanceof FileNotFoundException)) {
                e.printStackTrace();
            } else {
                Log.e(Constants.LOG_TAG, "Couldn't find archive for " + name);
            }

            return null;
        }

        try {
            UArchive archive = (UArchive) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
            return archive;
        } catch (IOException | ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e("Caching", "Serialized file malformed for UCacheObject: " + name);
            e.printStackTrace();
        }

        return null;
    }

    protected File getCacheFile() {
        File dir = new File(context.getCacheDir(), Constants.CACHE_BASE_DIRECTORY);
        dir.mkdirs();
        File filePath = new File(dir, name + ".cache");

        return filePath;
    }

    public void writeToArchive(DataObject[] uObjects) {
        if (uObjects != null) {
            this.uArchive = new UArchive(DateTime.now().plusMinutes(lifeTime), uObjects);
        }

        File cacheFile = getCacheFile();

        if (cacheFile.isFile()) {
            Log.w("Caching", "File for " + name + " already exists... overwriting.");
        } else {
            Log.w("Caching", "Writing new file for " + name);
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(getCacheFile());

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(uArchive);
            objectOutputStream.flush();

            objectOutputStream.close();
            fileOutputStream.close();

        } catch (IOException e) {
            Log.e(Constants.LOG_TAG, "Error writing archive for: " + name);

            e.printStackTrace();
        }
    }

    @Override
    public long getFileSize() {
        File archiveFile = getCacheFile();
        return archiveFile == null ? 0 : archiveFile.length();
    }

    @Override
    public String getName() {
        return name;
    }
}
