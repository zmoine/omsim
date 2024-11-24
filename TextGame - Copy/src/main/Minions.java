package main;
import records.CharacterRecord;
import records.MinionsRecord;
import java.util.Random;

public class Minions {
    private final MinionsRecord attributes;
    private int currentHealth;
    private final String minionName;
    private final int minionDamage;
    Random random = new Random();

    public Minions(MinionsRecord attributes) {
        this.attributes = attributes;
        this.currentHealth = attributes.health();
        this.minionName = attributes.name();
        this.minionDamage = attributes.damage();
    }

    public String getMinionsName() {return minionName;}
    public int getMinionsHealth() {return currentHealth;}
    public int getMinionDamage() {return randomDamage(minionDamage);}

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

class WrathDrones extends Minions {
    public WrathDrones() {
        super(new MinionsRecord("Wrath Drones", 250, 50));
    }
}
