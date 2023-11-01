module ru.teamentropy.irisrgb {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires jssc;

    opens ru.teamentropy.irisrgb to javafx.fxml;
    exports ru.teamentropy.irisrgb;
}