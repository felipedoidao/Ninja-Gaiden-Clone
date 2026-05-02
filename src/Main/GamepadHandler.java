package Main;

import net.java.games.input.*;
import Entities.Player;
import java.lang.reflect.Constructor;

public class GamepadHandler {

    private Controller joystick;
    private boolean searching;
    private boolean stopAxisX;
    private boolean stopAxisY;
    private boolean stopPovX;
    private boolean stopPovY;
    private boolean stopJump;

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

                if (val > 0.5 || val < -0.5){
                    //System.out.println("ID: " + comp.getIdentifier().getName() + " | Valor: " + val);
                }
                //System.out.println("ID: " + comp.getIdentifier().getName() + " | Valor: " + val); 
                if (name.equals("pov")){
                    if (val == 0.5){
                        player.rig = 1;
                        player.lef = 0;
                        stopPovX = false;
                        if(!player.inGrip && !player.knockBack && !player.isAttacking && !player.launching){
                            player.last_hori_dir = 1;
                        }
                            
                    }else if (val == 1.0){
                        player.lef = 1;
                        player.rig = 0;
                        stopPovX = false;
                        if(!player.inGrip && !player.knockBack && !player.isAttacking && !player.launching){
                            player.last_hori_dir = -1;
                        }
                            
                    }else if (!stopPovX){
                        player.rig = 0;
                        player.lef = 0;
                        stopPovX = true;
                    }
                    if (val == 0.25){
                        player.up = 1;
                        stopPovY = false;
                    
                    }else if (val == 0.75){
                        player.down = 1;
                        stopPovY = false;
                    
                    }else if (!stopPovY){
                        player.up = 0;
                        player.down = 0;
                        stopPovY = true;
                    }
                }else if (name.equals("x")){
                    if (val > 0.5 && val <= 1){
                        player.rig = 1;
                        player.lef = 0;
                        stopAxisX = false;
                        if(!player.inGrip && !player.knockBack && !player.isAttacking && !player.launching){
                            player.last_hori_dir = 1;
                        }
                    
                    }else if (val < -0.5 && val >= -1){
                        player.lef = 1;
                        player.rig = 0;
                        stopAxisX = false;
                        if(!player.inGrip && !player.knockBack && !player.isAttacking && !player.launching){
                            player.last_hori_dir = -1;
                        }
                        
                    }else if (!stopAxisX){
                        player.rig = 0;
                        player.lef = 0;
                        stopAxisX = true;
                    } 
                    
                }else if (name.equals("y")){
                    if (val > 0.5 && val <= 1){
                        player.down = 1;
                        stopAxisY = false;
                    
                    }else if (val < -0.5 && val >= -1){
                        player.up = 1;
                        stopAxisY = false;
                    
                    }else if (!stopAxisY){
                        player.up = 0;
                        player.down = 0;
                        stopAxisY = true;
                    }
                }
                
                if (name.equals("0")){
                    if (val != 0){
                        if (player.inGround && player.isJumping == false || player.inGrip && player.isJumping == false){
                            player.jumped = true;
                            player.isJumping = true;
                            player.inGrip = false;
                            player.grabbed = true;
                            stopJump = false;
                        }
                    }else if (val == 0 && !stopJump){
                        player.jumped = false;
                        player.isJumping = false;
                        player.grabbed = false;
                        stopJump = true;
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
