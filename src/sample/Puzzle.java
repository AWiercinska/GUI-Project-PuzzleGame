package sample;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Puzzle extends Button {
    Image image;
    int col, row;
    double size;

    public Puzzle(Image image, int x, int y, double size) {
        this.image = image;
        this.row = x;
        this.col = y;
        this.size = size;
    }

    void setImageToTransparent(){

    }

    void setPuzzleImage(){
        ImageView iv = new ImageView(this.image);
        iv.setFitWidth(this.size);
        iv.setFitHeight(this.size);
        this.setGraphic(iv);
        this.getChildren().add(iv);
    }
}
