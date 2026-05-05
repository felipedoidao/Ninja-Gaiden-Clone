package Entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Entities.Collectibles.Collectible_fireball;
import Entities.Collectibles.Collectible_shuriken;
import Entities.Collectibles.Collectible_spin;
import Entities.Collectibles.Collectibles;
import Entities.Weapons.Ball;
import Entities.Weapons.Invincible_fire;
import Entities.Weapons.Shuriken;
import Main.Main;
import World.Camera;
import World.Floor_tile;
import World.Grip_Wall;
import World.Ladder;
import World.Tile;
import World.World;

public class Player extends Entities{

    public static boolean invincible = false;

    //Coordenadas do personagem
    private double x;
    private double y;

    //Inventário
    public static Collectibles[] bag;

    public boolean isUsing = false;
    public boolean used = false;
    public boolean usingIten = false;
    public boolean launching = false;
    private int launchingCd = 0;
    
    //Controladors dos movimentos do personagem
    private double speed = 3;
    private double climbSpeed = 2;
    private double fallSpeed = 0;
    private double aceleration = 0.4;

    //Ui e mecânicas
    public static int lives = 16;
    public static int score = 0;
    public static int energy = 200;

    //Reação ao tomar dano
    public int hitCd = 0;
    private int knockBackCD = 0;

    public boolean knockBack = false;
    public boolean inKnockBack = false;
    public boolean hitted = false;
    public boolean gotHit = false;

    //Variáveis de invencibilidade
    private boolean isInvincible = false;
    private double angle = 0;
    private Invincible_fire[] fire;
    public static int invincibleTimer = 0;

    //Controles aéreos e em paredes
    public boolean jumped = false;
    public boolean jumped_from_wall = false;
    public boolean isJumping = false;
    public boolean inGround = false;
    public boolean inGrip = false;
    public boolean grabbed;

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
    public int frames, index; 
    private int cd = 0;

    public BufferedImage sprite_standing, sprite_attacking, sprite_jummping;

    //Listas dos frames para animações
    private BufferedImage[] right, left, walk_right, walk_left;
    private BufferedImage[] attacking_right, attacking_left;
    private BufferedImage[] jumping_right, jumping_left, spin_right, spin_left;
    private BufferedImage[] grab_right, grab_left;
    private BufferedImage[] knockingBack_right, knockingBack_left;
    private BufferedImage[] using_right, using_left;

    Ball ball;

    public Player(int x, int y, int width, int height){
        super(x, y, width, height);

        maxFrames = 5;
        maxIndex = 4;

        fire = new Invincible_fire[3];

        bag = new Collectibles[1];

        //Inicializa as listas com os números de frames
        right = new BufferedImage[1];
        left = new BufferedImage[1];

        walk_right = new BufferedImage[4];
        walk_left = new BufferedImage[4];

        attacking_right = new BufferedImage[4];
        attacking_left = new BufferedImage[4];

        jumping_right = new BufferedImage[4];
        jumping_left = new BufferedImage[4];

        spin_right = new BufferedImage[4];
        spin_left = new BufferedImage[4];

        grab_right = new BufferedImage[1];
        grab_left = new BufferedImage[1];

        knockingBack_right = new BufferedImage[1];
        knockingBack_left = new BufferedImage[1];

        using_right = new BufferedImage[1];
        using_left = new BufferedImage[1];

        //pegando as coordenadas dos frames nas imagens fornecidas
        right[0] = Main.ninja.getSprite(0, 32*2, 32, 32);
        left[0] = Main.ninja.getSprite(32, 32*2, 32, 32);
        grab_right[0] = Main.ninja.getSprite(0, 32*3, 32, 32);
        grab_left[0] = Main.ninja.getSprite(32, 32*3, 32, 32);
        knockingBack_right[0] = Main.ninja.getSprite(32, 32*4, 32, 32);
        knockingBack_left[0] = Main.ninja.getSprite(0, 32*4, 32, 32);
        using_right[0] = Main.ninja.getSprite(0, 256, 32, 32);
        using_left[0] = Main.ninja.getSprite(96, 256, 32, 32);

        //como essas possuem mais de um frame, cria-se um loop para armazenar na lista todos os frames da animação
        for(int i = 0; i < 4; i++){
            jumping_right[i] = Main.ninja.getSprite(32*i, 0, 32, 32);
            jumping_left[i] = Main.ninja.getSprite(32*4+(32*i), 0, 32, 32);

            walk_right[i] = Main.ninja.getSprite(32*i, 32*6, 32, 32);
            walk_left[i] = Main.ninja.getSprite(32*i, 32*7, 32, 32);

            attacking_right[i] = Main.ninja.getSprite(64*i, 32, 64, 32);
            attacking_left[i] = Main.ninja.getSprite(64+(64*i), 64, 64, 32);

            spin_right[i] = Main.ninja.getSprite(128+(64*i), 64*2, 64, 64);
            spin_left[i] = Main.ninja.getSprite(128+(64*i), 64*3, 64, 64);

        }
    }

    public void render(Graphics g){
       //decide qual animação vai ser usada


        switch (last_hori_dir) {
            case 1:
                if (knockBack) {
                    g.drawImage(knockingBack_right[0], getX() - Camera.x, getY() - Camera.y, null);
                } else if (usingIten) {
                    g.drawImage(spin_right[index], getX() - Camera.x - 16, getY() - Camera.y - 16, null);
                } else if (launching) {
                    g.drawImage(using_right[0], getX() - Camera.x, getY() - Camera.y, null);
                } else if (inGrip) {
                    g.drawImage(grab_right[0], getX() - Camera.x , getY() - Camera.y, null);
                } else if (isAttacking) {
                    g.drawImage(attacking_right[index], (int)this.x - Camera.x, (int)this.y - Camera.y, null);
                } else if (!inGround) {
                    g.drawImage(jumping_right[index], (int)this.x - Camera.x, (int)this.y - Camera.y, null);
                } else if (hori_dir == last_hori_dir) {
                    g.drawImage(walk_right[index], getX() - Camera.x, getY() - Camera.y, null);
                } else {
                    g.drawImage(right[0], getX() - Camera.x, getY() - Camera.y, null);
                }
                break;

            case -1:
                if (knockBack) {
                    g.drawImage(knockingBack_left[0], getX() - Camera.x, getY() - Camera.y, null);
                } else if (usingIten) {
                    g.drawImage(spin_left[index], getX() - Camera.x - 16, getY() - Camera.y - 16, null);
                } else if (launching) {
                    g.drawImage(using_left[0], getX() - Camera.x - 6, getY() - Camera.y, null);
                } else if (inGrip) {
                    g.drawImage(grab_left[0], getX() - Camera.x - 2, getY() - Camera.y, null);
                } else if (isAttacking) {
                    g.drawImage(attacking_left[index], (int)this.x - Camera.x - 38, (int)this.y - Camera.y, null);
                } else if (!inGround) {
                    g.drawImage(jumping_left[index], (int)this.x - Camera.x, (int)this.y - Camera.y, null);
                } else if (hori_dir == last_hori_dir) {
                    g.drawImage(walk_left[index], getX() - Camera.x - 1, getY() - Camera.y, null);
                } else {
                    g.drawImage(left[0], (int)this.x - Camera.x - 1, (int)this.y - Camera.y, null);
                }
                break;
        }
    }

    //Método que diz quando chamar a próxima imagem da animação
    public void animFrames(){
        this.frames++;
        if (this.frames >= maxFrames){
            this.index++;
            this.frames = 0;
            if (this.index >= maxIndex){
                this.index = 0;
            }
        }
    }

    public void update(){
        animFrames();
        hit();
        move();
        attack();
        equipments();
        invinclbe();

        if (this.y > Camera.y+Main.HEIGHT) {
            this.setX(2*32);
            this.setY(2*32);
        }

    }

    public void invinclbe(){
        if (Player.invincible){
            invincibleTimer++;
            if (invincibleTimer >= 6*60){
                invincibleTimer = 0;
                Player.invincible = false;
            }
            
            final double distance = ((2*Math.PI)/3);

            if (!isInvincible){
                for (int i = 0; i < 3; i++){
                    Invincible_fire f = new Invincible_fire(0, 0, 16, 16);
                    Main.entities.add(f);
                    fire[i] = f;
                }
                isInvincible = true;
            }
            

            for (int i = 0; i < 3; i++){
                Invincible_fire f = fire[i];

                double individualAngle = (angle + (i*distance));

                f.setX((this.getX()+5) + 25 * Math.cos(individualAngle));

                f.setY((this.getY()+5) + 25 * Math.sin(individualAngle));
            }
            angle += 0.3;
        }else {
            angle = 0;
            isInvincible = false;
        }
    }

    private void attack(){
        if(this.isAttacking){

            this.ySword = getY() +3;
            this.widthSword = 34;
            this.heightSword = 16;
            if(last_hori_dir > 0){
                this.xSword = this.getX() +29;
            }else {
                this.xSword = this.getX() - 36;
            }
            //Controla quanto tempo dura o ataque
            this.cd++;
            if (this.cd >= 20){
                this.cd = 0;
                this.isAttacking = false;
            }
        }else if (usingIten){
            this.xSword = this.getX()-14;
            this.ySword = this.getY()-14;
            this.widthSword = 60;
            this.heightSword = 60;
        
        }else {
            this.xSword = 10;
            this.ySword = 0;
            this.widthSword = 1;
            this.heightSword = 1;
        }
        
    }

    private void equipments(){

        if (launching){
            isAttacking = false;
            cd = 0;
            launchingCd++;
            if (launchingCd >= 20){
                launchingCd = 0;
                launching = false;
            }
        }

        if (Player.bag[0] != null && Player.energy >= 5){
            if (isUsing){
                switch (Player.bag[0]){
                    case Collectible_shuriken s:
                        Player.energy -= 5;
                        Shuriken sh = new Shuriken(this.getX()+4, this.getY()+8, 16, 16);
                        Main.entities.add(sh);
                        isUsing = false;
                        launching = true;
                        break;

                    case Collectible_fireball f:
                        Player.energy -= 5;
                        Ball b = new Ball(this.getX()+4, this.getY()+8, 16, 16);
                        Main.entities.add(b);
                        isUsing = false;
                        launching = true;
                        break; 
                        
                    case Collectible_spin s:
                        if (!inGround){
                            Player.energy -= 5;
                            usingIten = true;
                        }
                        isUsing = false;
                        
                        break; 
                    default:
                        break;
                }
            } 
        }
    }

    private void hit(){

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
            knockBackCD = 0;
            isAttacking = false;
            cd = 0;
            this.speed = 2.5;
            knockBackCD++;
            if (knockBackCD >= 25){
                knockBack = false;
                knockBackCD = 0;
            }

        }
    }

    private void move(){
        this.fallSpeed += aceleration;
        this.hori_dir = this.rig - this.lef;
        this.vert_dir = this.down - this.up;
        if (this.fallSpeed >= 7) this.fallSpeed= 6.5;
        
        if(this.jumped){
            
            this.fallSpeed = -7;
            
            if (this.hori_dir != 0) this.last_hori_dir = this.hori_dir;
        }

        //Para cada valor da velocidade do jogar roda um codigo para detectar colisão pixel por pixel
        for (int i = 0; i < this.speed; i++){

            Tile hit = World.isFree((this.getX() + hori_dir), getY(), width, height);
            Tile hitted = World.isFree(this.getX() - last_hori_dir, getY(), width, height);
            Tile grab = World.isFree((int)(x + hori_dir), (int)y+16, width, 1);

            if(!knockBack && !isAttacking && !launching){
                switch (hit){
                    case null -> {
                        
                       this.x += this.hori_dir;
                            
                    }
                    case Grip_Wall g -> {
                        if(hori_dir == last_hori_dir && grab != null){
                            if (!this.inGround && !this.grabbed){
                                this.grabbed = true;
                                this.inGrip = true;
                            }
                        }
                        break;
                    }
                    case Ladder l -> {
                        if(hori_dir == last_hori_dir && grab != null){
                            if (!this.inGround && !this.grabbed){
                                this.grabbed = true;
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
            this.usingIten = false;
            inKnockBack = false;
            this.fallSpeed = 0;
            this.speed = 0;
            this.isAttacking = false;
            cd = 0;

            //Verifica se o que está se segurando é uma Escada sendo uma escada podemos subir e descer livremente
            Tile ladder = World.isFree((int)x + last_hori_dir, (int)(y), width, height);

            //Verificar colisão enquanto se move para cima ou para baixo copiando as outras colisões
            if (!(ladder instanceof Ladder)){
                for (int i = 0; i < climbSpeed; i++){

                    Tile hit = World.isFree((int)x, (int)(y+vert_dir), width, height);

                    switch (hit){
                        case null -> {

                            this.y += this.vert_dir;

                            //Verificações para os blocos na base e o topo do personagem
                            Tile Topo = World.isFree((int)x + last_hori_dir, (int)(y + 8), width, 1);
                            Tile Base = World.isFree((int)x + last_hori_dir, (int)(y + 30), width, 1);

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

                Tile hit = World.isFree((int)this.x, (int)(this.y+this.vert_dir), width, height);

                switch (hit){
                    case null -> {

                        this.y += this.vert_dir;

                        Tile Topo = World.isFree((int)x + last_hori_dir, (int)(y + 8), width, 1);
                        Tile Base = World.isFree((int)x + last_hori_dir, (int)(y + 30), width, 1);

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

            Tile hit = World.isFree((int) x, (int)(this.y + moveStep), width, height);

            switch (hit){
                case null -> {
                    if (isAttacking && !usingIten){
                        maxFrames = 5;
                    }else {
                        maxFrames = 2;
                    }
                    this.y += moveStep;
                    this.inGround = false;
                    this.jumped = false;
                    inKnockBack = false;
                    break;
                }
                default -> {
                    maxFrames = 5;
                    this.usingIten = false;
                    this.inGround = true;
                    this.fallSpeed = 0;
                    this.knockBack = false;
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

    public Rectangle getSwordMask(){
        return new Rectangle(getSwordX(), getSwordY(), getSwordWidth(), getSwordHeight());
    }

}