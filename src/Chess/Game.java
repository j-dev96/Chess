package Chess;

import javax.swing.*;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;

import static Chess.Piece.*;
import static Chess.Piece.Color.*;

public class Game implements Runnable {

    private final Collection<GameInterfaceListener> listening =
            new CopyOnWriteArraySet<>();

    private Board board;

    private PlayerInterface white;

    private PlayerInterface black;

    private Color turn;

    private volatile Boolean done = false;

    public Game(final Board gameBoard) {
        board = gameBoard;
    }

    public final void set(final PlayerInterface whitePlayerInterface,
                          final PlayerInterface blackPlayerInterface) {
        white = whitePlayerInterface;
        black = blackPlayerInterface;
    }

    public final void end() {
        listening.clear();
        done = true;
    }

    public final void begin() {
        done = false;
        turn = B;
        GameState(GameStatus.TURN);
        new Thread(this).start();
    }

    @Override
    public final void run() {
        if (!done) {
            do {
                PlayerInterface player;
                if (turn == W) {
                    turn = B;
                    player = black;
                } else {
                    turn = W;
                    player = white;
                }
                Move move;//get move
                move = player.takeTurn(getBoard(), turn);
                board.move(move);
                if (done) {
                    return;
                }
                Color opp = opposite(turn);
                if (checkEndGame(opp)) return;//check if games over
            }while (!done);
        }
        }
    private boolean checkEndGame(Color opp) {
        if (!board.checkmate(opp)) {
            if (!board.stalemate(opp)) {
                GameState(GameStatus.TURN);
            } else {
                done = true;
                GameState(GameStatus.GAME_END);
                JOptionPane.showMessageDialog(null, "Stalemate!");
                return true;
            }
        } else {
            done = true;
            GameState(GameStatus.GAME_END);
            JOptionPane.showMessageDialog(null, opp == B ? "White Wins!" : "Black Wins!");
            return true;
        }
        return false;
    }
    //get board
    public final Board getBoard() {
        return board.copy();
    }

    public final void addGameListener(final GameInterfaceListener listener) {
        listening.add(listener);
    }

    private void GameState(final int type) {
        listening.forEach(listen -> listen.gameEvent(new GameStatus(this, type)));
    }
}
