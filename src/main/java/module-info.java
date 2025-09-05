module gsole {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires okhttp3;

    opens gsole to javafx.fxml;
    opens gsole.supabase to javafx.fxml;

    exports gsole;
}
