package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;


public class GameController {

    Parent root;
    int difficultyLevel;
    private BufferedImage imageFile;

    @FXML GridPane puzzleGrid;

    public GameController(BufferedImage image, int difficultyLevel, Parent root){
        this.imageFile=image;
        this.difficultyLevel=difficultyLevel;
        this.root=root;
    }

    public void initialize(){

    }

    void cutImage(){

    }
}
