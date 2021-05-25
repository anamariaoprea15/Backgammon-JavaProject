package Backgammon.Graphics;

import Backgammon.Board;
import Backgammon.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BoardPanel extends JPanel {

    private Graphics2D g;
    private final Color[] piecesColors;
    private Board board;
    private final Pair players;
    private BufferedImage boardImage;

    private final int width = 760, height = 560;
    private final int top = 35, bottom = 80, left = 60, right = 50;
    private final int rowSize = 50;
    private final int barSize = 60;
    private final int radius = 20;
    private final int pieceSize = 8, outline = 1;
    private int x,y;

    public BoardPanel(Board board, Pair players) {

        this.board = board;

        try {
            boardImage = ImageIO.read(this.getClass().getResource("board.jpg"));
        } catch (IOException ex) {
            System.out.println("Could not find the image file " + ex);
        }


        this.players = players;
        piecesColors = new Color[2];
        piecesColors[0] = Color.WHITE;
        piecesColors[1] = Color.BLACK;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.WHITE);


    }

    private void showPiece(int player, int x, int y) {
        g.setColor(Color.BLACK);
        Ellipse2D.Double ellipseBlack = new Ellipse2D.Double(x, y, 2 * radius, 2 * radius);
        g.fill(ellipseBlack);
        Ellipse2D.Double ellipseColour = new Ellipse2D.Double(x + outline, y + outline, 2 * (radius - outline), 2 * (radius - outline));
        g.setColor(piecesColors[player]);
        g.fill(ellipseColour);
    }

    private void showPieceOut(int player, int x, int y) {
        if(player == 1){
            g.setColor(Color.DARK_GRAY);
        }
        else{
            g.setColor(Color.BLACK);
        }

        Rectangle2D.Double pieceOut = new Rectangle2D.Double(x, y, 2 * radius, pieceSize);
        g.fill(pieceOut);
        Rectangle2D.Double pieceOutFilled = new Rectangle2D.Double(x + outline, y + outline, 2 * (radius - outline), pieceSize - 2 * outline);
        g.setColor(piecesColors[player]);
        g.fill(pieceOutFilled);
    }


    public void refresh() {
        revalidate();
        repaint();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g = (Graphics2D) g;
        this.g.drawImage(boardImage, 0, 0, width, height, this);

        for (int player = 0; player < 2; player++) {
            // row numbers
            for (int row = 1; row <= 24; row++) {
                if (row > 3 * 24 / 4) {
                    x = width / 2 + barSize / 2 + (row - 3 * 24 / 4 - 1) * rowSize + rowSize / 4;
                } else if (row > 24 / 2) {
                    x = left + (row - 24 / 2 - 1) * rowSize + rowSize / 4;
                } else if (row > 24 / 4) {
                    x = left + (24 / 2 - row) * rowSize + rowSize / 4;
                } else {
                    x = width / 2 + barSize / 2 + (24 / 4 - row) * rowSize + rowSize / 4;
                }
                if (row > 24 / 2) {
                    y = 3 * top / 4;
                } else {
                    y = height - bottom / 4;
                }
                g.setColor(players.getTurn().getColor());
                g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
                if (players.getTurn().getId() == 0) {
                    g.drawString(Integer.toString(row), x, y);
                } else {
                    //change numbers for opponent
                    g.drawString(Integer.toString(24 - row + 1), x, y);
                }
            }


            // show piece on bar
            for (int i = 1; i <= board.getNumberOfPieces(player, 25); i++) {
                x = width / 2 - radius;
                if (player == 0) {
                    y = height / 4 + (i - 1) * radius;
                } else {
                    y = 3 * height / 4 - (i - 1) * radius;
                }
                showPiece(player, x, y);
            }

            // show board
            for (int row = 1; row <= 24; row++) {
                for (int i = 1; i <= board.getNumberOfPieces(player, row); i++) {
                    if (row > 3 * 24 / 4) {
                        x = width / 2 + barSize / 2 + (row - 3 * 24 / 4 - 1) * rowSize;
                    } else if (row > 24 / 2) {
                        x = left + (row - 24 / 2 - 1) * rowSize;
                    } else if (row > 24 / 4) {
                        x = left + (24 / 2 - row) * rowSize;
                    } else {
                        x = width / 2 + barSize / 2 + (24 / 4 - row) * rowSize;
                    }
                    if ((player == 0 && row > 24 / 2) || (player == 1 && row < 24 / 2)) {
                        y = top + (i - 1) * 2 * radius;
                    } else {
                        y = height - bottom - (i - 1) * 2 * radius;
                    }
                    showPiece(player, x, y);
                }
            }
            // piece out
            for (int i = 1; i <= board.getNumberOfPieces(player, 0); i++) {
                x = width - right / 2 - radius;
                if (player == 0) {
                    y = height - bottom - (i - 1) * pieceSize;
                } else {
                    y = top + (i - 1) * pieceSize;
                }
                showPieceOut(player, x, y);
            }

        }


    }

}
