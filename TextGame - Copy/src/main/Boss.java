package main;
import records.BossRecord;
import records.GeneralRecord;

import java.util.Random;

public class Boss {
    private final BossRecord attributes;
    private int currentHealth;
    private final String bossName;
    private final int bossDamage;
    Random random = new Random();

    public Boss(BossRecord attributes) {
        this.attributes = attributes;
        this.currentHealth = attributes.health();
        this.bossName = attributes.name();
        this.bossDamage = attributes.damage();
    }

    public String getBossName() { return bossName; }
    public int getBossHealth() { return currentHealth; }
    public int getBossDamage() {return randomDamage(bossDamage);}

    public void takeDamage(int damage) {
        currentHealth -= damage;
        if (currentHealth < 0) {
            currentHealth = 0;
        }
    }

    public boolean isAlive() {
        return currentHealth > 0;
    }
    private int randomDamage(int baseDamage) {
        int variation = (int) (baseDamage * 0.1);
        return baseDamage - variation + random.nextInt(2 * variation + 1); // Randomized damage
    }
}

class CoreGuardian extends Boss {
    public CoreGuardian() {
        super(new BossRecord("Core Guardian", 300, 70));
    }
}