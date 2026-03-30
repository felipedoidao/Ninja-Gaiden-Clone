package World;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Entities.Enemies.Bird;
import Entities.Enemies.Launcher;
import Entities.Enemies.Red_ninja;
import Entities.Enemies.Soldier;
import Main.Main;

public class World {

    public static Tile[] tiles;
    public static int WIDTH, HEIGHT;
    public static final int TILE_SIZE = 32;

    public static BufferedImage map;

    public World (String path){
        try {
            
            //Variáveis para selecionar a imagem, criar lista de pixels, largura e altura da imagem
            map = ImageIO.read(getClass().getResourceAsStream(path));
            int[] pixels = new int[map.getWidth()*map.getHeight()];
            WIDTH = map.getWidth();
            HEIGHT = map.getHeight();

            //Criação da lita de tiles
            tiles = new Tile[map.getWidth() * map.getHeight()];

            //Comando para detectar cor dos pixels da imagem usando RGB
            map.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);

            //Loop para escanear todos os pixels da imagem
            for (int yy = 0; yy < HEIGHT; yy++){
                for (int xx = 0; xx < WIDTH; xx++){

                    //Pixel que está sendo analisado no momento
                    int ActualPixel = pixels[xx + (yy * WIDTH)];
                    
                    //verifica se o pixel da Imagem é transparente isolando o canal alpha do hexadecimal, se for transparente, pula
                    if (((ActualPixel >> 24) & 0xff) == 0){
                        tiles[xx +(yy * WIDTH)] = null;
                        continue;
                    }

                    //verifica apartir do código Hexadecimal a cor do pixel analisado e decide qual Tile colocar no lugar
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
                        case 0xFFffff5b:
                            tiles[xx + (yy * WIDTH)] = new Ladder(xx*32, yy*32, Tile.LADDER_RIGHT);
                            break;
                        case 0xFFffff2e:
                            tiles[xx + (yy * WIDTH)] = new Ladder(xx*32, yy*32, Tile.LADDER_LEFT);
                            break;
                        case 0xFFac3232:
                            Red_ninja red_ninja = new Red_ninja(xx*32, yy*32, 32, 32);
                            red_ninja.xStart = xx*32;
                            red_ninja.yStart = yy*32;
                            Main.entities.add(red_ninja);
                            break;
                        case 0xFFd95763:
                            Bird bird = new Bird(xx*32, yy*32, 28, 10);
                            bird.xStart = xx*32;
                            bird.yStart = yy*32;
                            Main.entities.add(bird);
                            break;
                        case 0xFFff0000:
                            Soldier soldier = new Soldier(xx*32, yy*32, 25, 32);
                            soldier.xStart = xx*32;
                            soldier.yStart = yy*32;
                            Main.entities.add(soldier);
                            break;
                        case 0xFFdf7126:
                            Launcher launcher = new Launcher(xx*32, yy*32, 17, 32);
                            launcher.xStart = xx*32;
                            launcher.yStart = yy*32;
                            Main.entities.add(launcher);
                            break;
                        case 0xFF1eff00:
                            Main.player.setX(xx*32);
                            Main.player.setY(yy*32);
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

        //Coordenadas do início da renderização. O ">> 5" é equivalente a dividr Camera.x por 2 elevado a 5, ou seja, dividir por 32
        int xStart = Camera.x >> 5;
        int yStart = Camera.y >> 5;

        //Pega o inicio e soma com a largura e altura também divididos por 32 para saber quantos blocos de 32x32 cabem nesse espaço
        int xFinal = xStart + (Main.Largura >> 5);
        int yFinal = yStart + (Main.Altura >> 5);

        //Escaneia o espaço recebido para desenhar nas posições
        for (int xx = xStart; xx <= xFinal; xx++){
            for (int yy = yStart; yy <= yFinal; yy++){
                
                //Se as coordenadas derem fora da imagem ele pula para não dar erro
                if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT){
                    continue;
                }

                //Instancia um novo tile dentro da lista
                Tile tile = tiles[xx + (yy * WIDTH)];

                //Desenha apenas se o tile não for vazio
                if(tile != null) {
                    tile.render(g);
                }
            }
        }
    }

    //Método de colisão que compara a posição da entidade com o tile e especificado
    public static Tile isFree(int xNext, int yNext, int width, int height){

        //Pega os quatros cantos da entidade
        int x1 = (xNext) / TILE_SIZE;
        int y1 = (yNext) / TILE_SIZE;

        int x2 = (xNext+width-1) / TILE_SIZE;
        int y2 = (yNext) / TILE_SIZE;

        int x3 = (xNext) / TILE_SIZE;
        int y3 = (yNext+height-1) / TILE_SIZE;

        int x4 = (xNext+width-1) / TILE_SIZE;
        int y4 = (yNext+height-1) / TILE_SIZE;
        
        //Impede que o jogo dê erro ao sair da tela pela parte de cima 
        if (y1 < 0 || y2 < 0 || y3 < 0 || y4 < 0) return null;

        //Checa qual a posição na lista do bloco que a entidade encontrou
        Tile[] check = {
            tiles[x1 + (y1 * World.WIDTH)],
            tiles[x2 + (y2 * World.WIDTH)],
            tiles[x3 + (y3 * World.WIDTH)],
            tiles[x4 + (y4 * World.WIDTH)]

        };

        //Determina e retorna a instância a qual o bloco encontrado pertence
        for (Tile t : check){
            if (t instanceof Floor_tile || t instanceof Grip_Wall || t instanceof Ladder) return t;
        }

        //Caso não encontre bloco, retorna vazio
        return null;
    }

}
