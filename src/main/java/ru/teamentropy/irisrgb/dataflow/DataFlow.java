package ru.teamentropy.irisrgb.dataflow;

import javafx.scene.paint.Color;
import jssc.SerialPortException;

import java.io.Serial;

public interface DataFlow {
    public void setColor(Color color);
    public String[] getPorts();
    public void connect(String addr, int baudrate, int databits, int stopBits, int parity) throws SerialPortException;
}
