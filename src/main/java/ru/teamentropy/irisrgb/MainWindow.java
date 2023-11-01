package ru.teamentropy.irisrgb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.teamentropy.irisrgb.dataflow.DataFlow;
import ru.teamentropy.irisrgb.dataflow.DataFlowImpl;

import java.io.IOException;
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

    public static void main(String[] args) throws InterruptedException {
        DataFlow dataFlow = new DataFlowImpl();

        //System.out.println(Arrays.toString(dataFlow.getPorts()));
        Thread thread = new Thread(){
            public void run(){
                try {
                    dataFlow.connect("COM6", 115200, 8, 0, 0);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        thread.start();
        //launch();
    }
}