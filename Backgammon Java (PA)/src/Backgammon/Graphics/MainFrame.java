package Backgammon.Graphics;

import Backgammon.Backgammon;
import Backgammon.BoardActions.Play;
import Backgammon.BoardActions.PlayOptions;
import Backgammon.Graphics.BoardPanel;
import Backgammon.Graphics.InputPanel;
import Backgammon.Graphics.MovesPanel;
import Backgammon.Board;
import Backgammon.Pair;
import Backgammon.InputBox;
import Backgammon.Player;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{

    private final BoardPanel boardPanel;
    private final MovesPanel movesPanel;
    private final InputPanel inputPanel;

    public MainFrame (Board board, Pair players) {

        JFrame frame = new JFrame();
        frame.setSize(1150, 590);
        frame.setTitle("Backgammon Java");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        boardPanel = new BoardPanel(board,players);
        movesPanel = new MovesPanel();
        inputPanel = new InputPanel();

        Panel rightPanel = new Panel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        rightPanel.add(movesPanel, BorderLayout.NORTH);
        rightPanel.add(inputPanel, BorderLayout.SOUTH);

        frame.add(boardPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.CENTER);

        frame.setResizable(false);
        frame.setVisible(true);


        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\ASUS\\Desktop\\JavaProject\\Backgammon Java (PA)\\src\\Backgammon\\Graphics\\icon.png");
        frame.setIconImage(icon);
    }

    public void refreshBoardPanel() {
        boardPanel.refresh();
    }

    public String readInput() {
        return inputPanel.readInput();
    }

    public InputBox getInput(PlayOptions possiblePlays) {

        InputBox choice;
        do {
            String commandString = inputPanel.readInput();
            choice = new InputBox(commandString,possiblePlays);
            if (!choice.isValid()) {
                showMessage("Incorrect command. Try again!");
            }
        } while (!choice.isValid());

        return choice;
    }


    public void showOptions(Player player, PlayOptions possiblePlays) {
        movesPanel.showText(player.getName() + " [" + player.getColorName() + "] has this available moves: ");
        int index = 0;
        for (Play play : possiblePlays) {
            movesPanel.showText("[" + index + "] " + play);
            index++;
        }
    }

    public void showMessage(String string) {
        movesPanel.showText(string);
    }


}