package Main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import Entities.Entities;
import Entities.Player;
import Graphics.Textures;
import Graphics.Ui;
import World.Camera;
import World.World;

public class Main extends Canvas implements Runnable, KeyListener{

    //Variáveis para renderização da janela
    private boolean rodando = false;
    private static JFrame frame;
    final static int SCALE = 3;
    public final static int WIDTH = 320;
    public final static int HEIGHT = 288;

    private BufferedImage image;

    public static Player player;
    public static World world;
    public Ui ui;
    public static List<Entities> entities;
    public int timerCD = 0;
    public static int timer = 300;

    //Controla as imagens usadas para o jogador
    public static Textures ninja, level, enemies;

    public static void main(String[] args) throws Exception {
        frame = new JFrame("Nijna Gaiden");
        Main main = new Main();

        //Criação e renderização da janela
        frame.setResizable(false);
        frame.add(main);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //início da lógica
        main.start();
    }

    
    public Main(){
        
        //Comandos para o teclado
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();

        //prepara uma imagem vazia para desenhar
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        //Inicializa as imagens para serem usadas
        ninja = new Textures("/rsc/Ninja Spritesheet.png");
        level = new Textures("/rsc/Mundo Bizarro.png");
        enemies = new Textures("/rsc/Enemies.png");
        
        //Dimensionando o tamanho da janela utilizando escala para manter o aspecto pixelado
        this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));

        entities = new ArrayList<Entities>();

        //Inicia o jogador
        player = new Player((WIDTH/2)-32, 0, 32, 32);
        
        world = new World("/rsc/Mapa medonho.png");

        ui = new Ui();

    }

    //Função para iniciar a renderização de forma a não ocorrer o "thread racing"
    public synchronized void start(){
        rodando = true;
        new Thread(this).start();
    }

    //Função para renderizar imagens na tela
    private void renderizar(){

        //forma de manter quadros nos bastidores prontos antes da troca 
        BufferStrategy bs = this.getBufferStrategy();

        //verificar se BufferStrategy está vazio, se sim ele cria a estratégia
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }

        //variável que vai preparar os desenhos dos objetos
        Graphics g = image.getGraphics();

        //desenhos na tela
        g.setColor(new Color(200, 200, 255));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        world.render(g);

        for (int i = 0; i < entities.size(); i++){
            Entities e = entities.get(i);
            if (e.inScreen()) e.render(g);
        }
        
        //Método de renderização do jogador
        player.render(g);

        ui.render(g);

        //sumir com o pincel após o uso para evitar vazamento de memória
        g.dispose();

        //Trazer os desenhos prontos para a imagem vazia
        Graphics g2 = bs.getDrawGraphics();
        g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

        g2.dispose();

        //sincroniza o driver de vídeo com a tela
        Toolkit.getDefaultToolkit().sync();

        //mostrar o que foi desenhado
        bs.show();
    }

    //Função para a lógica das classes
    public void update(){
        player.update();

        timerCD ++;
        if (timerCD >= 60){
            timer--;
            timerCD = 0;
        }
        
        for (int i = 0; i < entities.size(); i++){
            Entities e = entities.get(i);
            e.update();
        }

        Camera.x = (int)player.getX() - (WIDTH/2);

        Camera.x = Camera.clamp(Camera.x, 0, (World.WIDTH*32) - WIDTH);
    }

    //Loop do jogo
    @Override public void run(){

        //Váriaveis para manter a lógica do jogo com velocidade constante
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;

        while(rodando){

            //diz quanto tempo se passou entre os quadros
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;

            //diz quando que a lógica deve ser atualizada
            while(delta >= 1){
                update();
                delta --;
            }

            //desenha na tela a cada loop
            renderizar();

            try {
                Thread.sleep(1); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //Funções obrigatórias para os eventos do teclado
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
       switch (e.getKeyCode()) {
        case KeyEvent.VK_SPACE:
            if (player.inGround && player.isJumping == false || player.inGrip && player.isJumping == false){
                player.jumped = true;
                player.isJumping = true;
                player.inGrip = false;
            }
            break;

        case KeyEvent.VK_W:
            player.up = 1;
            break;

        case KeyEvent.VK_S:
            player.down = 1;
            break;

        case KeyEvent.VK_D:
            player.rig = 1;
                
                if(!player.inGrip && !player.knockBack && !player.isAttacking){
                    player.last_hori_dir = 1;
                }
            
            break;

        case KeyEvent.VK_A:
            player.lef = 1;
             
                if(!player.inGrip && !player.knockBack && !player.isAttacking){
                    player.last_hori_dir = -1;
                }
            
            break;
        
        case KeyEvent.VK_F:
            if (!player.attacked && !player.inGrip && !player.knockBack){
                player.isAttacking = true;
                player.attacked = true;
            }
            break;

        case KeyEvent.VK_E:
            if (Player.bag[0] != null){
                if (!player.used && !player.isUsing){
                    player.isUsing = true;
                    player.used = true;
                }
                
            }
            break;
       }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_SPACE:
            player.jumped = false;
            player.isJumping = false;
            break;
        
        case KeyEvent.VK_W:
            player.up = 0;
            break;

        case KeyEvent.VK_S:
            player.down = 0;
            break;

        case KeyEvent.VK_D:
            player.rig = 0;
            player.isGrabbing = false;
            break;

        case KeyEvent.VK_A:
            player.lef = 0;
            player.isGrabbing = false;
            break;
        
        case KeyEvent.VK_F:
            player.attacked = false;
            break;
        case KeyEvent.VK_E:
            player.used = false;
            break;
       }
    }

}
