package org.aecc.superdiary.data.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;


public class ApiConnection implements Callable<String> {
    public static final String REQUEST_METHOD_GET = "GET";
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_VALUE_JSON = "application/json; charset=utf-8";
    private URL url;
    private String requestVerb;
    private int responseCode = 0;
    private String response = "";

    private ApiConnection(String url, String requestVerb) throws MalformedURLException {
        this.url = new URL(url);
        this.requestVerb = requestVerb;
    }

    public static ApiConnection createGET(String url) throws MalformedURLException {
        return new ApiConnection(url, REQUEST_METHOD_GET);
    }

    public String requestSyncCall() {
        connectToApi();
        return response;
    }

    private void connectToApi() {
        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            setupConnection(urlConnection);

            responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = getStringFromInputStream(urlConnection.getInputStream());
            } else {
                response = getStringFromInputStream(urlConnection.getErrorStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private String getStringFromInputStream(InputStream inputStream) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilderResult = new StringBuilder();

        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilderResult.append(line);
            }
            return stringBuilderResult.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void setupConnection(HttpURLConnection connection) throws IOException {
        if (connection != null) {
            connection.setRequestMethod(requestVerb);
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoInput(true);
            connection.setRequestProperty(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON);
        }
    }

    @Override
    public String call() throws Exception {
        return requestSyncCall();
    }
}
