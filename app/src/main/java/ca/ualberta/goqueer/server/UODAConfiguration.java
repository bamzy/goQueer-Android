package ca.ualberta.goqueer.server;

import android.net.Uri;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ca.ualberta.goqueer.config.Constants;


/**
 * Created by aghilide on 7/25/2016.
 */
public class UODAConfiguration {
    private String redirectUrl;
    private String clientId;
    private String clientSecret;
    private String guestId;
    private String guestSecret;
    private String oauthTokenServerUrl;
    private String oauthTokenLogoutUrl;
    private String oauthLoginServerUrl;
    private String oauthCheckServerUrl;
    private String apiServerUrl;
    private String grantType;
    private String credentialsGrantType;
    private String scopes;

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public String getOauthLoginServerUrl() {
        return oauthLoginServerUrl;
    }

    public String getApiServerUrl() {
        return apiServerUrl;
    }

    public UODAConfiguration(HashMap dictionary) {
        this.grantType = dictionary.containsKey(Constants.GRANT_TYPE) ? (dictionary.get(Constants.GRANT_TYPE)).toString() : "authorization_code";

        this.credentialsGrantType = dictionary.containsKey(Constants.GUEST_GRANT_TYPE) ? (dictionary.get(Constants.GUEST_GRANT_TYPE)).toString() : "client_credentials";

        this.scopes = dictionary.containsKey(Constants.SCOPES) ? (dictionary.get(Constants.SCOPES)).toString() : "basic";

        this.clientId = dictionary.containsKey(Constants.CLIENT_ID) ? (dictionary.get(Constants.CLIENT_ID)).toString() : null;

        this.clientSecret = dictionary.containsKey(Constants.CLIENT_SECRET) ? (dictionary.get(Constants.CLIENT_SECRET)).toString() : null;

        this.guestId = dictionary.containsKey(Constants.GUEST_ID) ? (dictionary.get(Constants.GUEST_ID)).toString() : null;

        this.guestSecret = dictionary.containsKey(Constants.GUEST_SECRET) ? (dictionary.get(Constants.GUEST_SECRET)).toString() : null;

        this.redirectUrl = dictionary.containsKey(Constants.REDIRECT_URL) ? (dictionary.get(Constants.REDIRECT_URL)).toString() : null;

        this.oauthCheckServerUrl = dictionary.containsKey(Constants.OAUTH_CHECK_SERVER_URL) ? (dictionary.get(Constants.OAUTH_CHECK_SERVER_URL)).toString() : null;

        this.oauthLoginServerUrl = dictionary.containsKey(Constants.OAUTH_LOGIN_SERVER_URL) ? (dictionary.get(Constants.OAUTH_LOGIN_SERVER_URL)).toString() : null;

        this.oauthTokenLogoutUrl = dictionary.containsKey(Constants.OAUTH_TOKEN_LOGOUT_URL) ? dictionary.get(Constants.OAUTH_TOKEN_LOGOUT_URL).toString() : null;

        this.oauthTokenServerUrl = dictionary.containsKey(Constants.OAUTH_TOKEN_SERVER_URL) ? (dictionary.get(Constants.OAUTH_TOKEN_SERVER_URL)).toString() : null;

        this.apiServerUrl = dictionary.containsKey(Constants.API_SERVER_URL) ? (dictionary.get(Constants.API_SERVER_URL)).toString() : null;
    }

    public String getLoginGuestURL() {
        return oauthTokenServerUrl;
    }

    public String getLogoutTokenUrl() {
        return oauthTokenLogoutUrl;
    }

    public String buildQueryString(Map param) {
        String query = "";
        Iterator it = param.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            query = query + "&" + pair.getKey() + "=" + pair.getValue();
            it.remove();
        }
        return query;
    }

    public String getCheckTokenBaseUrl() {
        return oauthCheckServerUrl + "?&scope=basic,person.identification,enrollments.read,schedule_shares";
    }

    public String getAccessTokenQuery(Map param) {
        return buildQueryString(param);
    }

    public Map getAccessTokenBody(String authorizationCode) {
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", Constants.AUTHORIZATION_CODE);
        params.put("code", authorizationCode);
        params.put("redirect_uri", redirectUrl);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        return params;
    }

    public String getLoginRequestURL() {
        return Uri.parse(oauthLoginServerUrl).buildUpon()
                .appendQueryParameter("client_id", clientId)
                .appendQueryParameter("response_type", "code")
                .appendQueryParameter("scope", scopes)
                .appendQueryParameter("redirect_uri", redirectUrl)
                .build().toString();
    }

    public Map<String, String> getRefreshTokenBody(String refreshToken) {
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", refreshToken);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("scope", scopes);

        return params;
    }

    public String getRefreshTokenBaseUrl() {
        return oauthTokenServerUrl;
    }

    public Map<String, String> getAccessTokenHeader() {
        HashMap<String, String> params = new HashMap<>();
        params.put("Content-Type", "application/x-www-form-urlencoded");
        return params;
    }

    public Map<String, String> getCheckTokenHeader(String accessToken) {
        HashMap<String, String> params = new HashMap<>();
        params.put("Authorization", accessToken);
        return params;
    }

    public String getLoginPostUrl(String authorizationCode) {
        return oauthTokenServerUrl
                + "?&grant_type=" + "authorization_code"
                // this line will be replaced by variables as the code continues to grow
                + "&code=" + authorizationCode
                + "&redirect_uri=" + redirectUrl
                + "&client_id=" + clientId
                + "&client_secret=" + clientSecret;

    }

    public Map<String, String> getGuestTokenBody() {
        HashMap<String, String> params = new HashMap<>();
        params.put("client_id", guestId);
        params.put("client_secret", guestSecret);
        params.put("grant_type", credentialsGrantType);
        params.put("scope", "basic");
        return params;
    }
}
