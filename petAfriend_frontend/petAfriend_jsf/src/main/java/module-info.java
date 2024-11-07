module com.pets.petafriend.petafriend_javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.pets.petafriend.petafriend_javafx to javafx.fxml;
    exports com.pets.petafriend.petafriend_javafx;
}