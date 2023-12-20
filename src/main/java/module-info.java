module com.example.javafxautocomplete {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.javafxautocomplete to javafx.fxml;
    exports com.example.javafxautocomplete;
}