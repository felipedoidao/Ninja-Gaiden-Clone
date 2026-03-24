package World;

import Main.Main;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.IOException;

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

            for (int yy = 0; yy < HEIGHT; yy++){
                for (int xx = 0; xx < WIDTH; xx++){

                    int ActualPixel = pixels[xx + (yy * WIDTH)];
                    
                    //verifica se o pixel da Imagem é transparente isolando o canal alpha do hexadecimal
                    if (((ActualPixel >> 24) & 0xff) == 0){
                        tiles[xx +(yy * WIDTH)] = null;
                        continue;
                    }

                    //verifica apartir do código Hexadecimal a cor do pixel analisado e decide qual Tile colocar no lugar
                    /*if (ActualPixel == 0xFFFFFFFF){
                        tiles[xx + (yy * WIDTH)] = new Floor_tile(xx*32, yy*32, Tile.TILE_FLOOR);
                    }*/
                    switch (ActualPixel) {
                        case 0xFFFFFFFF:
                            tiles[xx + (yy * WIDTH)] = new Floor_tile(xx*32, yy*32, Tile.TILE_FLOOR);
                            break;
                    
                        case 0xFFffffce:
                            tiles[xx + (yy * WIDTH)] = new Grip_Wall(xx*32, yy*32, Tile.GRIP_WALL_RIGHT);
                            break;
                        case 0xFFffffca:
                            tiles[xx + (yy * WIDTH)] = new Grip_Wall(xx*32, yy*32, Tile.GRIP_WALL_LEFT);
                            break;
                   }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(){

    }

    //Método de renderização que ignora tiles vazios e renderiza apenas o que aparece dentro dos limites da câmera
    public void render(Graphics g){

        int xStart = Camera.x >> 5;
        int yStart = Camera.y >> 5;

        int xFinal = xStart + (Main.Largura >> 5);
        int yFinal = yStart + (Main.Altura >> 5);

        for (int xx = xStart; xx <= xFinal; xx++){
            for (int yy = yStart; yy <= yFinal; yy++){

                if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT){
                    continue;
                }
                Tile tile = tiles[xx + (yy * WIDTH)];

                if(tile != null) {
                    tile.render(g);
                }
            }
        }
    }

    //Método de colisão que compara a posição da entidade com o tile e especificado
    public static Tile isFree(int xNext, int yNext, int width, int height){

        int x1 = (xNext) / TILE_SIZE;
        int y1 = (yNext) / TILE_SIZE;

        int x2 = (xNext+width-1) / TILE_SIZE;
        int y2 = (yNext) / TILE_SIZE;

        int x3 = (xNext) / TILE_SIZE;
        int y3 = (yNext+height-1) / TILE_SIZE;

        int x4 = (xNext+width-1) / TILE_SIZE;
        int y4 = (yNext+height-1) / TILE_SIZE;
        
        if (y1 < 0 || y2 < 0 || y3 < 0 || y4 < 0) return null;

        Tile[] check = {
            tiles[x1 + (y1 * World.WIDTH)],
            tiles[x2 + (y2 * World.WIDTH)],
            tiles[x3 + (y3 * World.WIDTH)],
            tiles[x4 + (y4 * World.WIDTH)]

        };

        for (Tile t : check){
            if (t instanceof Floor_tile || t instanceof Grip_Wall) return t;
        }

        return null;

        /*return !((tiles[x1 + (y1*Main.world.WIDTH)] instanceof Floor_tile) ||
				(tiles[x2 + (y2*Main.world.WIDTH)] instanceof Floor_tile) ||
				(tiles[x3 + (y3*Main.world.WIDTH)] instanceof Floor_tile) ||
				(tiles[x4 + (y4*Main.world.WIDTH)] instanceof Floor_tile));*/
    }

}
