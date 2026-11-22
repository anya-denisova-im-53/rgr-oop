package app;


import calclib.CalcException;
import calclib.CalculatorEngine;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class CalcMainFrame extends JFrame {
    private final CalculatorEngine engine = new CalculatorEngine();
    private final JTextField displayField;

    public CalcMainFrame() {
        setTitle("РГР (Варіант 9) — Калькулятор (Власна бібліотека calclib)");
        setSize(360, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

       
        displayField = new JTextField("0");
        displayField.setEditable(false);
        displayField.setFont(new Font("Consolas", Font.BOLD, 28));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setBackground(Color.WHITE);
        displayField.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.add(displayField, BorderLayout.NORTH);
        mainPanel.add(createButtonPanel(), BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 4, 5, 5));

        String[] buttons = {
            "MC", "MR", "M+", "M-",
            "CE", "C", "±", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "⌫", "="
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("SansSerif", Font.BOLD, 16));
            btn.setFocusPainted(false);
            btn.addActionListener(e -> handleButtonPress(text));
            panel.add(btn);
        }

        return panel;
    }

    private void handleButtonPress(String cmd) {
        try {
            if (cmd.matches("[0-9]")) {
                engine.inputDigit(cmd);
            } else if (cmd.equals(".")) {
                engine.inputDecimalPoint();
            } else if (cmd.matches("[+*/\\-]")) {
                engine.performOperation(cmd);
            } else if (cmd.equals("=")) {
                engine.calculateEquals();
            } else if (cmd.equals("C")) {
                engine.clearAll();
            } else if (cmd.equals("CE")) {
                engine.clearEntry();
            } else if (cmd.equals("MC")) {
                engine.getMemoryStore().memoryClear();
            } else if (cmd.equals("MR")) {
                engine.inputDigit(String.valueOf(engine.getMemoryStore().memoryRead()));
            } else if (cmd.equals("M+")) {
                engine.getMemoryStore().memoryAdd(Double.parseDouble(engine.getDisplay()));
            } else if (cmd.equals("M-")) {
                engine.getMemoryStore().memorySubtract(Double.parseDouble(engine.getDisplay()));
            } else if (cmd.equals("±")) {
                double val = Double.parseDouble(engine.getDisplay()) * -1;
              
            }
            displayField.setText(engine.getDisplay());
        } catch (CalcException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Помилка обчислення", JOptionPane.ERROR_MESSAGE);
            engine.clearAll();
            displayField.setText("0");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalcMainFrame().setVisible(true));
    }
}