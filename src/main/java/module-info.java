module fantastpicture.mvc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.datatransfer;

    opens fantastpicture.mvc to javafx.fxml;
    exports fantastpicture.mvc;
}