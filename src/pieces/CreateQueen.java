package pieces;

import Chess.Piece;

public final class CreateQueen {

    public static Piece create(final String name, final Piece.Color color) {
        if ("Queen".equals(name)) {
            return new Queen(color);
        } else {
            return null;

        }
    }
}
