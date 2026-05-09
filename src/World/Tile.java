package World;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.Main;

//Classe mãe de todas as outras classes Tiles 
public class Tile {
    
    //Variáveis para pegar as coordenadas dos tiles na imagem que se encontra na classe Main
    public static BufferedImage TILE_FLOOR = Main.level.getSprite(0, 0, 32, 32);
    public static BufferedImage GRIP_WALL_RIGHT = Main.level.getSprite(32, 0, 32, 32);
    public static BufferedImage GRIP_WALL_LEFT = Main.level.getSprite(32*2, 0, 32, 32);
    public static BufferedImage LADDER_RIGHT = Main.level.getSprite(32*3, 0, 32, 32);
    public static BufferedImage LADDER_LEFT = Main.level.getSprite(32*4, 0, 32, 32);
    
    private BufferedImage sprite;
    private int x, y;

    public Tile(int x, int y, BufferedImage sprite){
        this.x = x;
        this.y = y;
        this.sprite = sprite;

    }

    //Método de renderização dos tiles
    public void render(Graphics g){
        //Desenha a imagem de acordo com a posição da câmera
        g.drawImage(sprite, x - Camera.x, y - Camera.y, 32, 32, null);
    }


    //Métodos para outras classes acessarem as coordenadas
    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
}
