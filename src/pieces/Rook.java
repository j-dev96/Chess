package pieces;

import Chess.Move;
import Chess.Moves;
import Chess.Piece;
import Chess.Position;

public class Rook extends Piece {

    public Rook(final Color color) {
        super(color, "R");
    }

    public static Moves getMoves(final Piece p,
                                 final Moves list) {
        Position home = p.getPos();
        int x = home.getX();
        int y = home.getY();
        if (x >= 0) {
            do {
                x--;
                Position pos = new Position(x, y);
                if (!list.addCapture(new Move(home, pos))) {
                    break;
                }
                if (!p.getBoard().isFree(pos)) {
                    break;
                }
            } while (x >= 0);
        }
        x = home.getX();
        y = home.getY();
        while (true) {
            if (!(x < p.getBoard().getWidth())) break;
            x++;
            Position pos = new Position(x, y);
            if (!list.addCapture(new Move(home, pos))) {
                break;
            }
            if (!p.getBoard().isFree(pos)) {
                break;
            }
        }
        x = home.getX();
        y = home.getY();
        while (true) {
            if (!(y >= 0)) break;
            y--;
            Position pos = new Position(x, y);
            if (!list.addCapture(new Move(home, pos))) {
                break;
            }
            if (!p.getBoard().isFree(pos)) {
                break;
            }
        }
        x = home.getX();
        y = home.getY();
        if (y < p.getBoard().getHeight()) {
            do {
                y++;
                Position pos = new Position(x, y);
                if (!list.addCapture(new Move(home, pos))) {
                    break;
                }
                if (!p.getBoard().isFree(pos)) {
                    break;
                }
            } while (y < p.getBoard().getHeight());
        }
        return list;
    }

    @Override
    public final Moves generateMoves(final boolean check) {
        Moves list = new Moves(getBoard(), check);
        list = getMoves(this, list);
        return list;
    }
}
