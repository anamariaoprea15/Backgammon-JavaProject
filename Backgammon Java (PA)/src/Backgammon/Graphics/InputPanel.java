package Backgammon.Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InputPanel extends JPanel  {

    private final JTextField inputField;
    private final ArrayList<String> inputBuffer;

    public InputPanel() {
        inputField = new JTextField();
        inputBuffer = new ArrayList<>();
        class AddActionListener implements ActionListener {
            public void actionPerformed(ActionEvent event)	{
                synchronized (inputBuffer) {
                    inputBuffer.add(inputField.getText());
                    inputField.setText("");
                    inputBuffer.notify();
                }
            }
        }
        ActionListener listener = new AddActionListener();
        inputField.addActionListener(listener);
        inputField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        setLayout(new BorderLayout());
        add(inputField, BorderLayout.CENTER);
    }

    public String readInput() {
        String input;
        synchronized(inputBuffer) {
            while (inputBuffer.isEmpty()) {
                try {
                    inputBuffer.wait();
                } catch (InterruptedException ex) {
                    System.err.println("Exception at read: " + ex);
                }
            }

            input = inputBuffer.remove(0);
        }
        return input;
    }

}
