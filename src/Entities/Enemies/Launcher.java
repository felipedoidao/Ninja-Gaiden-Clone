package Entities.Enemies;

import java.awt.Color;
import java.awt.Graphics;

import Entities.Player;
import World.Camera;
import Main.Main;

public class Launcher extends Enemies{

    public double vy;
    public double vx;

    private boolean isAttacking = false;
    private int attackCD = 0;

    public Launcher(int x , int y, int width, int height){
        super(x, y, width, height);
        
    }

    public void render(Graphics g){
        g.setColor(Color.red);
        g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, 32, 32);
    }

    public void update(){
        attack(Main.player);
    }

    public void attack(Player player){
        
        double dx = player.getX() - this.getX();
        double dy = player.getY() - this.getY();
            
        int maxHeight = 80;
        int maxD = 180;
        double g = 0.4;

        if(dx > -maxD && dx < maxD){
            attackCD++;
            if(attackCD >= 90){
                isAttacking = true;
                attackCD = 0;
            }

            if(isAttacking){

                this.vy = -(Math.sqrt(2*g*maxHeight));

                double t = (Math.abs(this.vy) + Math.sqrt((this.vy*this.vy) + 2 * g * dy))/g;

                this.vx = dx/t;

                Projectile projectile = new Projectile(this.getX() + (this.width/2), this.getY() + (this.height/2), 4, 4);
                projectile.vx = this.vx;
                projectile.vy = this.vy;
                Main.entities.add(projectile);

                this.isAttacking = false;
            }
        }
        

    }

}
