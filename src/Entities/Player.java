package Entities;

import java.awt.Graphics;
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

    public int lives = 10;
    public int hitCd = 0;
    private int knockBackCD = 0;

    public boolean knockBack = false;
    public boolean inKnockBack = false;
    public boolean hitted = false;
    public boolean gotHit = false;
    public boolean jumped = false;
    public boolean jumped_from_wall = false;
    public boolean isJumping = false;
    public boolean inGround = false;
    public boolean inGrip = false;
    public boolean isGrabbing = false;

    //Hurtbox
    private int xSword;
    private int ySword;
    private int widthSword = 27;
    private int heightSword = 7;

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
    private BufferedImage[] knockingBack_right, knockingBack_left;

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
        knockingBack_right = new BufferedImage[1];
        knockingBack_left = new BufferedImage[1];

        //pegando as coordenadas dos frames nas imagens fornecidas
        right[0] = Main.ninja.getSprite(0, 32*2, 32, 32);
        left[0] = Main.ninja.getSprite(32, 32*2, 32, 32);
        attacking_right[0] = Main.ninja.getSprite(0, 32, 64, 32);
        attacking__left[0] = Main.ninja.getSprite(32*2, 32, 64, 32);
        grab_right[0] = Main.ninja.getSprite(0, 32*3, 32, 32);
        grab_left[0] = Main.ninja.getSprite(32, 32*3, 32, 32);
        knockingBack_right[0] = Main.ninja.getSprite(32, 32*4, 32, 32);
        knockingBack_left[0] = Main.ninja.getSprite(0, 32*4, 32, 32);

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
            if (isAttacking && !inGrip){
                g.drawImage(attacking_right[0], (int)this.x - Camera.x, (int)this.y - Camera.y, null);
                //g.drawImage(right[0], (int)this.x, (int)this.y, null);

            }else if (knockBack){
                g.drawImage(knockingBack_right[0], getX() - Camera.x, getY() - Camera.y, null);
            
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
            if (isAttacking && !inGrip){
                g.drawImage(attacking__left[0], (int)this.x - Camera.x - 36, (int)this.y - Camera.y, null);
                //g.drawImage(right[0], (int)this.x, (int)this.y, null);

            }else if (knockBack){
                g.drawImage(knockingBack_left[0], getX() - Camera.x, getY() - Camera.y, null);
            
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
        this.frames++;
        if (this.frames >= 5){
            this.index++;
            this.frames = 0;
            if (this.index >= 3){
                this.index = 0;
            }
        }
    }

    public void update(){
        //System.out.println(lives);
        this.fallSpeed += aceleration;
        this.animFrames();
        this.hori_dir = this.rig - this.lef;
        this.vert_dir = this.down - this.up;
        if (this.fallSpeed >= 7) this.fallSpeed= 6.5;
        
        if(this.jumped){
            
            this.fallSpeed = -7;
            
            if (this.hori_dir != 0) this.last_hori_dir = this.hori_dir;
        }

        //Muda o formato da hitbox para quando o personagem estiver no ar
        if(!this.inGround) {
            this.maskX = getX() + 6;
            this.maskY = getY() + 5;
            this.maskWidth = 20;
            this.maskHeight = 20;
        }else{
            this.maskX = getX();
            this.maskY = getY();
            this.maskWidth = 28;
            this.maskHeight = 32;
        }

        if(this.isAttacking){
            this.ySword = getY() +9;
        if(last_hori_dir > 0){
            this.xSword = this.getX() +32;
        }else {
            this.xSword = this.getX() - 32;
        }
        this.cd++;
        if (this.cd >= 20){
            this.cd = 0;
            this.isAttacking = false;
        }
        }else {
            this.xSword = -10;
            this.ySword = -10;
        }
        //Controla quanto tempo dura o ataque
        

        if (gotHit){
            hitCd ++;
            if (hitCd >= 60){
                hitCd = 0;
                hitted = false;
                gotHit = false;
            }
        }
        if (inKnockBack){
            fallSpeed = -4;
        }

        if (knockBack){
            this.speed = 2.5;
            knockBackCD++;
            if (knockBackCD >= 25){
                knockBack = false;
                knockBackCD = 0;
            }

        }

        //Para cada valor da velocidade do jogar roda um codigo para detectar colisão pixel por pixel
        for (int i = 0; i < this.speed; i++){

            Tile hit = World.isFree((int)(x + hori_dir), (int)y, 28, 32);
            Tile hitted = World.isFree((int)(x - last_hori_dir), (int)y, 28, 32);
            Tile grab = World.isFree((int)(x + hori_dir), (int)y+16, 28, 1);

            if(!knockBack && !isAttacking){
                switch (hit){
                    case null -> {
                        
                        this.x += this.hori_dir;
                            
                    }
                    case Grip_Wall g -> {
                        if(hori_dir == last_hori_dir && grab != null){
                            if (!this.inGround && !this.isGrabbing){
                                this.isGrabbing = true;
                                this.inGrip = true;
                            }
                        }
                        break;
                    }
                    case Ladder l -> {
                        if(hori_dir == last_hori_dir && grab != null){
                            if (!this.inGround && !this.isGrabbing){
                                this.isGrabbing = true;
                                this.inGrip = true;
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
            
            }else if (knockBack){
                switch (hitted){
                    case null -> {
                        if(!inGrip){
                            x-=last_hori_dir;
                        }
                        break;
                    }
                    default -> {
                        break;
                    }
                }
            }
            
        }


        //Se o personagem estiver se segurando na parede, velocidade é zero, previne que o personagem se movimente na horizontal
        if (this.inGrip){
            this.fallSpeed = 0;
            this.speed = 0;
            this.isAttacking = false;

            //Verifica se o que está se segurando é uma Escada sendo uma escada podemos subir e descer livremente
            Tile ladder = World.isFree((int)x + last_hori_dir, (int)(y), 28, 32);

            //Verificar colisão enquanto se move para cima ou para baixo copiando as outras colisões
            if (!(ladder instanceof Ladder)){
                for (int i = 0; i < climbSpeed; i++){

                    Tile hit = World.isFree((int)x, (int)(y+vert_dir), 28, 32);

                    switch (hit){
                        case null -> {

                            this.y += this.vert_dir;

                            //Verificações para os blocos na base e o topo do personagem
                            Tile Topo = World.isFree((int)x + last_hori_dir, (int)(y + 8), 28, 1);
                            Tile Base = World.isFree((int)x + last_hori_dir, (int)(y + 30), 28, 1);

                            //Se abaixo ou acima do personagem não for uma escada ele para de se mover
                            if (this.vert_dir < 0){
                                if (!(Topo instanceof Ladder)){
                                    this.y -= this.vert_dir;
                                }

                            }else if (this.vert_dir > 0){
                                if (!(Base instanceof Ladder)){
                                    this.y -= this.vert_dir;
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
            for (int i = 0; i < this.climbSpeed; i++){

                Tile hit = World.isFree((int)this.x, (int)(this.y+this.vert_dir), 28, 32);

                switch (hit){
                    case null -> {

                        this.y += this.vert_dir;

                        Tile Topo = World.isFree((int)x + last_hori_dir, (int)(y + 8), 28, 1);
                        Tile Base = World.isFree((int)x + last_hori_dir, (int)(y + 30), 28, 1);

                        if (this.vert_dir < 0){
                            if (!(Topo instanceof Ladder)){
                               this.y -= this.vert_dir;
                            }

                        }else if (this.vert_dir > 0){
                            if (!(Base instanceof Ladder)){
                                this.y -= this.vert_dir;
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
            this.speed = 3.3;
            
        }    

        //Mesmo sistema de colisão, mas para o eixo y
        for (int i = 0; i < Math.abs(this.fallSpeed); i++){
            double moveStep = Math.signum(this.fallSpeed);

            Tile hit = World.isFree((int) x, (int)(this.y + moveStep), 28, 32);

            switch (hit){
                case null -> {
                    this.y += moveStep;
                    this.inGround = false;
                    this.jumped = false;
                    inKnockBack = false;
                    break;
                }
                default -> {
                    this.inGround = true;
                    this.fallSpeed = 0;
                    this.knockBack = false;
                    this.knockBackCD = 0;
                    this.isGrabbing = false;
                    if (!isAttacking && this.hori_dir != 0) this.last_hori_dir = this.hori_dir;
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

    public int getMaskWidth(){
        return this.maskWidth;
    }
    public int getMaskHeight(){
        return this.maskHeight;
    }
    public int getMaskX(){
        return this.maskX;
    }
    public int getMaskY(){
        return this.maskY;
    }

    public int getSwordWidth(){
        return this.widthSword;
    }
    public int getSwordHeight(){
        return this.heightSword;
    }
    public int getSwordX(){
        return this.xSword;
    }
    public int getSwordY(){
        return this.ySword;
    }

}