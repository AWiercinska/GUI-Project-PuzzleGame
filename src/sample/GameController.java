package sample;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class GameController {

    //Parent root;
    int difficultyLevel;
    private BufferedImage puzzleImg;

    @FXML GridPane puzzleGrid;

    void setValues(BufferedImage image, int difficultyLevel){
        this.puzzleImg=image;
        this.difficultyLevel=difficultyLevel;
    }

    void prepareBoard(){
        cutImage();
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
}
