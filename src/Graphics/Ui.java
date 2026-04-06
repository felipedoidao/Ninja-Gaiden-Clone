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
                    g.setColor(Color.yellow);
                    g.fillRect(106, 26, 16, 16);
                    break; 
                case Collectible_spin s:
                    g.setColor(Color.blue);
                    g.fillRect(106, 26, 16, 16);
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

            g.setColor(Color.pink);
            g.fillRect(distance + (6*i), 22, 5, 10);
            g.fillRect(distance + (6*i), 33, 5, 10);
            
            player_life(g, i);
        }

    }

    private void player_life(Graphics g, int i){
         switch (Player.lives){
            case 16:
                break;
            case 15:
                g.setColor(Color.white);
                if (i >= 15){
                    g.fillRect(distance + (6*i), 22, 5, 10);
                }
                break;
            case 14:
                g.setColor(Color.white);
                if (i >= 14){
                    g.fillRect(distance + (6*i), 22, 5, 10);
                }
                break;
            case 13:
                g.setColor(Color.white);
                if (i >= 13){
                    g.fillRect(distance + (6*i), 22, 5, 10);
                }
                break;
            case 12:
                g.setColor(Color.white);
                if (i >= 12){
                    g.fillRect(distance + (6*i), 22, 5, 10);
                }
                break;
            case 11:
                g.setColor(Color.white);
                if (i >= 11){
                    g.fillRect(distance + (6*i), 22, 5, 10);
                }
                break;
            case 10:
                g.setColor(Color.white);
                if (i >= 10){
                    g.fillRect(distance + (6*i), 22, 5, 10);
                }
                break;
            case 9:
                g.setColor(Color.white);
                if (i >= 9){
                    g.fillRect(distance + (6*i), 22, 5, 10);
                }
                break;
            case 8:
                g.setColor(Color.white);
                if (i >= 8){
                    g.fillRect(distance + (6*i), 22, 5, 10);
                }
                break;
            case 7:
                g.setColor(Color.white);
                if (i >= 7){
                    g.fillRect(distance + (6*i), 22, 5, 10);
                }
                break;
            case 6:
                g.setColor(Color.white);
                if (i >= 6){
                    g.fillRect(distance + (6*i), 22, 5, 10);
                }
                break;
            case 5:
                g.setColor(Color.white);
                if (i >= 5){
                    g.fillRect(distance + (6*i), 22, 5, 10);
                }
                break;
            case 4:
                g.setColor(Color.white);
                if (i >= 4){
                    g.fillRect(distance + (6*i), 22, 5, 10);
                }
                break;
            case 3:
                g.setColor(Color.white);
                if (i >= 3){
                    g.fillRect(distance + (6*i), 22, 5, 10);
                }
                break;
            case 2:
                g.setColor(Color.white);
                if (i >= 2){
                    g.fillRect(distance + (6*i), 22, 5, 10);
                }
                break;
            case 1:
                g.setColor(Color.white);
                if (i >= 1){
                    g.fillRect(distance + (6*i), 22, 5, 10);
                }
                break;
            case 0:
                g.setColor(Color.white);
                g.fillRect(distance + (6*i), 22, 5, 10);
                break;
            default:
                g.setColor(Color.white);
                g.fillRect(distance + (6*i), 22, 5, 10);
                break;
            }
    }

}
