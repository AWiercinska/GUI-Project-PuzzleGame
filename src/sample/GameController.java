package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class GameController {

    private int difficultyLevel;
    ArrayList <Integer> imagesIndexes;
    private BufferedImage puzzleImg;

    @FXML GridPane puzzleGrid;
    @FXML Button resetGameButton;

    void setController(BufferedImage image, int difficultyLevel){
        this.puzzleImg=image;
        this.difficultyLevel=difficultyLevel;
        imagesIndexes = new ArrayList<>();
    }

    void prepareBoard(){
        prepareImage();
        setGrid();
    }

    void prepareImage(){
        int index = 0;

        try {
            for (int y = 0; y <= (puzzleImg.getHeight() - puzzleImg.getHeight() / difficultyLevel); y += puzzleImg.getHeight() / difficultyLevel) {
                int xx = 0;
                for (int x = xx; x <= (puzzleImg.getWidth() - puzzleImg.getWidth() / difficultyLevel); x += puzzleImg.getWidth() / difficultyLevel) {
                    ImageIO.write(
                            puzzleImg.getSubimage(x, y, puzzleImg.getWidth() / difficultyLevel, puzzleImg.getHeight() / difficultyLevel),
                            "jpg", new File("/Users/cheap_ramen/Documents/college/Projekt_2_s18710/src/sample/cutImage/" +
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
        double puzzleWidth = puzzleGrid.getWidth()/difficultyLevel;

        for(int i =0; i<difficultyLevel;i++){
            ColumnConstraints column = new ColumnConstraints(puzzleWidth);
            column.setHalignment(HPos.CENTER);
            puzzleGrid.getColumnConstraints().add(column);

            RowConstraints row = new RowConstraints(puzzleWidth);
            row.setValignment(VPos.CENTER);
            puzzleGrid.getRowConstraints().add(row);
        }

        for(int row = 0; row < difficultyLevel; row++){
            for(int col = 0; col < difficultyLevel; col++){

                int randomIndex = (int)(Math.random() * imagesIndexes.size());
                int index = imagesIndexes.get(randomIndex);
                imagesIndexes.remove(randomIndex);
                File puzzleImage = new File("/Users/cheap_ramen/Documents/college/Projekt_2_s18710/src/sample/cutImage/" +
                        index + ".jpg");
                //if(imagesIndexes.size() == 0) puzzleImage = null;
                Puzzle newPuzzle = new Puzzle(new Image("file:"+puzzleImage.toPath().toString(),puzzleGrid.getWidth()/difficultyLevel,
                        puzzleGrid.getWidth()/difficultyLevel,
                        false,true),col,row,puzzleWidth);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Puzzle.fxml"));
                fxmlLoader.setController(newPuzzle);

                try{
                    Pane newPane = fxmlLoader.load();
                    newPane.setPrefSize(puzzleWidth,puzzleWidth);
                    newPuzzle.setImage();
                    puzzleGrid.add(newPane,col,row);
                }catch (IOException e){
                    e.printStackTrace();
                }


            }
        }

    }
}
