package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Inventory extends GameFrame {
    private JList<String> itemList;
    private DefaultListModel<String> itemListModel;
    private JButton useButton;
    private JButton discardButton;
    private final ArrayList<String> items;
    private static Inventory instance;
    private String selectedItem;
    Shop shop;

    public Inventory() {
        items = new ArrayList<>();
        initializeInventoryUI();
        shop = new Shop(NihraGalaxyGamePlay.GameManager, new NihraGalaxyGamePlay(getChosenCharacter()).totalTimeCrystals);// Initialize but do not show yet
        setupButtons();            // Set up the buttons separately
        setVisible(false);
    }

    private void initializeInventoryUI() {
        setTitle("Inventory");
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

        useButton = new JButton("Use Item");
        discardButton = new JButton("Discard Item");

        styleButton(useButton);
        styleButton(discardButton);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10)); // Horizontal buttons with spacing
        buttonPanel.setBackground(new Color(40, 40, 40)); // Same dark gray background as the frame
        buttonPanel.add(useButton);
        buttonPanel.add(discardButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(40, 40, 40));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setLocationRelativeTo(null); // Centers the window
        setVisible(false); // Hide it initially
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

    private void setupButtons() {
        useButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedItem = itemList.getSelectedValue();
                if (selectedItem != null) {
                    JOptionPane.showMessageDialog(Inventory.this, "Used: " + selectedItem, "Item Used", JOptionPane.INFORMATION_MESSAGE);
                    itemListModel.removeElement(selectedItem);
                    shop.getUseItem(selectedItem);
                } else {
                    JOptionPane.showMessageDialog(Inventory.this, "No item selected!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        discardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedItem = itemList.getSelectedValue();
                if (selectedItem != null) {
                    JOptionPane.showMessageDialog(Inventory.this, "Discarded: " + selectedItem, "Item Discarded", JOptionPane.WARNING_MESSAGE);
                    itemListModel.removeElement(selectedItem);
                } else {
                    JOptionPane.showMessageDialog(Inventory.this, "No item selected!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void showInventoryItems() {
        itemListModel.clear();
        for (String item : items) {
            itemListModel.addElement(item);
        }
        setVisible(true);
    }

    public void addItem(String item) {
        items.add(item);
        itemListModel.addElement(item);
        itemList.revalidate();
        itemList.repaint();
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public static Inventory getInstance() {
        if (instance == null) {
            instance = new Inventory();
        }
        return instance;
    }

    public String getSelectedItem(){
        return selectedItem;
    }
}
