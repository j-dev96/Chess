package Chess;

public final class Move {

    private final Position origin;

    private final Position destination;

    private Move next;

    private Piece captured;

    private String replacement;

    private Piece.Color replaceColor;

    private double score;

    public Move(final Position orig, final Position dest) {
        destination = dest;
        origin = orig;
        next = null;
    }

    public Move(final Move move) {
        this(move.getOrigin(), move.getDest());
        captured = move.getCaptured();
        replacement = move.getReplacement();
        replaceColor = move.getReplaceColor();
        if (move.getNext() != null) {
            next = new Move(move.getNext());
        }
    }

    public Move getNext() {
        return next;
    }

    public void setNext(final Move move) {
        next = move;
    }

    public Position getOrigin() {
        return origin;
    }

    public Position getDest() {
        return destination;
    }

    public Piece getCaptured() {
        return captured;
    }

    public void setCaptured(final Piece p) {
        captured = p;
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(final String pieceName) {
        replacement = pieceName;
    }

    public Piece.Color getReplaceColor() {
        return replaceColor;
    }

    public void setReplaceColor(final Piece.Color color) {
        replaceColor = color;
    }

    @Override
    public String toString() {
        return "" + origin + destination;
    }

    public double getScore() {
        return score;
    }

    public void setScore(final double newscore) {
        this.score = newscore;
    }
}
