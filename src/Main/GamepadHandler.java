package Main;

import net.java.games.input.*;
import Entities.Player;
import java.lang.reflect.Constructor;

public class GamepadHandler {

    private Controller joystick;
    private boolean searching;

    public GamepadHandler(){
        findJoystick();
    }

    private void findJoystick(){
        try {
            Constructor<?> constructor = Class.forName("net.java.games.input.DefaultControllerEnvironment").getDeclaredConstructor();
            constructor.setAccessible(true);
            ControllerEnvironment env = (ControllerEnvironment) constructor.newInstance();
            Controller[] controllers = env.getControllers();
        
            for (Controller c: controllers){
                if (c.getType() == Controller.Type.GAMEPAD || c.getType() == Controller.Type.STICK){
                    this.joystick = c;
                    return;
                }
            }
        }catch (Exception e){
            System.err.println();
        }
        this.joystick = null;
    }

    public void readCommands(Player player){
        if(joystick == null){
            if (!searching){ 
                searching = true;

                new Thread(() -> {
                    try{
                        Thread.sleep(2000);
                        findJoystick();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }finally {
                        searching = false;
                    }
                }).start();
            }
            return;
        }
        try {
            if(!joystick.poll()){
                this.joystick = null;
                return;
            }

            Component[] components = joystick.getComponents();
            for (Component comp : components){
                float val = comp.getPollData();
                String name = comp.getIdentifier().getName();

                //System.out.println("ID: " + comp.getIdentifier().getName() + " | Valor: " + val); 
                if (name.equals("pov") &&  val !=0){
                    if (val == 0.5){
                        player.rig = 1;
                        player.lef = 0;
                        if(!player.inGrip && !player.knockBack && !player.isAttacking && !player.launching){
                            player.last_hori_dir = 1;
                        }
                            
                    }else if (val == 1.0){
                        player.lef = 1;
                        player.rig = 0;
                        if(!player.inGrip && !player.knockBack && !player.isAttacking && !player.launching){
                            player.last_hori_dir = -1;
                        }
                            
                    }else {
                        player.rig = 0;
                        player.lef = 0;
                    }
                    if (val == 0.25){
                        player.up = 1;
                    
                    }else if (val == 0.75){
                        player.down = 1;
                    
                    }else {
                        player.up = 0;
                        player.down = 0;
                    }
                }else if (name.equals("x")){
                    if (val > 0.5 && val <= 1){
                        player.rig = 1;
                        player.lef = 0;
                        if(!player.inGrip && !player.knockBack && !player.isAttacking && !player.launching){
                            player.last_hori_dir = 1;
                        }
                    
                    }else if (val < -0.5 && val >= -1){
                        player.lef = 1;
                        player.rig = 0;
                        if(!player.inGrip && !player.knockBack && !player.isAttacking && !player.launching){
                            player.last_hori_dir = -1;
                        }
                        
                    }else {
                        player.rig = 0;
                        player.lef = 0;
                        player.isGrabbing = false;
                    } 
                    
                }else if (name.equals("y")){
                    if (val > 0.5 && val <= 1){
                        player.down = 1;
                    
                    }else if (val < -0.5 && val >= -1){
                        player.up = 1;
                    
                    }else {
                        player.up = 0;
                        player.down = 0;
                    }
                }
                
                if (name.equals("0")){
                    if (val != 0){
                        if (player.inGround && player.isJumping == false || player.inGrip && player.isJumping == false){
                            player.jumped = true;
                            player.isJumping = true;
                            player.inGrip = false;
                        }
                    }else {
                        player.jumped = false;
                        player.isJumping = false;
                    }
                }

                if (name.equals("2")){
                    if (val != 0){
                        if (!player.attacked && !player.isAttacking &&!player.inGrip && !player.knockBack && !player.usingIten){
                            player.isAttacking = true;
                            player.attacked = true;
                            Main.player.index = 0;
                            Main.player.frames = 0;
                        }
                    }else {
                        player.attacked = false;
                    }
                }

                if (name.equals("1")){
                    if (val != 0){
                        if (Player.bag[0] != null && Player.energy >= 5){
                            if (!player.used && !player.isUsing){
                                player.isUsing = true;
                                player.used = true;
                            }   
                        }
                    }else {
                        player.used = false;
                    }
                }
            }
        }catch (Exception e){
            joystick = null;
        }
    }    
}
