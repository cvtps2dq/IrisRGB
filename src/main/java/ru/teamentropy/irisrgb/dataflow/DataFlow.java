package ru.teamentropy.irisrgb.dataflow;

import javafx.scene.paint.Color;

public interface DataFlow {
    public void setColor(Color color);
    public String[] getPorts();
    public void connect(String addr, int baudrate, int databits, int stopBits, int parity) throws InterruptedException;
}
