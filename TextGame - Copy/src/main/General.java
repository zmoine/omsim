package main;
import records.GeneralRecord;

import java.util.Random;

public class General {
    private final GeneralRecord attributes;
    private int currentHealth;
    private final String generalName;
    private final int generalDamage;
    Random random = new Random();

    public General(GeneralRecord attributes) {
        this.attributes = attributes;
        this.currentHealth = attributes.health();
        this.generalName = attributes.name();
        this.generalDamage = attributes.damage();
    }

    public String getGeneralName() { return attributes.name(); }
    public int getGeneralHealth() { return currentHealth; }
    public int getGeneralDamage() {return randomDamage(generalDamage);}

    public void takeDamage(int damage) {
        currentHealth -= damage;
        if (currentHealth < 0) {
            currentHealth = 0;
        }
    }

    public boolean isAlive() {return currentHealth > 0;}

    private int randomDamage(int baseDamage) {
        int variation = (int) (baseDamage * 0.1);
        return baseDamage - variation + random.nextInt(2 * variation + 1); // Randomized damage
    }
}

class CommanderSynthrax extends General {
    public CommanderSynthrax() {
        super(new GeneralRecord("Commander Synthrax", 200, 60));
    }
}
