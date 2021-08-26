package gui;

import Chess.PlayerInterface;
import ai.AlphaBeta;
import Chess.Board;
import Chess.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//new Game Dialog popup
class NewGame extends JDialog implements ActionListener {

    private final Frame frame;
    private final PlayerSelected white;
    private final PlayerSelected black;


    public NewGame(final Frame chess) {
        super(chess, "New Game", true);
        frame = chess;
        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(chess);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        white = new PlayerSelected("White:", true);
        black = new PlayerSelected("Black:", false);
        white.setAlignmentX(Component.LEFT_ALIGNMENT);
        black.setAlignmentX(Component.RIGHT_ALIGNMENT);
        add(white, BorderLayout.WEST);
        add(black, BorderLayout.EAST);

        JButton start = new JButton("Start!");
        start.addActionListener(this);
        add(start, BorderLayout.SOUTH);
        pack();
    }

    @Override
    public final void actionPerformed(final ActionEvent e) {
        if ("Start!".equals(e.getActionCommand())) {
        }
        setVisible(false);
        dispose();
    }

    private PlayerInterface createPlayer(final Game game, String player) {
        if ("human".equals(player)) {
            return frame.getPlayer();
        } else {
            return new AlphaBeta(game, PlayerSelected.level_ai);
        }
    }

    private Board createBoard() {
        return new Standard();
    }

    public final Game getGame() {
        Game game = new Game(createBoard());
        PlayerInterface white = createPlayer(game, this.white.getPlayer());
        PlayerInterface black = createPlayer(game, this.black.getPlayer());
        game.set(white, black);
        return game;
    }
}
