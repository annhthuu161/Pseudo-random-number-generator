

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class MersenneTwisterApp {
    public class MersenneTwisterRNG {
        private Random random;
    
        public MersenneTwisterRNG(long seed) {
            this.random = new Random(seed);
        }
    
        public int randint(int minValue, int maxValue) {
            return minValue + random.nextInt(maxValue - minValue + 1);
        }
    }
    
    private JFrame frame;
    private JTextField minField;
    private JTextField maxField;
    private JLabel resultLabel;

    public MersenneTwisterApp() {
        frame = new JFrame("Mersenne Twister RNG");
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
            MersenneTwisterRNG  rng = new MersenneTwisterRNG (seed);
    
            // Measure performance metrics
            long startTime = System.nanoTime();
            long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    
            int randomNumber = rng.randint(minValue, maxValue);
    
            long endTime = System.nanoTime();
            long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    
            long timeTaken = endTime - startTime;
            long memoryUsed = endMemory - startMemory;
    
            // Throughput: number of operations per second (1 operation / time in seconds)
            double throughput = 1.0 / (timeTaken / 1_000_000_000.0); // timeTaken is in nanoseconds
    
            resultLabel.setText("Random Number: " + randomNumber);
    
            // Print performance metrics to console
            System.out.println("Latency: " + timeTaken + " nanoseconds");
            System.out.println("Throughput: " + throughput + " operations/second");
            System.out.println("Memory used: " + memoryUsed + " bytes");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter valid integer values for min and max.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new MersenneTwisterApp();
    }
}
