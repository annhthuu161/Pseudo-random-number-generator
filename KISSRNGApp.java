import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class KISSRNGApp {
    public class KISSRNG {
        private long x;
        private long y;
        private long z;
        private long w;
    
        public KISSRNG(long seed) {
            this.x = seed;
            this.y = seed;
            this.z = seed;
            this.w = seed;
        }
    
        private long rand() {
            x = 69069 * x + 12345;
            y ^= (y << 13);
            y ^= (y >> 17);
            y ^= (y << 5);
            z = 18000 * (z & 65535) + (z >> 16);
            w = 30903 * (w & 65535) + (w >> 16);
            return (x + y + z + w) & 0xFFFFFFFFL;
        }
    
        public int randint(int minValue, int maxValue) {
            return minValue + (int)(rand() % (maxValue - minValue + 1));
        }
    }


    private JFrame frame;
    private JTextField minField;
    private JTextField maxField;
    private JLabel resultLabel;

    public KISSRNGApp() {
        frame = new JFrame("KISS RNG");
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
            KISSRNG rng = new KISSRNG(seed);
            int randomNumber = rng.randint(minValue, maxValue);
            resultLabel.setText("Random Number: " + randomNumber);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter valid integer values for min and max.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new KISSRNGApp();
    }
}
