package Backgammon;

import Backgammon.BoardActions.Play;
import Backgammon.BoardActions.PlayOptions;

public class InputBox {

    private String input = "";
    private boolean valid, move, restart;
    private Play play;

    public InputBox() {
        valid = true;
        move = false;
        restart = false;

    }

    public InputBox(String input, PlayOptions possiblePlays) {

        this.input = input.toLowerCase().trim();
        int maxOption = possiblePlays.getNumberOfPlays() - 1;

        if(isNumber(this.input)){
            int numberText = Integer.parseInt(this.input);
            if (numberText >= 0 && numberText <= maxOption) {
                play = possiblePlays.get(numberText);
                valid = true;
                move = true;
                restart=false;
            }
        }
        else if (this.input.equals("restart")) {
            valid = true;
            move = false;
            restart = true;
        } else {
            valid = false;
        }
    }

    public boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }

    }

    public boolean isValid() {
        return valid;
    }

    public boolean isMove() {
        return move;
    }

    public Play getMove() {
        return play;
    }

    public boolean isRestart() {
        return restart;
    }

    public String toString() {
        return input;
    }
}
