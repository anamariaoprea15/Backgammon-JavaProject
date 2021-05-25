package Backgammon.BoardActions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayOptions implements Iterable<Play> {

    public List<Play> plays;

    public PlayOptions() {
        plays = new ArrayList<>();
    }

    public void addOption(Play playOption) {
        plays.add(playOption);
    }

    public void addOptions(PlayOptions playOptions) {
        for (Play play : playOptions) {
            this.plays.add(play);
        }
    }

    public int getNumberOfPlays() {
        return plays.size();
    }

    public Play get(int index) {
        return plays.get(index);
    }

    public void makeFirst(Move move) {
        for (Play play : plays) {
            play.makeFirst(move);
        }
    }


    public Iterator<Play> iterator() {
        return plays.iterator();
    }

}
