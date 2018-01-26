package agh.cs.oop;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Kamil on 2018-01-25.
 */
public class RequestMaker {
    private final static int TIMEOUT = 5000;
    private final String baseUrl = "https://airapi.airly.eu/v1/";
    private final String acceptHeader = "application/json";
    private final CommandLine cmd;
    private RequestType requestType;
    private HashMap<String, String> parameters = new HashMap<>();
    private String apikeyHeader;
    private String url;

    public RequestMaker(CommandLine cmd, String apiKey) throws IOException {
        this.cmd = cmd;
        apikeyHeader = apiKey;
        if (cmd.hasOption("sensorid")) {
            this.requestType = RequestType.Sensor;
            parameters.put("sensorID", cmd.getOptionValue("sensorid"));
        } else {
            this.requestType = RequestType.NearestSensor;
            parameters.put("longitude", cmd.getOptionValue("longitude"));
            parameters.put("latitude", cmd.getOptionValue("latitude"));
            if (this.getDataAsJsonObject().getAsJsonPrimitive("id") == null)
                throw new IOException("Couldn't find ID of a nearest sensor. Please check the coordinates.");
            String id = this.getDataAsJsonObject().getAsJsonPrimitive("id").getAsString();
            parameters.clear();
            this.requestType = RequestType.Sensor;
            parameters.put("sensorID", id);
        }
    }

    public JsonObject getDataAsJsonObject() throws IOException {

        final int responseCode;

        this.makeURL();
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("apikey", apikeyHeader);
        conn.setRequestProperty("Accept", acceptHeader);
        conn.setConnectTimeout(TIMEOUT);
        responseCode = conn.getResponseCode();
        switch (responseCode) {
            case 404:
                throw new IOException("HTTP ERROR 404: Not Found");
            case 401:
                throw new IOException("HTTP ERROR 401: Unauthorized");
            case 403:
                throw new IOException("HTTP ERROR 403: Forbidden");
            case 500:
                throw new IOException("HTTP ERROR 500: Unexpected Error");
            case 400:
                throw new IOException("HTTP ERROR 400: Input validation error");
            case 200: {
                JsonParser parser = new JsonParser();
                return parser.parse(new InputStreamReader(conn.getInputStream())).getAsJsonObject();

            }
            default:
                return null;
        }
    }

    private void makeURL() {

        StringBuilder tempURL = new StringBuilder(baseUrl);
        tempURL.append(this.requestType);
        switch (this.requestType) {
            case NearestSensor:
                tempURL.append("?latitude=")
                        .append(parameters.get("latitude"))
                        .append("&");
                tempURL.append("longitude=")
                        .append(parameters.get("longitude"))
                        .append("&maxDistance=1000");
                break;
            case Sensor:
                tempURL.append("?sensorId=")
                        .append(parameters.get("sensorID"));
                break;
        }
        this.url = tempURL.toString();

    }

}
