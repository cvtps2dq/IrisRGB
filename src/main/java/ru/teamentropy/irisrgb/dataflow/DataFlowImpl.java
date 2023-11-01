package ru.teamentropy.irisrgb.dataflow;

import javafx.scene.paint.Color;
import jssc.*;


public class DataFlowImpl implements DataFlow{

    private String port;

    public String[] getPorts(){
        return SerialPortList.getPortNames();
    }

    @Override
    public void setColor(Color color) {

    }

    @Override
    public void connect(String addr, int baudrate, int databits, int stopBits, int parity) throws SerialPortException {
        SerialPort port = new SerialPort(addr);
        port.openPort();
        port.setParams(baudrate, databits, stopBits, parity);
        port.writeString("255 0 255 ");
    }
}
