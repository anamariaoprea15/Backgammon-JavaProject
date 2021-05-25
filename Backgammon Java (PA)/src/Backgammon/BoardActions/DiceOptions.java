package Backgammon.BoardActions;

import Backgammon.Dice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DiceOptions implements Iterable<Integer>{

    List<Integer> diceOptions;

    public DiceOptions(Dice dice) {

        diceOptions = new ArrayList<>();
        if (dice.isDouble()) {
            // double => 4 dice
            diceOptions.add(dice.getDie1());
            diceOptions.add(dice.getDie2());
            diceOptions.add(dice.getDie1());
            diceOptions.add(dice.getDie2());

        } else {
            diceOptions.add(dice.getDie1());
            diceOptions.add(dice.getDie2());
        }
    }

    public DiceOptions(DiceOptions diceOptions) {

        this.diceOptions = new ArrayList<>();
        for (Integer movement : diceOptions) {
            this.diceOptions.add(movement);
        }

    }

    public int getFirst() {
        return diceOptions.get(0);
    }

    public void removeFirst() {
        diceOptions.remove(0);
    }

    public int nrOfOptions() {
        return diceOptions.size();
    }

    public void reverseDice() {

        Integer aux = diceOptions.get(0);
        diceOptions.set(0, diceOptions.get(1));
        diceOptions.set(1, aux);

    }

    @Override
    public Iterator<Integer> iterator() {
        return diceOptions.iterator();
    }
}
