package sample;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Puzzle{
    GameController gameController;
    Image image;
    int col, row;
    double size;
    int imgIndex;
    boolean isGameWon;

    @FXML ImageView imageView;

    public Puzzle(Image image, int column, int row, double size, int index, GameController gc, boolean isGameWon) {
        this.image = image;
        this.row = row;
        this.col = column;
        this.size = size;
        this.imgIndex = index;
        this.gameController = gc;
        this.isGameWon = isGameWon;
    }

    void setImage(){
            imageView.setImage(this.image);
            imageView.setFitHeight(size);
            imageView.setFitWidth(size);
    }

    void setGameWonToTrue(){
        this.isGameWon = true;
    }

    @FXML void puzzleClicked(){
        if(!isGameWon){
            gameController.checkIfSwapPossible(this);
        }else {
            return;
        }
    }


}
