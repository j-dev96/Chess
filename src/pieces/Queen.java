package pieces;

import Chess.Moves;
import Chess.Piece;

public class Queen extends Piece {

    public Queen(final Color color) {
        super(color, "Q");
    }

    @Override
    public final Moves generateMoves(final boolean check) {
        Moves list = new Moves(getBoard(), check);
        list = Rook.getMoves(this, list);
        list = Bishop.generateMoves(this, list);//bishop + rook moves
        return list;
    }
}
