package edu.unibe.springcloud;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ImaggaUtil {
    private static final String API_KEY="acc_679f88baddf7278";
    private static final String API_SECRET="8d47071b6eb0ee19434366ae7b5f5882";
    public static JsonObject getTags(String imageUrl) throws IOException {
        String credentialsToEncode = API_KEY + ":" + API_SECRET;
        String basicAuth = Base64.getEncoder().encodeToString(credentialsToEncode.getBytes(StandardCharsets.UTF_8));

        String endpointUrl = "https://api.imagga.com/v2/tags";

        String url = endpointUrl + "?image_url=" + imageUrl;
        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();

        connection.setRequestProperty("Authorization", "Basic " + basicAuth);

        int responseCode = connection.getResponseCode();

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader connectionInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String jsonResponse = connectionInput.readLine();

        JsonObject jsonObject = new Gson().fromJson(jsonResponse, JsonObject.class);

        connectionInput.close();

        return jsonObject;
    }
}
