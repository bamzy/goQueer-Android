package ca.ualberta.goqueer.server;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.util.Map;

import ca.ualberta.goqueer.config.Constants;
import ca.ualberta.goqueer.network.VolleyMyCoordinatesCallback;
import ca.ualberta.goqueer.network.VolleyQueueManager;
import ca.ualberta.goqueer.server.response_type.UODACheck;
import ca.ualberta.goqueer.server.response_type.UODAToken;


/**
 * Created by bamdad on 7/20/2016.
 */
public class UODAAuthClient {

    private UODAConfiguration uodaConfiguration;
    private VolleyQueueManager queueManager;

    public UODAAuthClient(UODAConfiguration uodaConfiguration, VolleyQueueManager volleyQueueManager) {
        this.uodaConfiguration = uodaConfiguration;
        this.queueManager = volleyQueueManager;
    }

    public void refreshAccessToken(final String refreshToken, final VolleyMyCoordinatesCallback volleyMyCoordinatesCallback) {
        StringRequest refreshTokenRequest = new StringRequest(Request.Method.POST, uodaConfiguration.getRefreshTokenBaseUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        UODAToken uodaToken = extractCredentials(response);
//                        volleyMyCoordinatesCallback.onSuccess(uodaToken);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(Constants.LOG_TAG, "Volley Error: " + new String(error.networkResponse.data));
                        volleyMyCoordinatesCallback.onError(error);

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return uodaConfiguration.getRefreshTokenBody(refreshToken);
            }
        };

        // Set timeout for requests to SERVER_TIMEOUT_MILLIS
        refreshTokenRequest.setRetryPolicy(new DefaultRetryPolicy(
                Constants.SERVER_TIMEOUT_MILLIS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queueManager.addToRequestQueue(refreshTokenRequest);
    }

    public void checkAccessToken(final String accessToken, final VolleyMyCoordinatesCallback volleyMyCoordinatesCallback) {
        StringRequest checkTokenRequest = new StringRequest(Request.Method.GET, uodaConfiguration.getCheckTokenBaseUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        UODACheck uodaCheck = new UODACheck(response);
//                        volleyMyCoordinatesCallback.onSuccess(uodaCheck);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyMyCoordinatesCallback.onError(error);

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return uodaConfiguration.getCheckTokenHeader(accessToken);
            }
        };

        // Set timeout for requests to SERVER_TIMEOUT_MILLIS
        checkTokenRequest.setRetryPolicy(new DefaultRetryPolicy(
                Constants.SERVER_TIMEOUT_MILLIS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queueManager.addToRequestQueue(checkTokenRequest);
    }

    public void getTokenFromOAUTH(final String firstStepCode, final VolleyMyCoordinatesCallback volleyMyCoordinatesCallback) {
        StringRequest loginPostRequest = new StringRequest(Request.Method.POST, uodaConfiguration.getLoginPostUrl(firstStepCode),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        UODAToken uodaToken = extractCredentials(response);
//                        volleyMyCoordinatesCallback.onSuccess(uodaToken);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyMyCoordinatesCallback.onError(error);

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return uodaConfiguration.getAccessTokenHeader();
            }

            @Override
            public Map<String, String> getParams() {
                return uodaConfiguration.getAccessTokenBody(firstStepCode);

            }
        };

        // Set timeout for requests to SERVER_TIMEOUT_MILLIS
        loginPostRequest.setRetryPolicy(new DefaultRetryPolicy(
                Constants.SERVER_TIMEOUT_MILLIS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queueManager.addToRequestQueue(loginPostRequest);
    }

    public void getGuestCredentials(final VolleyMyCoordinatesCallback volleyMyCoordinatesCallback) {
        StringRequest loginGetCredentials = new StringRequest(Request.Method.POST, uodaConfiguration.getLoginGuestURL(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        UODAToken uodaToken = extractCredentials(response, Constants.GUEST_ID);

//                        volleyMyCoordinatesCallback.onSuccess(uodaToken);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyMyCoordinatesCallback.onError(error);

                    }
                })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return uodaConfiguration.getAccessTokenHeader();
            }

            @Override
            public Map<String, String> getParams() {
                return uodaConfiguration.getGuestTokenBody();

            }
        };

        // Set timeout for requests to SERVER_TIMEOUT_MILLIS
        loginGetCredentials.setRetryPolicy(new DefaultRetryPolicy(
                Constants.SERVER_TIMEOUT_MILLIS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queueManager.addToRequestQueue(loginGetCredentials);
    }

    public void logoutAuthorizationToken(final VolleyMyCoordinatesCallback callback) {
        String baseURL = uodaConfiguration.getLogoutTokenUrl();

        StringRequest loginGetCredentials = new StringRequest(Request.Method.GET, baseURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(null);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);

                    }
                })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return uodaConfiguration.getAccessTokenHeader();
            }
        };

        // Set timeout for requests to SERVER_TIMEOUT_MILLIS
        loginGetCredentials.setRetryPolicy(new DefaultRetryPolicy(
                Constants.SERVER_TIMEOUT_MILLIS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queueManager.addToRequestQueue(loginGetCredentials);
    }

    public String getLoginGetRequest() {
        return uodaConfiguration.getLoginRequestURL();
    }

    /**
     * Extracts UODA Credentials from response JSON.  Some credentials may not contain a Refresh Key.
     *
     * @param rawResponse  The raw String response from UODA.
     * @return  UODAToken containing credentials if response not malformed.
     */
    private UODAToken extractCredentials(String rawResponse) {
        try {
            JsonObject jsonToken = new JsonParser().parse(rawResponse).getAsJsonObject();
            return new UODAToken(jsonToken);
        } catch (JsonParseException e) {
            Log.e(Constants.LOG_TAG, "Could not parse malformed JSON: \"" + rawResponse + "\"");
            return null;
        }
    }

    private UODAToken extractCredentials(String rawResponse, String overrideCCID) {
        UODAToken token = extractCredentials(rawResponse);
        token.setCcid(overrideCCID);

        return token;
    }
}
