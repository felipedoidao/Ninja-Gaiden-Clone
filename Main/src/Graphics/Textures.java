package Graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Textures {

    private BufferedImage spritesheet;

    //Construtor da classe que pega uma imagem spritesheet pedindo o caminho dela na pasta
    public Textures(String path) {
    try {
        // Tenta carregar o arquivo como um fluxo de dados
        this.spritesheet = ImageIO.read(getClass().getResourceAsStream(path));
        
        // Verificação de segurança crucial:
        if (this.spritesheet == null) {
            System.err.println("ERRO: O arquivo existe mas o Java não conseguiu ler: " + path);
        }
    } catch (Exception e) {
        System.err.println("ERRO CRÍTICO: Não foi possível localizar o arquivo: " + path);
        e.printStackTrace();
    }
}

    //Método que determina as coordenadas do frame que será usado
    public BufferedImage getSprite(int x, int y, int width, int height){
        return spritesheet.getSubimage(x, y, width, height);
    }

}
