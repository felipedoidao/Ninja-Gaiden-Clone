package Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import Main.Main;
import World.Camera;
import World.Floor_tile;
import World.Grip_Wall;
import World.Ladder;
import World.Tile;
import World.World;

public class Player{

    //Coordenadas do personagem
    private double x;
    private double y;
    
    //Controladors dos movimentos do personagem
    private double speed = 3.3;
    private double climbSpeed = 2;
    private double fallSpeed = 0;
    private double aceleration = 0.4;

    public boolean jumped = false;
    public boolean jumped_from_wall = false;
    public boolean isJumping = false;
    public boolean inGround = false;
    public boolean inGrip = false;
    public boolean isGrabbing = false;

    //Hitbox
    private int maskX;
    private int maskY;
    private int maskWidth;
    private int maskHeight;

    //variáveis para saber se está atacando e não permitir que segure o botão
    public boolean isAttacking = false;
    public boolean attacked = false;

    //Definir movimento,direção atual e salvar direção anterior 
    public int hori_dir = 1;
    public int vert_dir = 0;
    public int up = 0;
    public int down = 0;
    public int rig = 0;
    public int lef = 0;
    public int last_hori_dir = 1;

    //variavéis para animação
    private int frames, index; 
    private int cd = 0;

    public BufferedImage sprite_standing, sprite_attacking, sprite_jummping;

    //Listas dos frames para animações
    private BufferedImage[] right, left;
    private BufferedImage[] attacking_right, attacking__left;
    private BufferedImage[] jumping_right, jumping_left;
    private BufferedImage[] grab_right, grab_left;

    public Player(int x, int y, int width, int height){
        this.x = x;
        this.y = y;

        //Inicializa as listas com os números dos frames
        right = new BufferedImage[1];
        left = new BufferedImage[1];
        attacking_right = new BufferedImage[1];
        attacking__left = new BufferedImage[1];
        jumping_right = new BufferedImage[4];
        jumping_left = new BufferedImage[4];
        grab_right = new BufferedImage[1];
        grab_left = new BufferedImage[1];

        //pegando as coordenadas dos frames nas imagens fornecidas
        right[0] = Main.ninja.getSprite(0, 32*2, 32, 32);
        left[0] = Main.ninja.getSprite(32, 32*2, 32, 32);
        attacking_right[0] = Main.ninja.getSprite(0, 32, 64, 32);
        attacking__left[0] = Main.ninja.getSprite(32*2, 32, 64, 32);
        grab_right[0] = Main.ninja.getSprite(0, 32*3, 32, 32);
        grab_left[0] = Main.ninja.getSprite(32, 32*3, 32, 32);

        //como essas possuem mais de um frame, cria-se um loop para armazenar na lista todos os frames da animação
        for(int i = 0; i < 4; i++){
            jumping_right[i] = Main.ninja.getSprite(32*i, 0, 32, 32);
        }

        for(int i = 0; i < 4; i++){
            jumping_left[i] = Main.ninja.getSprite(32*4+(32*i), 0, 32, 32);
        }

    }

    public void render(Graphics g){

       //decide qual animação vai ser usada
       switch (last_hori_dir){
        case 1:
            if (isAttacking){
                g.drawImage(attacking_right[0], (int)this.x - Camera.x, (int)this.y - Camera.y, null);
                //g.drawImage(right[0], (int)this.x, (int)this.y, null);

            }else if(!inGround && !inGrip){
                g.drawImage(jumping_right[index], (int)this.x - Camera.x, (int)this.y - Camera.y, null);

            }else if (inGround){
                //g.drawImage(attacking_right[0], (int)this.x, (int)this.y, null);
                g.drawImage(right[0], (int)this.x - Camera.x, (int)this.y - Camera.y, null);

            }else if (inGrip){
                g.drawImage(grab_right[0], getX() - Camera.x + 2, getY() - Camera.y, null);
            }
            break;
        case -1:
            if (isAttacking){
                g.drawImage(attacking__left[0], (int)this.x - Camera.x - 32, (int)this.y - Camera.y, null);
                //g.drawImage(right[0], (int)this.x, (int)this.y, null);

            }else if(!inGround && !inGrip){
                g.drawImage(jumping_left[index], (int)this.x - Camera.x, (int)this.y - Camera.y, null);

            }else if (inGround){
                //g.drawImage(attacking_right[0], (int)this.x, (int)this.y, null);
                g.drawImage(left[0], (int)this.x - Camera.x - 4, (int)this.y - Camera.y, null);

            }else if (inGrip){
                g.drawImage(grab_left[0], getX() - Camera.x - 2, getY() - Camera.y, null);
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
        fallSpeed += aceleration;
        hori_dir = rig - lef;
        vert_dir = down - up;
        if (fallSpeed >= 7) fallSpeed= 6.5;
        if(this.jumped){
            fallSpeed = -7;
            if (hori_dir != 0) last_hori_dir = hori_dir;
        }
        
        //Muda o formato da hitbox para quando o personagem estiver no ar
        if(!inGround) {
            maskX = getX() + 6;
            maskY = getY() + 5;
            maskWidth = 20;
            maskHeight = 20;
        }else{
            maskX = getX();
            maskY = getY();
            maskWidth = 28;
            maskHeight = 32;
        }

        //Controla quanto tempo dura o ataque
        if(isAttacking){
            cd++;
            if (cd >= 15){
                cd = 0;
                isAttacking = false;
            }
        }

        //Para cada valor da velocidade do jogar roda um codigo para detectar colisão pixel por pixel
        for (int i = 0; i < speed; i++){

            Tile hit = World.isFree((int)(x + hori_dir), (int)y, 28, 32);

            switch (hit){
                case null -> {
                    x += hori_dir;
                }
                case Grip_Wall g -> {
                    if(getY()+8 >= g.getY() && (getY()+24) < (g.getY()+32)){
                        if (!inGround && !isGrabbing){
                            isGrabbing = true;
                            inGrip = true;
                        }
                    }
                    break;
                }
                case Ladder l -> {
                    if(getY()+8 >= l.getY() && (getY()+24) < (l.getY()+32)){
                        if (!inGround && !isGrabbing){
                            isGrabbing = true;
                            inGrip = true;
                        }
                    }
                    break;
                }
                case Floor_tile f -> {
                    break;
                }
                default -> { 
                    break;
                }
            }
        }

        if (inGrip){
            fallSpeed = 0;
            speed = 0;

            //Verifica se o que está se segurando é uma Escada sendo uma escada podemos subir e descer livremente
            Tile ladder = World.isFree((int)x + last_hori_dir, (int)(y), 28, 32);
            //Verificar colisão enquanto se move para cima ou para baixo copiando as outras colisões
            if (!(ladder instanceof Ladder)){
                for (int i = 0; i < climbSpeed; i++){

                    Tile hit = World.isFree((int)x, (int)(y+vert_dir), 28, 32);

                    switch (hit){
                        case null -> {

                            y += vert_dir;

                            //Verificações para os blocos na base e o topo do personagem
                            Tile Topo = World.isFree((int)x + last_hori_dir, (int)(y + 8), 28, 1);
                            Tile Base = World.isFree((int)x + last_hori_dir, (int)(y + 30), 28, 1);

                            //Se abaixo ou acima do personagem não for uma escada ele para de se mover
                            if (vert_dir < 0){
                                if (!(Topo instanceof Ladder)){
                                    y -= vert_dir;
                                }

                            }else if (vert_dir > 0){
                                if (!(Base instanceof Ladder)){
                                    y -= vert_dir;
                                }
                            }
                        }
                        case Floor_tile f -> {
                            break;
                        }
                        default -> { 
                            break;
                        }
                    }
                }
            }
            for (int i = 0; i < climbSpeed; i++){

                Tile hit = World.isFree((int)x, (int)(y+vert_dir), 28, 32);

                switch (hit){
                    case null -> {

                        y += vert_dir;

                        Tile Topo = World.isFree((int)x + last_hori_dir, (int)(y + 8), 28, 1);
                        Tile Base = World.isFree((int)x + last_hori_dir, (int)(y + 30), 28, 1);

                        if (vert_dir < 0){
                            if (!(Topo instanceof Ladder)){
                                y -= vert_dir;
                            }

                        }else if (vert_dir > 0){
                            if (!(Base instanceof Ladder)){
                                y -= vert_dir;
                            }
                        }
                    }
                    case Floor_tile f -> {
                        break;
                    }
                    default -> { 
                        break;
                    }
                }
            }
        }else {
            speed = 3.3;
        }
        System.out.println(vert_dir);
         

        //Se o personagem estiver se segurando na parede, velocidade é zero, previne que o personagem se movimente na horizontal

        //Mesmo sistema de colisão, mas para o eixo y
        for (int i = 0; i < Math.abs(fallSpeed); i++){
            double moveStep = Math.signum(fallSpeed);

            Tile hit = World.isFree((int) x, (int)(y + moveStep), 28, 32);

            switch (hit){
                case null -> {
                    y += moveStep;
                    inGround = false;
                    jumped = false;
                    break;
                }
                default -> {
                    inGround = true;
                    fallSpeed = 0;
                    break;
                }
            }
        }
    }

    public int getX(){
        return (int)this.x;
    }
    public int getY(){
        return (int)this.y;
    }

    public void setX(double X){
        this.x = X;
    }
    public void setY(double Y){
        this.y = Y;
    }
}
