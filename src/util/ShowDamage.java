package util;

import entity.Enemy;
import entity.Player;

import java.util.TimerTask;

public class ShowDamage extends TimerTask {
    private static int i = 0;
    private final GameObject object;
    private final String texture;

    public ShowDamage(GameObject object, String texture) {
        this.object = object;
        this.texture = texture;
    }

    @Override
    public void run() {
        ++i;
        if (i == 1) {
            object.setTexture(texture);
            i = 0;
            cancel();
        }
    }
}
