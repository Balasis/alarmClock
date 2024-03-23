import javax.sound.sampled.*;
import java.util.Objects;

public class SoundPlayer {
    private Clip clip;
    private int volumePercentage=0;

    public SoundPlayer(String soundFilePath) {
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(SoundPlayer.class.getResource(soundFilePath)));
            DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioInputStream);
        }catch(Exception e){

        }
        //            audioInputStream.close();
    }

    public void play(){
        clip.start();
        setVolumePercentage(volumePercentage);
    }

    public void stop(){
        clip.stop();
    }

    public void setVolumePercentage(int volumePercentage){
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float rangeDB = gainControl.getMaximum() - gainControl.getMinimum();
        float volumeDB = (rangeDB * volumePercentage / 100) + gainControl.getMinimum();
        gainControl.setValue(volumeDB);
    }

}