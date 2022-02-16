package entity;

import util.GameObject;
import util.Point3f;
import util.ReloadAmmo;

import java.util.Timer;
import java.util.TimerTask;

public class Player extends GameObject {
    private int lives;
    private int upgradeLevel;
    private int ammo;
    private boolean invincible;

    public Player(){}

    public Player(String textureLocation, int width, int height, Point3f centre, int health, int upgradeLevel, int ammo) {
        super(textureLocation, width, height, centre);
        this.lives = health;
        this.ammo = ammo;
        this.upgradeLevel = 1;
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

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }
}
