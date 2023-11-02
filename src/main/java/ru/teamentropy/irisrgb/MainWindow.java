package ru.teamentropy.irisrgb;

import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.teamentropy.irisrgb.dataflow.DataFlowImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

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
        Color start = Color.MAGENTA;
        Color end = Color.FORESTGREEN;
        System.out.println(Arrays.toString(SerialPort.getCommPorts()));

        DataFlowImpl dataFlow = new DataFlowImpl(SerialPort.getCommPorts()[0],
                baudRate, dataBits, stopBits, parity);



        //System.out.println(Arrays.toString(dataFlow.getPorts()));
        Thread thread = new Thread(){
            public void run(){
                try {
                    dataFlow.connectAlt();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                dataFlow.transition(start, end, 64, 1000, 0.005D);
            }
        };
        thread.start();
        //launch();
    }
}