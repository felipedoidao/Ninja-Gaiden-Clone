package Entities.Enemies;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import World.Camera;
import Main.Main;

public class Projectile extends Enemies{

    public Launcher launcher;

    private BufferedImage[] projectile;

    public double vx;
    public double vy;
    private double g = 0.4;

    public Projectile(int x, int y, int width, int height){
        super(x, y, width, height);

        projectile = new BufferedImage[1];

        projectile[0] = Main.enemies.getSprite(32*8, (32*3)+10, 8, 32);
    }

    public void render(Graphics g){
        g.drawImage(projectile[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
    }

    public void update(){
        this.launch();
        if(this.hitPlayer(Main.player, this.getX(), this.getY()+1)){
            Main.player.gotHit = true;
            Main.player.lives -= 1;
            Main.player.hitted = true;
            Main.player.knockBack = true;
            Main.player.inKnockBack = true;
            Main.entities.remove(this);
        }
        if(this.hurt(Main.player, this.getX(), this.getY()+1)){
            Main.entities.remove(this);
        }

        if (!this.inScreen()){
            Main.entities.remove(this);
        }

    }

    public void launch(){
        this.x += this.vx;
        this.vy += this.g;
        this.y += this.vy;
        
    }   
}
