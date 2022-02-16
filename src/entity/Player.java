package entity;

import util.GameObject;
import util.Point3f;

public class Player extends GameObject {
    private int lives;
    private int upgradeLevel;
    private int ammo;

    public Player(){}

    public Player(String textureLocation, int width, int height, Point3f centre, int health, int upgradeLevel, int ammo) {
        super(textureLocation, width, height, centre);
        this.lives = health;
        this.upgradeLevel = upgradeLevel;
        this.ammo = ammo;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getUpgradeLevel() {
        return upgradeLevel;
    }

    public void setUpgradeLevel(int upgradeLevel) {
        this.upgradeLevel = upgradeLevel;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
}
