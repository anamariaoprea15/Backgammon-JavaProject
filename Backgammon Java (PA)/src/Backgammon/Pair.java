package Backgammon;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//a pair of two players in a game
public class Pair implements Iterable<Player>{

    private List<Player> playersList;
    private int playerTurn;

    Pair() {
        playersList = new ArrayList<>();
        playersList.add(new Player(0, Color.WHITE, "WHITE"));
        playersList.add(new Player(1, Color.BLACK, "BLACK"));
        playerTurn = 0;
    }

    public void setFirstTurn() {

        if (playersList.get(0).getDice().sumDice() > playersList.get(1).getDice().sumDice()) {
            playerTurn = 0;
        } else {
            playerTurn = 1;
        }
    }

    public Player getTurn() {
        return playersList.get(playerTurn);
    }

    public void changeTurn() {

        if ((playerTurn + 1) == 2) { //is last player, then goes to first
            playerTurn = 0;
        }
        else playerTurn = 1;
    }


    public Player getByID(int id) {
        return playersList.get(id);
    }

    public boolean isTie() {

        if(playersList.get(0).getDice().sumDice() == playersList.get(1).getDice().sumDice())
            return true;
        return false;
    }

    @Override
    public Iterator<Player> iterator() {
        return playersList.iterator();
    }


}
