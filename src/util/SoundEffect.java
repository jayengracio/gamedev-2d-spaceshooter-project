package util;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundEffect {
    String sfxFile;

    public SoundEffect(String sfxFile) {
        this.sfxFile = sfxFile;
    }

    public void playSFX() {
        InputStream sfx;
        try {
            sfx = new FileInputStream(new File(sfxFile));
            AudioStream audio = new AudioStream(sfx);
            AudioPlayer.player.start(audio);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
