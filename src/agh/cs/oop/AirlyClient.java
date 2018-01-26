package agh.cs.oop;

import java.io.IOException;

import org.apache.commons.cli.*;


public class AirlyClient {

    public static void main(String[] args) {

        Options options = new Options();

        Option longitude = new Option("longitude", true, "longitude of a point");
        options.addOption(longitude);

        Option latitude = new Option("latitude", true, "latitude of a point");
        options.addOption(latitude);

        Option history = new Option("history", false, "whether we will show history");
        options.addOption(history);

        Option sensorId = new Option("sensorid", true, "Id of a sensor");
        options.addOption(sensorId);

        Option apiKeyOpt = new Option("apikey", true, "API KEY, not needed if one is present in the environment");
        options.addOption(apiKeyOpt);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;
        String apiKey = null;
        try {
            cmd = parser.parse(options, args);

            if (System.getenv().get("API_KEY") != null)
                apiKey = System.getenv().get("API_KEY");

            if (cmd.hasOption("apikey") && apiKey == null)
                apiKey = cmd.getOptionValue("apikey");
            if ((!cmd.hasOption("sensorid") && (!cmd.hasOption("longitude") || !cmd.hasOption("latitude"))) || apiKey == null)
                throw new ParseException("Require either -sensorid or -latitude with -longitude and apikey (either in the environment or as option!");
            RequestMaker maker = new RequestMaker(cmd, apiKey);
            DataDeserializer deserializer = new DataDeserializer(maker.getDataAsJsonObject());
            Visualizer visualizer = new Visualizer(deserializer.deserialize());
            visualizer.visualize(cmd);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            System.out.println("Let you learn how to use it: " +
                    "\n 1. Make sure you have installed apache.commons.cli and gson-2.3.1 " +
                    "\n 2. Open terminal" +
                    "\n 3. Type name of bin" +
                    "\n 4. Add argument -api-key or make sure it is present in the environment" +
                    "\n 5. Add argument -longitude and longitude with -latitude and latitude or just -sensor-id with sensor ID" +
                    "\n 6. You can also add option -history to see a history of measurements");
            formatter.printHelp("utility-name", options);
            System.exit(0);
        } catch (IOException e) {
            System.out.print(e.getMessage());
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }


    }

}