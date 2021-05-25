package Backgammon;

import Backgammon.BoardActions.PlayOptions;
import Backgammon.Graphics.MainFrame;

import static java.lang.Thread.sleep;

public class Backgammon {

    private Pair players = new Pair();
    private Board board = new Board(players);

    private MainFrame graphics = new MainFrame(board,players);

	private void readNames() {
		for (Player player : players) {
			graphics.showMessage("Enter player's name:");
			String name = graphics.readInput();
			player.setName(name);
			graphics.showMessage("Name: " + player.getName());
			graphics.showMessage("Player " + player.getName() + " plays with [" + player.getColorName() + "] pieces.");
		}
	}

	private void startOrder() throws InterruptedException {
        do {
            for (Player player : players) {
				player.getDice().rollDice();
				graphics.showMessage(player.getName() + " [" + player.getColorName() + "] rolls " + player.getDice());
				sleep(1000);
            }
            if (players.isTie()) {
				graphics.showMessage("Tie! Roll dice again");
            }
        } while (players.isTie());

        players.setFirstTurn();
		graphics.showMessage(players.getTurn().getName() + " won the roll and starts the game.");
		graphics.refreshBoardPanel();

		sleep(1000);

    }


	//true if finds winner
    private boolean playRounds() throws InterruptedException {

        InputBox command = new InputBox();

        do {
            Player currentPlayer = players.getTurn();

            currentPlayer.getDice().rollDice();
			Dice currentDice = currentPlayer.getDice();
            //display roll
			graphics.showMessage(currentPlayer.getName() + " [" + currentPlayer.getColorName() + "] rolled " + currentDice);

			PlayOptions possibleMoves;
            possibleMoves = board.getPlayOptions(currentPlayer,currentDice);


			if (possibleMoves.getNumberOfPlays()==0){

				graphics.showMessage(currentPlayer.getName() + " doesn't have any valid moves. Wait for next turn!");
			}
			else if (possibleMoves.getNumberOfPlays()==1) {

				graphics.showMessage(currentPlayer.getName() + " made the only possible move.");
				board.move(currentPlayer, possibleMoves.get(0));
			}
	            else {
	            	//more than one move
					graphics.showOptions(currentPlayer, possibleMoves);
					graphics.showMessage(currentPlayer.getName() + " [" + currentPlayer.getColorName() + "] choose move (or restart):");
	                command = graphics.getInput(possibleMoves);
					graphics.showMessage("Chosen move: " + command);

	                if (command.isMove()) {
	                    board.move(currentPlayer, command.getMove());
	                } else if (command.isRestart()) {
	                	graphics.showMessage("Rearranging board...");
	                	sleep(2000);
	                    board.resetBoard();
	                    prepareGame();
	                }
	            }

				sleep(2000);
	            players.changeTurn();
				graphics.refreshBoardPanel();
            
            
        } while (!board.isEndGame());

       if(board.isEndGame()) {
			graphics.showMessage(board.getWinner().getName() + " won!");
			sleep(2000);
        	return true;
        }

		return false;
    }

    public void prepareGame() throws InterruptedException {
		graphics.refreshBoardPanel();
		graphics.showMessage("-------------------------------------------");
		graphics.showMessage("Welcome to Backgammon Java!");
		readNames();
		startOrder(); //player with max sumDice starts
	}

    private void startGame() throws InterruptedException {

		while(true) {
			prepareGame();
			boolean existWinner = playRounds();

			if (!existWinner){
				break;
			}
			else {
				graphics.showMessage("Press Enter to start another game!");
				graphics.readInput();
			}

			board.resetBoard();
		}
    }

    public static void main(String[] args) throws InterruptedException{

        Backgammon game = new Backgammon();
        game.startGame();

    }

}
