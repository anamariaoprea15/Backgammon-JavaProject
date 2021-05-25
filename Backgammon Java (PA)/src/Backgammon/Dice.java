package Backgammon;

import java.util.Random;

public class Dice {

    private int die1, die2;
    Random rnd = new Random();

    public Dice() {
        this.die1 = 1 + rnd.nextInt(6);
        this.die2 = 1 + rnd.nextInt(6);
    }

    public int getDie1() {
        return die1;
    }

    public int getDie2() {
        return die2;
    }

    public void rollDice() {

        this.die1 = 1 + rnd.nextInt(6);
        this.die2 = 1 + rnd.nextInt(6);

    }

    public int sumDice(){

        return die1 + die2;
    }


    public boolean isDouble() {

        if(this.die1 == this.die2){
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "Dice:" +
                "[" + die1 +
                ", " + die2 +
                ']';
    }
}
