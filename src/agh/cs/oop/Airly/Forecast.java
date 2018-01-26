package agh.cs.oop.Airly;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kamil on 2018-01-26.
 */


public class Forecast {

    private String fromDateTime;
    private String tillDateTime;
    private PollutionMeasurements polutionMeasurements;
    private Map<String, Object> additionalProperties = new HashMap<>();

    public String getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(String fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public String getTillDateTime() {
        return tillDateTime;
    }

    public void setTillDateTime(String tillDateTime) {
        this.tillDateTime = tillDateTime;
    }

    public PollutionMeasurements getPolutionMeasurements() {
        return polutionMeasurements;
    }

    public void setPolutionMeasurements(PollutionMeasurements polutionMeasurements) {
        this.polutionMeasurements = polutionMeasurements;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
