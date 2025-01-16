module uk.co.robertwalters.techathon {
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires eu.hansolo.tilesfx;
    requires java.desktop;

    opens uk.co.robertwalters.techathon to javafx.fxml;
    exports uk.co.robertwalters.techathon;

}