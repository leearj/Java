/**
 * *************************************************************
 * file: Sound.java
 * authors: Colin Trotter, Andrew Trang, Jaeseung Lee
 * class: CS 245 – Programming Graphical User Interfaces
 *
 * assignment: qtrProject_1.3
 * date last modified: 10/24/2016
 *
 * purpose: Plays audio files during the game.
 *
 ***************************************************************
 */
package hangman;

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
