package pieces;

import Chess.*;


public class Pawn extends Piece {

    public Pawn(final Color color) {
        super(color, "P");
    }

    @Override
    public final Moves generateMoves(final boolean check) {
        Moves list = new Moves(getBoard(), check);
        Position pos = getPos();
        Board board = getBoard();
        int dir = direction();
        Position dest = new Position(pos, 0, 1 * dir);
        Move first = new Move(pos, dest);
        addUpgrade(first);
        if (list.addMove(first) && !moved()) {
            list.addMove(new Move(pos, new Position(pos, 0, 2 * dir)));
        }
        Move capLeft = new Move(pos, new Position(pos, -1, 1 * dir));
        addUpgrade(capLeft);
        list.addCaptureOnly(capLeft);
        Move capRight = new Move(pos, new Position(pos, 1, 1 * dir));
        addUpgrade(capRight);
        list.addCaptureOnly(capRight);

        //en passant
        Move last = board.previous();
        if (last != null) {
            Position leftpassant = new Position(pos, -1, 0);
            Position rightpassant = new Position(pos, 1, 0);
            Position PawnOrigin = last.getOrigin();
            Position PawnDestination = last.getDest();
            if (!leftpassant.equals(PawnDestination) || (PawnOrigin.getX() != PawnDestination.getX()) ||
                    (PawnOrigin.getY() != PawnDestination.getY() + dir * 2) || (!(board.getPiece(leftpassant) instanceof Pawn))) {
                if (rightpassant.equals(PawnDestination) &&
                        (PawnOrigin.getX() == PawnDestination.getX()) &&
                        (PawnOrigin.getY() == PawnDestination.getY() + dir * 2) &&
                        (board.getPiece(rightpassant) instanceof Pawn)) {

                    //right
                    Move passantRight = new Move(pos, new Position(pos, 1, dir));
                    passantRight.setNext(new Move(rightpassant, null));
                    list.addMove(passantRight);
                }
            } else {

        //left
        Move passantLeft = new Move(pos, new Position(pos, -1, dir));
        passantLeft.setNext(new Move(leftpassant, null));
        list.addMove(passantLeft);
    }
        }
        return list;
    }

    private void addUpgrade(final Move move) {
        if (move.getDest().getY() != upgradeRow()) {
            return;
        }
        move.setNext(new Move(move.getDest(), null)); // remove the pawn
        Move upgrade = new Move(null, move.getDest());
        upgrade.setReplacement("Queen");
        upgrade.setReplaceColor(getColor());
        move.getNext().setNext(upgrade);              // add queen
    }

    private int direction() {//check which color to indicate how pawn operates
        if (getColor() == Color.W) {
            return 1;
        } else {
            return -1;
        }
    }

    private int upgradeRow() {
        if (getColor() == Color.B) {
            return 0;
        } else {
            return getBoard().getHeight() - 1;
        }
    }
}
