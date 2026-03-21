package Main;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.*;

import Player.Player;
import Graficos.Graficos;

public class Main extends Canvas implements Runnable, KeyListener{

    //Variáveis para renderização da janela
    private boolean rodando = false;
    private static JFrame frame;
    private static int Escala = 2;
    private static int Largura = 640;
    private static int Altura = 360 ;

    private BufferedImage image;

    Player player;

    //Controla as imagens usadas para o jogador
    public static Graficos idle, attack, jump;

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
        image = new BufferedImage(Largura, Altura, BufferedImage.TYPE_INT_RGB);

        //Inicializa as imagens para serem usadas
        idle = new Graficos("/rsc/Ninja medonho-Sheet.png");
        attack = new Graficos("/rsc/ninja medonho atacando-Sheet.png");
        jump = new Graficos("/rsc/Ninja cambalhota medonha-Sheet.png");
        
        //Dimensionando o tamanho da janela utilizando escala para manter o aspecto pixelado
        this.setPreferredSize(new Dimension(Largura*Escala, Altura*Escala));

        //Inicia o jogador
        player = new Player((Largura/2)-32, 0, 32, 32);

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
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, Largura, Altura);

        //Método de renderização do jogador
        player.render(g);

        //sumir com o pincel após o uso para evitar vazamento de memória
        g.dispose();

        //Trazer os desenhos prontos para a imagem vazia
        Graphics g2 = bs.getDrawGraphics();
        g2.drawImage(image, 0, 0, Largura*Escala, Altura*Escala, null);

        g2.dispose();

        //sincroniza o driver de vídeo com a tela
        Toolkit.getDefaultToolkit().sync();

        //mostrar o que foi desenhado
        bs.show();
    }

    //Função para a lógica das classes
    public void update(){
        player.update();
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
        }
    }


    //Funções obrigatórias para os eventos do teclado
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
       /*  if(e.getKeyCode() == KeyEvent.VK_W && player.inGround){
            player.jumped = true;
        }*/
       switch (e.getKeyCode()) {
        case KeyEvent.VK_W:
            player.jumped = true;
            break;
       
        case KeyEvent.VK_D:
            if (player.inGround){
                player.hori_dir = 1;
                player.last_hori_dir = 1;
            }
            break;

        case KeyEvent.VK_A:
            if (player.inGround){
                player.hori_dir = -1;
                player.last_hori_dir = -1;
            }
            break;
        
        case KeyEvent.VK_F:
            if (player.inGround && !player.attacked){
                player.isAttacking = true;
                player.attacked = true;
            }
            break;
       }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_W:
            player.jumped = true;
            break;
       
        case KeyEvent.VK_D:
            if (player.inGround){
                player.hori_dir = 1;
                player.last_hori_dir = 1;
            }
            break;

        case KeyEvent.VK_A:
            if (player.inGround){
                player.hori_dir = -1;
                player.last_hori_dir = -1;
            }
            break;
        
        case KeyEvent.VK_F:
            if (player.inGround){
                player.attacked = false;
                player.isAttacking = false;
            }
            break;
       }
    }

}
