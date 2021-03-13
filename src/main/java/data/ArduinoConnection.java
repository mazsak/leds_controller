package data;

import com.fazecast.jSerialComm.SerialPort;

import java.awt.*;
import java.io.IOException;

public class ArduinoConnection {

    private static ArduinoConnection instance;
    public SerialPort sp;

    private ArduinoConnection(){
        sp = SerialPort.getCommPort("COM3"); // device name TODO: must be changed
        sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written
    }

    public static ArduinoConnection getInstance() {
        if (instance == null) {
            instance = new ArduinoConnection();
        }
        return instance;
    }

    public void sendColor(Color color, int index) throws IOException, InterruptedException {
        if (sp == null) {
            return;
        }
        if (sp.openPort()) {
            System.out.println("Port is open :)");
        } else {
            System.out.println("Failed to open port :(");
            return;
        }

        sp.getOutputStream().write(String.format("#%02x%02x%02x %03x", color.getRed(), color.getGreen(), color.getBlue(), index).getBytes());
        sp.getOutputStream().flush();
    }
}
