package agh.cs.oop.Airly;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kamil on 2018-01-26.
 */


public class PollutionMeasurements {

    private Integer pollutionLevel;
    private Map<String, Object> additionalProperties = new HashMap<>();

    public Integer getPollutionLevel() {
        return pollutionLevel;
    }

    public void setPollutionLevel(Integer pollutionLevel) {
        this.pollutionLevel = pollutionLevel;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
