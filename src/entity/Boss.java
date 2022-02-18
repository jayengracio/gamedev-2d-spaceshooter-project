package entity;

import util.GameObject;
import util.Point3f;

public class Boss extends GameObject {
    private int health;
    private int level;
    private boolean defeated;

    public Boss() {
        this.defeated = false;
    }

    public Boss(String textureLocation, int width, int height, Point3f centre, int health, int level) {
        super(textureLocation, width, height, centre);
        this.health = health;
        this.level = level;
        this.defeated = false;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isDefeated() {
        return defeated;
    }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
    }
}
