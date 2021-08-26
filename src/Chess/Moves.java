package Chess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

public class Moves implements Iterable<Move> {

    private final ArrayList<Move> moves = new ArrayList<>();

    private final Board board;

    private final boolean check;

    public Moves(final Board verifyBoard) {
        this(verifyBoard, true);
    }

    public Moves(final Board verifyBoard, final boolean checkCheck) {
        board = verifyBoard;
        check = checkCheck;
    }

    public final void add(final Move move) {
        moves.add(move);
    }

    public final void addAll(final Iterable<Move> list) {
        for (Iterator<Move> iterator = list.iterator(); iterator.hasNext(); ) {
            Move move = iterator.next();
            moves.add(move);
        }
    }

    public final boolean addMove(final Move move) {
        if (board.isFree(move.getDest())) {
            if (causesCheck(move)) {
                add(move);
                return true;
            }
            return true;
        }
        return false;
    }
    public final boolean addCapture(final Move move) {
        Piece p = board.getPiece(move.getOrigin());
        if (board.isFree(move.getDest(), p.getColor())) {
            if (causesCheck(move)) {
                add(move);
                return true;
            }
            return true;
        }
        return false;
    }

    public final void addCaptureOnly(final Move move) {
        Piece p = board.getPiece(move.getOrigin());
        if (board.isFree(move.getDest(), p.getColor()) &&
                !board.isFree(move.getDest()) &&
                causesCheck(move)) {

            add(move);
        }
    }

    private boolean causesCheck(final Move move) {
        if (check) {
            Piece p = board.getPiece(move.getOrigin());
            board.move(move);
            boolean ret = board.inCheck(p.getColor());
            board.undo();
            return !ret;
        } else {
            return true;
        }
    }

    public final boolean containsDest(final Position pos) {
        for (Iterator<Move> iterator = this.iterator(); iterator.hasNext(); ) {
            Move move = iterator.next();
            if (Objects.equals(pos, move.getDest())) return true;
        }
        return false;
    }

    public final Move getMoveByDest(final Position dest) {
        for (Iterator<Move> iterator = this.iterator(); iterator.hasNext(); ) {
            Move move = iterator.next();
            if (Objects.equals(dest, move.getDest())) {
                return move;
            }
        }
        return null;
    }

    public final Move pop() {
        if (isEmpty()) {
            return null;
        }
        Move last = moves.get(moves.size() - 1);
        moves.remove(moves.size() - 1);
        return last;
    }

    public final Move peek() {
        if (isEmpty()) {
            return null;
        }
        return moves.get(moves.size() - 1);
    }

    public final int size() {
        return moves.size();
    }

    private boolean isEmpty() {
        return moves.isEmpty();
    }

    public final void shuffle() {
        Collections.shuffle(moves);
    }

    @Override
    public final Iterator<Move> iterator() {
        return moves.iterator();
    }
}
