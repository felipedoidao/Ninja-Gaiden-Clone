package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Entities.Enemies.Candle;

public class Console{

    public static String consoleInput;

    public static void render(Graphics g){
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, Main.HEIGHT*Main.SCALE/2, Main.WIDTH*Main.SCALE, Main.HEIGHT*Main.SCALE);
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.ITALIC, 20));
        g.drawString("Type help for command list", 10, (Main.HEIGHT*Main.SCALE/2) + 20);
        g.drawString(":" + consoleInput + "_", 10, (Main.HEIGHT*Main.SCALE/2) + 45);

    }

    public static void executeCommand(String command){
        if (command == null || command.isEmpty()) return;

        if (command.startsWith("candle")){
            try{
                String parts[] = command.split(" ");
                if (parts.length > 1){
                    int val = Integer.parseInt(parts[1]);
                    Candle.itemSelector = val;
                }
            }catch (Exception e){
                System.err.println("Error, Use: candle [integer]");
            }
        }

        if (command.startsWith("volume")){
            try{
                String parts[] = command.split(" ");
                if (parts.length > 1){
                    float val = Float.parseFloat(parts[1]);
                    Main.globalVolume = val;
                    Main.audio_design();
                }
            }catch (Exception e){
                System.err.println("Error, Use: volume [float]");
            }
        }
    }
}
