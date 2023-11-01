package ru.teamentropy.irisrgb.dataflow;

import javafx.scene.paint.Color;
import com.fazecast.jSerialComm.SerialPort;
import java.util.Arrays;


public class DataFlowImpl implements DataFlow{
    //private SerialPort port;

    public String[] getPorts(){
        return new String[]{Arrays.toString(SerialPort.getCommPorts())};
    }

    @Override
    public void setColor(Color color) {

    }

    @Override
    public void connect(String addr, int baudRate, int dataBits, int stopBits, int parity) throws InterruptedException {
        SerialPort port = SerialPort.getCommPort(addr);
        //Random random = new Random();
        port.openPort();
        port.setComPortParameters(baudRate,dataBits, stopBits, parity);
        byte[] bytes;
        for(int i = 0; i < 255; i+=10){
            bytes = (i + " " + i + " " + i + " ").getBytes();
            port.writeBytes(bytes, bytes.length);
            Thread.sleep(64);
            System.out.println(i);
            if(i > 249){
                i = 0;
            }
        }

    }

}
