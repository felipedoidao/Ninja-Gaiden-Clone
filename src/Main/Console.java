package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;

import Entities.Enemies.Candle;

public class Console{

    public static String consoleInput;
    public static List<String> logs = new ArrayList<String>();

    public static void render(Graphics g){
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, Main.HEIGHT*Main.SCALE/2, Main.WIDTH*Main.SCALE, Main.HEIGHT*Main.SCALE);

        g.setColor(Color.yellow);
        g.setFont(new Font("Consolas", Font.PLAIN, 20));
        for (int i = 0; i < logs.size(); i ++){
            g.drawString(logs.get(i), 10, ((Main.HEIGHT*Main.SCALE/2)+80) + 30*i);
        }
        
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.ITALIC, 20));
        g.drawString(">" + consoleInput + "_", 10, (Main.HEIGHT*Main.SCALE/2) + 50);

    }

    public static void addLog(String log){
        logs.add(log);
    }

    public static void executeCommand(String command){
        if (command == null || command.isEmpty()) return;

        if (command.startsWith("candle")){
            try{
                String parts[] = command.split(" ");
                if (parts.length > 1){
                    int val = Integer.parseInt(parts[1]);

                    if (val >= 0 && val < 115){
                        logs.clear();
                        Candle.itemSelector = val;
                        addLog("Candle item selector changed for: " + val);
                    }else{
                        logs.clear();
                        addLog("Error, Use: candle [0 < integer < 115]");
                    }
                }
            }catch (Exception e){
                logs.clear();
                addLog("Error, Use: candle [0 < integer < 115]");
            }

        }else if (command.startsWith("volume")){
            try{
                String parts[] = command.split(" ");
                if (parts.length > 1){
                    float val = Float.parseFloat(parts[1]);
                    if (val >= 0.0 && val <= 1.0){
                        logs.clear();
                        Main.globalVolume = val;
                        Main.audio_design();
                        addLog("Game volume changed for: " + val);
                    }else {
                        logs.clear();
                        addLog("Error, Use: volume [0.0 < float < 1.0]");
                    }
                }
            }catch (Exception e){
                logs.clear();
                addLog("Error, Use: volume [0.0 < float < 1.0]");
            }

        }else if (command.equals("help")){
            logs.clear();
            addLog("Integer is a number without decimal places.");
            addLog("Float is a number with decimal places.");
            addLog("volume [Float] for volume change");
            addLog("Candle [Integer] for item selector");
        }else {
            logs.clear();
            addLog("Unknown command, Type help for command list");
        }
    }
}
