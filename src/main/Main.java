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

    /** Defines what happens on application startup.*/
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginScreen.fxml")));
        primaryStage.setTitle("SchedulingSystem");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    /** Launches main. Opens and closes database connection.
     Javadocs path: SchedulingSystem/javadocs/ */
    public static void main(String[] args) {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }

}