package Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import Main.Main;

public class Player{

    private double x;
    private double y;
    private int width;
    private int height;

    private double speed = 0;
    private double aceleration = 0.5;
    public boolean jumped = false;
    public boolean inGround = false;

    //variáveis para saber se está atacando e não permitir que segure o botão
    public boolean isAttacking = false;
    public boolean attacked = false;

    public int hori_dir = 1;
    public int last_hori_dir = 1;

    private int frames, index; 
    private int cd = 0;

    public BufferedImage sprite_standing, sprite_attacking, sprite_jummping;

    //Listas dos frames para animações
    private BufferedImage[] right, left;
    private BufferedImage[] attacking_right, attacking__left;
    private BufferedImage[] jumping_right, jumping_left;

    public Player(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        //Inicializa as listas com os números dos frames
        right = new BufferedImage[1];
        left = new BufferedImage[1];
        attacking_right = new BufferedImage[1];
        attacking__left = new BufferedImage[1];
        jumping_right = new BufferedImage[4];
        jumping_left = new BufferedImage[4];

        //pegando as coordenadas dos frames nas imagens fornecidas
        right[0] = Main.idle.getSprite(0, 0, 32, 32);
        left[0] = Main.idle.getSprite(32, 0, 32, 32);
        attacking_right[0] = Main.attack.getSprite(0, 0, 64, 32);
        attacking__left[0] = Main.attack.getSprite(64, 0, 64, 32);

        //como essas possuem mais de um frame, cria-se um loop para armazenar na lista todos os frames da animação
        for(int i = 0; i < 4; i++){
            jumping_right[i] = Main.jump.getSprite(32*i, 0, 32, 32);
        }

        for(int i = 0; i < 4; i++){
            jumping_left[i] = Main.jump.getSprite(128+(32*i), 0, 32, 32);
        }

    }

    public void render(Graphics g){

       //decide qual animação vai ser usada
       switch (hori_dir){
        case 1:
            if (!isAttacking){
                g.drawImage(right[0], (int)this.x, (int)this.y, null);
            }else {
                g.drawImage(attacking_right[0], (int)this.x, (int)this.y, null);
            }
            break;
        case -1:
            if (!isAttacking){
                g.drawImage(left[0], (int)this.x, (int)this.y, null);
            }else {
                g.drawImage(attacking__left[0], ((int)this.x) - 32, (int)this.y, null);
            }
            break;
        case 0:
            switch (last_hori_dir){
                case 1:
                    g.drawImage(jumping_right[index], (int)this.x, (int)this.y, null);
                    break;
                case -1:
                    g.drawImage(jumping_left[index], (int)this.x, (int)this.y, null);
                    break;
            }
            break;
       }
    }

    //Método que diz quando chamar a próxima imagem da animação
    public void animFrames(){
        frames++;
        if (frames >= 5){
            index++;
            frames = 0;
            if (index >= 3){
                index = 0;
            }
        }
    }

    public void update(){
        animFrames();

        if(this.y >= 300){
            inGround = true;
            this.speed = 0;
            hori_dir = last_hori_dir;
        }else{
            inGround = false;
            this.jumped = false;
            this.speed += this.aceleration;
        }

        if(this.jumped){
            speed = -10;
            hori_dir = 0;
        }

        //Controla quanto tempo dura o ataque
        if(isAttacking){
            cd++;
            if (cd >= 5){
                cd = 0;
                isAttacking = false;
            }
        }

        this.y += this.speed;

    }
}
