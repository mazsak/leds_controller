package data;

import com.fazecast.jSerialComm.SerialPort;

import java.awt.*;
import java.io.IOException;

public class ArduinoConnection {

    private static ArduinoConnection instance;
    public SerialPort sp;

    private ArduinoConnection(){
        sp = SerialPort.getCommPort("COM4"); // device name TODO: must be changed
        sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written
    }

    public static ArduinoConnection getInstance() {
        if (instance == null) {
            instance = new ArduinoConnection();
        }
        return instance;
    }

    public void sendColor(String message) throws IOException, InterruptedException {
        if (sp == null) {
            return;
        }
        if (sp.openPort()) {
            sp.getOutputStream().write(message.getBytes());
            sp.getOutputStream().flush();
            System.out.println(message);
        } else {
            System.out.println("Failed to open port :(");
            return;
        }


    }
}
