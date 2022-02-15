package util;

import entity.Player;

import java.util.TimerTask;

public class ReloadAmmo extends TimerTask {
    public static int i = 0;
    private final Player player;
    private final int ammoCount;

    public ReloadAmmo(Player player, int ammoCount) {
        this.player = player;
        this.ammoCount = ammoCount;
    }

    @Override
    public void run() {
        ++i;
        if (i == 1) {
            player.setAmmo(ammoCount);
            i = 0;
            cancel();
        }
    }
}
