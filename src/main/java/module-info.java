module flores.firstproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens flores.firstproject to javafx.fxml;
    exports flores.firstproject;

    opens Model to javafx.fxml;
    exports Model;

}