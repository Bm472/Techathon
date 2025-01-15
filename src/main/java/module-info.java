module uk.co.robertwalters.techathon {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens uk.co.robertwalters.techathon to javafx.fxml;
    exports uk.co.robertwalters.techathon;

}