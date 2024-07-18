import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PRNGApp {
    public static void main(String[] args) {
        // Create the PRNG with example parameters
        long seed = 12345;
        long a = 1664525;
        long c = 1013904223;
        long m = (long) Math.pow(2, 32);
        LinearCongruentialGenerator lcg = new LinearCongruentialGenerator(seed, a, c, m);

        // Create the frame
        JFrame frame = new JFrame("Pseudorandom Number Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create a panel to hold components
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel, lcg);

        // Set the frame visibility
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel, LinearCongruentialGenerator lcg) {
        panel.setLayout(null);

        // Label for random number generation
        JLabel userLabel = new JLabel("Generate a random number:");
        userLabel.setBounds(10, 20, 200, 25);
        panel.add(userLabel);

        // Text area for displaying the random number
        JTextArea textArea = new JTextArea();
        textArea.setBounds(10, 50, 360, 60);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        panel.add(textArea);

        // Label for minimum range
        JLabel minLabel = new JLabel("Min:");
        minLabel.setBounds(10, 120, 80, 25);
        panel.add(minLabel);

        // Text field for minimum range
        JTextField minText = new JTextField(20);
        minText.setBounds(50, 120, 100, 25);
        panel.add(minText);

        // Label for maximum range
        JLabel maxLabel = new JLabel("Max:");
        maxLabel.setBounds(180, 120, 80, 25);
        panel.add(maxLabel);

        // Text field for maximum range
        JTextField maxText = new JTextField(20);
        maxText.setBounds(220, 120, 100, 25);
        panel.add(maxText);

        // Button to generate random number
        JButton generateButton = new JButton("Generate");
        generateButton.setBounds(10, 160, 100, 25);
        panel.add(generateButton);

        // Action listener for the button
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int min = Integer.parseInt(minText.getText());
                    int max = Integer.parseInt(maxText.getText());
                    if (min >= max) {
                        textArea.setText("Min should be less than Max");
                        return;
                    }
                    double randomValue = lcg.random();
                    int scaledRandomValue = (int) (randomValue * (max - min) + min);
                    textArea.setText("Random Number: " + scaledRandomValue);
                } catch (NumberFormatException ex) {
                    textArea.setText("Please enter valid integers for Min and Max.");
                }
            }
        });
    }
}
