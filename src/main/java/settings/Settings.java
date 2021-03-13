package settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.ArduinoConnection;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Settings {
    public static String LANGUAGE;
    public static String DEFAULT_DEVICE;
    public static Integer SCALE_WIDTH;
    public static Integer SCALE_HEIGHT;
    public static Integer TOP_AMOUNT;
    public static Integer BOTTOM_AMOUNT;
    public static Integer SIDES_AMOUNT;
    public static Double THICKNESS;
    public static Double STAND_WIDTH;
    public static Boolean INVERT_ORDER;

    public static void load() {
        ObjectMapper mapper = new ObjectMapper();
        Map data = new HashMap<>();
        try {
            data = mapper.readValue(Paths.get("settings", "settings.json").toFile(), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        write(data);

        Strings.getInstance();

        ArduinoConnection.getInstance();
    }

    public static void change() {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> data = createMap();

        try {
            mapper.writeValue(Paths.get("settings", "settings.json").toFile(), data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Strings.getInstance();
    }

    private static Map<String, String> createMap() {
        Map<String, String> data = new HashMap<>();
        data.put("LANGUAGE", LANGUAGE);
        data.put("DEFAULT_DEVICE", DEFAULT_DEVICE);
        data.put("SCALE_WIDTH", String.valueOf(SCALE_WIDTH));
        data.put("SCALE_HEIGHT", String.valueOf(SCALE_HEIGHT));
        data.put("TOP_AMOUNT", String.valueOf(TOP_AMOUNT));
        data.put("BOTTOM_AMOUNT", String.valueOf(BOTTOM_AMOUNT));
        data.put("SIDES_AMOUNT", String.valueOf(SIDES_AMOUNT));
        data.put("THICKNESS", String.valueOf(THICKNESS));
        data.put("STAND_WIDTH", String.valueOf(STAND_WIDTH));
        data.put("INVERT_ORDER",String.valueOf(INVERT_ORDER));
        return data;
    }

    private static void write(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            switch (entry.getKey()) {
                case "LANGUAGE":
                    LANGUAGE = entry.getValue();
                    break;
                case "DEFAULT_DEVICE":
                    DEFAULT_DEVICE = entry.getValue();
                    break;
                case "SCALE_WIDTH":
                    SCALE_WIDTH = Integer.valueOf(entry.getValue());
                    break;
                case "SCALE_HEIGHT":
                    SCALE_HEIGHT = Integer.valueOf(entry.getValue());
                    break;
                case "TOP_AMOUNT":
                    TOP_AMOUNT = Integer.valueOf(entry.getValue());
                    break;
                case "BOTTOM_AMOUNT":
                    BOTTOM_AMOUNT = Integer.valueOf(entry.getValue());
                    break;
                case "SIDES_AMOUNT":
                    SIDES_AMOUNT = Integer.valueOf(entry.getValue());
                    break;
                case "THICKNESS":
                    THICKNESS = Double.valueOf(entry.getValue());
                    break;
                case "STAND_WIDTH":
                    STAND_WIDTH = Double.valueOf(entry.getValue());
                    break;
                case "INVERT_ORDER":
                    INVERT_ORDER = Boolean.valueOf(entry.getValue());
                    default:
                    System.out.println("Fail settings");
            }
        }
    }

}
