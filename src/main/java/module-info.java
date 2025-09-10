module com.example.supermarketsim {
    requires java.desktop;

    opens com.example.supermarketsim to javafx.fxml;
    exports com.example.supermarketsim;
}