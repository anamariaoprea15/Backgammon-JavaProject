package Backgammon;

import java.awt.*;

public class Player {
	
    private final int id;
    private String name = "";
    private final Color color;
    private final String colorString;
    private Dice dice;


    public Player(int id, Color color, String colorString) {
        this.id = id;
        this.color = color;
        this.colorString = colorString;
        this.dice = new Dice();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getColorName() {
        return this.colorString;
    }

    public Color getColor() {
        return this.color;
    }

    public Dice getDice() { return dice; }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", colorName='" + colorString + '\'' +
                ", color=" + color +
                ", dice=" + dice +
                '}';
    }
}
