package ca.ualberta.huco.goqueer_android.uoda.response_type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.Serializable;

import ca.ualberta.huco.goqueer_android.config.Constants;


/**
 * Created by bamdad on 8/19/16.
 */
public class UResponse extends DataObject implements Serializable {
    //TODO: Succeeded boolean populated from response?

    public String rawResponse;
    public boolean success;
    public Integer id;
    public UResponse(String rawResponse) {
        this.rawResponse = rawResponse;
        this.success = rawResponse.contains(Constants.UODAResponse.ScheduleShare.SUCCESS);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jelem = gson.fromJson(rawResponse, JsonElement.class);
        JsonObject jsonObject = jelem.getAsJsonObject();
        this.id= optGetInt(jsonObject, Constants.UODAResponse.DATA_TAG);



    }
    public UResponse(JsonObject jsonObject) {
        this.rawResponse= optGetString(jsonObject, Constants.UODAResponse.DATA_TAG);
        this.id= optGetInt(jsonObject, Constants.UODAResponse.DATA_TAG);
    }

}
