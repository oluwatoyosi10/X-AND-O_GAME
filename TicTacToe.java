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

    private JPanel createGameScreen() {
        JPanel gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBackground(new Color(240, 240, 240));
        gamePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Status panel
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(new Color(240, 240, 240));
        statusLabel = new JLabel();
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        updateStatus();
        statusPanel.add(statusLabel);
        gamePanel.add(statusPanel, BorderLayout.NORTH);

        // Game board
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        boardPanel.setBackground(new Color(240, 240, 240));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        Font btnFont = new Font("Segoe UI", Font.BOLD, 60);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton btn = new JButton("");
                btn.setFont(btnFont);
                btn.setFocusPainted(false);
                btn.setBackground(Color.WHITE);
                btn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
                btn.setPreferredSize(new Dimension(100, 100));
                btn.setForeground(i == j ? new Color(70, 130, 180) : new Color(220, 100, 100));

                final int row = i, col = j;
                btn.addActionListener(e -> handleMove(btn, row, col));
                buttons[i][j] = btn;
                boardPanel.add(btn);
            }
        }

        gamePanel.add(boardPanel, BorderLayout.CENTER);

        // Control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(240, 240, 240));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton restartBtn = createStyledButton("RESTART", new Color(70, 130, 180));
        restartBtn.addActionListener(e -> resetGame());

        JButton menuBtn = createStyledButton("MAIN MENU", new Color(100, 100, 100));
        menuBtn.addActionListener(e -> cardLayout.show(mainPanel, "Welcome"));

        controlPanel.add(restartBtn);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(menuBtn);

        gamePanel.add(controlPanel, BorderLayout.SOUTH);

        return gamePanel;
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void handleMove(JButton btn, int row, int col) {
        if (!btn.getText().isEmpty()) return;

        btn.setText(xTurn ? "X" : "O");
        moves++;

        if (checkWinner()) {
            String winner = xTurn ? player1Name : player2Name;
            showGameOverDialog(winner + " wins!");
            resetGame();
        } else if (moves == 9) {
            showGameOverDialog("It's a draw!");
            resetGame();
        } else {
            xTurn = !xTurn;
            updateStatus();
        }
    }

    private void showGameOverDialog(String message) {
        JDialog dialog = new JDialog(this, "Game Over", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        dialog.add(label, BorderLayout.CENTER);

        JButton okButton = createStyledButton("OK", new Color(70, 130, 180));
        okButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
