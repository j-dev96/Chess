package gui;

import Chess.*;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Frame extends JFrame
        implements ComponentListener, GameInterfaceListener {

    private final Panel display;

    private Game game;

    public Frame() {
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Chess");
        setResizable(false);
        JMenuBar menu = new JMenuBar();
        JButton newGame = new JButton("New Game");
        newGame.addActionListener(e -> newGame());
        newGame.setAlignmentX(CENTER_ALIGNMENT);
        menu.add(newGame);
        setJMenuBar(menu);
        display = new Panel(new Standard());
        add(display);
        pack();

        addComponentListener(this);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void newGame() {
        NewGame ngFrame = new NewGame(this);
        ngFrame.setVisible(true);
        Game newGame = ngFrame.getGame();
        if (newGame == null) {
            return;
        }
        if (game != null) {
            game.end();
        }
        game = newGame;
        Board board = game.getBoard();
        display.setPanel(board);
        setSize(getPreferredSize());
        game.addGameListener(this);
        game.addGameListener(display);
        game.begin();
    }

    public final PlayerInterface getPlayer() { return display; }

    @Override
    public void gameEvent(GameStatus event) {

    }

    @Override
    public void componentHidden(final ComponentEvent e) {
    }

    @Override
    public void componentResized(ComponentEvent e) {

    }

    @Override
    public void componentMoved(final ComponentEvent e) {
    }

    @Override
    public void componentShown(final ComponentEvent e) {
    }
}

