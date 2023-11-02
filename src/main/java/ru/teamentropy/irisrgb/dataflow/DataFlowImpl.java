package ru.teamentropy.irisrgb.dataflow;

import javafx.scene.paint.Color;
import com.fazecast.jSerialComm.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;



public class DataFlowImpl{
    private SerialPort port;
    private int baudRate;
    private int dataBits;
    private int stopBits;
    private int parity;

    public DataFlowImpl(SerialPort port, int baudRate, int dataBits, int stopBits, int parity) {
        this.port = port;
        this.baudRate = baudRate;
        this.dataBits = dataBits;
        this.stopBits = stopBits;
        this.parity = parity;
    }

    public SerialPort getPort() {
        return port;
    }

    public void setPort(SerialPort port) {
        this.port = port;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public int getDataBits() {
        return dataBits;
    }

    public void setDataBits(int dataBits) {
        this.dataBits = dataBits;
    }

    public int getStopBits() {
        return stopBits;
    }

    public void setStopBits(int stopBits) {
        this.stopBits = stopBits;
    }

    public int getParity() {
        return parity;
    }

    public void setParity(int parity) {
        this.parity = parity;
    }

    public String[] getPorts(){
        return new String[]{Arrays.toString(SerialPort.getCommPorts())};
    }

    public void setColor(Color color) {

    }

    private double[] getColorComponents(Color color){
        double [] output = new double[3];
        output[0] = color.getRed();
        output[1] = color.getGreen();
        output[2] = color.getBlue();
        return output;
    }

    private String createDFColor(Color color){
        return ( (int)(color.getBlue() * 255) + " " + (int)(color.getGreen() * 255) + " " +
                (int)(color.getBlue() * 255) + " ");
    }

    private Color blend(Color color1, Color color2, double ratio){
        double ir = 1.0 - ratio;
        double[] rgb1;
        double[] rgb2;

        rgb1 = getColorComponents(color1);
        rgb2 = getColorComponents(color2);

        System.out.println("DF Blend: Color One - " + Arrays.toString(rgb1));
        System.out.println("DF Blend: Color Two - " + Arrays.toString(rgb2));

        return new Color(rgb1[0] * ratio + rgb2[0] * ir, rgb1[1] * ratio + rgb2[1] * ir,
                rgb1[2] * ratio + rgb2[2] * ir, 1.0);
    }


    public void transition(Color startColor, Color endColor, int delay, int steps, double acc) {
        port.openPort();
        byte[] bytes;
        for (double i = 0; i < steps; i += acc) {
            Color temp = blend(startColor, endColor, i);
            bytes = createDFColor(temp).getBytes();
            port.writeBytes(bytes, bytes.length);
            System.out.println("Trying to upload color: " + createDFColor(temp) + ".");
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void connectAlt() throws InterruptedException {
        port.openPort();
        port.setComPortParameters(baudRate, dataBits, stopBits, parity);
        System.out.println("Connected to: " + port.getSystemPortName());
        System.out.println(port.getDescriptivePortName());
        System.out.println("Baudrate: " + port.getBaudRate());
        port.flushIOBuffers();
        byte[] readBuffer = new byte[port.bytesAvailable()];
        port.readBytes(readBuffer, readBuffer.length);
        System.out.println("IRIS initializing...");
        port.writeBytes("255 255 255 ".getBytes(), "255 255 255 ".getBytes().length);
        while (port.bytesAvailable() == 0)
            Thread.sleep(20);
        while (!new String(readBuffer).equals("Color has been set.")) {
            System.out.println("Waiting");
            port.writeBytes("255 255 255 ".getBytes(), "255 255 255 ".getBytes().length);
            Thread.sleep(20);
            System.out.println("IRIS: " + new String(readBuffer));
            if (!new String(readBuffer).equals("")){

            }
        }

    }

}
