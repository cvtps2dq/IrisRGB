package ru.teamentropy.irisrgb.dataflow;

import com.fazecast.jSerialComm.SerialPort;
import ru.teamentropy.irisrgb.dataflow.color.DataFlowRGB;

import java.util.Arrays;


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

    public void colorWheel(int delay, double acc) throws InterruptedException{
           // port.flushIOBuffers();
            transition(new DataFlowRGB(255, 0, 0), new DataFlowRGB(0, 255, 0), delay, acc);
            transition(new DataFlowRGB(0, 255, 0), new DataFlowRGB(0, 0, 255), delay, acc);
            transition(new DataFlowRGB(0, 0, 255), new DataFlowRGB(255, 0, 0), delay, acc);
            try{
                Thread.sleep(delay);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
    }

    public void transition(DataFlowRGB startColor, DataFlowRGB endColor, int delay, double acc) throws InterruptedException {
        //port.openPort();
        byte[] bytes;
        for (double i = 0; i < 1; i += acc) {
            port.flushIOBuffers();
            DataFlowRGB color = startColor.interpolate(endColor, i);
            bytes = color.toBytes();
            port.writeBytes(bytes, bytes.length);
            while (port.bytesAwaitingWrite() > 0){
                Thread.sleep(20);
            }

            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Done.");

    }

    public void connectAlt() throws InterruptedException {
        port.openPort();
        port.setComPortParameters(baudRate, dataBits, stopBits, parity);
        System.out.println("Connected to: " + port.getSystemPortName());
        System.out.println(port.getDescriptivePortName());
        System.out.println("Baudrate: " + port.getBaudRate());

        // clear port from garbage data
        port.flushIOBuffers();
        System.out.println("IRIS initializing...");

        //write command for zero brightness
        port.writeBytes("0 0 0 ".getBytes(), "0 0 0 ".getBytes().length);

        while (true) {
            // write blue color until we get a message from IRIS
            byte[] readBuffer = new byte[port.bytesAvailable()];
            port.writeBytes("0 0 255 ".getBytes(), "0 0 255 ".getBytes().length);
            Thread.sleep(64);
            port.readBytes(readBuffer, readBuffer.length);
            if (!new String(readBuffer).equals("")){
                System.out.println("IRIS: " + new String(readBuffer));
            }
            // if IRIS responds with "Color has been set." we break from loop and move on
            if(!new String(readBuffer).equals("Color has been set.")){
                System.out.println("IRIS initialized");
                break;
            }
        }

    }

}
