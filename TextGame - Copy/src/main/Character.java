package main;
import records.CharacterRecord;

import java.util.Random;

public class Character {
    private final CharacterRecord attributes;
    private int currentHealth;
    private final String skill1, skill2, skill3, ultimate, name, role;
    private final int skill1Damage, skill2Damage, skill3Damage, ultimateDamage;
    private final int skill1Cost, skill1Gain, skill1EnergyCost, skill1EnergyGain;
    private final int skill2Cost, skill2Gain, skill2EnergyCost, skill2EnergyGain;
    private final int skill3Cost, skill3Gain, skill3EnergyCost, skill3EnergyGain;
    private final int ultimateSkillCost, ultimateSkillGain, ultimateEnergyCost, ultimateEnergyGain;

    public Character(CharacterRecord attributes) {
        this.attributes = attributes;
        this.currentHealth = attributes.health();
        this.name = attributes.name();
        this.skill1 = attributes.skill1();
        this.skill2 = attributes.skill2();
        this.skill3 = attributes.skill3();
        this.ultimate = attributes.ultimate();
        this.skill1Damage = attributes.skill1Damage();
        this.skill2Damage = attributes.skill2Damage();
        this.skill3Damage = attributes.skill3Damage();
        this.ultimateDamage = attributes.ultimateDamage();
        this.skill1Cost = attributes.skill1Cost();
        this.skill1Gain = attributes.skill1Gain();
        this.skill1EnergyCost = attributes.skill1EnergyCost();
        this.skill1EnergyGain = attributes.skill1EnergyGain();
        this.skill2Cost = attributes.skill2Cost();
        this.skill2Gain = attributes.skill2Gain();
        this.skill2EnergyCost = attributes.skill2EnergyCost();
        this.skill2EnergyGain = attributes.skill2EnergyGain();
        this.skill3Cost = attributes.skill3Cost();
        this.skill3Gain = attributes.skill3Gain();
        this.skill3EnergyCost = attributes.skill3EnergyCost();
        this.skill3EnergyGain = attributes.skill3EnergyGain();
        this.ultimateSkillCost = attributes.ultimateSkillCost();
        this.ultimateSkillGain = attributes.ultimateSkillGain();
        this.ultimateEnergyCost = attributes.ultimateEnergyCost();
        this.ultimateEnergyGain = attributes.ultimateEnergyGain();
        this.role = attributes.role();

    }

    public String getName(){return name;}
    public String getRole(){ return role; }
    public String getSkill1(){return skill1;}
    public String getSkill2(){return skill2;}
    public String getSkill3(){return skill3;}
    public String getUltimate(){ return ultimate; }
    public int getHealth(){ return currentHealth; }

    public void takeDamage(int damage) {
        currentHealth -= damage;
        if (currentHealth < 0) {
            currentHealth = 0;
        }
    }

    public boolean isAlive() {return currentHealth > 0;}
    public boolean isEnergyFull(int ultimateEnergyCost){
        return ultimateEnergyCost >= getUltimateEnergyCost();
    }

    public int getSkill1Damage() { return skill1Damage; }
    public int getSkill2Damage() { return skill2Damage; }
    public int getSkill3Damage() { return skill3Damage; }
    public int getUltimateDamage() { return ultimateDamage; }
    public int getSkill1Cost() { return skill1Cost; }
    public int getSkill1Gain() { return skill1Gain; }
    public int getSkill1EnergyCost() { return skill1EnergyCost; }
    public int getSkill1EnergyGain() { return skill1EnergyGain; }
    public int getSkill2Cost() { return skill2Cost; }
    public int getSkill2Gain() { return skill2Gain; }
    public int getSkill2EnergyCost() { return skill2EnergyCost; }
    public int getSkill2EnergyGain() { return skill2EnergyGain; }
    public int getSkill3Cost() { return skill3Cost; }
    public int getSkill3Gain() { return skill3Gain; }
    public int getSkill3EnergyCost() { return skill3EnergyCost; }
    public int getSkill3EnergyGain() { return skill3EnergyGain; }
    public int getUltimateSkillCost() { return ultimateSkillCost; }
    public int getUltimateSkillGain() { return ultimateSkillGain; }
    public int getUltimateEnergyCost() { return ultimateEnergyCost; }
    public int getUltimateEnergyGain() { return ultimateEnergyGain; }
}

// Subclasses for specific characters
class Cipher extends Character {
    public Cipher() {
        super(new CharacterRecord("Cipher", "Tech Specialist (Digital/Analog Hybrid)", "Circuit Strike", "Analog Shift", "Digital Override", "Signal Surge", 1200,  50, 60, 90, 250, 0, 5, 0, 10, 4, 0, 0, 12, 6 , 0, 0, 15, 0, 0, 50, 0));
    }
}

class Opal extends Character {
    public Opal() {
        super(new CharacterRecord("Opal", "Scout (Agility/Stealth)", "Shadowstep", "Trap Disable", "Venom Dart", "Decoy Phantom", 900,  30, 50, 80, 200, 0, 5, 0, 8, 4, 0, 0, 10, 5, 0, 0, 12, 0, 0,40, 0));
    }
}

class Pulse extends Character {
    public Pulse() {
        super(new CharacterRecord("Pulse", "Energy Manipulator (Support/Mage)", "Temporal Burst", "Energy Drain", "Time Rewind", "Heal Wave", 1100,  50, 70, 90, 210, 0, 6, 0, 10, 4, 0, 0, 12, 6, 0,0,  15, 0, 0, 50, 0));
    }
}

class Mirage extends Character {
    public Mirage() {
        super(new CharacterRecord("Mirage", "Illusionist (Tactical/Mind Control)", "Echo Illusion", "Mind Hack", "Phantom Circuit", "Ultimate", 950,  50, 70, 90, 220, 0, 5, 0, 10, 4, 0, 0, 12, 5, 0, 0, 15, 0, 0, 50, 0));
    }
}

class Zero extends Character {
    public Zero() {
        super(new CharacterRecord("Zero", "Time Warden (OP Character)", "Chrono Control", "Temporal Blade", "Fate Breaker", "Ultimate", 1500,  50, 70, 90, 300, 0, 7, 0, 10, 4, 0, 0, 15, 5, 0, 0, 17, 0, 0,50, 0));
    }
}
