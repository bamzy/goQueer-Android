package ca.ualberta.huco.goqueer_android.uoda.response_type;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import ca.ualberta.dev.myualberta.config.Constants;

/**
 * Created by bamdad on 7/27/2016.
 */
public class DataObject implements Serializable {

    protected String id = "";
    protected DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    protected DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");


    public DataObject(JsonObject attributes) {
        if (attributes == null) {
            return;
        }


        // ID may be string or long.
        this.id = optGetString(attributes, Constants.UODAResponse.ID);
        if (this.id.length() == 0) {
            this.id = Long.toString(optGetLong(attributes, Constants.UODAResponse.ID));
        }
    }
    public DataObject() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        timeFormat = new SimpleDateFormat("HH:mm:ss");


    }

    /**
     * Attempts to construct an array of DataObject descendants using reflection from classType.
     * Recursively checks through JsonArrays and elements named 'data'.
     * @param element A JsonElement (GSON) containing the DataObject subclass
     * @param classType The Class requestType to be instantiated, must be an extension DataObjects.
     * @return An array of DataObject subclasses.
     */
    public static DataObject[] instantiate(JsonElement element, Class<? extends DataObject> classType) {
        if (element != null) {
            if (element.isJsonArray()) {
                return instantiateFromArray(element.getAsJsonArray(), classType);
            }

            if (element.isJsonObject()) {
                JsonObject obj = element.getAsJsonObject();

                if (obj.has(Constants.UODAResponse.DATA_TAG)) {

                    JsonElement dataElement = obj.get(Constants.UODAResponse.DATA_TAG);
                    if (dataElement.isJsonPrimitive()) {
                        DataObject dataObj = instantiateSubclass(classType, obj);
                        return makeSubclassArray(classType, dataObj);

                    }

                    return instantiate(dataElement, classType);
                }

                DataObject dataObj = instantiateSubclass(classType, element.getAsJsonObject());

                if (dataObj != null) {
                    return makeSubclassArray(classType, dataObj);
                }
            }
        }

        return makeSubclassArray(classType, null);
    }

    private static DataObject[] instantiateFromArray(JsonArray jsonArray, Class<? extends DataObject> classType) {
        ArrayList<DataObject> collection = new ArrayList<>(jsonArray.size());

        for (int i=0; i < jsonArray.size(); i++) {
            JsonElement subObj = jsonArray.get(i);

            if (subObj != null) {
                collection.addAll(Arrays.asList(instantiate(subObj, classType)));
            }
        }
        return makeSubclassArray(collection, classType);
    }

    protected static DataObject instantiateSubclass(Class<? extends DataObject> classType, JsonObject attributes) {
        try {
            Constructor constructor = classType.getConstructor(JsonObject.class);

            return (DataObject) constructor.newInstance(attributes);

        } catch (Exception e) {
            Log.e(Constants.LOG_TAG, "Failed to instantiate requestType: " + classType.toString());
            e.printStackTrace();
        }

        return null;
    }


    public String getId() {
        return id;
    }


    public static DataObject[] instantiateByClassType(Class<? extends DataObject> returnType, String respObj) {
        JsonElement element = new JsonParser().parse(respObj);

        if (element == null) {
            throw new InvalidParameterException("Invalid JSON for DataObject Instantiation.");
        }

        return instantiate(element, returnType);
    }

    /**
     * Creates an array of DataObject subclasses from a json object.
     * @param rootObject Root JSON object containing an array of DataObject initial data.
     * @param key Key for the array.
     * @param classType DataObject subclass requestType to instantiate.
     * @return Array matching classType instantiated from the json array.  Won't return null.
     */
    protected static DataObject[] subObjectFromData(JsonObject rootObject, String key, Class<? extends DataObject> classType) {
        if (rootObject == null) {
            return instantiate(null, classType);
        }

        if (rootObject.has(key)) {
            return instantiate(rootObject.get(key), classType);
        }

        return makeSubclassArray(null, classType);
    }

    protected String optGetString(JsonObject rootObject, String key, String defValue) {
        JsonPrimitive primitive = optGetPrimitive(rootObject, key);

        if (primitive != null && primitive.isString()) {
            return primitive.getAsString();
        }

        return defValue;
    }

    protected String optGetString(JsonObject rootObject, String key) {
        return optGetString(rootObject, key, "");
    }


    protected DateTime optGetDateString(JsonObject rootObject, String key, DateTime defValue) {
        String dateString = optGetString(rootObject, key, "");

        try {
            return new DateTime(dateFormat.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
            return defValue;
        }
    }

    protected DateTime optGetDateString(JsonObject rootObject, String key) {
        return optGetDateString(rootObject, key, new DateTime(0));
    }

    protected Date optGetTimeString(JsonObject rootObject, String key, Date defValue) {
        String dateString = optGetString(rootObject, key, null);

        try {
            return timeFormat.parse(dateString);
        } catch (ParseException e) {
            Log.e("Date Parse", "Couldn't parse date: " + dateString);

            e.printStackTrace();
            return defValue;
        }
    }

    protected Date optGetTimeString(JsonObject rootObject, String key) {
        return optGetTimeString(rootObject, key, new Date());
    }

    protected DateTime optGetDateWithFormat(JsonObject rootObject, String key, String format) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        String dateTimeString = optGetString(rootObject, key);
        try {
            return formatter.parseDateTime(dateTimeString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new DateTime(0);
        }
    }

    protected boolean optGetBool(JsonObject rootObject, String key, boolean defValue) {
        JsonPrimitive primitive = optGetPrimitive(rootObject, key);

        if (primitive != null) {
            if (primitive.isBoolean()) {
                return primitive.getAsBoolean();
            }

            if (primitive.isNumber()) {
                return primitive.getAsInt() == 1;
            }
        }

        return defValue;
    }

    protected boolean optGetBool(JsonObject rootObject, String key) {
        return optGetBool(rootObject, key, false);
    }



    protected long optGetLong(JsonObject rootObject, String key, long defValue) {
        JsonPrimitive primitive = optGetPrimitive(rootObject, key);
        if (primitive != null && primitive.isNumber()) {
            return primitive.getAsLong();
        }

        return defValue;
    }

    protected long optGetLong(JsonObject rootObject, String key) {
        return optGetLong(rootObject, key, 0);
    }

    protected int optGetInt(JsonObject rootObject, String key, int defValue) {
        JsonPrimitive primitive = optGetPrimitive(rootObject, key);
        if (primitive != null && primitive.isNumber()) {
            return primitive.getAsInt();
        }

        return defValue;
    }

    protected int optGetInt(JsonObject rootObject, String key) {
        return optGetInt(rootObject, key, 0);
    }

    protected JsonPrimitive optGetPrimitive(JsonObject rootObject, String key) {
        if (rootObject != null && rootObject.has(key) && rootObject.get(key).isJsonPrimitive()) {
            return rootObject.getAsJsonPrimitive(key);
        }

        return null;
    }

    static public DataObject firstOrDefault(DataObject[] array, DataObject defReturn) {
        if (array != null && array.length > 0) {
            return array[0];
        }

        return defReturn;
    }

    static public DataObject[] makeSubclassArray(ArrayList<? extends DataObject> arrayList, Class<? extends DataObject> classType) {
        if (arrayList == null) {
            return (DataObject[]) Array.newInstance(classType, 0);
        }

        DataObject[] retArray = (DataObject[]) Array.newInstance(classType, arrayList.size());

        return arrayList.toArray(retArray);
    }

    static public DataObject[] makeSubclassArray(Class<? extends DataObject> classType, DataObject dataObject) {
        if (dataObject == null) {
            return (DataObject[]) Array.newInstance(classType, 0);
        }

        DataObject[] retArray = (DataObject[]) Array.newInstance(classType, 1);
        retArray[0] = dataObject;

        return retArray;
    }
}
