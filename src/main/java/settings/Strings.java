package settings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Data
public class Strings {

    private static Strings instance;

    @JsonProperty("name_app")
    private String nameApp;
    private String device;
    private String screen;
    private String resolution;
    @JsonProperty("refresh_rate")
    private String refreshRate;
    @JsonProperty("bit_depth")
    private String bitDepth;
    @JsonProperty("settings_app")
    private String settingsApp;
    private String language;
    private String zone;
    private String scale;
    @JsonProperty("top_amount")
    private String topAmount;
    @JsonProperty("bottom_amount")
    private String bottomAmount;
    @JsonProperty("sides_amount")
    private String sidesAmount;
    private String thickness;
    @JsonProperty("stand_width")
    private String standWidth;
    @JsonProperty("invert_order")
    private String invertOrder;
    private String calculate;
    private String start;
    private String stop;
    @JsonProperty("live_preview")
    private String livePreview;


    @JsonIgnore
    private String currentLanguage;

    private Strings() {

    }

    public static Strings getInstance() {
        if (instance == null || instance.currentLanguage.equals(Settings.LANGUAGE)) {
            instance = load();
            instance.currentLanguage = Settings.LANGUAGE;
        }
        return instance;
    }

    private static Strings load() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(Paths.get(new File(".").getCanonicalPath(), "settings", "language", Settings.LANGUAGE + ".json").toFile(), Strings.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Strings();
    }
}
