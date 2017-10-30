package ca.ualberta.goqueer.server;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ca.ualberta.goqueer.network.VolleyArrayCallback;
import ca.ualberta.goqueer.network.VolleyQueueManager;
import ca.ualberta.goqueer.server.response_type.DataObject;
import ca.ualberta.goqueer.server.response_type.UResponse;


/**
 * Created by bamdad on 7/26/2016.
 */

public class UODAClient {
    private String baseServerUrl;


    private VolleyQueueManager queueManager;
    private String authToken;


    public UODAClient(UODAConfiguration config, VolleyQueueManager volleyQueueManager, String authToken) {
        this.queueManager = volleyQueueManager;
        this.baseServerUrl = config.getApiServerUrl();
        this.authToken = authToken;
    }

    public Map getHeadersForGetRequest() {
        HashMap<String, String> params = new HashMap<>();
        params.put("Authorization", authToken);
        params.put("Content-Type", "application/json");
        params.put("Accept", "application/json");

        //Log.w("Request Headers", "Requesting using token: " + authToken);
        return params;
    }

    public Map getHeadersForPostRequest() {
        HashMap<String, String> params = new HashMap<>();
        params.put("Authorization", authToken);
        params.put("Content-Type", "application/json");
        params.put("Accept", "application/json");
        params.put("scope", "basic,schedule_shares");
        return params;
    }

    public Map getHeadersForPutRequest() {
        HashMap<String, String> params = new HashMap<>();
        params.put("Authorization", authToken);
        params.put("Content-Type", "application/json");
        params.put("Accept", "application/json");
        params.put("scope", "basic,schedule_shares");

        return params;
    }

    public void get(final String uri, HashMap<String, String> arguments, final Class<? extends DataObject> returnType, final VolleyArrayCallback callback) {
        String url = generateURIWithArguments(baseServerUrl + uri, arguments);

        StringRequest getRequest = new StringRequest(Request.Method.GET, generateURIWithArguments(url, arguments),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final DataObject[] uodaObjects = DataObject.instantiateByClassType(returnType, response);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccess(uodaObjects);
                            }
                        }, "Volley Return Thread").start();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getHeadersForGetRequest();
            }
        };

        queueManager.addToRequestQueue(getRequest);
    }

    public void post(String uri, HashMap<String, String> arguments, final VolleyArrayCallback callback) {
        String url = generateURIWithArguments(baseServerUrl + uri, arguments);
        StringRequest postRequest = new StringRequest(Request.Method.POST, generateURIWithArguments(url,arguments),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(new UResponse[]{new UResponse(response)});
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getHeadersForPostRequest();
            }
        };

        queueManager.addToRequestQueue(postRequest);
    }
    public void put(String uri, HashMap<String, String> arguments, final VolleyArrayCallback callback) {
        String url = generateURIWithArguments(this.baseServerUrl + uri, arguments);

        StringRequest putRequest = new StringRequest(Request.Method.PUT, generateURIWithArguments(url, arguments),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        callback.onSuccess(new UResponse[]{new UResponse(response)});
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getHeadersForPutRequest();
            }
        };

        queueManager.addToRequestQueue(putRequest);
    }


    public static String generateURIWithArguments(String baseUri, HashMap<String, String> arguments) {

        String processedFilters = "";
        if (arguments == null)
            return baseUri;
        if (!arguments.isEmpty()) {

            Iterator it = arguments.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                processedFilters += pair.getKey() + "=" + pair.getValue() + "&";
                it.remove();
            }

            return baseUri + "?" + processedFilters;
        }

        return baseUri;
    }
}
