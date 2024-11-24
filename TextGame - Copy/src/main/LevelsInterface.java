package main;

interface LevelsInterface {
    void startWorld();
    void gamePlayLevel1();
    void gamePlayLevel2();
    void gamePlayLevel3();
    void gamePlayLevel4();
    void gamePlayLevel5();
    void performCharacterAttack(Runnable nextLevel, String enemyName, int enemyHealth, int enemyDamage, String skillName, int damage, int energyGained);
    void performSkipEnemysTurn(Runnable nextLevel, String enemyName, int enemyHealth, int enemyDamage, String skillName, int damage, int energyGained);
    void performEnemyAttack(Runnable nextMethod, String enemyName, int enemyDamage);
    void displayEnemyDefeat(String enemyName,int timeCrystals, int damageUpgrade, Runnable nextLevel);
    void displayWorldComplete();
    void gameOver();
}