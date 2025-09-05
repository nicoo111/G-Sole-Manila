package gsole;

import gsole.supabase.SupabaseClient;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        SupabaseClient supabase = new SupabaseClient();

        TextArea output = new TextArea();
        output.setEditable(false);

        // Run Supabase fetch in a background thread
        new Thread(() -> {
            try {
                String items = supabase.getItems();
                javafx.application.Platform.runLater(() -> output.setText(items));
            } catch (Exception e) {
                e.printStackTrace();
                javafx.application.Platform.runLater(() -> output.setText("Error: " + e.getMessage()));
            }
        }).start();

        VBox root = new VBox(output);
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Supabase Test");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
