package Entities.Enemies;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Entities.Player;
import World.Camera;
import Main.Main;

public class Projectile extends Enemies{

    public Launcher launcher;

    private BufferedImage[] projectile;

    private int deadTimer = 0;

    public double vx;
    public double vy;
    private double g = 0.4;

    public Projectile(int x, int y, int width, int height){
        super(x, y, width, height);

        this.isDead = false;

        projectile = new BufferedImage[1];

        projectile[0] = Main.enemies.getSprite(32*8, (32*3)+10, 8, 32);
    }

    public void render(Graphics g){
        if (!isDead){
            g.drawImage(projectile[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
        }else if (this.dead){
            g.drawImage(this.explosion[index], this.getX() - Camera.x, this.getY()+5 - Camera.y, 16, 16, null);
            g.drawImage(this.p1[0], (int)this.p1x - Camera.x, (int)this.p1y+5 - Camera.y, 16, 16, null);
            g.drawImage(this.p2[0], (int)this.p2x - Camera.x, (int)this.p2y+5 - Camera.y, 16, 16, null);
            g.drawImage(this.p3[0], (int)this.p3x - Camera.x, (int)this.p3y+5 - Camera.y, 16, 16, null);
            g.drawImage(this.p4[0], (int)this.p4x - Camera.x, (int)this.p4y+5 - Camera.y, 16, 16, null);
        }
    }

    public void update(){
        this.deathAnimation();
        this.launch();
        if (!isDead){
            if(this.hitPlayer(Main.player, this.getX(), this.getY()+1)){
                Main.player.gotHit = true;
                Player.lives -= 1;
                Main.player.hitted = true;
                Main.player.knockBack = true;
                Main.player.inKnockBack = true;
                Main.entities.remove(this);
            }
            if(this.hurt(Main.player, this.getX(), this.getY()+1)){
                this.index = 0;
                this.frames = 0;
                this.dead = true;
                this.isDead = true;
                Player.score += 2;
            }

            if (!this.inScreen()){
                Main.entities.remove(this);
            }
        }else {
            this.animFrames();
            this.deadTimer++;
            if (this.deadTimer >= 10) {
                this.deadTimer = 0;
                Main.entities.remove(this);
            }
        }

    }

    public void launch(){
        this.x += this.vx;
        this.vy += this.g;
        this.y += this.vy;
        
    }   
}
