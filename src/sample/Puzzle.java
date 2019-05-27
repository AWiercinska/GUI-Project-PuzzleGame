package sample;

import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class Puzzle extends Button {
    Image image;
    int x, y;

    public Puzzle(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    void setImageToTransparent(){

    }

    void setImage(Image image){
        this.image = image;
    }
}
