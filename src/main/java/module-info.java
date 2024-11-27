module org.example.kivipaberkaarid {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.kivipaberkaarid to javafx.fxml;
    exports org.example.kivipaberkaarid;
}