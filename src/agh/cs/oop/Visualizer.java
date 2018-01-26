package agh.cs.oop;

import agh.cs.oop.Airly.CurrentMeasurements;
import agh.cs.oop.Airly.Measurement;
import org.apache.commons.cli.CommandLine;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Kamil on 2018-01-26.
 */
public class Visualizer {
    private final static String header = "\n" +
            "     ___       __  .______    __     ____    ____ \n" +
            "    /   \\     |  | |   _  \\  |  |    \\   \\  /   / \n" +
            "   /  ^  \\    |  | |  |_)  | |  |     \\   \\/   /  \n" +
            "  /  /_\\  \\   |  | |      /  |  |      \\_    _/   \n" +
            " /  _____  \\  |  | |  |\\  \\-.|  `----.   |  |     \n" +
            "/__/     \\__\\ |__| | _| `.__||_______|   |__|     \n" +
            "                                                      \n";
    private final static DecimalFormat f = new DecimalFormat("#0.00");
    private Measurement measurement;

    public Visualizer(Measurement measurement) {
        this.measurement = measurement;
    }

    public void visualize(CommandLine cmd) throws Exception {
        StringBuilder builder = new StringBuilder();
        CurrentMeasurements current = measurement.getCurrentMeasurements();
        builder.append(header);
        if (current.getAirQualityIndex() == null)
            throw new Exception("No data found for this sensor");
        String AQI = f.format(current.getAirQualityIndex());
        String pM25 = current.getPm25() != null
                ? f.format(current.getPm25()) + " μg/m³" : "--";
        String pM10 = current.getPm10() != null
                ? f.format(current.getPm10()) + " μg/m³" : "--";
        String pM1 = current.getPm1() != null
                ? f.format(current.getPm1()) + " μg/m³" : "--";
        String temp = current.getTemperature() != null
                ? f.format(current.getTemperature()) + " °C" : "--";
        String press = current.getPressure() != null
                ? f.format(current.getPressure()) + " hPa" : "--";
        String humi = current.getHumidity() != null
                ? f.format(current.getHumidity()) + "%" : "--";

        builder.append("\nAir Quality Index: ")
                .append(AQI)
                .append("\nPM 2.5:            ")
                .append(pM25)
                .append("\nPM 10:             ")
                .append(pM10)
                .append("\nTemperature:       ")
                .append(temp)
                .append("\nPressure:          ")
                .append(press)
                .append("\nHumidity:          ")
                .append(humi)
                .append("\n");
        System.out.print(builder);


        if (cmd.hasOption("history")) {
            System.out.println("\n" +
                    "  _  _  ___  ___  _____  ___   ___ __   __\n" +
                    " | || ||_ _|/ __||_   _|/ _ \\ | _ \\\\ \\ / /\n" +
                    " | __ | | | \\__ \\  | | | (_) ||   / \\ V / \n" +
                    " |_||_||___||___/  |_|  \\___/ |_|_\\  |_|  \n" +
                    "                                          \n");


            this.measurement.getHistory().forEach(measurement -> {
                if (measurement.getMeasurements() == null) {
                    System.out.println("No history for time period.");
                    return;
                }

                String pm10 = measurement.getMeasurements().getPm10() != null
                        ? f.format(measurement.getMeasurements().getPm10()) : "--";

                String pm25 = measurement.getMeasurements().getPm25() != null
                        ? f.format(measurement.getMeasurements().getPm25()) : "--";


                final SimpleDateFormat parseDate = new SimpleDateFormat("yyyy-MM-DD'T'HH:mm:ss'Z'");
                final SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm");
                final SimpleDateFormat formatDate2 = new SimpleDateFormat("MM-DD");
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("\033[1m")
                            .append("On  ")
                            .append(formatDate2.format(parseDate.parse(measurement.getFromDateTime())))
                            .append("\nFrom ")
                            .append(formatDate.format(parseDate.parse(measurement.getFromDateTime())))
                            .append(" to ")
                            .append(formatDate.format(parseDate.parse(measurement.getTillDateTime())))
                            .append("\033[0m")
                            .append(":\nPM 2.5:      ")
                            .append(pm25)
                            .append("μg/m³")
                            .append("\nPM10:        ")
                            .append(pm10)
                            .append("μg/m³\n\n");
                    System.out.print(sb);
                } catch (ParseException e) {
                    System.out.println(e.getMessage());
                }
            });
        }
    }

}
