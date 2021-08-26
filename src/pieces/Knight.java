package pieces;

import Chess.Move;
import Chess.Moves;
import Chess.Piece;
import Chess.Position;

public class Knight extends Piece {

    public Knight(final Color color) {
        super(color, "N");
    }

    private static Moves getMoves(final Piece p,
                                  final Moves list) {
        Position pos = p.getPos();
        list.addCapture(new Move(pos, new Position(pos, 1, 2))); //1 = short L
        list.addCapture(new Move(pos, new Position(pos, 2, 1))); //2 = long L
        list.addCapture(new Move(pos, new Position(pos, -2, 1)));
        list.addCapture(new Move(pos, new Position(pos, -2, -1)));
        list.addCapture(new Move(pos, new Position(pos, 2, -1)));
        list.addCapture(new Move(pos, new Position(pos, 1, -2)));
        list.addCapture(new Move(pos, new Position(pos, -1, -2)));
        list.addCapture(new Move(pos, new Position(pos, -1, 2)));
        return list;
    }

    @Override
    public final Moves generateMoves(final boolean check) {
        Moves list = new Moves(getBoard(), check);
        list = getMoves(this, list);
        return list;
    }
}
