package Backgammon.BoardActions;

public class Move{

    private int startPoint, endPoint;
    private boolean capture = false;

    public Move() {
        startPoint = 0;
        endPoint = 0;
    }

    public Move(int startPoint, int endPoint, boolean capture) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.capture = capture;
    }

    public int getStartPoint() {
        return startPoint;
    }

    public int getEndPoint() {
        return endPoint;
    }


    @Override
    public String toString() {
        String start, end, captured;
        if (startPoint == 25) {
            start = "(bar)";
        } else {
            start = Integer.toString(startPoint);
        }
        if (endPoint == 0) {
            end = "(bear off)";
        } else {
            end = Integer.toString(endPoint);
        }
        if (capture) {
            captured = " (↑) ";
        } else {
            captured = "";
        }
        return start + "→" + end + captured;
    }

}
