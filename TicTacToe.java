import javax.swing.*;
import java.awt.*;

public class TicTacToe extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);

    private final JTextField player1Field = new JTextField(15);
    private final JTextField player2Field = new JTextField(15);
    private String player1Name = "Player 1";
    private String player2Name = "Player 2";

    private final JButton[][] buttons = new JButton[3][3];
    private boolean xTurn = true;
    private int moves = 0;
    private JLabel statusLabel;

    public TicTacToe() {
        setTitle("WELCOME TO Tic Tac Toe GAME");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainPanel.add(createWelcomeScreen(), "Welcome");
        mainPanel.add(createGameScreen(), "Game");

        add(mainPanel);
        cardLayout.show(mainPanel, "Welcome");
    }

    private JPanel createWelcomeScreen() {
        JPanel welcome = new JPanel();
        welcome.setLayout(new BoxLayout(welcome, BoxLayout.Y_AXIS));
        welcome.setBackground(new Color(240, 240, 240));
        welcome.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("WELCOME TO Tic Tac Toe");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(70, 130, 180));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Enter player names to begin");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcome.add(Box.createVerticalStrut(20));
        welcome.add(title);
        welcome.add(subtitle);
        welcome.add(Box.createVerticalStrut(40));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(240, 240, 240));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel player1Label = new JLabel("Player 1 (X):");
        player1Label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        inputPanel.add(player1Label, gbc);

        gbc.gridx = 1;
        player1Field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        player1Field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        inputPanel.add(player1Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel player2Label = new JLabel("Player 2 (O):");
        player2Label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        inputPanel.add(player2Label, gbc);

        gbc.gridx = 1;
        player2Field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        player2Field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        inputPanel.add(player2Field, gbc);

        welcome.add(inputPanel);
        welcome.add(Box.createVerticalStrut(30));

        JButton continueBtn = createStyledButton("START GAME", new Color(70, 130, 180));
        continueBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueBtn.addActionListener(e -> {
            player1Name = player1Field.getText().trim().isEmpty() ? "Player 1" : player1Field.getText().trim();
            player2Name = player2Field.getText().trim().isEmpty() ? "Player 2" : player2Field.getText().trim();
            resetGame();
            updateStatus();
            cardLayout.show(mainPanel, "Game");
        });

        welcome.add(continueBtn);

        return welcome;
    }
