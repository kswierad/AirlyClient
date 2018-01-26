package agh.cs.oop;

import agh.cs.oop.Airly.CurrentMeasurements;
import agh.cs.oop.Airly.Forecast;
import agh.cs.oop.Airly.History;
import agh.cs.oop.Airly.Measurement;
import com.google.gson.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kamil on 2018-01-26.
 */
public class DataDeserializer {
    private final JsonObject data;

    public DataDeserializer(JsonObject data) {
        this.data = data;
    }

    public Measurement deserialize() {
        Measurement result = new Measurement();
        Gson gson = new Gson();
        result.setCurrentMeasurements(gson.fromJson(data.get("currentMeasurements"), CurrentMeasurements.class));
        JsonArray history = data.getAsJsonArray("history");
        List<History> list = new LinkedList<>();
        for (JsonElement element : history) {
            list.add(gson.fromJson(element, History.class));
        }
        result.setHistory(list);
        JsonArray forecast = data.getAsJsonArray("forecast");
        List<Forecast> forecasts = new LinkedList<>();
        for (JsonElement element : forecast) {
            forecasts.add(gson.fromJson(element, Forecast.class));
        }
        result.setForecast(forecasts);
        return result;

    }

}
