package entity;

import util.GameObject;
import util.Point3f;

public class Player extends GameObject {
    private int lives;
    private int upgradeLevel;
    private int ammo;
    private int maxAmmo;
    private boolean invincible;
    private boolean dead = false;
    private boolean shieldCd;
    private String defaultTexture;

    public Player(){}

    public Player(String textureLocation, int width, int height, Point3f centre, int health, int ammo) {
        super(textureLocation, width, height, centre);
        this.lives = health;
        this.ammo = ammo;
        this.upgradeLevel = 1;
        this.defaultTexture = textureLocation;
        this.maxAmmo = ammo;
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

    public int getMaxAmmo() {
        if (upgradeLevel == 2) {
            maxAmmo = 10;
        }
        return maxAmmo;
    }

    public String getDefaultTexture() {
        return defaultTexture;
    }

    public boolean isDead() {
        if (!getTexture().equals("res/blankShip.png") && getLives() <= 0) {
            setTexture("res/blankShip.png");
        }
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isShieldCd() {
        return shieldCd;
    }

    public void setShieldCd(boolean shieldCd) {
        this.shieldCd = shieldCd;
    }
}
