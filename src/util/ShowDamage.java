package util;

import entity.Enemy;

import java.util.TimerTask;

public class ShowDamage extends TimerTask {
    public static int i = 0;
    private final Enemy enemy;
    private final String texture;

    public ShowDamage(Enemy enemy, String texture) {
        this.enemy = enemy;
        this.texture = texture;
    }

    @Override
    public void run() {
        ++i;
        if (i == 1) {
            enemy.setTexture(texture);
            i = 0;
            cancel();
        }
    }
}
