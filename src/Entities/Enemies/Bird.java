package Entities.Enemies;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Bird extends Enemies{

    private BufferedImage[] right, left;

    public Bird (int x, int y, int width, int height){
        super(x, y, width, height);

        right = new BufferedImage[4];
        left = new BufferedImage[4];

        for (int i = 0; i < 3; i++){
            
        }
    }

    public void render (Graphics g){
        
    }


}
