import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PlaySound {
    private String bgm = "bgm.wav";

    public void play() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(PlaySound.class.getResourceAsStream(bgm));
                    clip.open(inputStream);
                    clip.start();
                    while(clip.isOpen()) {
                        try { Thread.sleep(2000); } catch(InterruptedException ie) {}
                        if(!clip.isActive()) break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}