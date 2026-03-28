package Entities.Enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import Main.Main;
import World.Camera;

public class Bullet extends Enemies{

    private BufferedImage[] bullet;
    private double speed = 10;

    public Bullet(int x, int y, int width, int height, Soldier soldier){
        super(x, y, width, height);

        bullet = new BufferedImage[1];

        bullet[0] = Main.enemies.getSprite(32*8, 32*2, 8, 32);

        this.shoot(soldier);
    }

    public void render (Graphics g){
        g.drawImage(bullet[0], this.getX() - Camera.x, this.getY() - Camera.y, null);

    }

    public void update(){

        this.x+=this.speed;

        if (this.hitPlayer(Main.player, this.getX() + 2, this.getY() + 15) && !Main.player.hitted){
            Main.player.gotHit = true;
            Main.player.lives -= 1;
            Main.player.hitted = true;
            Main.player.knockBack = true;
            Main.player.inKnockBack = true;
            Main.entities.remove(this);
        }

        if (this.hurt(Main.player, this.getX() + 2, this.getY() + 15)){
            Main.entities.remove(this);
        }

        if (this.x > Camera.x + Main.Largura || this.x + 4 < Camera.x){
            Main.entities.remove(this);
        }
    }

    public void shoot(Soldier soldier){
        if (soldier.last_hori_dir > 0){
            this.speed = 4;
        }else {
            this.speed = -4;
        }

        Main.entities.add(this);
    }
}
