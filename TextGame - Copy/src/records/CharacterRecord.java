package records;

public record CharacterRecord(
        String name,
        String role,
        String skill1,
        String skill2,
        String skill3,
        String ultimate,
        int health,
        int skill1Damage,
        int skill2Damage,
        int skill3Damage,
        int ultimateDamage,
        int skill1Cost,
        int skill1Gain,
        int skill1EnergyCost,
        int skill1EnergyGain,
        int skill2Cost,
        int skill2Gain,
        int skill2EnergyCost,
        int skill2EnergyGain,
        int skill3Cost,
        int skill3Gain,
        int skill3EnergyCost,
        int skill3EnergyGain,
        int ultimateSkillCost,
        int ultimateSkillGain,
        int ultimateEnergyCost,
        int ultimateEnergyGain
){ }