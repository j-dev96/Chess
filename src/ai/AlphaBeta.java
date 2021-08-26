package ai;

import Chess.*;
import pieces.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AlphaBeta implements PlayerInterface {

     final Game game;

    private final Map<Class, Double> weight;

    private final int Depth;

    private final double weightMaterial;

    private final double weightSafety;

    private final double weightMobility;

    private Piece.Color color;

    private Move optimalMove;

    public AlphaBeta(final Game active, final int level) {
        game = active;
        weight = new HashMap<>();
        //weigh pieces
        weight.put((new Pawn(color)).getClass(), 1.0);
        weight.put((new Knight(color)).getClass(), 5.0);
        weight.put((new Bishop(color)).getClass(), 5.0);
        weight.put((new Rook(color)).getClass(), 9.0);
        weight.put((new Queen(color)).getClass(), 15.0);
        weight.put((new King(color)).getClass(), 9999.0);

        Depth = level;
        weightMaterial = 1.0;
        weightSafety = 0.20;
        weightMobility = 0.02;
    }

    @Override
    public final Move takeTurn(Board board,
                               Piece.Color currentColor) {
        color = currentColor;
        //Gather all the moves
        Moves moves = board.allMoves(color, true);
        Moves movelist = board.allMoves(color,true);
        Move[] m = new Move[board.allMoves(color,true).size()];
        moves.shuffle();
        optimalMove = null;
        for (Iterator<Move> iterator = moves.iterator(); iterator.hasNext(); ) {
            Move move = iterator.next();
            move = moveList(move,board.copy());
            movelist.add(move);
        }
        //then pick the best move
        for (int i = 0; i < movelist.size(); i++) {
                m[i] = movelist.pop();
                if (optimalMove == null || m[i].getScore() > optimalMove.getScore()) {
                    optimalMove = m[i];
                }
            }
        return optimalMove;
    }
    public Move moveList(Move move, Board board){
        board.move(move);
        double beta = Double.POSITIVE_INFINITY;
        if (optimalMove != null) {
            beta = -optimalMove.getScore();
        }
        double alpha = alphaBeta(board, Depth - 1, Piece.opposite(color), Double.NEGATIVE_INFINITY, beta);
        move.setScore(-alpha);
        return move;
    }

    //AI searches for best move recursively
    private double alphaBeta(final Board b, final int depth, final Piece.Color s,
                             final double alpha, final double beta) {
        if (depth == 0) {
            double v = valuate(b);
            return (s != color) ? -v : v;
        }
        Piece.Color opp = Piece.opposite(s);
        double best = alpha;
        Moves list = b.allMoves(s, true);
        for (Iterator<Move> iterator = list.iterator(); iterator.hasNext(); ) {
            Move move = iterator.next();
            b.move(move);
            best = Math.max(best, -alphaBeta(b, depth - 1, opp, -beta, -best));
            b.undo();
            if (beta <= best) { //alphabeta pruning
                return best;
            }
        }
        return best;
    }

    //determine value on board
    private double valuate(final Board b) {
        double material = materialValue(b);
        double kingSafety = kingSafety(b);
        double mobility = mobilityValue(b);
        return material * weightMaterial + kingSafety * weightSafety + mobility * weightMobility;
    }
    private double materialValue(final Board b) {
        double value = 0;
        int y = 0;
        while (y < b.getHeight()) {
            int x = 0;
            while (x < b.getWidth()) {
                Position pos = new Position(x, y);
                Piece p = b.getPiece(pos);
                if (p != null) {
                    value += weight.get(p.getClass()) * p.getColor().value();
                }
                x++;
            }
            y++;
        }
        return value * color.value();
    }

    //higher is worse
    private double kingSafety(final Board b) {
        return kingSafety(b, Piece.opposite(color)) -
                kingSafety(b, color);
    }
    private double kingSafety(final Board b, final Piece.Color s) {
        Position king = b.findKing(s);
        if (king == null) {
            return Double.POSITIVE_INFINITY;
        }
        Moves list = new Moves(b, false);
        Rook.getMoves(b.getPiece(king), list);
        Bishop.generateMoves(b.getPiece(king), list);
        return list.size();
    }

    private double mobilityValue(final Board b) {
        return b.allMoves(color, false).size() -
                b.allMoves(Piece.opposite(color), false).size();
    }

}
