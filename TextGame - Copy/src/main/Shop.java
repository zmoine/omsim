package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Shop extends GameFrame {
    private final JList<String> itemList;
    private final DefaultListModel<String> itemListModel;
    private final ArrayList<String> shopItems;
    private int playerTimeCrystals;
    private JPanel buttonPanel;
    private final JPanel itemPanel;
    private static Shop instance;
    Inventory inventory;
    private final String item1Name, item2Name, item3Name, item4Name, item5Name;
    private final int item1UseItem, item2UseItem, item3UseItem, item4UseItem, item5UseItem;
    private final int item1Price, item2Price, item3Price, item4Price, item5Price;
    private NihraGalaxyGamePlay.GameManager gameManager;

    public Shop(NihraGalaxyGamePlay.GameManager gameManager, int timeCrystals) {
        this.gameManager = gameManager;
        this.playerTimeCrystals = timeCrystals;
        shopItems = new ArrayList<>();

        setTitle("AI Terminal");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(40, 40, 40));

        itemListModel = new DefaultListModel<>();
        itemList = new JList<>(itemListModel);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setBackground(new Color(60, 60, 60));
        itemList.setForeground(Color.WHITE);
        itemList.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(itemList);

        inventory = Inventory.getInstance();

        // Hide the components initially
        itemPanel = new JPanel();
        itemPanel.setLayout(new BorderLayout());
        itemPanel.setBackground(new Color(40, 40, 40));
        itemPanel.add(scrollPane, BorderLayout.CENTER);
        itemPanel.setVisible(false); // Hide initially

        setupButtons();
        createLayout();

        item1Name = new TimeStabilizer().getItemName();
        item2Name = new QuantumElixir().getItemName();
        item3Name = new PlasmaBurst().getItemName();
        item4Name = new IonBlade().getItemName();
        item5Name = new NeutroniumArmor().getItemName();
        item1UseItem = new TimeStabilizer().getUseItem();
        item2UseItem = new QuantumElixir().getUseItem();
        item3UseItem = new PlasmaBurst().getUseItem();
        item4UseItem = new IonBlade().getUseItem();
        item5UseItem = new NeutroniumArmor().getUseItem();
        item1Price = new TimeStabilizer().getItemPrice();
        item2Price = new QuantumElixir().getItemPrice();
        item3Price = new PlasmaBurst().getItemPrice();
        item4Price = new IonBlade().getItemPrice();
        item5Price = new NeutroniumArmor().getItemPrice();

        initializeShopItems();

        setLocationRelativeTo(null);
        setVisible(false);
    }

    public Shop getInstance(Character chosenCharacter) {
        if (instance == null) {
            int crystals = new NihraGalaxyGamePlay(chosenCharacter).getTotalTimeCrystals();
            instance = new Shop(gameManager, getPlayerTimeCrystals());
        }
        return instance;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(100, 100, 255)); // Light blue color for buttons
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // White border for buttons
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand on hover
    }

    private void initializeShopItems() {
        shopItems.add(item1Name);
        shopItems.add(item2Name);
        shopItems.add(item3Name);
        shopItems.add(item4Name);
        shopItems.add(item5Name);

        for (String item : shopItems) {
            itemListModel.addElement(item);
        }
    }

    public int getUseItem(String itemName) {
        return switch (itemName) {
            case "Time Stabilizer (Health Potion)" -> item1UseItem;
            case "Quantum Elixir (Energy Potion)" -> item2UseItem;
            case "Plasma Burst (Fireball Spell)" -> item3UseItem;
            case "Ion Blade (Iron Sword)" -> item4UseItem;
            case "Neutronium Armor (Energy Beam & Skip Enemy Next Turn)" -> item5UseItem;
            default -> 0;
        };
    }

    private int getItemPrice(String itemName) {
        return switch (itemName) {
            case "Time Stabilizer (Health Potion)" -> item1Price;
            case "Quantum Elixir (Energy Potion)" -> item2Price;
            case "Plasma Burst (Fireball Spell)" -> item3Price;
            case "Ion Blade (Iron Sword)" -> item4Price;
            case "Neutronium Armor (Energy Beam & Skip Enemy Next Turn)" -> item5Price;
            default -> 0;
        };
    }

    private void setupButtons() {
        JButton buyButton = new JButton("Buy Item");
        JButton closeButton = new JButton("Close Shop");

        styleButton(buyButton);
        styleButton(closeButton);

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = itemList.getSelectedValue();
                if (selectedItem != null && !selectedItem.isEmpty()) {
                    int itemPrice = getItemPrice(selectedItem);
                    if (playerTimeCrystals >= itemPrice) {
                        playerTimeCrystals -= itemPrice;
                        inventory.addItem(selectedItem);
                        JOptionPane.showMessageDialog(Shop.this, "Bought: " + selectedItem + "\nRemaining Time Crystals: " + playerTimeCrystals, "Purchase Successful", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(Shop.this, "Not enough Time Crystals!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(Shop.this, "No item selected!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBackground(new Color(40, 40, 40));
        buttonPanel.add(buyButton);
        buttonPanel.add(closeButton);
    }

    private void createLayout() {
        // Add components to main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(40, 40, 40));

        // Initially show only the button panel
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add itemPanel (with items) when the "Buy Item" button is clicked
        mainPanel.add(itemPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    public void showShopItems() {
        // Show the shop items when called (e.g., after clicking an "Open Shop" button)
        itemPanel.setVisible(true); // Show the list of items
    }

    public int getPlayerTimeCrystals() {
        return playerTimeCrystals;
    }
}


