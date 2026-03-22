package World;

import Main.Main;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class World {

    public static Tile[] tiles;
    public static int WIDTH, HEIGHT;
    public static final int TILE_SIZE = 32;

    public static BufferedImage map;

    public World (String path){
        try {
            
            map = ImageIO.read(getClass().getResourceAsStream(path));
            int[] pixels = new int[map.getWidth()*map.getHeight()];
            WIDTH = map.getWidth();
            HEIGHT = map.getHeight();

            tiles = new Tile[map.getWidth() * map.getHeight()];
            map.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);

            for (int xx = 0; xx < WIDTH; xx++){
                for (int yy = 0; yy < HEIGHT; yy++){

                    int ActualPixel = pixels[xx + (yy * WIDTH)];
                    
                    //verifica se o pixel da Imagem é transparente isolando o canal alpha do hexadecimal
                    if (((ActualPixel >> 24) & 0xff) == 0){
                        tiles[xx +(yy * WIDTH)] = null;
                        continue;
                    }

                    //verifica apartir do código Hexadecimal a cor do pixel analisado e decide qual Tile colocar no lugar
                    if (ActualPixel == 0xFF8f563b){
                        tiles[xx + (yy * WIDTH)] = new Tile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_FLOOR);
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
