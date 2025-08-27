module gsole {
    requires javafx.controls;
    requires javafx.fxml;

    opens gsole to javafx.fxml;
    exports gsole;
}
