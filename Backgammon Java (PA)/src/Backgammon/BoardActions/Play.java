package Backgammon.BoardActions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Play implements Iterable<Move>{

    private List<Move> moves;

    public Play(Move move) {
        moves = new ArrayList<>();
        moves.add(move);
    }

    public void makeFirst(Move move) {
        moves.add(0,move);
    }


    @Override
    public String toString() {
        String moveLine = "";
        for (Move move : moves) {
            moveLine = moveLine + move + "   ";
        }
        return moveLine;
    }

    @Override
    public Iterator<Move> iterator() {
        return moves.iterator();
    }



}
