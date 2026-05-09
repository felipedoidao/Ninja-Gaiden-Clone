package Graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.Main;
import Entities.Player;
import Entities.Collectibles.Collectible_fireball;
import Entities.Collectibles.Collectible_shuriken;
import Entities.Collectibles.Collectible_spin;

public class Ui {

    private int distance = Main.WIDTH -115;

    private BufferedImage[] energy;

    public Ui(){
        energy = new BufferedImage[1];

        energy[0] = Main.ninja.getSprite(0, 32*5, 16, 16);
    }
    
    public void render(Graphics g){

        g.setColor(Color.black);
        g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT/4);
        g.setColor(Color.white);
        
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("[  ]", 100, 40);
        
        if (Player.bag[0] != null){
            switch (Player.bag[0]){
                case Collectible_shuriken s:
                    g.drawImage(Collectible_shuriken.shuriken[0], 106, 26 , 16, 16, null);
                    break;
                case Collectible_fireball f:
                    g.drawImage(Collectible_fireball.image, 106, 26 , 16, 16, null);
                    break; 
                case Collectible_spin s:
                    g.drawImage(Collectible_spin.image, 106, 26 , 16, 16, null);
                    break; 
                default:
                    break;
            }
        }
        
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("SCORE: " + String.format("%06d", Player.score), 30, 20);
        g.drawString("TIMER: " + String.format("%03d" ,Main.timer), 30, 31);
        g.drawString(": " + String.format("%03d" , Player.energy), 66, 42);
        g.drawImage(energy[0], 48, 32, null);
        
        g.drawString("STAGE 0-1", Main.WIDTH-160, 20);
        g.drawString("NINJA -", Main.WIDTH-160, 31);
        g.drawString("ENEMY- ", Main.WIDTH-160, 42);

        for(int i = 0; i < 16; i++){

            g.setColor(Color.white);
            g.fillRect(distance + (6*i), 22, 5, 10);
            g.setColor(Color.black);
            g.fillRect(distance + 1 + (6*i), 23, 3, 8);
        }

        for(int i = 0; i < Player.lives; i++){
            g.setColor(Color.pink);
            g.fillRect(distance + (6*i), 22, 5, 10);
        }

    }   
}
