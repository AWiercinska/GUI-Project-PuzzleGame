package sample;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Imagetest {

    public static void main(String[] args) throws IOException {
        int splits = 2;

        final BufferedImage source = ImageIO.read(new File("/Users/cheap_ramen/Desktop/th-2.jpeg"));
        int idx = 0;
        for (int y = 0; y <= (source.getHeight()- source.getHeight()/splits); y += source.getHeight()/splits) {
            int xx = 0;
            for(int x = xx; x<= (source.getWidth()- source.getWidth()/splits); x+= source.getWidth()/splits){
                ImageIO.write(source.getSubimage(x, y, source.getWidth()/splits, source.getHeight()/splits),
                        "jpeg", new File("/Users/cheap_ramen/Desktop/" + idx++ + ".png"));
            }

        }

    }
}
