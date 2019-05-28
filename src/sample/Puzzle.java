package sample;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Puzzle{
    Image image;
    int col, row;
    double size;

    @FXML ImageView imageView;

    public Puzzle(Image image, int x, int y, double size) {
        this.image = image;
        this.row = x;
        this.col = y;
        this.size = size;

    }

    void setImage(){
        imageView.setImage(this.image);
        imageView.setFitHeight(size);
        imageView.setFitWidth(size);
    }


}
