package main;

        import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;
        import utils.DBConnection;

        import java.io.IOException;
        import java.util.Objects;

/** This is the main class.*/
public class Main extends Application {

    /** Loads test data for application startup.*/
    private void addTestData() {
        ;
    }

    /** Defines what happens on application startup.*/
    @Override
    public void start(Stage primaryStage) throws IOException {
        addTestData();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        primaryStage.setTitle("Appointment Scheduling System");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    /** Launches main.
     Javadocs path: SchedulingSystem/javadocs/ */
    public static void main(String[] args) {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}