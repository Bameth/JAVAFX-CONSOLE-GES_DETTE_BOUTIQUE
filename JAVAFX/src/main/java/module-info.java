module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; 
    requires java.persistence;
    requires org.yaml.snakeyaml;
    requires lombok;
    requires org.hibernate.orm.core;
    requires javafx.graphics;

    opens org.example.data.entities to org.hibernate.orm.core;
    opens org.example to javafx.fxml;
    exports org.example;

    opens org.example.controllers to javafx.fxml;
    exports org.example.controllers;
}
