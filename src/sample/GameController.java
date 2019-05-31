package sample;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class GameController {

    int difficultyLevel;
    private BufferedImage puzzleImg;
    ArrayList<Image> imageList;

    @FXML GridPane puzzleGrid;
    @FXML Button resetGameButton;

    void setController(BufferedImage image, int difficultyLevel){
        this.puzzleImg=image;
        this.difficultyLevel=difficultyLevel;
        imageList = new ArrayList<>();
    }

    void prepareBoard(){
        cutImage();
        setGrid();
    }

    void cutImage(){
        int index = 0;

        try {
            for (int y = 0; y <= (puzzleImg.getHeight() - puzzleImg.getHeight() / difficultyLevel); y += puzzleImg.getHeight() / difficultyLevel) {
                int xx = 0;
                for (int x = xx; x <= (puzzleImg.getWidth() - puzzleImg.getWidth() / difficultyLevel); x += puzzleImg.getWidth() / difficultyLevel) {
                    ImageIO.write(
                            puzzleImg.getSubimage(x, y, puzzleImg.getWidth() / difficultyLevel, puzzleImg.getHeight() / difficultyLevel),
                            "jpg", new File("/Users/cheap_ramen/Documents/college/Projekt_2_s18710/src/sample/cutImage/" +
                                    index++ + ".jpg"));
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

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

        int imageIndex = 0;
        for(int row = 0; row < difficultyLevel; row++){
            for(int col = 0; col < difficultyLevel; col++){
                File puzzleImage = new File("/Users/cheap_ramen/Documents/college/Projekt_2_s18710/src/sample/cutImage/" +
                        imageIndex++ + ".jpg");
                imageList.add(new Image("file:"+puzzleImage.toPath().toString(),puzzleWidth,puzzleWidth,
                        true,true));
                Puzzle newPuzzle = new Puzzle(new Image("file:"+puzzleImage.toPath().toString(),
                        puzzleWidth,puzzleWidth,
                        true,true),col,row,puzzleWidth);
                newPuzzle.setPrefSize(puzzleWidth,puzzleWidth);
                newPuzzle.setPuzzleImage();
                newPuzzle.setId("puzzleButton");
                puzzleGrid.add(newPuzzle,col,row);

            }
        }

    }
}
