import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import javax.swing.*;

public class Main extends Canvas implements Runnable, KeyListener {


    //Variáveis para renderização da janela
    private boolean rodando = false;
    private static JFrame frame;
    private static int Escala = 2;
    private static int Largura = 640;
    private static int Altura = 360 ;

    public int x = 0;
    public int y = 0;

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
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

    //Dimensionando o tamanho da janela utilizando escala para manter o aspecto pixelado
    public Main(){
        this.setPreferredSize(new Dimension(Largura*Escala, Altura*Escala));
    }

    //Função para iniciar a renderização de forma a não ocorrer o "thread racing"
    public synchronized void start(){
        rodando = true;
        new Thread(this).start();
    }

    private void renderizar(){
        //forma de manter quadros nos bastidores prontos antes da troca 
        BufferStrategy bs = this.getBufferStrategy();
        //verificar se BufferStrategy está vazio, se sim ele cria a estratégia
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }
        //variável que vai ser usada para desenhar os objetos na tela
        Graphics g = bs.getDrawGraphics();

        //desenhos na tela
        g.setColor(Color.black);
        g.fillRect(0, 0, Largura*Escala, Altura*Escala);

        g.setColor(Color.red);
        g.fillRect(x, y, 100, 100);

        //sumir com o pincel após o uso para evitar vazamento de memória
        g.dispose();
        //mostrar o que foi desenhado
        bs.show();

    }

    public void update(){

    }

    //loop do jogo
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













    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }
}
