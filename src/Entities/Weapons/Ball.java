package Entities.Weapons;

import java.awt.Graphics;
import Main.Main;

public class Ball extends Weapons{

    Fire f1, f2, f3;
    Fire[] fire;
    int[] pos1, pos2, pos3;

    private double angle = 0;
    private double[] speed = {5, 5};


    public Ball(double x, double y, int width, int height){
        super(x, y, width, height);

        pos1 = new int[]{this.getX(), this.getY()-16};
        pos2 = new int[]{this.getX()-16, this.getY() +16};
        pos3 = new int[]{this.getX() +16, this.getY() + 16};

        f1 = new Fire(pos1[0], pos1[1], this.width, this.height);
        f2 = new Fire(pos2[0], pos2[1], this.width, this.height);
        f3 = new Fire(pos3[0], pos3[1], this.width, this.height);

        Main.entities.add(f1);
        Main.entities.add(f2);
        Main.entities.add(f3);

        fire = new Fire[]{f1, f2, f3};

        this.aim();
    }

    public void render (Graphics g){
    }

    public void update(){
        this.moveFire();

        this.x += speed[0];
        this.y -= speed[1];

    }

    public void moveFire(){

        final double distance = ((2*Math.PI)/3);

        for (int i = 0; i < 3; i++){
            Fire f = fire[i];

            double individualAngle = (angle + (i*distance));

            f.setX(this.getX() + 25 * Math.cos(individualAngle));

            f.setY(this.getY() + 25 * Math.sin(individualAngle));
        }

        angle += 0.2;

    }

    public void aim(){
        if (Main.player.last_hori_dir > 0){
            speed[0] = 6;
        }else {
            speed[0] = -6;
        }
    }
}
