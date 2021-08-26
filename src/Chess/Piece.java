package Chess;

import pieces.LoadImages;

import java.awt.*;

public abstract class Piece {

    public Color color;

    private Position pos;

    private Board board;

    public int moved = 0;

    private String name;

    protected Piece(final Color player, final String thePiece) {
        color = player;
        name = thePiece;
    }

    public static Color opposite(final Color s) {
        if (s == Color.B) {
            return Color.W;
        } else {
            return Color.B;
        }
    }

    public abstract Moves generateMoves(boolean checkCheck);

    public final Position getPos() {
        return pos;
    }

    public final void setPosition(final Position position) {
        pos = position;
    }

    public final Board getBoard() {
        return board;
    }

    public final void setBoard(final Board surface) {
        board = surface;
    }

    public final Color getColor() {
        return color;
    }

    public final Image getImage() {
        return LoadImages.getTileImage(color+name);
    }

    public final Boolean moved() {
        return moved != 0;
    }

    public enum Color {

        W(1),

        B(-1);

        private final int col; //color value, white is 1, black is -1.

        Color(final int val) {
            col = val;
        }

        public int value() {
            return col;
        }
    }
}
