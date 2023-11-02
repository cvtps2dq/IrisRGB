package ru.teamentropy.irisrgb;

import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.teamentropy.irisrgb.dataflow.ColorUtils;
import ru.teamentropy.irisrgb.dataflow.DataFlowImpl;


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
        ColorUtils colorUtils = new ColorUtils();
        Random rand = new Random();
        System.out.println(Arrays.toString(SerialPort.getCommPorts()));

        DataFlowImpl dataFlow = new DataFlowImpl(SerialPort.getCommPorts()[0],
                baudRate, dataBits, stopBits, parity);

        Thread thread = new Thread(() -> {
            try {
                dataFlow.connectAlt();
                while (true) {
                    //Color start = colorUtils.createRandomHSV();
                    //Color end = colorUtils.createRandomHSV();
                    //System.out.println(end.toString());
                    //dataFlow.transition(start, end, 64, 0.05D);
                    dataFlow.colorWheel(32);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        //launch();
    }
}