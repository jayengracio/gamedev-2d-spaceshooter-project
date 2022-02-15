package entity;

import util.GameObject;
import util.Point3f;

public class Enemy extends GameObject {
    private int health;
    private int level;

    public Enemy() {}

    public Enemy(String textureLocation, int width, int height, Point3f centre, int health, int level) {
        super(textureLocation, width, height, centre);
        this.health = health;
        this.level = level;
    }

    public Enemy(String texture, int i, int i1, Point3f point3f) {
        super(texture, i, i1, point3f);
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
}
