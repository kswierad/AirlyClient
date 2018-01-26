package agh.cs.oop;

/**
 * Created by Kamil on 2018-01-25.
 */
public enum RequestType {
    NearestSensor,
    Sensor;

    @Override
    public String toString() {
        switch (this) {
            case NearestSensor:
                return "nearestSensor/measurements";
            case Sensor:
                return "sensor/measurements";
            default:
                return null;
        }
    }
}
