package pieces;

import Chess.Move;
import Chess.Moves;
import Chess.Piece;
import Chess.Position;


public class King extends Piece {


    private Moves enemy;


    private Boolean inCheck;


    public King(final Color color) {
        super(color, "K");
    }

    @Override
    public final Moves generateMoves(final boolean check) {
        Moves list = new Moves(getBoard(), check);
        Position pos = getPos();
        int y = -1;
        while (y <= 1) {
            int x = -1;
            while (x <= 1) {
                if (x != 0 || y != 0) {
                    list.addCapture(new Move(pos, new Position(pos, x, y)));
                }
                x++;
            }
            y++;
        }
        enemy = null;
        inCheck = null;
        if (!check || moved()) {
            return list;
        }
        Move left = castle(-1);
        if (left == null) {
        } else {
            list.add(left);
        }
        Move right = castle(1);
        if (right == null) {
            return list;
        }
        list.add(right);
        return list;
    }

    private Move castle(final int dir) { //castle method in given direction
        int dist = getBoard().getWidth() / 2 - 2;
        Position kingpos = getPos();

        int max;
        max = dir < 0 ? 0 : getBoard().getWidth() - 1;

        Position rookPos = new Position(max, kingpos.getY());
        Piece rook = getBoard().getPiece(rookPos);
        if (rook == null || rook.moved()) {
            return null;
        }

        if (safeCastle(getPos(), dir, max) && !inCheck()) {
            Position kingPos = new Position(kingpos, dir * dist, 0);
            Move kingDest = new Move(kingpos, kingPos);
            Position rookpos = new Position(kingpos, dir * dist - dir, 0);
            Move rookDest = new Move(rookPos, rookpos);
            kingDest.setNext(rookDest);
            return kingDest;
        }
        return null;
    }


    private boolean safeCastle(final Position start, final int dir,//check castle row for threats
                               final int max) {
        for (int i = start.getX() + dir; i != max; i += dir) {
            Position pos = new Position(i, start.getY());
            if (getBoard().getPiece(pos) != null ||
                    enemyThreat().containsDest(pos)) {

                return false;
            }
        }
        return true;
    }

    private Moves enemyThreat() {//check if enemy is threatening castle
        if (enemy != null) {
            return enemy;
        }
        enemy = getBoard().allMoves(opposite(getColor()), false);
        return enemy;
    }

    private boolean inCheck() {//returns true if king in check
        if (inCheck != null) {
            return inCheck;
        }
        inCheck = getBoard().inCheck(getColor());
        return inCheck;
    }
}
