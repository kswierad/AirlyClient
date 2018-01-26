package agh.cs.oop.Airly;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kamil on 2018-01-26.
 */


public class Measurement {

    private CurrentMeasurements currentMeasurements;
    private List<History> history = null;
    private List<Forecast> forecast = null;
    private Map<String, Object> additionalProperties = new HashMap<>();

    public CurrentMeasurements getCurrentMeasurements() {
        return currentMeasurements;
    }

    public void setCurrentMeasurements(CurrentMeasurements currentMeasurements) {
        this.currentMeasurements = currentMeasurements;
    }

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
    }

    public List<Forecast> getForecast() {
        return forecast;
    }

    public void setForecast(List<Forecast> forecast) {
        this.forecast = forecast;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
