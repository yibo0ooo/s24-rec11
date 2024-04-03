package edu.cmu.cs.cs214.rec11.plugin;

import edu.cmu.cs.cs214.rec11.framework.core.GameFramework;
import edu.cmu.cs.cs214.rec11.framework.core.GamePlugin;
import edu.cmu.cs.cs214.rec11.games.TicTacToe;
import edu.cmu.cs.cs214.rec11.games.TicTacToe.Player;

/**
 * An example Tic-Tac-Toe game plug-in.
 */
public class TTTPlugin implements GamePlugin<String> {

    private static final String GAME_NAME = "Tic Tac Toe";
    private TicTacToe game;
    private GameFramework framework;

    @Override
    public String getGameName() {
        return GAME_NAME;
    }

    @Override
    public int getGridWidth() {
        return TicTacToe.SIZE;
    }

    @Override
    public int getGridHeight() {
        return TicTacToe.SIZE;
    }

    @Override
    public void onRegister(GameFramework f) {
        this.framework = f;
    }

    @Override
    public void onNewGame() {
        this.game = new TicTacToe();
        for (int x = 0; x < TicTacToe.SIZE; x++) {
            for (int y = 0; y < TicTacToe.SIZE; y++) {
                framework.setSquare(x, y, "");
            }
        }
        framework.setFooterText("Player X's turn");
    }

    @Override
    public void onNewMove() {
        // Not used in this implementation.
    }

    @Override
    public boolean isMoveValid(int x, int y) {
        return game.isValidPlay(x, y);
    }

    @Override
    public boolean isMoveOver() {
        // A move in Tic-Tac-Toe ends immediately after it's made.
        return true;
    }

    @Override
    public void onMovePlayed(int x, int y) {
        game.play(x, y);
        framework.setSquare(x, y, game.getSquare(x, y).toString());
        if (game.isOver()) {
            Player winner = game.winner();
            if (winner != null) {
                framework.setFooterText(winner + " wins!");
            } else {
                framework.setFooterText("The game ended in a tie.");
            }
        } else {
            framework.setFooterText("Player " + game.currentPlayer() + "'s turn");
        }
    }

    @Override
    public boolean isGameOver() {
        return game.isOver();
    }

    @Override
    public String getGameOverMessage() {
        Player winner = game.winner();
        if (winner != null) {
            return winner + " wins!";
        } else if (game.isOver()) {
            return "The game ended in a tie.";
        } else {
            return "";
        }
    }

    @Override
    public void onGameClosed() {
        // Not used in this implementation.
    }

    @Override
    public String currentPlayer() {
        return "Player " + game.currentPlayer();
    }
}
