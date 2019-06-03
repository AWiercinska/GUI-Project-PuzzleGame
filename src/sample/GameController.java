package sample;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class GameController {

    private int gridSize;
    private ArrayList <Integer> imagesIndexes;
    private BufferedImage puzzleImg;
    private boolean gameStarted, gameWon;

    //arrays:
    //board which contains current info on puzzles on the grid
    private Puzzle [][] currentBoard;
    //two arrays holding the starting indexes and images for the puzzles
    private int [][] startingIndexes;
    private Image [][] startingImages;

    //for switching the scene to menu
    Stage stage;
    MenuController menuController;


    @FXML Label timer;
    @FXML GridPane puzzleGrid;
    @FXML Button goToMenu;
    @FXML public Button resetGameButton;

    void setController(BufferedImage image, int difficultyLevel, Stage stage, MenuController mc){
        this.stage = stage;
        this.menuController = mc;
        this.puzzleImg=image;
        this.gridSize=difficultyLevel;
        imagesIndexes = new ArrayList<>();

        currentBoard = new Puzzle[difficultyLevel][difficultyLevel];
        startingIndexes = new int[difficultyLevel][difficultyLevel];
        startingImages = new Image[difficultyLevel][difficultyLevel];

        gameStarted = false;
        gameWon = false;
    }

    //prepares the board for the game
    void prepareBoard(){
        prepareImage();
        setGrid();
    }

    //cuts the imported image
    void prepareImage(){
        int index = 0;

        try {
            for (int y = 0; y <= (puzzleImg.getHeight() - puzzleImg.getHeight() / gridSize); y += puzzleImg.getHeight() / gridSize) {
                int xx = 0;
                for (int x = xx; x <= (puzzleImg.getWidth() - puzzleImg.getWidth() / gridSize); x += puzzleImg.getWidth() / gridSize) {
                    ImageIO.write(
                            puzzleImg.getSubimage(x, y, puzzleImg.getWidth() / gridSize, puzzleImg.getHeight() / gridSize),
                            "jpg", new File("/Users/cheap_ramen/Documents/college/Projekt_2_s18710/src/sample/otherFiles/" +
                                    index++ + ".jpg"));
                    imagesIndexes.add(index-1);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    //prepares the game board (sets grid pane, creates new puzzles)
    //adds puzzles to the grid pane and shuffles the puzzle images
    void setGrid(){
        double puzzleWidth = puzzleGrid.getWidth()/gridSize;

        // this loop prepares the gridPane columns and rows
        for(int i =0; i<gridSize;i++){
            ColumnConstraints column = new ColumnConstraints(puzzleWidth);
            column.setHalignment(HPos.CENTER);
            puzzleGrid.getColumnConstraints().add(column);

            RowConstraints row = new RowConstraints(puzzleWidth);
            row.setValignment(VPos.CENTER);
            puzzleGrid.getRowConstraints().add(row);
        }

        //this loop creates new puzzles and adds them to the grid pane
        for(int row = 0; row < gridSize; row++){
            for(int col = 0; col < gridSize; col++){

                int randomIndex = (int)(Math.random() * imagesIndexes.size());
                int index = imagesIndexes.get(randomIndex);
                imagesIndexes.remove(randomIndex);
                Image puzzleImage = new Image("file:"+ new File("/Users/cheap_ramen/Documents/college/Projekt_2_s18710/src/sample/otherFiles/" +
                        index + ".jpg").getAbsolutePath(),puzzleGrid.getWidth()/gridSize,
                        puzzleGrid.getWidth()/gridSize,
                        false,true);
                //if this is the last puzzle to be added the image is set to null
                if(imagesIndexes.size() == 0) puzzleImage = null;
                Puzzle newPuzzle = new Puzzle(puzzleImage,col,row,puzzleWidth,index,this, gameWon);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxmlFiles/Puzzle.fxml"));
                fxmlLoader.setController(newPuzzle);

                try{
                    Pane newPane = fxmlLoader.load();
                    newPane.setPrefSize(puzzleWidth,puzzleWidth);
                    newPuzzle.setImage();
                    currentBoard[row][col] = newPuzzle;
                    startingIndexes[row][col] = newPuzzle.imgIndex;
                    startingImages[row][col] = newPuzzle.image;
                    puzzleGrid.add(newPane,col,row);
                }catch (IOException e){
                    e.printStackTrace();
                }


            }
        }

    }

    //checks if a given puzzle can be swapped with an empty field
    //if so the field is swapped by the swap Puzzles method
    void checkIfSwapPossible(Puzzle p) {

        if(!gameStarted){
            gameStarted = true;
            startTimer();
        }

        if (p.row + 1 < gridSize && currentBoard[p.row + 1][p.col].image == null)
            swapPuzzles(p.row+1, p.col, p);

        if(p.row - 1 >= 0 && currentBoard[p.row-1][p.col].image == null)
            swapPuzzles(p.row-1, p.col, p);

        if(p.col + 1 < gridSize && currentBoard[p.row][p.col +1].image == null)
            swapPuzzles(p.row, p.col+1, p);

        if(p.col - 1 >= 0 && currentBoard[p.row][p.col - 1].image == null)
            swapPuzzles(p.row, p.col-1, p);

        checkIfGameWon();
    }

    //swaps two puzzles
    void swapPuzzles(int row, int col, Puzzle p){
        Image tmpImg = p.image;
        p.image = currentBoard[row][col].image;
        currentBoard[row][col].image = tmpImg;

        int tmpIndex = p.imgIndex;
        p.imgIndex = currentBoard[row][col].imgIndex;
        currentBoard[row][col].imgIndex = tmpIndex;

        currentBoard[row][col].setImage();
        p.setImage();
    }

    //checks if the game is won
    //if so an alert is shown
    void checkIfGameWon(){
        int winIndex = 0;
        for(int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if(currentBoard[row][col].imgIndex == winIndex){
                    gameWon = true;
                }else{
                    gameWon = false;
                    return;
                }
                winIndex++;
            }
        }

        //alert is shown when the game is won
        if(gameWon){

            for(int row = 0; row <gridSize; row++){
                for(int col = 0; col <gridSize; col++){
                    currentBoard[row][col].setGameWonToTrue();
                }
            }


            ImageView winImage = new ImageView();
            winImage.setImage(new Image("file:"+ new File(
                    "/Users/cheap_ramen/Documents/college/Projekt_2_s18710/src/sample/otherFiles/gameWonIMG.jpg")));

            Alert winAlert = new Alert(Alert.AlertType.INFORMATION);
            winAlert.setTitle("Win");
            winAlert.setContentText("You solved the puzzle in "+timer.getText()+ "! Congratulation!");
            winAlert.setGraphic(winImage);
            winAlert.show();

            writeScoreToFile();

            for(int i = 0; i < gridSize*gridSize; i++){
                File fileToDelete = new File("/Users/cheap_ramen/Documents/college/Projekt_2_s18710/src/sample/otherFiles"
                + i + ".jpg");
                fileToDelete.delete();
            }
        }
    }

    //resets the board when clicked
    @FXML void resetBoard(){
        for(int row = 0; row < gridSize; row++){
            for(int col = 0; col < gridSize; col++){
                currentBoard[row][col].image = startingImages [row][col];
                currentBoard[row][col].imgIndex = startingIndexes[row][col];

                currentBoard[row][col].setImage();
            }
        }
    }

    //switches view to puzzle selection menu
    @FXML void setGoToMenu(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxmlFiles/PuzzleSetUpScreen.fxml"));
        fxmlLoader.setController(menuController);
        try {
            Scene scene = new Scene(fxmlLoader.load(),600,400);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //starts the timer thread
    void startTimer(){
        Task<Double> timerTask = new Task<Double>() {

            double startTime = System.currentTimeMillis();
            double currentTime;

            @Override
            protected Double call() throws Exception {
                while(!gameWon){
                    Thread.sleep(100);
                   currentTime = System.currentTimeMillis() - startTime;
                   updateMessage(timeToString(currentTime));
                }
                return currentTime;
            }
        };

        Thread timerThread = new Thread(timerTask);
        timerThread.setDaemon(true);
        timerThread.start();
        timer.textProperty().bind(timerTask.messageProperty());
    }

    //converts miliseconds into text that will appear on a timer label
    String timeToString(double time){

        int minutes, seconds, miliSeconds;

        minutes = (int)(time / 60000);
        seconds = (int)(time /1000 - minutes*60);
        miliSeconds = (int)(time - minutes*60000)%100;

        StringBuilder timerBuilder = new StringBuilder();

        timerBuilder.append(minutes < 10 ? "0"+minutes+":" : minutes+":");
        timerBuilder.append(seconds < 10 ? "0"+seconds+":" : seconds+":");
        timerBuilder.append(miliSeconds < 10 ? "0"+miliSeconds : miliSeconds);

        return timerBuilder.toString();

    }

    void writeScoreToFile(){
        try{
            File bestScoresFile = new File(
                    "/Users/cheap_ramen/Documents/college/Projekt_2_s18710/src/sample/otherFiles/solveTimes.txt"
            );
            bestScoresFile.createNewFile();
            FileWriter fw = new FileWriter(bestScoresFile, true);
            fw.write(timer.getText()+System.lineSeparator());
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
