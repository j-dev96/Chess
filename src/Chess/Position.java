package Chess;

public final class Position implements Comparable<Position> {

    private final int x, y;

    public Position(final int posX, final int posY) {
        x = posX;
        y = posY;
    }

    public Position(final Position pos, final int deltax, final int deltay) {
        this(pos.x + deltax, pos.y + deltay);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return "" + ((char) ('a' + (char) x)) + (y + 1);
    }

    public boolean equals(final Position pos) {
        return (pos != null) && (pos.x == x) && (pos.y == y);
    }

    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }
        return equals((Position) o);
    }

    public int hashCode() {
        return x ^ y;
    }

    public int compareTo(final Position pos) {
        if (pos.y == y) {
            return x - pos.x;
        } else {
            return y - pos.y;
        }
    }
}
