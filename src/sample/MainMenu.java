package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenu extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxmlFiles/MainMenu.fxml"));
        Parent root = fxmlLoader.load();

        MenuController controller = fxmlLoader.getController();

        primaryStage.setTitle("PuzzlePics");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
        controller.setStage(primaryStage, fxmlLoader,root);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
