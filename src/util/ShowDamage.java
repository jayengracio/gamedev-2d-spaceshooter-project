package util;

import entity.Enemy;
import entity.Player;

import java.util.TimerTask;

public class ShowDamage extends TimerTask {
    private static int i = 0;
    private static int period;
    private final GameObject object;
    private final String texture;

    public ShowDamage(GameObject object, String texture) {
        this.object = object;
        this.texture = texture;
        period = 1;
    }

    public ShowDamage(GameObject object, String texture, int period) {
        this.object = object;
        this.texture = texture;
        ShowDamage.period = period;
    }

    @Override
    public void run() {
        ++i;
        if (i == period) {
            object.setTexture(texture);
            i = 0;
            cancel();
        }
    }
}
