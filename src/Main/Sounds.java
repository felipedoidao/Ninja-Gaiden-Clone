package Main;

import java.io.*;
import javax.sound.sampled.*;

import Main.Sounds.Clips;

public class Sounds {
    public static class Clips {
        public Clip[] clips;
        private int p;
        private int count;

        public Clips(byte[] buffer, int count)throws LineUnavailableException, IOException, UnsupportedAudioFileException{
            if (buffer==null) return;

            clips = new Clip[count];
            this.count = count;

            for (int i = 0; i<count; i++){
                clips[i] = AudioSystem.getClip();

                clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
            }
        }

        public void play(){
            if (clips == null) return;

            clips[p].stop();
            clips[p].setFramePosition(0);
            clips[p].start();
            p++;
            if (p>= count) p = 0;
        }

        public void loop(){
            if (clips == null) return;

            clips[p].loop(300);
        }

        public void setVolume(float volume){
            if (clips == null) return;

            if (volume < 0.0f) volume = 0.0f;
            if (volume > 1.0f) volume = 1.0f;

            float db = (float) (Math.log(volume) / Math.log(10.0) * 20.0);

            for (Clip clip : clips){
                if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)){
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(db);
                }
            }
        }

        public static Clips attack = Clips.load("/rsc/Sounds/Attack.wav", 1);
        public static Clips dying_enemy = Clips.load("/rsc/Sounds/DyingEn.wav", 5);
        public static Clips jump = Clips.load("/rsc/Sounds/Jump.wav", 1);
        public static Clips shot = Clips.load("/rsc/Sounds/Shot.wav", 6);

        public static Clips load(String name, int count){
            try{
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                DataInputStream dis = new DataInputStream(Sounds.class.getResourceAsStream(name));

                byte[] buffer = new byte[1024];
                int read = 0;

                while((read = dis.read(buffer)) >= 0){
                    baos.write(buffer, 0, read);
                }

                dis.close();
                byte[] data = baos.toByteArray();
                return new Clips(data, count);  
            
            }catch(Exception e){
                try{
                    return new Clips(null, 0);
                
                }catch (Exception ee){
                    return null;
                }
            }
        }
    }
}
