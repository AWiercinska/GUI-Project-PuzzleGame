package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenu extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = fxmlLoader.load();

        MenuController controller = fxmlLoader.getController();

        System.out.println(primaryStage.toString());

        primaryStage.setTitle(" Welcome to PuzzlePics");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
        System.out.println(fxmlLoader);
        controller.setStage(primaryStage, fxmlLoader,root);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
