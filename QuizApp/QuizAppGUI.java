import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizAppGUI extends JFrame implements ActionListener, KeyListener {

    String[] questions = {
        "What is the capital of India?",
        "Which language is used for Android development?",
        "What is 2 + 2?",
        "Which data structure uses LIFO?",
        "Which protocol is used for sending email?",
        "What does JVM stand for?",
        "What is the time complexity of binary search?",
        "Which layer in the OSI model does IP belong to?",
        "Which SQL command is used to remove a table?",
        "Which language is most used for web development?"
    };

    String[][] options = {
        {"Delhi", "Mumbai", "Kolkata", "Chennai"},
        {"Python", "Java", "Swift", "PHP"},
        {"3", "4", "5", "6"},
        {"Queue", "Stack", "Tree", "Graph"},
        {"HTTP", "FTP", "SMTP", "TCP"},
        {"Java Virtual Machine", "Java Vendor Manager", "Joint Vector Method", "Java Visual Memory"},
        {"O(n)", "O(n log n)", "O(log n)", "O(1)"},
        {"Transport", "Data Link", "Network", "Session"},
        {"DELETE", "REMOVE", "DROP", "CLEAR"},
        {"C++", "Java", "HTML", "Assembly"}
    };

    int[] answers = {0, 1, 1, 1, 2, 0, 2, 2, 2, 2};
    int[] userSelections = new int[questions.length];

    int currentQuestion = 0;
    int score = 0;

    JLabel questionLabel;
    JRadioButton[] optionButtons;
    ButtonGroup optionsGroup;
    JButton nextButton;

    public QuizAppGUI() {
        setTitle("Java Quiz App");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        questionLabel = new JLabel("Question");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        optionButtons = new JRadioButton[4];
        optionsGroup = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].addKeyListener(this); // Add key listener
            optionsGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }

        add(optionsPanel, BorderLayout.CENTER);

        nextButton = new JButton("Next");
        nextButton.setPreferredSize(new Dimension(100, 35)); // 🔹 Smaller width
        nextButton.addActionListener(this);
        add(nextButton, BorderLayout.SOUTH);

        loadQuestion();
        setVisible(true);
    }

    void loadQuestion() {
        questionLabel.setText("Q" + (currentQuestion + 1) + ": " + questions[currentQuestion]);
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(options[currentQuestion][i]);
        }
        optionsGroup.clearSelection();
    }

    public void actionPerformed(ActionEvent e) {
        processAnswer();
    }

    void processAnswer() {
        int selected = -1;
        for (int i = 0; i < 4; i++) {
            if (optionButtons[i].isSelected()) {
                selected = i;
                break;
            }
        }

        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Please select an option.");
            return;
        }

        userSelections[currentQuestion] = selected;

        if (selected == answers[currentQuestion]) {
            score++;
        }

        currentQuestion++;

        if (currentQuestion < questions.length) {
            loadQuestion();
        } else {
            showResult();
        }
    }

    void showResult() {
        StringBuilder review = new StringBuilder();
        review.append("Your Score: ").append(score).append("/").append(questions.length).append("\n\n");

        boolean anyMistake = false;

        for (int i = 0; i < questions.length; i++) {
            if (userSelections[i] != answers[i]) {
                anyMistake = true;
                review.append("Q").append(i + 1).append(": ").append(questions[i]).append("\n");
                review.append("❌ Your Answer: ").append(options[i][userSelections[i]]).append("\n");
                review.append("✅ Correct Answer: ").append(options[i][answers[i]]).append("\n\n");
            }
        }

        if (!anyMistake) {
            review.append("🎉 You got all answers correct!");
        }

        JTextArea textArea = new JTextArea(review.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        JOptionPane.showMessageDialog(this, scrollPane, "Quiz Result", JOptionPane.INFORMATION_MESSAGE);

        nextButton.setEnabled(false);
    }

    // 🔑 Enter key triggers next
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            processAnswer();
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new QuizAppGUI();
    }
}
