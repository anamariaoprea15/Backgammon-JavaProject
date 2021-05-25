package Backgammon;

import Backgammon.BoardActions.DiceOptions;
import Backgammon.BoardActions.Move;
import Backgammon.BoardActions.Play;
import Backgammon.BoardActions.PlayOptions;

public class Board {

    private static int[] initialBoard; //0 house, 25 bar, 1-24 board
    //matrix: 2 rows for players; 26 columns for board rows with value = nr of pieces
    private int[][] pieces;
    private final Pair players;

    private void initBoard(){

        initialBoard = new int[26];
        initialBoard[24] = 2;
        initialBoard[13] = 5;
        initialBoard[8] = 3;
        initialBoard[6] = 5;
    }

    public Board(Pair players) {

        initBoard();
        this.players = players;
        pieces = new int[2][26];
        for (int row=0; row<26; row++){
            pieces[0][row] = initialBoard[row]; //player 1
            pieces[1][row] = initialBoard[row]; //player 2
        }

    }

    public Board(Pair players, Board board) {
        this.players = players;
        this.pieces = new int[2][26];
        for (int row=0; row<26; row++){
            pieces[0][row] = board.pieces[0][row];
            pieces[1][row] = board.pieces[1][row];
        }
    }


    public void resetBoard() {
        //Resets the pieces on the board to their initial configuration
        for (int row=0; row<26; row++){
            pieces[0][row] = initialBoard[row];
            pieces[1][row] = initialBoard[row];
        }

    }


    public int getNumberOfPieces(int player, int row) {
        return pieces[player][row];
    }


    public int getOpponent(Player player) {

        if (player.getId()==0) {
            return 1;
        }

        return 0;
    }

    public boolean isEndGame() {

        //a player removed all the pieces from the board
        if ((pieces[0][0] == 15) || (pieces[1][0] == 15)) {
            return true;
        }
        return false;
    }


    public Player getWinner() {

        if (pieces[0][0] == 15) {
            return players.getByID(0);
        }
        return players.getByID(1);

    }

    public boolean canBearOff(Player player) {

        int piecesInHouse=0;
        for (int row=0; row<=6; row++) { //only in house
            piecesInHouse = piecesInHouse + getNumberOfPieces(player.getId(), row);
        }
        if (piecesInHouse==15) { //all pieces are in house
            return true;
        }

        return false;

    }

    public int equivalentOpponentRow(int row) {
        return 24 - row + 1;
    }

    public void move(Player player, Move move) {

        pieces[player.getId()][move.getStartPoint()]--; //remove piece
        pieces[player.getId()][move.getEndPoint()]++; //put piece

        if (move.getEndPoint() >= 1 && move.getEndPoint() <= 24) {
            if (pieces[getOpponent(player)][equivalentOpponentRow(move.getEndPoint())] == 1) {
                //move opponent's piece to bar
                pieces[getOpponent(player)][equivalentOpponentRow(move.getEndPoint())]--;
                pieces[getOpponent(player)][25]++;
            }
        }
    }

    public void move(Player player, Play play) {
        for (Move move : play) {
            move(player,move);
        }
    }

    public PlayOptions getAllOptions(Board board, Player player, DiceOptions diceOptions) {

        PlayOptions playOptions = new PlayOptions();
        Move movePiece = new Move();
        int startRow;

        // first check if there are pieces on the bar
        if (board.pieces[player.getId()][25] > 0) {
            startRow = 25;
        } else {
            startRow = 0;
        }

        // check valid moves
        for (int startPoint = 25; startPoint >= startRow; startPoint--) {

            if (board.pieces[player.getId()][startPoint] > 0) {

                int endPoint = startPoint-diceOptions.getFirst(); //first die

                boolean makeMove = false;
                if (endPoint > 0) {

                    if (board.pieces[getOpponent(player)][equivalentOpponentRow(endPoint)]==0) {
                        //opponent doesn't have pieces on the row
                        movePiece = new Move(startPoint,endPoint,false);
                        makeMove = true;
                    } else if (board.pieces[getOpponent(player)][equivalentOpponentRow(endPoint)]==1) {
                        //opponent have a piece on the row => capture piece
                        movePiece = new Move(startPoint,endPoint,true);
                        makeMove = true;
                    }
                } else {
                    //if pieces are in house
                    if (board.canBearOff(player)){
                        if (endPoint==0) {
                            movePiece = new Move(startPoint, 0, false);
                            makeMove = true;
                        }
                        else if (endPoint<0){
                            //the dice is bigger, but can move from house
                            int row;
                            for (row=25; row>=0; row--) {
                                if (pieces[player.getId()][row]>0) { //first row with pieces
                                    break;
                                }
                            }
                            if(row == startPoint){
                                movePiece = new Move(startPoint, 0, false);
                                makeMove = true;
                            }
                        }
                    }
                }
                // move and check the next one
                if (makeMove) {
                    if (diceOptions.nrOfOptions()>1) {

                        Board newBoard = new Board(players,board);
                        newBoard.move(player,movePiece);
                        DiceOptions newMovements = new DiceOptions(diceOptions);
                        newMovements.removeFirst();

                        //start with new move
                        PlayOptions newPlays = getAllOptions(newBoard, player, newMovements);

                        if (newPlays.getNumberOfPlays()>0) {

                            newPlays.makeFirst(movePiece);
                            playOptions.addOptions(newPlays); //add the move options
                        } else {
                            playOptions.addOption(new Play(movePiece)); //add the first move
                        }
                    }
                    else {
                        playOptions.addOption(new Play(movePiece)); //add only the first one
                    }
                }
            }
        }
        return playOptions;
    }


    public PlayOptions getPlayOptions(Player player, Dice dice) {

        // all possible options for current dice
        PlayOptions playOptions;
        DiceOptions moveOptions = new DiceOptions(dice); //add dice for moves

        if (player.getDice().isDouble()) {
            playOptions = getAllOptions(this, player, moveOptions);
        } else {
            playOptions = getAllOptions(this,player,moveOptions);

            moveOptions.reverseDice(); //check with reversed values for dice
            playOptions.addOptions(getAllOptions(this,player,moveOptions));
        }

        return playOptions;
    }
}