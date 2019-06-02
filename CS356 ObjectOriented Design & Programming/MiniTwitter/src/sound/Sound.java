package sound;

/*	This class provides the music during GUI. */

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Sound {

    public void playVoice(URL source) {
        try {
            File sound = new File(source.getPath());
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();

        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            System.out.println("GUI: Audio not found.");
        }
    }

    public void playBGM(URL source) {
        try {
            File sound = new File(source.getPath());
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.loop(Integer.MAX_VALUE);

        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            System.out.println("GUI: Audio not found.");
        }
    }

}
