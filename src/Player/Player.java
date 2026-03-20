package Player;
import java.awt.*;

public class Player{

    private double x;
    private double y;
    private int width;
    private int height;

    private double speed = 0;
    private double aceleration = 0.5;
    public boolean jumped = false;
    public boolean inGround = false;

    public Player(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    public void render(Graphics g){
        g.setColor(Color.red);
        g.fillOval((int)this.x, (int)this.y, this.width, this.height);

    }

    public void update(){
        if(this.y >= 600){
            inGround = true;
            this.speed = 0;
        }else{
            inGround = false;
            this.jumped = false;
            this.speed += this.aceleration;
        }

        if(this.jumped){
            speed = -20;
        }


        this.y += this.speed;
    }
}
