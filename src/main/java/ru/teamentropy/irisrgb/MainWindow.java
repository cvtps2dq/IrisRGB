package ru.teamentropy.irisrgb;

import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.teamentropy.irisrgb.dataflow.DataFlowImpl;
import ru.teamentropy.irisrgb.dataflow.color.DataFlowRGB;


import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class MainWindow extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("IRIS RGB");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        int baudRate = 115200;
        int dataBits = 8;
        int stopBits = 0;
        int parity = 0;
        Random rand = new Random();
        System.out.println(Arrays.toString(SerialPort.getCommPorts()));

        DataFlowImpl dataFlow = new DataFlowImpl(SerialPort.getCommPorts()[0],
                baudRate, dataBits, stopBits, parity);


        Thread thread = new Thread(() -> {
            try {

                dataFlow.connectAlt();
                //audioEngine.getData();
                DataFlowRGB start = new DataFlowRGB(rand.nextDouble(1), rand.nextDouble(1), rand.nextDouble(1));
                while (true) {

                    DataFlowRGB end = new DataFlowRGB(rand.nextDouble(1), rand.nextDouble(1), rand.nextDouble(1));
                    //dataFlow.transition(start, end, 15, 0.005);
                    //start = end;
                    //dataFlow.pulse(64, 0.05, 1000, new DataFlowRGB(255, 0, 255));
                    //dataFlow.audioCapture();
                    //dataFlow.setColor(new Color(0.0, 0.0, 0.0, 0.0));
                    //dataFlow.colorWheel(32, 0.05D);
                    //dataFlow.strobe(new DataFlowRGB(255, 255, 255), 100);

                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        thread.start();
        //launch();
    }
}