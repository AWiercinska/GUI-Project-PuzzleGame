package sample;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenu extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MenuController controller = new MenuController();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxmlFiles/MainMenu.fxml"));
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();


        primaryStage.setTitle("PuzzlePics");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
        Timeline timeline = new Timeline();
        controller.setStage(primaryStage, fxmlLoader,root,timeline);
        controller.animatePuzzles();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
