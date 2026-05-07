package Entities.Enemies;

import Entities.Entities;
import Entities.Player;
import Entities.Collectibles.*;
import Entities.Weapons.Weapons;

import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import Main.Main;
import Main.Sounds.Clips;
import World.Camera;

public class Candle extends Enemies{

    private Random random;
    private int itemSelector = 0;

    public Candle (int x, int y, int width, int height){
        super(x, y, width, height);

        random = new Random();
    }

    public void render(Graphics g){
        if (!isDead) {
            g.setColor(Color.orange);
            g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, this.width, this.height);
        }

    }

    public void update(){
        if (!isDead) {
            if (this.hurt(Main.player, this.getX(), this.getY())){
                /*random.nextInt(115)*/
                //Seletor de número aleatório que vai de 0 a 99
                itemSelector = 79;
                //Dependendo do valor selecionado decide qual item será instanciado
                if (itemSelector < 5){
                    Main.entities.add(new Collectible_hourglass(getX(), getY(), 16, 16));
                }else if (itemSelector < 10){
                    Main.entities.add(new Collectible_star(getX(), getY(), 16, 16));
                }else if (itemSelector < 40){
                    Main.entities.add(new Collectible_ki(this.getX(), this.getY(), 16, 16));
                }else if (itemSelector < 60){
                    Main.entities.add(new Collectible_shuriken(this.getX(), this.getY(), 16, 16));
                }else if (itemSelector < 80){
                    Main.entities.add(new Collectible_spin(this.getX(), this.getY(), 16, 16));
                }else if (itemSelector< 100){
                    Main.entities.add(new Collectible_fireball(this.getX(), this.getY(), 16, 16));
                }else {
                    Main.entities.add(new Collectible_cure(this.getX(), this.getY(), 16, 16));
                }

                this.isDead = true;
        
            }
        }else {
            this.respawn();
        }
    }
    
    public boolean hurt(Player player, int x, int y){

        for (int i = 0; i < Main.entities.size(); i++){
            Entities e = Main.entities.get(i);
            if (e instanceof Weapons){

                if (this.getMask().intersects(e.getMask())){
                    return true;
                }
            }
        }
        if (this.getMask().intersects(player.getSwordMask())){
            return true;
        }

        return false;
    } 

}
