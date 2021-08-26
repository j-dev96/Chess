package Chess;

import gui.Standard;
import pieces.King;
import pieces.CreateQueen;

import java.util.Iterator;

public abstract class Board {

    private int boardWidth, boardHeight; //self explanatory

    private Piece[][] board; //board array with pieces

    public final Moves moves = new Moves(this);

    protected final void clear() {
        board = new Piece[boardWidth][boardHeight];
    }

    public abstract Boolean checkmate(Piece.Color color);

    public abstract Boolean stalemate(Piece.Color color);

    public abstract Boolean inCheck(Piece.Color color);

    public final Position findKing(final Piece.Color color) {
        int y = 0;
        while (y < getHeight()) {
            int x = 0;
            while (x < getWidth()) {
                Position position = new Position(x, y);
                Piece p = getPiece(position);
                if (p instanceof King &&
                        p.getColor() == color) {

                    return position;
                }
                x++;
            }
            y++;
        }
        return null;
    }

    public final int getWidth() {
        return boardWidth;
    }

    protected final void setWidth(final int width) {
        boardWidth = width;
    }

    public final int getHeight() {
        return boardHeight;
    }

    protected final void setHeight(final int height) {
        boardHeight = height;
    }

    protected final void placePiece(final int x, final int y, final Piece p) {
        placePiece(new Position(x, y), p);
    }

    private void placePiece(final Position pos, final Piece p) {
        board[pos.getX()][pos.getY()] = p;
        if (p != null) {
            p.setPosition(pos);
            p.setBoard(this);
        }
    }

    public final Piece getPiece(final Position pos) {
        return board[pos.getX()][pos.getY()];
    }


    public final void move(final Move move) {
        moves.add(move);
        execMove(move);
    }


    private void execMove(final Move move) {
        if (move == null) {
            return;
        }
        Position origin = move.getOrigin();
        Position destination = move.getDest();
        if (origin != null && destination != null) {
            move.setCaptured(getPiece(destination));
            placePiece(destination, getPiece(origin));
            placePiece(origin, null);
            getPiece(destination).setPosition(destination);
            getPiece(destination).moved++;
        } else if (origin != null && destination == null) {
            move.setCaptured(getPiece(origin));
            placePiece(origin, null);
        } else {
            placePiece(destination, CreateQueen.create(move.getReplacement(), move.getReplaceColor()));
        }
        execMove(move.getNext());
    }

    public final void undo() {
        execUndo(moves.pop());
    }

    public void execUndo(final Move move) {
        if (move == null) {
            return;
        }
        execUndo(move.getNext()); // undo in reverse
        Position origin = move.getOrigin();
        Position destination = move.getDest();
        if (origin != null && destination != null) {
            placePiece(origin, getPiece(destination));
            placePiece(destination, move.getCaptured());
            getPiece(origin).setPosition(origin);
            getPiece(origin).moved--;
        } else if (origin != null && destination == null) {
            placePiece(origin, move.getCaptured());
        } else {
            placePiece(destination, null);
        }
    }

    public final Move previous() {
        return moves.peek();
    }

    private Boolean isEmpty(final Position pos) {
        return getPiece(pos) == null;
    }

    private Boolean isEmpty(final Position pos, final Piece.Color color) {
        Piece p = getPiece(pos);
        if (p == null) {
            return true;
        }
        return p.getColor() != color;
    }

    public final Boolean inRange(final Position pos) {
        return (pos.getX() >= 0) && (pos.getY() >= 0) &&
                (pos.getX() < boardWidth) && (pos.getY() < boardHeight);
    }

    public final Boolean isFree(final Position pos) {
        return inRange(pos) && isEmpty(pos);
    }

    public final Boolean isFree(final Position pos, final Piece.Color color) {
        return inRange(pos) && isEmpty(pos, color);
    }

    public final Board copy() {
        Board copiedBoard = new Standard();
        for (Iterator<Move> iterator = moves.iterator(); iterator.hasNext(); ) {
            Move move = iterator.next();
            copiedBoard.move(new Move(move));
        }
        return copiedBoard;
    }

    public final Moves allMoves(final Piece.Color color, final boolean check) {
        Moves list = new Moves(this, false);
        int y = 0;
        while (y < boardHeight) {
            for (int x = 0; x < boardWidth; x++) {
                Piece p = board[x][y];
                if (p != null && p.getColor() == color) {
                    list.addAll(p.generateMoves(check));
                }
            }
            y++;
        }
        return list;
    }

}
