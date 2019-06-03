package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class MenuController {
    Stage stage;
    FXMLLoader fxmlLoader;
    Parent root;
    private Image imageFile;
    BufferedImage imageToCut;
    int difficultyLevel;
    Timeline timeline;


    @FXML public Button startGame;
    @FXML public Button exitGame;
    @FXML public Button openMainMenu;
    @FXML public Button viewScores;
    @FXML public Button getImageButton;
    @FXML public ImageView selectedImageDisplay;
    @FXML public TextField difficultySetField;

    //three puzzle Images to be animated
    @FXML ImageView puzzle1;
    @FXML ImageView puzzle2;
    @FXML ImageView puzzle3;

    public MenuController(){
        difficultyLevel = 2;
        imageToCut = null;

    }

    //when the "Start game" button is clicked the window
    //is switched to the "PuzzleSelect" view
    @FXML
    public void openPuzzleSetUp(){

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxmlFiles/PuzzleSetUpScreen.fxml"));
                fxmlLoader.setController(this);
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                stage.setTitle("PuzzlePics");
                stage.setScene(scene);
                stage.show();

                //setting the default image;
                imageFile = selectedImageDisplay.getImage();
                File defaultImage = new File(
                        "/Users/cheap_ramen/Documents/college/Projekt_2_s18710/src/sample/otherFiles/defaultImage.jpg");
                imageToCut = ImageIO.read(new File(defaultImage.toPath().toString()));
            } catch (IOException x) {
                x.printStackTrace();
            }
    }

    //Upon clicking Button opens Finder and
    //allows user to choose their own image for the puzzles
    @FXML
    public void pickImage (){

        getImageButton.setOnAction((e) -> {
            FileChooser fch = new FileChooser();
            fch.setTitle("Choose an Image");
            fch.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg","*.jpeg", "*.gif"
                    ));
            File selectedImage = fch.showOpenDialog(stage);
            if(selectedImage == null){
                return;
            }else{
                Image newImage = new Image("file:"+selectedImage.toPath().toString());
                try {
                    imageToCut = ImageIO.read(new File(selectedImage.toPath().toString()));
                }catch (IOException exc){
                    exc.printStackTrace();
                }
                imageFile = newImage;
                selectedImageDisplay.setImage(imageFile);
            }
        });
    }

    //After the user has chosen the Image an difficulty
    //the game is initialized. It opens in another Window
    @FXML
    public void setInitializeGame(){
        if(difficultyLevel != 0 && imageToCut != null) {
            GameController gc = new GameController();
            gc.setController(imageToCut,difficultyLevel, stage, this);
            try {
                fxmlLoader = new FXMLLoader(getClass().getResource("fxmlFiles/PuzzleGameScreen.fxml"));
                fxmlLoader.setController(gc);
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                stage.setScene(scene);
                gc.prepareBoard();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            return;
        }
    }

    //loads the main menu screen upon clicking on the
    //left arrow button
    @FXML
    private void setOpenMainMenu(){
        fxmlLoader = new FXMLLoader(getClass().getResource("fxmlFiles/MainMenu.fxml"));
        fxmlLoader.setController(this);
        try {
            Scene scene = new Scene(fxmlLoader.load(),600,400);
            stage.setScene(scene);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //Gets value from the text field
    //which decides into how many puzzles the image will be cut into
    @FXML
   private void difficultyValueSet(){
        difficultyLevel= Integer.parseInt(difficultySetField.getText());
        if(difficultyLevel >=7){
            Alert faultyDifficultyAlert = new Alert(Alert.AlertType.ERROR);
            faultyDifficultyAlert.setTitle("Selected level is too high");
            faultyDifficultyAlert.setContentText("Please enter a level between 2 and 6");
            faultyDifficultyAlert.show();
            difficultyLevel = 2;
            return;
        }
        System.out.println(difficultyLevel);
    }

    //button closes the application
    @FXML
    private void exitGame(){
        exitGame.setOnAction((event) -> {
            Platform.exit();
        });
    }

    //opens the best scores window
    @FXML
    private void openViewScores(){
        try {
            scoresViewController scoresViewController = new scoresViewController(timeline,this, stage);
            fxmlLoader = new FXMLLoader(getClass().getResource("fxmlFiles/BestScoresScreen.fxml"));
            fxmlLoader.setController(scoresViewController);
            Scene scene = new Scene(fxmlLoader.load(), 600,400);
            stage.setTitle("PuzzlePics");
            stage.setScene(scene);
            stage.show();
            scoresViewController.setTextFields();
            scoresViewController.setAnimations();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //animates the puzzle images in Main menu
    public void animatePuzzles(){
        RotateTransition rotatePuzzle1 = new RotateTransition(Duration.seconds(4), puzzle1);
        RotateTransition rotatePuzzle2 = new RotateTransition(Duration.seconds(4), puzzle2);
        RotateTransition rotatePuzzle3 = new RotateTransition(Duration.seconds(4), puzzle3);

        rotatePuzzle1.setByAngle(180);
        rotatePuzzle2.setByAngle(180);
        rotatePuzzle3.setByAngle(180);

        rotatePuzzle1.setAutoReverse(true);
        rotatePuzzle2.setAutoReverse(true);
        rotatePuzzle3.setAutoReverse(true);

        rotatePuzzle2.setCycleCount(100);
        rotatePuzzle1.setCycleCount(100);
        rotatePuzzle3.setCycleCount(100);

        rotatePuzzle1.play();
        rotatePuzzle2.play();
        rotatePuzzle3.play();
    }

    //sets the stage for controller
    public void setStage(Stage stage, FXMLLoader fxmlLoader, Parent root, Timeline timeline){
        this.stage=stage;
        this.fxmlLoader = fxmlLoader;
        this.root = root;
        this.timeline = timeline;
    }

}
