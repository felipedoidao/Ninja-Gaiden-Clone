package Entities.Enemies;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Entities.Entities;
import Entities.Player;
import Entities.Weapons.Weapons;
import Main.Main;
import Main.Sounds;
import Main.Sounds.Clips;
import World.Camera;

public class Enemies extends Entities{

    protected int deathTimer = 0;
    protected int hori_dir;

    public int xStart;
    public int yStart;

    protected boolean isDead = true;
    protected boolean canRespawn = true;

    protected BufferedImage[] explosion, p1, p2, p3, p4;
    protected double p1x, p1y, p2x, p2y, p3x, p3y, p4x, p4y;

    public Enemies(double x, double y, int width, int height){
        super(x, y, width, height);

        explosion = new BufferedImage[2];
        p1 = new BufferedImage[1];
        p2 = new BufferedImage[1];
        p3 = new BufferedImage[1];
        p4 = new BufferedImage[1];

        p1[0] = Main.enemies.getSprite(32*2, 200, 32, 32);
        p2[0] = Main.enemies.getSprite(32*3, 200, 32, 32);
        p3[0] = Main.enemies.getSprite(32*4, 200, 32, 32);
        p4[0] = Main.enemies.getSprite(32*5, 200, 32, 32);

        for (int i=0; i < 2; i++){
            explosion[i] = Main.enemies.getSprite(32*i, 200, 32, 32);
        }

    }

    public void locatePlayer(Player player){
        if (this.x > player.getX()){
            this.hori_dir = -1;
        }else if (this.x < player.getX()){
            this.hori_dir = 1;
        }
    }

    public boolean hitPlayer(Player player, int x, int y){

        if (!Player.invincible && this.getMask().intersects(player.getMask())){
            return true;
        }
        return false;
    }

    public boolean hurt(Player player, int x, int y){

        this.p1x = this.getX();
        this.p1y = this.getY();

        this.p2x = this.getX();
        this.p2y = this.getY();

        this.p3x = this.getX();
        this.p3y = this.getY();

        this.p4x = this.getX();
        this.p4y = this.getY();

        for (int i = 0; i < Main.entities.size(); i++){
            Entities e = Main.entities.get(i);
            if (e instanceof Weapons){

                if (this.getMask().intersects(e.getMask())){
                    Clips.dying_enemy.play();
                    return true;
                }
            }
        }
        if (this.getMask().intersects(player.getSwordMask())){
            Clips.dying_enemy.play();
            return true;
        }

        return false;
    }

    public void respawn(){

        if (this.xStart + 32 < Camera.x || this.xStart > Camera.x + Main.WIDTH){
            this.canRespawn = true;
        }
         if (this.canRespawn && this.xStart+32 > Camera.x && this.xStart < Camera.x + Main.WIDTH){
            this.x = this.xStart;
            this.y = this.yStart;
            this.isDead = false;
            this.canRespawn = false;
            this.locatePlayer(Main.player);
        }
    }

    public void deathAnimation(){

        int tspeed = 3;
        if (this.dead){
            this.maxFrames = 4;
            this.maxIndex = 2;
            this.deathTimer++;
            this.p1x -= tspeed;
            this.p1y -= tspeed;

            this.p2x += tspeed;
            this.p2y -= tspeed;

            this.p3x -= tspeed;
            this.p3y += tspeed;

            this.p4x += tspeed;
            this.p4y += tspeed;

            if (this.deathTimer >= 10){
                this.dead = false;
                this.deathTimer = 0;
            }
        }
    }


    public void render(Graphics g){}

    public void update(){}

}
