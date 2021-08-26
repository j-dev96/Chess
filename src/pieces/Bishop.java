package pieces;

import Chess.Move;
import Chess.Moves;
import Chess.Piece;
import Chess.Position;

public class Bishop extends Piece {


    public Bishop(final Color color) {
        super(color, "B");
    }

    public static Moves generateMoves(final Piece p,
                                      final Moves list) {
        Position home = p.getPos();
        int x = home.getX();
        int y = home.getY();
        while (true) {
            if (!(x >= 0 && y >= 0)) break;
            x--;
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
        if (x < p.getBoard().getWidth() &&
                y < p.getBoard().getHeight()) {
            do {

                x++;
                y++;
                Position pos = new Position(x, y);
                if (!list.addCapture(new Move(home, pos))) {
                    break;
                }
                if (!p.getBoard().isFree(pos)) {
                    break;
                }
            } while (x < p.getBoard().getWidth() &&
                    y < p.getBoard().getHeight());
        }
        x = home.getX();
        y = home.getY();
        while (true) {
            if (!(x >= 0 && y < p.getBoard().getHeight())) break;
            x--;
            y++;
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
        if (x >= p.getBoard().getWidth() || y < 0) {
            return list;
        }
        do {
            x++;
            y--;
            Position pos = new Position(x, y);
            if (!list.addCapture(new Move(home, pos))) {
                break;
            }
            if (!p.getBoard().isFree(pos)) {
                break;
            }
        } while (x < p.getBoard().getWidth() && y >= 0);
        return list;
    }

    @Override
    public final Moves generateMoves(final boolean check) {
        Moves list = new Moves(getBoard(), check);
        list = generateMoves(this, list);
        return list;
    }
}
