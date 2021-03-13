import data.ArduinoConnection;
import settings.Settings;

public class Main {

    public static void main(String[] args) {
        System.out.println("Started");
        Settings.load();
        new Window();
    }
}
