package sample;

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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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


    //@FXML private Text title;
    @FXML public Button startGame;
    @FXML public Button exitGame;
    @FXML public Button getImageButton;
    @FXML public ImageView selectedImageDisplay= null;
    @FXML public TextField difficultySetField;

    public MenuController(){
        difficultyLevel = 0;
        imageToCut = null;
    }

    //when the "Start game" button is clicked the window
    //is switched to the "PuzzleSelect" view
    @FXML
    public void openSelection(){

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PuzzleSelect.fxml"));
                fxmlLoader.setController(this);
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                stage.setTitle("Select Puzzle");
                stage.setScene(scene);
                stage.show();
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
            gc.setController(imageToCut,difficultyLevel);
            try {
                fxmlLoader = new FXMLLoader(getClass().getResource("PuzzleGameScreen.fxml"));
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

    //Gets value from the text field
    //which decides into how many puzzles the image will be cut into
    @FXML
    void difficultyValueSet(){
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
    public void exitGame(){
        exitGame.setOnAction((event) -> {
            Platform.exit();
        });
    }

    //sets the stage for controller
    void setStage(Stage stage, FXMLLoader fxmlLoader, Parent root){
        this.stage=stage;
        this.fxmlLoader = fxmlLoader;
        this.root = root;
    }
}
