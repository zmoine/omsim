package main;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class GameFrame extends JFrame{
    protected final JPanel gamePanel;
    protected JTextPane dialoguePane;
    private JButton backButton;
    protected JButton nextButton;
    protected Timer[] timer = new Timer[1];
    public GameFrame(){
        gamePanel = new JPanel();

        setContentPane(gamePanel);
        setTitle("RPG Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setBackground(Color.BLACK);

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/icon.jpg")));
        setIconImage(icon.getImage());
    }

    public void startGame(){
        gamePanel.removeAll();
        gamePanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gamePanel.setBackground(Color.BLACK);

        JLabel gameTitle = new JLabel("<html><div style='text-align: center;'>THE ECHOES<br>OF<br>THE ANALOG</div></html>");
        gameTitle.setFont(new Font("OCR A Extended", Font.PLAIN, 80));
        gameTitle.setForeground(Color.WHITE);

        JButton startGameButton = new JButton(" START GAME ");
        startGameButton.setFont(new Font("OCR A Extended", Font.PLAIN, 30));
        startGameButton.setForeground(Color.WHITE);
        startGameButton.setBackground(Color.BLACK);
        startGameButton.setBorder(new LineBorder(Color.WHITE, 1));
        Color originalColor = startGameButton.getForeground();
        Color blinkColor = Color.GREEN;

        Timer timer = new Timer(300, new ActionListener() {
            private boolean isOriginalColor = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                startGameButton.setForeground(isOriginalColor ? blinkColor : originalColor);
                isOriginalColor = !isOriginalColor;
            }
        });
        timer.start();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        gamePanel.add(gameTitle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 5, 5, 5);
        gamePanel.add(startGameButton, gbc);

        startGameButton.addActionListener(e -> {
            timer.stop();
            gamePanel.removeAll();
            gamePanel.revalidate();
            gamePanel.repaint();
            dialogue(new Opening());
        });

        gamePanel.revalidate();
        gamePanel.repaint();
        setVisible(true);
    }

    protected void dialogue(Opening opening) {
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

        backButton = new JButton(" BACK ");
        backButton.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(new LineBorder(Color.WHITE, 1));

        nextButton = new JButton(" NEXT ");
        nextButton.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBorder(new LineBorder(Color.WHITE, 1));

        ActionListener actionListener = new ActionListener() {
            private boolean isBlackScreen = false;
            private int globalIndex = 0;
            private int showCount;
            private int charIndex = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isBlackScreen) {
                    if (globalIndex < opening.getOpening().length) {
                        dialoguePane.setText("");
                        isBlackScreen = false;
                        showCount = 0;
                        globalIndex++;
                    }
                } else {
                    if (globalIndex < opening.getOpening().length) {
                        String currentLine = opening.getOpening()[globalIndex].dialogue();
                        if (charIndex < currentLine.length()) {
                            try {
                                dialoguePane.getStyledDocument().insertString(dialoguePane.getStyledDocument().getLength(), String.valueOf(currentLine.charAt(charIndex)), null);
                            } catch (BadLocationException ex) {
                                ex.printStackTrace();
                            }
                            charIndex++;
                        } else {
                            charIndex = 0;
                            showCount++;
                            if (globalIndex <= opening.getOpening().length - 1 && showCount >= 1) {
                                isBlackScreen = true;
                            }
                            if (globalIndex == opening.getOpening().length - 1){
                                timer[0].stop();
                                gamePanel.removeAll();
                                gamePanel.revalidate();
                                gamePanel.repaint();
                                galaxyImages();
                            }
                        }
                    } else {
                        timer[0].stop();
                    }
                }
            }
        };

        timer[0] = new Timer(0, actionListener);
        timer[0].start();

        backButton.addActionListener(e -> {
            if (timer[0] != null) {
                timer[0].stop();
            }
            gamePanel.removeAll();
            gamePanel.revalidate();
            gamePanel.repaint();
            startGame();
        });

        nextButton.addActionListener(e -> {
            if (timer[0] != null) {
                timer[0].stop();
            }
            gamePanel.removeAll();
            gamePanel.revalidate();
            gamePanel.repaint();
            galaxyImages();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(backButton, BorderLayout.WEST);
        buttonPanel.add(nextButton, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        gamePanel.revalidate();
        gamePanel.repaint();
        setVisible(true);
    }

    private boolean characterChosen = false;
    protected void galaxyImages() {
        gamePanel.removeAll();
        gamePanel.setBackground(Color.BLACK);
        gamePanel.setLayout(new BorderLayout());

        ImageIcon galaxy1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/galaxy1.gif")));
        ImageIcon galaxy2 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/galaxy2.gif")));
        ImageIcon galaxy3 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/galaxy3.gif")));

        JLabel gifLabel = new JLabel();
        gifLabel.setHorizontalAlignment(JLabel.CENTER);
        gamePanel.add(gifLabel, BorderLayout.CENTER);
        gifLabel.setIcon(galaxy1);
        gamePanel.revalidate();
        gamePanel.repaint();

        backButton = new JButton(" BACK ");
        backButton.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(new LineBorder(Color.WHITE, 1));
        backButton.addActionListener(e -> {
            gamePanel.removeAll();
            gamePanel.revalidate();
            gamePanel.repaint();
            dialogue(new Opening());
        });

        nextButton = new JButton(" NEXT ");
        nextButton.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBorder(new LineBorder(Color.WHITE, 1));
        nextButton.addActionListener(e -> {
            if (!characterChosen) {
                characterChosen = true;
                gamePanel.removeAll();
                gamePanel.revalidate();
                gamePanel.repaint();
                chooseCharacter();
            }
            chooseCharacter();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(backButton, BorderLayout.WEST);
        buttonPanel.add(nextButton, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        Timer gifTimer = new Timer(3000, new ActionListener() {
            private int gifIndex = 1;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (gifIndex == 1) {
                    gifLabel.setIcon(galaxy2);
                    gifIndex++;
                } else if (gifIndex == 2) {
                    gifLabel.setIcon(galaxy3);
                    gifIndex++;
                } else {
                    ((Timer) e.getSource()).stop();
                    if (!characterChosen) {
                        chooseCharacter();
                    }
                }
                gamePanel.revalidate();
                gamePanel.repaint();
            }
        });

        gifTimer.setRepeats(true);
        gifTimer.start();
        setVisible(true);
    }

    Character selectedCharacter = getChosenCharacter();
    int index;
    private JRadioButton char1, char2, char3, char4, char5;
    ArrayList<String> completedWorlds = new ArrayList<>();
    protected void chooseCharacter(){
        gamePanel.removeAll();
        gamePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gamePanel.setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("CHOOSE A CHARACTER");
        titleLabel.setFont(new Font("OCR A Extended", Font.PLAIN, 50));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(100, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        gamePanel.add(titleLabel, gbc);

        char1 = new JRadioButton("  Cipher - Tech Specialist  ");
        char2 = new JRadioButton("  Opal - Scout  ");
        char3 = new JRadioButton("  Pulse - Energy Manipulator  ");
        char4 = new JRadioButton("  Mirage - Illusionist  ");
        char5 = new JRadioButton("  Zero - Time Warden  ");

        JRadioButton[] radioButtons = {char1, char2, char3, char4, char5};
        ButtonGroup group = new ButtonGroup();
        for (JRadioButton button : radioButtons) {
            group.add(button);
            button.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
            button.setForeground(Color.WHITE);
            button.setBackground(Color.BLACK);
            button.setBorder(new LineBorder(Color.BLACK, 1));
        }

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10,100,0,0);
        gbc.anchor = GridBagConstraints.LINE_START;
        gamePanel.add(char1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10,100,0,0);
        gbc.anchor = GridBagConstraints.LINE_START;
        gamePanel.add(char2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10,100,0,0);
        gbc.anchor = GridBagConstraints.LINE_START;
        gamePanel.add(char3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(10,100,0,0);
        gbc.anchor = GridBagConstraints.LINE_START;
        gamePanel.add(char4, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(10,100,10,0);
        gbc.anchor = GridBagConstraints.LINE_START;
        gamePanel.add(char5, gbc);

        JButton saveAndStartButton = new JButton(" Save And Start ");
        saveAndStartButton.setFont(new Font("OCR A Extended", Font.PLAIN, 30));
        saveAndStartButton.setForeground(Color.WHITE);
        saveAndStartButton.setBackground(Color.BLACK);
        saveAndStartButton.setBorder(new LineBorder(Color.WHITE, 1));

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(-90, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        gamePanel.add(saveAndStartButton, gbc);

        saveAndStartButton.addActionListener(e -> {
            if (char1.isSelected()) {
                index = 0;
            } else if (char2.isSelected()) {
                index = 1;
            } else if (char3.isSelected()) {
                index = 2;
            } else if (char4.isSelected()) {
                index = 3;
            } else if (char5.isSelected()) {
                index = 4;
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Please select a character to know about the past!");
            }
            selectedCharacter = getChosenCharacter();

            int option = JOptionPane.showConfirmDialog(
                    gamePanel,
                    "Are you sure about " + getChosenCharacter().getName() + "?",
                    "Character Selection",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                chooseWorld(completedWorlds);
            } else {
                chooseCharacter();
            }
        });

        backButton = new JButton(" BACK ");
        backButton.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(new LineBorder(Color.WHITE, 1));
        backButton.addActionListener(e -> {
            gamePanel.removeAll();
            gamePanel.revalidate();
            gamePanel.repaint();
            galaxyImages();
        });
        gbc.insets = new Insets(200, 0, 5, 0);
        add(backButton, gbc);

        gamePanel.revalidate();
        gamePanel.repaint();
        setVisible(true);
    }

    protected Character getChosenCharacter() {
        return switch (index) {
            case 0 -> new Cipher();
            case 1 -> new Opal();
            case 2 -> new Pulse();
            case 3 -> new Mirage();
            case 4 -> new Zero();
            default -> null;
        };
    }

    protected void chooseWorld(ArrayList<String> completedWorlds){
        gamePanel.removeAll();
        gamePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gamePanel.setBackground(Color.BLACK);

        JLabel chooseWorldLabel = new JLabel("CHOOSE A WORLD TO START " + getChosenCharacter().getName() + "'s JOURNEY");
        chooseWorldLabel.setForeground(Color.WHITE);
        chooseWorldLabel.setHorizontalTextPosition(JLabel.CENTER);
        chooseWorldLabel.setVerticalTextPosition(JLabel.BOTTOM);
        chooseWorldLabel.setFont(new Font("OCR A Extended", Font.PLAIN, 30));
        chooseWorldLabel.setForeground(Color.WHITE);
        chooseWorldLabel.setBackground(Color.BLACK);

        JButton nihraGalaxyButton = new JButton(" Nihra Galaxy ");
        nihraGalaxyButton.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
        nihraGalaxyButton.setForeground(Color.WHITE);
        nihraGalaxyButton.setBackground(Color.BLACK);
        nihraGalaxyButton.setBorder(new LineBorder(Color.WHITE, 1));

        JButton veridraGalaxyButton = new JButton(" Veridra Galaxy ");
        veridraGalaxyButton.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
        veridraGalaxyButton.setForeground(Color.WHITE);
        veridraGalaxyButton.setBackground(Color.BLACK);
        veridraGalaxyButton.setBorder(new LineBorder(Color.WHITE, 1));

        JButton drathosGalaxyButton = new JButton(" Drathos Galaxy ");
        drathosGalaxyButton.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
        drathosGalaxyButton.setForeground(Color.WHITE);
        drathosGalaxyButton.setBackground(Color.BLACK);
        drathosGalaxyButton.setBorder(new LineBorder(Color.WHITE, 1));

        backButton = new JButton(" BACK ");
        backButton.setFont(new Font("OCR A Extended", Font.PLAIN, 15));
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(new LineBorder(Color.WHITE, 1));
        backButton.addActionListener(e -> {
            gamePanel.removeAll();
            gamePanel.revalidate();
            gamePanel.repaint();
            chooseCharacter();
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(30, 10, 50, 20);
        gbc.anchor = GridBagConstraints.SOUTH;
        gamePanel.add(backButton, gbc);

        if (completedWorlds.contains("Nihra Galaxy")) {
            nihraGalaxyButton.setEnabled(false);
            backButton.setEnabled(false);
        }
        if (completedWorlds.contains("Veridra Galaxy")) {
            veridraGalaxyButton.setEnabled(false);
            backButton.setEnabled(false);
        }
        if (completedWorlds.contains("Drathos Galaxy")) {
            drathosGalaxyButton.setEnabled(false);
            backButton.setEnabled(false);
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(280, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        gamePanel.add(chooseWorldLabel, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 30, 200, 30);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gamePanel.add(drathosGalaxyButton, gbc);

        gbc.gridx = 1;
        gamePanel.add(veridraGalaxyButton, gbc);

        gbc.gridx = 2;
        gamePanel.add(nihraGalaxyButton, gbc);

        nihraGalaxyButton.addActionListener(e -> {
            String selectedGalaxy = "Nihra Galaxy";
            int option = JOptionPane.showConfirmDialog(
                    gamePanel,
                    "You are about to enter " + selectedGalaxy + "." + "\nDo you want to proceed? Remember that once you've stepped into this world, there's no turning back.",
                    "World Selection",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                gamePanel.removeAll();
                gamePanel.revalidate();
                gamePanel.repaint();
                NihraGalaxyGamePlay nihraGalaxy = new NihraGalaxyGamePlay(selectedCharacter);
                setContentPane(nihraGalaxy.getContentPane());
                nihraGalaxy.startWorld();
            } else {
                chooseWorld(completedWorlds);
            }
        });

        veridraGalaxyButton.addActionListener(e -> {
            String selectedGalaxy = "Veridra Galaxy";
            int option = JOptionPane.showConfirmDialog(
                    gamePanel,
                    "You are about to enter " + selectedGalaxy + "." + "\nDo you want to proceed? Do you want to proceed? Remember that once you've stepped into this world, there's no turning back.",
                    "World Selection",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                gamePanel.removeAll();
                gamePanel.revalidate();
                gamePanel.repaint();
                NihraGalaxyGamePlay nihraGalaxy = new NihraGalaxyGamePlay(selectedCharacter); // Use selectedCharacter here
                setContentPane(nihraGalaxy.getContentPane());
                nihraGalaxy.startWorld();
            }else {
                chooseWorld(completedWorlds);
            }
        });

        drathosGalaxyButton.addActionListener(e -> {
            String selectedGalaxy = "Drathos Galaxy";
            int option = JOptionPane.showConfirmDialog(
                    gamePanel,
                    "You are about to enter \n" + selectedGalaxy + "." + "\nDo you want to proceed? Remember that once you've stepped into this world, there's no turning back.",
                    "World Selection",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                // chooseWorld();  placeholder
            } else {
                chooseWorld(completedWorlds);
            }
        });

        gamePanel.revalidate();
        gamePanel.repaint();
    }
}