package main;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

class NihraGalaxyGamePlay extends GameFrame implements LevelsInterface{
    public static NihraGalaxyGamePlay.GameManager GameManager;
    private final Character chosenCharacter;
    String characterName;
    int characterHealth;
    int skill1Damage, skill2Damage, skill3Damage, ultimateDamage;
    int skill1Cost, skill1Gain, skill1EnergyCost, skill1EnergyGain;
    int skill2Cost, skill2Gain, skill2EnergyCost, skill2EnergyGain;
    int skill3Cost, skill3Gain, skill3EnergyCost, skill3EnergyGain;
    int ultimateSkillCost, ultimateSkillGain, ultimateEnergyCost, ultimateEnergyGain;
    String skill1, skill2, skill3, ultimate;
    Minions minion = new WrathDrones();
    String minionName;
    int minionHealth = minion.getMinionsHealth();
    General general = new CommanderSynthrax();
    String generalName;
    int generalHealth = general.getGeneralHealth();
    Boss boss = new CoreGuardian();
    String bossName;
    int bossHealth = boss.getBossHealth();
    private int energy = 0;
    private int damageUpgrade = 0;
    private int totalDamageUpgrade = 0;
    protected int level = 0;
    private int stamina = 0;
    protected int timeCrystals = 0;
    protected int totalTimeCrystals = 1000;
    Inventory inventory = Inventory.getInstance();
    private GameManager gameManager;
    Shop shop;

    public NihraGalaxyGamePlay(Character chosenCharacter){
        this.chosenCharacter = chosenCharacter;
        characterName = chosenCharacter.getName();
        skill1Damage = chosenCharacter.getSkill1Damage();
        skill2Damage = chosenCharacter.getSkill2Damage();
        skill3Damage = chosenCharacter.getSkill3Damage();
        ultimateDamage = chosenCharacter.getUltimateDamage();
        skill1 = chosenCharacter.getSkill1();
        skill2 = chosenCharacter.getSkill2();
        skill3 = chosenCharacter.getSkill3();
        ultimate = chosenCharacter.getUltimate();
        skill1Cost = chosenCharacter.getSkill1Cost();
        skill1Gain = chosenCharacter.getSkill1Gain();
        skill1EnergyCost = chosenCharacter.getSkill1EnergyCost();
        skill1EnergyGain = chosenCharacter.getSkill1EnergyGain();
        skill2Cost = chosenCharacter.getSkill2Cost();
        skill2Gain = chosenCharacter.getSkill2Gain();
        skill2EnergyCost = chosenCharacter.getSkill2EnergyCost();
        skill2EnergyGain = chosenCharacter.getSkill2EnergyGain();
        skill3Cost = chosenCharacter.getSkill3Cost();
        skill3Gain = chosenCharacter.getSkill3Gain();
        skill3EnergyCost = chosenCharacter.getSkill3EnergyCost();
        skill3EnergyGain = chosenCharacter.getSkill3EnergyGain();
        ultimateSkillCost = chosenCharacter.getUltimateSkillCost();
        ultimateSkillGain = chosenCharacter.getUltimateSkillGain();
        ultimateEnergyCost = chosenCharacter.getUltimateEnergyCost();
        ultimateEnergyGain = chosenCharacter.getUltimateEnergyGain();

        shop = new Shop(gameManager, getTotalTimeCrystals());
        NihraGalaxyGamePlay.this.dispose();
        gamePlayLevel1();
        minionName = minion.getMinionsName();
        generalName = general.getGeneralName();
        bossName = boss.getBossName();
    }

    public class GameManager {
        private Shop shop;

        public GameManager() {
            gameManager = this;
        }

        public void initializeShop(Character chosenCharacter) {
            if (shop == null) {
                int crystals = new NihraGalaxyGamePlay(chosenCharacter).getTotalTimeCrystals();
                shop = new Shop(this, crystals);
            }
        }

        public Shop getShop() {
            return shop;
        }
    }


    @Override
    public void startWorld(){
        gamePanel.removeAll();
        gamePanel.setLayout(new GridBagLayout());
        gamePanel.setBackground(Color.BLACK);

        if (chosenCharacter == null) {
            JOptionPane.showMessageDialog(gamePanel, "No character selected! Please choose a character to continue.");
            return;
        }

        displayOpeningDialogue(new NihraDialogue());
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    public void bottomButtons(){
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 5, 10));
        bottomPanel.setBackground(Color.BLACK);

        JButton inventoryButton = new JButton(" DATA CACHE ");
        JButton shopButton = new JButton(" SHOP ");

        for (JButton button : Arrays.asList(inventoryButton, shopButton)) {
            button.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
            button.setBackground(Color.BLACK);
            button.setForeground(Color.WHITE);
            button.setBorder(new LineBorder(Color.WHITE, 1));
            button.setMargin(new Insets(10, 0, 10, 0));
            bottomPanel.add(button);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.SOUTH;
        gamePanel.add(bottomPanel, gbc);

        inventoryButton.addActionListener(e -> {
            isInInventory = true;
            inventory.showInventoryItems();
        });

        final Shop shopWindow = new Shop(gameManager, totalTimeCrystals);
        shopButton.addActionListener(e -> {
            inventory.setVisible(false);
            shopWindow.setVisible(true);
            shopWindow.showShopItems();
        });

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    private boolean skipEnemyTurn = false;
    private boolean isInInventory = false;
    @Override
    public void gamePlayLevel1() {
        gamePanel.removeAll();
        gamePanel.setLayout(new GridBagLayout());
        gamePanel.setBackground(Color.BLACK);

        if (chosenCharacter == null) {
            JOptionPane.showMessageDialog(gamePanel, "No character selected! Please choose a character to continue.");
            return;
        }

        bottomButtons();
        level1Title(minionName, minionHealth);

        JPanel controlPanel = new JPanel(new GridLayout(4, 4, 5, 10));
        controlPanel.setBackground(Color.BLACK);

        JButton skill1Button = new JButton(" " + skill1 + "  | Damage: " + (skill1Damage + totalDamageUpgrade) + "  | Stamina Cost: " + skill1Cost + " | Stamina Gain: " + skill1Gain + " | Energy Cost: " + skill1EnergyCost + "  | Energy Gain: " + skill1EnergyGain + " ");
        JButton skill2Button = new JButton(" " + skill2 + "  | Damage: " + (skill2Damage + totalDamageUpgrade) + "  | Stamina Cost: " + skill2Cost + " | Stamina Gain: " + skill2Gain + " | Energy Cost: " + skill2EnergyCost + "  | Energy Gain: " + skill2EnergyGain + " ");
        JButton skill3Button = new JButton(" " + skill3 + "    | Damage: " + (skill3Damage + totalDamageUpgrade) + "  | Stamina Cost: " + skill3Cost + " | Stamina Gain: " + skill3Gain + " | Energy Cost: " + skill3EnergyCost + "  | Energy Gain: " + skill3EnergyGain + " ");
        JButton ultimateButton = new JButton("   " + ultimate + "     | Damage: " + (ultimateDamage + totalDamageUpgrade) + "  | Stamina Cost: " + ultimateSkillCost + " | Stamina Gain: " + ultimateSkillGain + " | Energy Cost: " + ultimateEnergyCost + " | Energy Gain: " + ultimateEnergyGain + " ");

        for (JButton button : Arrays.asList(skill1Button, skill2Button, skill3Button, ultimateButton)) {
            button.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
            button.setBackground(Color.BLACK);
            button.setForeground(Color.WHITE);
            button.setBorder(new LineBorder(Color.WHITE, 1));
            button.setMargin(new Insets(10, 0, 10, 0));
            controlPanel.add(button);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 100, 10);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 0.25;
        gamePanel.add(controlPanel, gbc);

        if(isInInventory) {
            System.out.println("Items in inventory: " + inventory.getItems());
            if (!inventory.isVisible()) {
                isInInventory = false;
            }
            String selectedItem = inventory.getSelectedItem();
            System.out.println(selectedItem); // checking
            System.out.print(shop.getUseItem(selectedItem)); // checking
            int useItem = shop.getUseItem(selectedItem);

            switch (selectedItem) {
                case "Time Stabilizer (Health Potion)" -> {
                    chosenCharacter.takeDamage(useItem);
                    System.out.print("1"); // checking
                }
                case "Quantum Elixir (Energy Potion)" -> {
                    energy += useItem;
                    System.out.print("2"); // checking
                }
                case "Plasma Burst (Fireball Spell)" -> {
                    minion.takeDamage(useItem);
                    System.out.print("3"); // checking
                }
                case "Ion Blade (Iron Sword)" -> {
                    minion.takeDamage(useItem);
                    System.out.print("4"); // checking
                }
                case "Neutronium Armor (Energy Beam & Skip Enemy Next Turn)" -> {
                    skipEnemyTurn = true;
                    System.out.print("5"); // checking
                }
                default -> System.out.print("Item not found!");
            }

            if (skipEnemyTurn) {
                skipEnemyTurn = false;
                minion.takeDamage(useItem);
                performSkipEnemysTurn(this::gamePlayLevel1, minionName, minionHealth, minion.getMinionDamage(), selectedItem, useItem, 0);
                System.out.print("skip");
            }
        }

        level = 1;
        if(!minion.isAlive() && level == 1){
            timeCrystals = 10;
            totalTimeCrystals += timeCrystals;
            damageUpgrade = 5;
            totalDamageUpgrade += damageUpgrade;
            displayEnemyDefeat(minionName, timeCrystals, damageUpgrade, new Runnable() {
                @Override
                public void run() {
                    displayDialogue1(new NihraDialogue());
                }
            });
        }

        if(!chosenCharacter.isAlive()){
            gameOver();
        }

        skill1Button.addActionListener(e -> {
            stamina += skill1Gain;
            stamina -= chosenCharacter.getSkill1Cost();
            energy += chosenCharacter.getSkill1EnergyGain();
            minion.takeDamage(skill1Damage + totalDamageUpgrade);
            minionHealth = minion.getMinionsHealth();
            performCharacterAttack(this::gamePlayLevel1, minionName, minionHealth, minion.getMinionDamage(), skill1, (skill1Damage + totalDamageUpgrade), skill1EnergyGain);
        });

        if(stamina >= skill2Cost) {
            for (ActionListener al : skill2Button.getActionListeners()) {
                skill2Button.removeActionListener(al);
            }

            skill2Button.addActionListener(e -> {
                stamina -= skill2Cost;
                energy += skill2EnergyGain;
                minion.takeDamage(skill2Damage + totalDamageUpgrade);
                minionHealth = minion.getMinionsHealth();
                performCharacterAttack(this::gamePlayLevel1, minionName, minionHealth, minion.getMinionDamage(), skill2, (skill2Damage + totalDamageUpgrade), skill2EnergyGain);
            });
            skill2Button.setEnabled(true);
        } else {
            skill2Button.setEnabled(false);
        }

        if(stamina >= skill3Cost) {
            for (ActionListener al : skill3Button.getActionListeners()) {
                skill3Button.removeActionListener(al);
            }

            skill3Button.addActionListener(e -> {
                stamina -= skill3Cost;
                energy += skill3EnergyGain;
                minion.takeDamage(skill3Damage + totalDamageUpgrade);
                minionHealth = minion.getMinionsHealth();
                performCharacterAttack(this::gamePlayLevel1, minionName, minionHealth, minion.getMinionDamage(), skill3, (skill3Damage + totalDamageUpgrade), skill3EnergyGain);
            });
            skill3Button.setEnabled(true);
        } else {
            skill3Button.setEnabled(false);
        }

        if (chosenCharacter.isEnergyFull(energy)) {
            for (ActionListener al : ultimateButton.getActionListeners()) {
                ultimateButton.removeActionListener(al);
            }

            ultimateButton.addActionListener(e -> {
                energy -= ultimateEnergyCost; // Reset energy
                minion.takeDamage(ultimateDamage + totalDamageUpgrade);
                minionHealth = minion.getMinionsHealth();// Perform the attack
                performCharacterAttack(this::gamePlayLevel1, minionName, minionHealth, minion.getMinionDamage(), ultimate, (ultimateDamage + totalDamageUpgrade), ultimateEnergyGain);
            });
            ultimateButton.setEnabled(true);
        } else {
            ultimateButton.setEnabled(false);
        }

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    @Override
    public void gamePlayLevel2() {
        gamePanel.removeAll();
        gamePanel.setLayout(new GridBagLayout());
        gamePanel.setBackground(Color.BLACK);

        bottomButtons();
        level2Title(generalName, generalHealth);

        JPanel controlPanel = new JPanel(new GridLayout(4, 4, 5, 10));
        controlPanel.setBackground(Color.BLACK);

        JButton skill1Button = new JButton(skill1 + "  | Damage: " + (skill1Damage + totalDamageUpgrade) + "  | Stamina Cost: " + skill1Cost + " | Stamina Gain: " + skill1Gain + " | Energy Cost: " + skill1EnergyCost + "  | Energy Gain: " + skill1EnergyGain + " ");
        JButton skill2Button = new JButton(skill2 + "  | Damage: " + (skill2Damage + totalDamageUpgrade) + "  | Stamina Cost: " + skill2Cost + " | Stamina Gain: " + skill2Gain + " | Energy Cost: " + skill2EnergyCost + "  | Energy Gain: " + skill2EnergyGain + " ");
        JButton skill3Button = new JButton(skill3 + "    | Damage: " + (skill3Damage + totalDamageUpgrade) + "  | Stamina Cost: " + skill3Cost + " | Stamina Gain: " + skill3Gain + " | Energy Cost: " + skill3EnergyCost + "  | Energy Gain: " + skill3EnergyGain + " ");
        JButton ultimateButton = new JButton("  " + ultimate + "     | Damage: " + (ultimateDamage + totalDamageUpgrade) + "  | Stamina Cost: " + ultimateSkillCost + " | Stamina Gain: " + ultimateSkillGain + " | Energy Cost: " + ultimateEnergyCost + " | Energy Gain: " + ultimateEnergyGain + " ");

        for (JButton button : Arrays.asList(skill1Button, skill2Button, skill3Button, ultimateButton)) {
            button.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
            button.setBackground(Color.BLACK);
            button.setForeground(Color.WHITE);
            button.setBorder(new LineBorder(Color.WHITE, 1));
            button.setMargin(new Insets(10, 0, 10, 0));
            controlPanel.add(button);
        }

        // GridBagConstraints for positioning control panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 100, 10);
        gbc.gridx = 0;  // First column
        gbc.gridy = 2;  // Third row (below the status panel)
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 0.25;
        gamePanel.add(controlPanel, gbc);

        level = 2;
        if(!general.isAlive() && level == 2){
            timeCrystals = 15;
            totalTimeCrystals += timeCrystals;
            damageUpgrade = 5;
            totalDamageUpgrade += damageUpgrade;
            displayEnemyDefeat(generalName, timeCrystals, damageUpgrade, new Runnable() {
                @Override
                public void run() {
                    displayDialogue2(new NihraDialogue());
                }
            });
        }

        if(!chosenCharacter.isAlive()){
            gameOver();
        }

        skill1Button.addActionListener(e -> {
            stamina += skill1Gain;
            energy += skill1EnergyGain;
            general.takeDamage(skill1Damage + totalDamageUpgrade);
            generalHealth = general.getGeneralHealth();
            enemyDefeated = false;
            performCharacterAttack(this::gamePlayLevel2, generalName, generalHealth, general.getGeneralDamage(), skill1, (skill1Damage + totalDamageUpgrade), skill1EnergyGain);
        });

        if(stamina >= skill2Cost) {
            for (ActionListener al : skill2Button.getActionListeners()) {
                skill2Button.removeActionListener(al);
            }

            skill2Button.addActionListener(e -> {
                stamina -= skill2Cost;
                energy += skill2EnergyGain;
                general.takeDamage(skill2Damage + totalDamageUpgrade);
                generalHealth = general.getGeneralHealth();
                enemyDefeated = false;
                performCharacterAttack(this::gamePlayLevel2, generalName, generalHealth, general.getGeneralDamage(), skill2, (skill2Damage + totalDamageUpgrade), skill2EnergyGain);
            });
            skill2Button.setEnabled(true);
        } else {
            skill2Button.setEnabled(false);
        }

        if(stamina >= skill3Cost) {
            for (ActionListener al : skill3Button.getActionListeners()) {
                skill3Button.removeActionListener(al);
            }

            skill3Button.addActionListener(e -> {
                stamina -= skill3Cost;
                energy += skill3EnergyGain;
                general.takeDamage(skill3Damage + totalDamageUpgrade);
                generalHealth = general.getGeneralHealth();
                enemyDefeated = false;
                performCharacterAttack(this::gamePlayLevel2, generalName, generalHealth, general.getGeneralDamage(), skill3, (skill3Damage + totalDamageUpgrade), skill3EnergyGain);
            });
            skill3Button.setEnabled(true);
        } else {
            skill3Button.setEnabled(false);
        }

        if (chosenCharacter.isEnergyFull(energy)) {
            for (ActionListener al : ultimateButton.getActionListeners()) {
                ultimateButton.removeActionListener(al);
            }

            ultimateButton.addActionListener(e -> {
                energy -= ultimateEnergyCost; // Reset energy
                general.takeDamage(ultimateDamage + totalDamageUpgrade);
                generalHealth = general.getGeneralHealth();
                enemyDefeated = false;
                performCharacterAttack(this::gamePlayLevel2, generalName, generalHealth, general.getGeneralDamage(), ultimate, (ultimateDamage + totalDamageUpgrade), ultimateEnergyGain);
            });
            ultimateButton.setEnabled(true);
        } else {
            ultimateButton.setEnabled(false);
        }

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    @Override
    public void gamePlayLevel3() {
        gamePanel.removeAll();
        gamePanel.setLayout(new GridBagLayout());
        gamePanel.setBackground(Color.BLACK);

        bottomButtons();
        level3Title(bossName, bossHealth);

        JPanel controlPanel = new JPanel(new GridLayout(4, 4, 5, 10));
        controlPanel.setBackground(Color.BLACK);

        JButton skill1Button = new JButton(skill1 + "  | Damage: " + (skill1Damage + totalDamageUpgrade) + "  | Stamina Cost: " + skill1Cost + " | Stamina Gain: " + skill1Gain + " | Energy Cost: " + skill1EnergyCost + "  | Energy Gain: " + skill1EnergyGain + " ");
        JButton skill2Button = new JButton(skill2 + "  | Damage: " + (skill2Damage + totalDamageUpgrade) + "  | Stamina Cost: " + skill2Cost + " | Stamina Gain: " + skill2Gain + " | Energy Cost: " + skill2EnergyCost + "  | Energy Gain: " + skill2EnergyGain + " ");
        JButton skill3Button = new JButton(skill3 + "    | Damage: " + (skill3Damage + totalDamageUpgrade) + "  | Stamina Cost: " + skill3Cost + " | Stamina Gain: " + skill3Gain + " | Energy Cost: " + skill3EnergyCost + "  | Energy Gain: " + skill3EnergyGain + " ");
        JButton ultimateButton = new JButton("  " + ultimate + "     | Damage: " + (ultimateDamage + totalDamageUpgrade) + "  | Stamina Cost: " + ultimateSkillCost + " | Stamina Gain: " + ultimateSkillGain + " | Energy Cost: " + ultimateEnergyCost + " | Energy Gain: " + ultimateEnergyGain + " ");

        for (JButton button : Arrays.asList(skill1Button, skill2Button, skill3Button, ultimateButton)) {
            button.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
            button.setBackground(Color.BLACK);
            button.setForeground(Color.WHITE);
            button.setBorder(new LineBorder(Color.WHITE, 1));
            button.setMargin(new Insets(10, 0, 10, 0));
            controlPanel.add(button);
        }

        // GridBagConstraints for positioning control panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 100, 10);
        gbc.gridx = 0;  // First column
        gbc.gridy = 2;  // Third row (below the status panel)
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 0.25;
        gamePanel.add(controlPanel, gbc);

        level = 3;
        if(!boss.isAlive() && level == 3){
            timeCrystals = 20;
            totalTimeCrystals += timeCrystals;
            damageUpgrade = 5;
            totalDamageUpgrade += damageUpgrade;
            displayEnemyDefeat(bossName, timeCrystals, damageUpgrade, new Runnable() {
                @Override
                public void run() {
                    displayDialogue3(new NihraDialogue());
                }
            });
        }

        if(!chosenCharacter.isAlive()){
            gameOver();
        }

        skill1Button.addActionListener(e -> {
            stamina += skill1Gain;
            energy += skill1EnergyGain;
            boss.takeDamage(skill1Damage + totalDamageUpgrade);
            bossHealth = boss.getBossHealth();
            enemyDefeated = false;
            performCharacterAttack(this::gamePlayLevel3, bossName, bossHealth, boss.getBossDamage(), skill1, (skill1Damage + totalDamageUpgrade), skill1EnergyGain);
        });

        if(stamina >= skill2Cost) {
            for (ActionListener al : skill2Button.getActionListeners()) {
                skill2Button.removeActionListener(al);
            }

            skill2Button.addActionListener(e -> {
                stamina -= skill2Cost;
                energy += skill2EnergyGain;
                boss.takeDamage(skill2Damage + totalDamageUpgrade);
                bossHealth = boss.getBossHealth();
                enemyDefeated = false;
                performCharacterAttack(this::gamePlayLevel3, bossName, bossHealth, boss.getBossDamage(), skill2, (skill2Damage + totalDamageUpgrade), skill2EnergyGain);
            });
            skill2Button.setEnabled(true);
        } else {
            skill2Button.setEnabled(false);
        }

        if(stamina >= skill3Cost) {
            for (ActionListener al : skill3Button.getActionListeners()) {
                skill3Button.removeActionListener(al);
            }

            skill3Button.addActionListener(e -> {
                stamina -= skill3Cost;
                energy += skill3EnergyGain;
                boss.takeDamage(skill3Damage + totalDamageUpgrade);
                bossHealth = boss.getBossHealth();
                enemyDefeated = false;
                performCharacterAttack(this::gamePlayLevel3, bossName, bossHealth, boss.getBossDamage(), skill3, (skill3Damage + totalDamageUpgrade), skill3EnergyGain);
            });
            skill3Button.setEnabled(true);
        } else {
            skill3Button.setEnabled(false);
        }

        if (chosenCharacter.isEnergyFull(energy)) {
            for (ActionListener al : ultimateButton.getActionListeners()) {
                ultimateButton.removeActionListener(al);
            }

            ultimateButton.addActionListener(e -> {
                energy -= ultimateEnergyCost; // Reset energy
                boss.takeDamage(ultimateDamage + totalDamageUpgrade);
                bossHealth = boss.getBossHealth();
                enemyDefeated = false;
                performCharacterAttack(this::gamePlayLevel3, bossName, bossHealth, boss.getBossDamage(), ultimate, (ultimateDamage + totalDamageUpgrade), ultimateEnergyGain);
            });
            ultimateButton.setEnabled(true);
        } else {
            ultimateButton.setEnabled(false);
        }

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    @Override
    public void gamePlayLevel4(){}

    @Override
    public void gamePlayLevel5(){}

    private boolean enemyDefeated = false;
    @Override
    public void performCharacterAttack(Runnable nextLevel, String enemyName, int enemyHealth, int enemyDamage, String skillName, int damage, int energyGained) {
        gamePanel.removeAll();
        gamePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gamePanel.setBackground(Color.BLACK);

        if (timer[0] != null) {
            timer[0].stop();
        }

        JLabel attackMessage1 = new JLabel(characterName + " attacks with " + skillName + " and gains " + energyGained + " energy!");
        attackMessage1.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        attackMessage1.setForeground(Color.WHITE);
        JLabel attackMessage2 = new JLabel(enemyName + " takes " + damage + " damage!");
        attackMessage2.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        attackMessage2.setForeground(Color.WHITE);
        JLabel attackMessage3 = new JLabel("Remaining health: " + enemyHealth);
        attackMessage3.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        attackMessage3.setForeground(Color.WHITE);
        JLabel attackMessage4 = new JLabel("Total Energy: " + energy + "  |  Total Stamina: " + stamina);
        attackMessage4.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        attackMessage4.setForeground(Color.WHITE);

        JPanel attackPanel = new JPanel(new GridBagLayout());
        attackPanel.setBackground(Color.BLACK);

        gbc.gridx = 0;        // Center horizontally
        gbc.gridy = 0;        // Center vertically
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;      // Allow horizontal expansion
        gbc.weighty = 1;      // Allow vertical expansion
        gbc.anchor = GridBagConstraints.CENTER;  // Center the label
        gbc.insets = new Insets(70, 10, 10, 10); // Padding around the label
        attackPanel.add(attackMessage1, gbc);
        gamePanel.add(attackPanel, gbc);

        gbc.gridx = 0;        // Center horizontally
        gbc.gridy = 1;        // Center vertically
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;      // Allow horizontal expansion
        gbc.weighty = 1;      // Allow vertical expansion
        gbc.anchor = GridBagConstraints.CENTER;  // Center the label
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around the label
        attackPanel.add(attackMessage2, gbc);
        gamePanel.add(attackPanel, gbc);

        gbc.gridx = 0;        // Center horizontally
        gbc.gridy = 2;        // Center vertically
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;      // Allow horizontal expansion
        gbc.weighty = 1;      // Allow vertical expansion
        gbc.anchor = GridBagConstraints.CENTER;  // Center the label
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around the label
        attackPanel.add(attackMessage3, gbc);
        gamePanel.add(attackPanel, gbc);

        gbc.gridx = 0;        // Center horizontally
        gbc.gridy = 3;        // Center vertically
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;      // Allow horizontal expansion
        gbc.weighty = 1;      // Allow vertical expansion
        gbc.anchor = GridBagConstraints.CENTER;  // Center the label
        gbc.insets = new Insets(10, 10, 50, 10); // Padding around the label
        attackPanel.add(attackMessage4, gbc);
        gamePanel.add(attackPanel, gbc);

        nextButton = new JButton(" Press Enter To Continue... ");
        nextButton.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBorder(null);
        nextButton.setFocusable(true);

        nextButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                // This method is called when a key is pressed
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { // For example, Enter key
                    if(enemyHealth <= 0 && !enemyDefeated){
                        enemyDefeated = true;
                        damageUpgrade = 5;
                        nextLevel(nextLevel);
                    }else {
                        performEnemyAttack(nextLevel, enemyName, enemyDamage);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        nextButton.addActionListener(e -> {
            if (timer[0] != null) {
                timer[0].stop();
            }
            gamePanel.removeAll();
            gamePanel.revalidate();
            gamePanel.repaint();
            performEnemyAttack(nextLevel, enemyName, enemyDamage);
        });

        gbc.gridx = 0; // Center horizontally
        gbc.gridy = 3; // Add it to the first row
        gbc.gridwidth = 1; // Take up one column
        gbc.gridheight = 1; // Take up one row
        gbc.weightx = 1; // Allow horizontal expansion
        gbc.weighty = 1; // Allow vertical expansion
        gbc.anchor = GridBagConstraints.SOUTH; // Anchor to the bottom
        gbc.insets = new Insets(10, 10, 100, 10); // Padding around the button
        gamePanel.add(nextButton, gbc);
        nextButton.requestFocusInWindow();

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    @Override
    public void performSkipEnemysTurn(Runnable nextLevel, String enemyName, int enemyHealth, int enemyDamage, String skillName, int damage, int energyGained) {
        gamePanel.removeAll();
        gamePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gamePanel.setBackground(Color.BLACK);

        if (timer[0] != null) {
            timer[0].stop();
        }

        String ownership;
        if(enemyName.charAt(enemyName.length()-1) == 's'){
            ownership = "'";
        }else{
            ownership = "'s";
        }

        JLabel attackMessage1 = new JLabel(characterName + " skips " + enemyName + ownership + " turn with " + skillName);
        attackMessage1.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        attackMessage1.setForeground(Color.WHITE);
        JLabel attackMessage2 = new JLabel(enemyName + " takes " + damage + " damage!");
        attackMessage2.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        attackMessage2.setForeground(Color.WHITE);
        JLabel attackMessage3 = new JLabel("Remaining health: " + enemyHealth);
        attackMessage3.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        attackMessage3.setForeground(Color.WHITE);
        JLabel attackMessage4 = new JLabel("Total Energy: " + energy + "  |  Total Stamina: " + stamina);
        attackMessage4.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        attackMessage4.setForeground(Color.WHITE);

        JPanel attackPanel = new JPanel(new GridBagLayout());
        attackPanel.setBackground(Color.BLACK);

        gbc.gridx = 0;        // Center horizontally
        gbc.gridy = 0;        // Center vertically
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;      // Allow horizontal expansion
        gbc.weighty = 1;      // Allow vertical expansion
        gbc.anchor = GridBagConstraints.CENTER;  // Center the label
        gbc.insets = new Insets(70, 10, 10, 10); // Padding around the label
        attackPanel.add(attackMessage1, gbc);
        gamePanel.add(attackPanel, gbc);

        gbc.gridx = 0;        // Center horizontally
        gbc.gridy = 1;        // Center vertically
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;      // Allow horizontal expansion
        gbc.weighty = 1;      // Allow vertical expansion
        gbc.anchor = GridBagConstraints.CENTER;  // Center the label
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around the label
        attackPanel.add(attackMessage2, gbc);
        gamePanel.add(attackPanel, gbc);

        gbc.gridx = 0;        // Center horizontally
        gbc.gridy = 2;        // Center vertically
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;      // Allow horizontal expansion
        gbc.weighty = 1;      // Allow vertical expansion
        gbc.anchor = GridBagConstraints.CENTER;  // Center the label
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around the label
        attackPanel.add(attackMessage3, gbc);
        gamePanel.add(attackPanel, gbc);

        gbc.gridx = 0;        // Center horizontally
        gbc.gridy = 3;        // Center vertically
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;      // Allow horizontal expansion
        gbc.weighty = 1;      // Allow vertical expansion
        gbc.anchor = GridBagConstraints.CENTER;  // Center the label
        gbc.insets = new Insets(10, 10, 50, 10); // Padding around the label
        attackPanel.add(attackMessage4, gbc);
        gamePanel.add(attackPanel, gbc);

        nextButton = new JButton(" Press Enter To Continue... ");
        nextButton.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBorder(null);
        nextButton.setFocusable(true);

        nextButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                // This method is called when a key is pressed
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { // For example, Enter key
                    if(enemyHealth <= 0 && !enemyDefeated){
                        enemyDefeated = true;
                        damageUpgrade = 5;
                        nextLevel(nextLevel);
                    }else {
                        nextLevel(nextLevel);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        nextButton.addActionListener(e -> {
            if (timer[0] != null) {
                timer[0].stop();
            }
            gamePanel.removeAll();
            gamePanel.revalidate();
            gamePanel.repaint();
            nextLevel(nextLevel);
        });

        gbc.gridx = 0; // Center horizontally
        gbc.gridy = 3; // Add it to the first row
        gbc.gridwidth = 1; // Take up one column
        gbc.gridheight = 1; // Take up one row
        gbc.weightx = 1; // Allow horizontal expansion
        gbc.weighty = 1; // Allow vertical expansion
        gbc.anchor = GridBagConstraints.SOUTH; // Anchor to the bottom
        gbc.insets = new Insets(10, 10, 100, 10); // Padding around the button
        gamePanel.add(nextButton, gbc);
        nextButton.requestFocusInWindow();

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    @Override
    public void performEnemyAttack(Runnable nextMethod, String enemyName, int enemyDamage) {
        gamePanel.removeAll();
        gamePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gamePanel.setBackground(Color.BLACK);

        if (timer[0] != null) {
            timer[0].stop();
        }

        chosenCharacter.takeDamage(enemyDamage);
        characterHealth = chosenCharacter.getHealth();

        JLabel attackMessage1 = new JLabel(enemyName + " strikes back!");
        attackMessage1.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        attackMessage1.setForeground(Color.WHITE);
        JLabel attackMessage2 = new JLabel(characterName + " takes " + enemyDamage + " damage!");
        attackMessage2.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        attackMessage2.setForeground(Color.WHITE);
        JLabel attackMessage3 = new JLabel("Remaining health: " + characterHealth);
        attackMessage3.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        attackMessage3.setForeground(Color.WHITE);

        JPanel attackPanel = new JPanel(new GridBagLayout());
        attackPanel.setBackground(Color.BLACK);

        gbc.gridx = 0;        // Center horizontally
        gbc.gridy = 0;        // Center vertically
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;      // Allow horizontal expansion
        gbc.weighty = 1;      // Allow vertical expansion
        gbc.anchor = GridBagConstraints.CENTER;  // Center the label
        gbc.insets = new Insets(190, 10, 10, 10); // Padding around the label
        attackPanel.add(attackMessage1, gbc);
        gamePanel.add(attackPanel, gbc);

        gbc.gridx = 0;        // Center horizontally
        gbc.gridy = 1;        // Center vertically
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;      // Allow horizontal expansion
        gbc.weighty = 1;      // Allow vertical expansion
        gbc.anchor = GridBagConstraints.CENTER;  // Center the label
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around the label
        attackPanel.add(attackMessage2, gbc);
        gamePanel.add(attackPanel, gbc);

        gbc.gridx = 0;        // Center horizontally
        gbc.gridy = 2;        // Center vertically
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;      // Allow horizontal expansion
        gbc.weighty = 1;      // Allow vertical expansion
        gbc.anchor = GridBagConstraints.CENTER;  // Center the label
        gbc.insets = new Insets(10, 10, 50, 10); // Padding around the label
        attackPanel.add(attackMessage3, gbc);
        gamePanel.add(attackPanel, gbc);

        nextButton = new JButton(" Press Enter To Continue... ");
        nextButton.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBorder(null);
        nextButton.setFocusable(true);

        nextButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                // This method is called when a key is pressed
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { // For example, Enter key
                    nextMethod.run();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        nextButton.addActionListener(e -> {
            if (timer[0] != null) {
                timer[0].stop();
            }
            gamePanel.removeAll();
            gamePanel.revalidate();
            gamePanel.repaint();
            nextMethod.run();
        });

        gbc.gridx = 0; // Center horizontally
        gbc.gridy = 3; // Add it to the first row
        gbc.gridwidth = 1; // Take up one column
        gbc.gridheight = 1; // Take up one row
        gbc.weightx = 1; // Allow horizontal expansion
        gbc.weighty = 1; // Allow vertical expansion
        gbc.anchor = GridBagConstraints.SOUTH; // Anchor to the bottom
        gbc.insets = new Insets(10, 10, 150, 10); // Padding around the button
        gamePanel.add(nextButton, gbc);
        nextButton.requestFocusInWindow();

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    private void level1Title(String minionName, int minionHealth) {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.BLACK);

        JLabel level1Title = new JLabel(minionName + " are here! Fight back using " + chosenCharacter.getName() + "'s skills!");
        level1Title.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
        level1Title.setForeground(Color.WHITE);
        titlePanel.add(level1Title);

        // GridBagConstraints for positioning the title panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Expand horizontally
        gbc.insets = new Insets(100, 10, 20, 10);  // Add padding around the panel
        gbc.gridx = 0;  // Center horizontally
        gbc.gridy = 0;  // First row (top row)
        gbc.gridwidth = 2;  // Span two columns
        gbc.gridheight = 1; // Take up one row
        gbc.weightx = 1;    // Allow expansion horizontally
        gbc.weighty = 0;    // Do not stretch vertically
        gbc.anchor = GridBagConstraints.CENTER; // Center it in the grid cell
        gamePanel.add(titlePanel, gbc);

        JLabel updateMessage1 = new JLabel("Total Energy: " + energy + "  |  Total Stamina: " + stamina);
        updateMessage1.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        updateMessage1.setForeground(Color.WHITE);
        JLabel updateMessage2 = new JLabel(characterName + "'s Health: " + chosenCharacter.getHealth() + "  |  " + minionName + "' Health: " + minionHealth);
        updateMessage2.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        updateMessage2.setForeground(Color.WHITE);

        JPanel attackPanel = new JPanel(new GridBagLayout());
        attackPanel.setBackground(Color.BLACK);

        GridBagConstraints attackGbc = new GridBagConstraints();
        attackGbc.gridx = 0;
        attackGbc.gridy = 0;
        attackGbc.weightx = 1;
        attackGbc.weighty = 0;
        attackGbc.anchor = GridBagConstraints.CENTER;
        attackGbc.insets = new Insets(5, 5, 5, 5);
        attackPanel.add(updateMessage1, attackGbc);

        attackGbc.gridy = 1;
        attackPanel.add(updateMessage2, attackGbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        gamePanel.add(attackPanel, gbc);
    }

    private void level2Title(String generalName, int generalHealth) {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.BLACK);

        JLabel level2Title = new JLabel(generalName + " is attacking! Fight back using " + chosenCharacter.getName() + "'s skills!");
        level2Title.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
        level2Title.setForeground(Color.WHITE);
        titlePanel.add(level2Title);

        // GridBagConstraints for positioning the title panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Expand horizontally
        gbc.insets = new Insets(100, 30, 10, 30);  // Add padding around the panel
        gbc.gridx = 0;  // Center horizontally
        gbc.gridy = 0;  // First row (top row)
        gbc.gridwidth = 2;  // Span two columns
        gbc.gridheight = 1; // Take up one row
        gbc.weightx = 1;    // Allow expansion horizontally
        gbc.weighty = 0;    // Do not stretch vertically
        gbc.anchor = GridBagConstraints.CENTER; // Center it in the grid cell
        gamePanel.add(titlePanel, gbc);

        JLabel updateMessage1 = new JLabel("Total Energy: " + energy + "  |  Total Stamina: " + stamina);
        updateMessage1.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        updateMessage1.setForeground(Color.WHITE);
        JLabel updateMessage2 = new JLabel(characterName + "'s Health: " + chosenCharacter.getHealth() + "  |  " + generalName + "'s Health: " + generalHealth);
        updateMessage2.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        updateMessage2.setForeground(Color.WHITE);

        JPanel attackPanel = new JPanel(new GridBagLayout());
        attackPanel.setBackground(Color.BLACK);

        GridBagConstraints attackGbc = new GridBagConstraints();
        attackGbc.gridx = 0;
        attackGbc.gridy = 0;
        attackGbc.weightx = 1;
        attackGbc.weighty = 0;
        attackGbc.anchor = GridBagConstraints.CENTER;
        attackGbc.insets = new Insets(5, 5, 5, 5);
        attackPanel.add(updateMessage1, attackGbc);

        attackGbc.gridy = 1;
        attackPanel.add(updateMessage2, attackGbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        gamePanel.add(attackPanel, gbc);
    }

    private void level3Title(String bossName, int bossHealth) {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.BLACK);

        JLabel level2Title = new JLabel(bossName + " is in your way! Fight back using " + chosenCharacter.getName() + "'s skills!");
        level2Title.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
        level2Title.setForeground(Color.WHITE);
        titlePanel.add(level2Title);

        // GridBagConstraints for positioning the title panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Expand horizontally
        gbc.insets = new Insets(100, 10, 10, 10);  // Add padding around the panel
        gbc.gridx = 0;  // Center horizontally
        gbc.gridy = 0;  // First row (top row)
        gbc.gridwidth = 2;  // Span two columns
        gbc.gridheight = 1; // Take up one row
        gbc.weightx = 1;    // Allow expansion horizontally
        gbc.weighty = 0;    // Do not stretch vertically
        gbc.anchor = GridBagConstraints.CENTER; // Center it in the grid cell
        gamePanel.add(titlePanel, gbc);

        JLabel updateMessage1 = new JLabel("Total Energy: " + energy + "  |  Total Stamina: " + stamina);
        updateMessage1.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        updateMessage1.setForeground(Color.WHITE);
        JLabel updateMessage2 = new JLabel(characterName + "'s Health: " + chosenCharacter.getHealth() + "  |  " + bossName + "'s Health: " + bossHealth);
        updateMessage2.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        updateMessage2.setForeground(Color.WHITE);

        JPanel attackPanel = new JPanel(new GridBagLayout());
        attackPanel.setBackground(Color.BLACK);

        GridBagConstraints attackGbc = new GridBagConstraints();
        attackGbc.gridx = 0;
        attackGbc.gridy = 0;
        attackGbc.weightx = 1;
        attackGbc.weighty = 0;
        attackGbc.anchor = GridBagConstraints.CENTER;
        attackGbc.insets = new Insets(5, 5, 5, 5);
        attackPanel.add(updateMessage1, attackGbc);

        attackGbc.gridy = 1;
        attackPanel.add(updateMessage2, attackGbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        gamePanel.add(attackPanel, gbc);
    }

    protected void displayOpeningDialogue(NihraDialogue dialogue){
        gamePanel.removeAll();
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(Color.BLACK);

        dialoguePane = new JTextPane();
        dialoguePane.setEditable(false);
        dialoguePane.setForeground(Color.WHITE);
        dialoguePane.setBackground(Color.BLACK);
        dialoguePane.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        dialoguePane.setBorder(null);

        StyledDocument doc = dialoguePane.getStyledDocument();
        SimpleAttributeSet justified = new SimpleAttributeSet();
        StyleConstants.setAlignment(justified, StyleConstants.ALIGN_JUSTIFIED);
        doc.setParagraphAttributes(0, doc.getLength(), justified, false);
        dialoguePane.setBorder(BorderFactory.createEmptyBorder(220, 140, 20, 130));

        gamePanel.add(dialoguePane, BorderLayout.CENTER);

        nextButton = new JButton(" NEXT ");
        nextButton.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBorder(new LineBorder(Color.WHITE, 1));

        ActionListener actionListener = new ActionListener() {
            private boolean isBlackScreen = false;
            private int globalIndex = 0;
            private int charIndex = 0;
            private final StringBuilder currentLineBuilder = new StringBuilder();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isBlackScreen) {
                    if (globalIndex < dialogue.getNihraOpeningDialogue().length) {
                        dialoguePane.setText("");
                        isBlackScreen = false;
                        charIndex = 0;
                        currentLineBuilder.setLength(0);
                        globalIndex++;
                    }
                } else {
                    if (globalIndex < dialogue.getNihraOpeningDialogue().length) {
                        String currentLine = dialogue.getNihraOpeningDialogue()[globalIndex].dialogue();
                        if (charIndex < currentLine.length()) {
                            currentLineBuilder.append(currentLine.charAt(charIndex));
                            dialoguePane.setText(currentLineBuilder.toString());
                            charIndex++;
                        } else {
                            isBlackScreen = true;
                        }
                    } else {
                        timer[0].stop();
                        gamePanel.removeAll();
                        gamePanel.revalidate();
                        gamePanel.repaint();
                        gamePlayLevel1();
                    }
                }
            }
        };

        timer[0] = new Timer(0, actionListener);
        timer[0].start();

        nextButton.addActionListener(e -> {
            if (timer[0] != null) {
                timer[0].stop();
            }
            gamePanel.removeAll();
            gamePanel.revalidate();
            gamePanel.repaint();
            gamePlayLevel1();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(nextButton, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    protected void displayDialogue1(NihraDialogue dialogue){
        gamePanel.removeAll();
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(Color.BLACK);

        dialoguePane = new JTextPane();
        dialoguePane.setEditable(false);
        dialoguePane.setForeground(Color.WHITE);
        dialoguePane.setBackground(Color.BLACK);
        dialoguePane.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        dialoguePane.setBorder(null);

        StyledDocument doc = dialoguePane.getStyledDocument();
        SimpleAttributeSet justified = new SimpleAttributeSet();
        StyleConstants.setAlignment(justified, StyleConstants.ALIGN_JUSTIFIED);
        doc.setParagraphAttributes(0, doc.getLength(), justified, false);
        dialoguePane.setBorder(BorderFactory.createEmptyBorder(220, 140, 20, 130));

        gamePanel.add(dialoguePane, BorderLayout.CENTER);

        nextButton = new JButton(" NEXT ");
        nextButton.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBorder(new LineBorder(Color.WHITE, 1));

        ActionListener actionListener = new ActionListener() {
            private boolean isBlackScreen = false;
            private int globalIndex = 0;
            private int charIndex = 0;
            private final StringBuilder currentLineBuilder = new StringBuilder();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isBlackScreen) {
                    if (globalIndex < dialogue.getNihralevel1Dialogue().length) {
                        dialoguePane.setText("");
                        isBlackScreen = false;
                        charIndex = 0;
                        currentLineBuilder.setLength(0);
                        globalIndex++;
                    }
                } else {
                    if (globalIndex < dialogue.getNihralevel1Dialogue().length) {
                        String currentLine = dialogue.getNihralevel1Dialogue()[globalIndex].dialogue();
                        if (charIndex < currentLine.length()) {
                            currentLineBuilder.append(currentLine.charAt(charIndex));
                            dialoguePane.setText(currentLineBuilder.toString());
                            charIndex++;
                        } else {
                            isBlackScreen = true;
                        }
                    } else {
                        timer[0].stop();
                        gamePanel.removeAll();
                        gamePanel.revalidate();
                        gamePanel.repaint();
                        gamePlayLevel2();
                    }
                }
            }
        };

        timer[0] = new Timer(0, actionListener);
        timer[0].start();

        nextButton.addActionListener(e -> {
            if (timer[0] != null) {
                timer[0].stop();
            }
            gamePanel.removeAll();
            gamePanel.revalidate();
            gamePanel.repaint();
            gamePlayLevel2();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(nextButton, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    protected void displayDialogue2(NihraDialogue dialogue){
        gamePanel.removeAll();
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(Color.BLACK);

        dialoguePane = new JTextPane();
        dialoguePane.setEditable(false);
        dialoguePane.setForeground(Color.WHITE);
        dialoguePane.setBackground(Color.BLACK);
        dialoguePane.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        dialoguePane.setBorder(null);

        StyledDocument doc = dialoguePane.getStyledDocument();
        SimpleAttributeSet justified = new SimpleAttributeSet();
        StyleConstants.setAlignment(justified, StyleConstants.ALIGN_JUSTIFIED);
        doc.setParagraphAttributes(0, doc.getLength(), justified, false);
        dialoguePane.setBorder(BorderFactory.createEmptyBorder(220, 140, 20, 130));

        gamePanel.add(dialoguePane, BorderLayout.CENTER);

        nextButton = new JButton(" NEXT ");
        nextButton.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBorder(new LineBorder(Color.WHITE, 1));

        ActionListener actionListener = new ActionListener() {
            private boolean isBlackScreen = false;
            private int globalIndex = 0;
            private int charIndex = 0;
            private final StringBuilder currentLineBuilder = new StringBuilder();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isBlackScreen) {
                    if (globalIndex < dialogue.getNihralevel2Dialogue().length) {
                        dialoguePane.setText("");
                        isBlackScreen = false;
                        charIndex = 0;
                        currentLineBuilder.setLength(0);
                        globalIndex++;
                    }
                } else {
                    if (globalIndex < dialogue.getNihralevel2Dialogue().length) {
                        String currentLine = dialogue.getNihralevel2Dialogue()[globalIndex].dialogue();
                        if (charIndex < currentLine.length()) {
                            currentLineBuilder.append(currentLine.charAt(charIndex));
                            dialoguePane.setText(currentLineBuilder.toString());
                            charIndex++;
                        } else {
                            isBlackScreen = true;
                        }
                    } else {
                        timer[0].stop();
                        gamePanel.removeAll();
                        gamePanel.revalidate();
                        gamePanel.repaint();
                        gamePlayLevel3();
                    }
                }
            }
        };

        timer[0] = new Timer(0, actionListener);
        timer[0].start();

        nextButton.addActionListener(e -> {
            if (timer[0] != null) {
                timer[0].stop();
            }
            gamePanel.removeAll();
            gamePanel.revalidate();
            gamePanel.repaint();
            gamePlayLevel3();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(nextButton, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    protected void displayDialogue3(NihraDialogue dialogue){
        gamePanel.removeAll();
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(Color.BLACK);

        dialoguePane = new JTextPane();
        dialoguePane.setEditable(false);
        dialoguePane.setForeground(Color.WHITE);
        dialoguePane.setBackground(Color.BLACK);
        dialoguePane.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        dialoguePane.setBorder(null);

        StyledDocument doc = dialoguePane.getStyledDocument();
        SimpleAttributeSet justified = new SimpleAttributeSet();
        StyleConstants.setAlignment(justified, StyleConstants.ALIGN_JUSTIFIED);
        doc.setParagraphAttributes(0, doc.getLength(), justified, false);
        dialoguePane.setBorder(BorderFactory.createEmptyBorder(220, 140, 20, 130));

        gamePanel.add(dialoguePane, BorderLayout.CENTER);

        nextButton = new JButton(" NEXT ");
        nextButton.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBorder(new LineBorder(Color.WHITE, 1));

        ActionListener actionListener = new ActionListener() {
            private boolean isBlackScreen = false;
            private int globalIndex = 0;
            private int charIndex = 0;
            private final StringBuilder currentLineBuilder = new StringBuilder();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isBlackScreen) {
                    if (globalIndex < dialogue.getNihralevel3Dialogue().length) {
                        dialoguePane.setText("");
                        isBlackScreen = false;
                        charIndex = 0;
                        currentLineBuilder.setLength(0);
                        globalIndex++;
                    }
                } else {
                    if (globalIndex < dialogue.getNihralevel3Dialogue().length) {
                        String currentLine = dialogue.getNihralevel3Dialogue()[globalIndex].dialogue();
                        if (charIndex < currentLine.length()) {
                            currentLineBuilder.append(currentLine.charAt(charIndex));
                            dialoguePane.setText(currentLineBuilder.toString());
                            charIndex++;
                        } else {
                            isBlackScreen = true;
                            if (globalIndex == dialogue.getNihralevel3Dialogue().length - 1){
                                timer[0].stop();
                                gamePanel.revalidate();
                                gamePanel.repaint();

                                Inventory inventory = new Inventory();
                                inventory.addItem("Nihra Galaxy Chip");
                            }
                        }
                    } else {
                        timer[0].stop();
                        gamePanel.removeAll();
                        gamePanel.revalidate();
                        gamePanel.repaint();
                        displayWorldComplete();
                    }
                }
            }
        };

        timer[0] = new Timer(0, actionListener);
        timer[0].start();

        nextButton.addActionListener(e -> {
            if (timer[0] != null) {
                timer[0].stop();
            }
            gamePanel.removeAll();
            gamePanel.revalidate();
            gamePanel.repaint();
            displayWorldComplete();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(nextButton, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    @Override
    public void displayEnemyDefeat(String enemyName, int timeCrystals, int damageUpgrade, Runnable nextLevel) {
        gamePanel.removeAll();
        gamePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gamePanel.setBackground(Color.BLACK);

        if (timer[0] != null) {
            timer[0].stop();
        }

        JLabel defeatedMessage1 = new JLabel(enemyName + " defeated!");
        defeatedMessage1.setFont(new Font("OCR A Extended", Font.PLAIN, 30));
        defeatedMessage1.setForeground(Color.WHITE);
        JLabel defeatedMessage2 = new JLabel("You have gained skill damage of " + damageUpgrade + "!");
        defeatedMessage2.setFont(new Font("OCR A Extended", Font.PLAIN, 30));
        defeatedMessage2.setForeground(Color.WHITE);
        JLabel defeatedMessage3 = new JLabel("You have gained " + timeCrystals + " Time Crystals!");
        defeatedMessage3.setFont(new Font("OCR A Extended", Font.PLAIN, 30));
        defeatedMessage3.setForeground(Color.WHITE);

        JPanel attackPanel = new JPanel(new GridBagLayout());
        attackPanel.setBackground(Color.BLACK);

        gbc.gridx = 0;        // Center horizontally
        gbc.gridy = 0;        // Center vertically
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;      // Allow horizontal expansion
        gbc.weighty = 1;      // Allow vertical expansion
        gbc.anchor = GridBagConstraints.CENTER;  // Center the label
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around the label
        attackPanel.add(defeatedMessage1, gbc);
        gamePanel.add(attackPanel, gbc);

        gbc.gridx = 0;        // Center horizontally
        gbc.gridy = 1;        // Center vertically
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;      // Allow horizontal expansion
        gbc.weighty = 1;      // Allow vertical expansion
        gbc.anchor = GridBagConstraints.CENTER;  // Center the label
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around the label
        attackPanel.add(defeatedMessage2, gbc);
        gamePanel.add(attackPanel, gbc);

        gbc.gridx = 0;        // Center horizontally
        gbc.gridy = 2;        // Center vertically
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;      // Allow horizontal expansion
        gbc.weighty = 1;      // Allow vertical expansion
        gbc.anchor = GridBagConstraints.CENTER;  // Center the label
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around the label
        attackPanel.add(defeatedMessage3, gbc);
        gamePanel.add(attackPanel, gbc);

        nextButton = new JButton(" Press Enter To Continue... ");
        nextButton.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBorder(null);
        nextButton.setFocusable(true);

        nextButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                // This method is called when a key is pressed
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { // For example, Enter key
                    nextLevel.run();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        nextButton.addActionListener(e -> {
            if (timer[0] != null) {
                timer[0].stop();
            }

            gamePanel.removeAll();
            gamePanel.revalidate();
            gamePanel.repaint();
            nextLevel.run();
        });

        gbc.gridx = 0; // Center horizontally
        gbc.gridy = 2; // Add it to the first row
        gbc.gridwidth = 1; // Take up one column
        gbc.gridheight = 1; // Take up one row
        gbc.weightx = 1; // Allow horizontal expansion
        gbc.weighty = 1; // Allow vertical expansion
        gbc.anchor = GridBagConstraints.SOUTH; // Anchor to the bottom
        gbc.insets = new Insets(10, 10, 100, 10); // Padding around the button
        gamePanel.add(nextButton, gbc);
        nextButton.requestFocusInWindow();

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    @Override
    public void displayWorldComplete() {
        gamePanel.removeAll();
        gamePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gamePanel.setBackground(Color.BLACK);

        if (timer[0] != null) {
            timer[0].stop();
        }

        JLabel worldCompleted = new JLabel("ALL NIHRA GALAXY LEVELS COMPLETED!");
        worldCompleted.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
        worldCompleted.setForeground(Color.WHITE);
        Color originalColor = worldCompleted.getForeground();
        Color blinkColor = Color.GREEN;

        Timer blinkTimer = new Timer(300, new ActionListener() {
            private boolean isOriginalColor = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                worldCompleted.setForeground(isOriginalColor ? blinkColor : originalColor);
                isOriginalColor = !isOriginalColor;
            }
        });
        blinkTimer.start();

        JPanel attackPanel = new JPanel(new GridBagLayout());
        attackPanel.setBackground(Color.BLACK);

        gbc.gridx = 0;        // Center horizontally
        gbc.gridy = 0;        // Center vertically
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;      // Allow horizontal expansion
        gbc.weighty = 1;      // Allow vertical expansion
        gbc.anchor = GridBagConstraints.CENTER;  // Center the label
        gbc.insets = new Insets(100, 10, 10, 10); // Padding around the label
        attackPanel.add(worldCompleted, gbc);
        gamePanel.add(attackPanel, gbc);

        nextButton = new JButton(" Press Enter To Continue... ");
        nextButton.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBorder(null);
        nextButton.setFocusable(true);

        nextButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { // For example, Enter key
                    completedWorlds.add("Nihra Galaxy");
                    chooseWorld(completedWorlds);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        nextButton.addActionListener(e -> {
            if (timer[0] != null) {
                timer[0].stop();
            }
            gamePanel.removeAll();
            gamePanel.revalidate();
            gamePanel.repaint();
//            nextLevel.run();
        });

        gbc.gridx = 0; // Center horizontally
        gbc.gridy = 2; // Add it to the first row
        gbc.gridwidth = 1; // Take up one column
        gbc.gridheight = 1; // Take up one row
        gbc.weightx = 1; // Allow horizontal expansion
        gbc.weighty = 1; // Allow vertical expansion
        gbc.anchor = GridBagConstraints.SOUTH; // Anchor to the bottom
        gbc.insets = new Insets(10, 10, 100, 10); // Padding around the button
        gamePanel.add(nextButton, gbc);
        nextButton.requestFocusInWindow();

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    @Override
    public void gameOver() {
        gamePanel.removeAll();
        gamePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gamePanel.setBackground(Color.BLACK);

        if (timer[0] != null) {
            timer[0].stop();
        }

        JLabel worldCompleted = new JLabel("GAME OVER!");
        worldCompleted.setFont(new Font("OCR A Extended", Font.PLAIN, 40));
        worldCompleted.setForeground(Color.WHITE);
        Color originalColor = worldCompleted.getForeground();
        Color blinkColor = Color.RED;

        Timer blinkTimer = new Timer(300, new ActionListener() {
            private boolean isOriginalColor = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                worldCompleted.setForeground(isOriginalColor ? blinkColor : originalColor);
                isOriginalColor = !isOriginalColor;
            }
        });
        blinkTimer.start();

        JPanel attackPanel = new JPanel(new GridBagLayout());
        attackPanel.setBackground(Color.BLACK);

        gbc.gridx = 0;        // Center horizontally
        gbc.gridy = 0;        // Center vertically
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;      // Allow horizontal expansion
        gbc.weighty = 1;      // Allow vertical expansion
        gbc.anchor = GridBagConstraints.CENTER;  // Center the label
        gbc.insets = new Insets(100, 10, 10, 10); // Padding around the label
        attackPanel.add(worldCompleted, gbc);
        gamePanel.add(attackPanel, gbc);

        nextButton = new JButton(" Press Enter To Continue... ");
        nextButton.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBorder(null);
        nextButton.setFocusable(true);

        nextButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    NihraGalaxyGamePlay.this.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        nextButton.addActionListener(e -> {
            if (timer[0] != null) {
                timer[0].stop();
            }
            gamePanel.removeAll();
            gamePanel.revalidate();
            gamePanel.repaint();
            NihraGalaxyGamePlay.this.dispose();
        });

        gbc.gridx = 0; // Center horizontally
        gbc.gridy = 2; // Add it to the first row
        gbc.gridwidth = 1; // Take up one column
        gbc.gridheight = 1; // Take up one row
        gbc.weightx = 1; // Allow horizontal expansion
        gbc.weighty = 1; // Allow vertical expansion
        gbc.anchor = GridBagConstraints.SOUTH; // Anchor to the bottom
        gbc.insets = new Insets(10, 10, 100, 10); // Padding around the button
        gamePanel.add(nextButton, gbc);
        nextButton.requestFocusInWindow();

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    public void nextLevel(Runnable nextLevel){
        nextLevel.run();
    }

    public int getTotalTimeCrystals(){
        return totalTimeCrystals;
    }
}
