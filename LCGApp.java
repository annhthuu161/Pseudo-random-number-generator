import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LCGApp {
    public class LCGRNG {
        private long a;
        private long c;
        private long m;
        private long seed;
    
        public LCGRNG(long seed) {
            this.a = 1664525;
            this.c = 1013904223;
            this.m = (1L << 32); // 2^32
            this.seed = seed;
        }
    
        private long rand() {
            seed = (a * seed + c) % m;
            return seed;
        }
    
        public int randint(int minValue, int maxValue) {
            return minValue + (int)(rand() % (maxValue - minValue + 1));
        }
    }
    
    private JFrame frame;
    private JTextField minField;
    private JTextField maxField;
    private JLabel resultLabel;

    public LCGApp() {
        frame = new JFrame("LCG RNG");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(5, 1));

        JLabel minLabel = new JLabel("Min Value:");
        minField = new JTextField();
        JLabel maxLabel = new JLabel("Max Value:");
        maxField = new JTextField();

        JButton generateButton = new JButton("Generate Random Number");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRandomNumber();
            }
        });

        resultLabel = new JLabel("Random Number: ");

        frame.add(minLabel);
        frame.add(minField);
        frame.add(maxLabel);
        frame.add(maxField);
        frame.add(generateButton);
        frame.add(resultLabel);

        frame.setVisible(true);
    }

    private void generateRandomNumber() {
        try {
            int minValue = Integer.parseInt(minField.getText());
            int maxValue = Integer.parseInt(maxField.getText());

            if (minValue > maxValue) {
                JOptionPane.showMessageDialog(frame, "Minimum value cannot be greater than maximum value.", "Invalid Range", JOptionPane.ERROR_MESSAGE);
                return;
            }

            long seed = System.currentTimeMillis(); // Using the current time as a seed
            LCGRNG rng = new LCGRNG(seed);
            int randomNumber = rng.randint(minValue, maxValue);
            resultLabel.setText("Random Number: " + randomNumber);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter valid integer values for min and max.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new LCGApp();
    }
}
