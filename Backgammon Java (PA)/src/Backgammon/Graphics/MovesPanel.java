package Backgammon.Graphics;

import javax.swing.*;
import java.awt.*;

public class MovesPanel extends JPanel {

    private final JTextArea info;

    public MovesPanel() {

        info  = new JTextArea(25, 1);
        JScrollPane scrollPane = new JScrollPane(info);
        info.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        info.setLineWrap(true);
        info.setWrapStyleWord(true);
        info.setEditable(false);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }



    public void showText(String text) {
        info.setText(info.getText() + "\n" + text);
    }

}
