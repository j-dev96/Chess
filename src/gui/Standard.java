package gui;

import pieces.*;
import Chess.Board;
import Chess.Piece;
import Chess.Position;

import java.util.stream.IntStream;

public class Standard extends Board {


    private static final int WPR = 1; //White Pawn Row
    private static final int BPR = 6;
    private static final int WHITE = 0; //White back Row
    private static final int BLACK = 7;
    private static final int LEFT_ROOK = 0;
    private static final int LEFT_KNIGHT = 1;
    private static final int LEFT_BISHOP = 2;
    private static final int QUEEN = 3;
    private static final int KING = 4;
    private static final int RIGHT_BISHOP = 5;
    private static final int RIGHT_KNIGHT = 6;
    private static final int RIGHT_ROOK = 7;

    public Standard() {
        setWidth(8);
        setHeight(8);
        clear();
        IntStream.range(0, this.getWidth()).forEach(x -> {
            placePiece(x, WPR, new Pawn(Piece.Color.W));
            placePiece(x, BPR, new Pawn(Piece.Color.B));
        });
        placePiece(LEFT_ROOK, WHITE, new Rook(Piece.Color.W)); //Place a white left rook
        placePiece(RIGHT_ROOK, WHITE, new Rook(Piece.Color.W));
        placePiece(LEFT_ROOK, BLACK, new Rook(Piece.Color.B)); //Place a black left rook [Position,color, new Piece]
        placePiece(RIGHT_ROOK, BLACK, new Rook(Piece.Color.B));
        placePiece(LEFT_KNIGHT, WHITE, new Knight(Piece.Color.W));
        placePiece(RIGHT_KNIGHT, WHITE, new Knight(Piece.Color.W));
        placePiece(LEFT_KNIGHT, BLACK, new Knight(Piece.Color.B));
        placePiece(RIGHT_KNIGHT, BLACK, new Knight(Piece.Color.B));
        placePiece(LEFT_BISHOP, WHITE, new Bishop(Piece.Color.W));
        placePiece(RIGHT_BISHOP, WHITE, new Bishop(Piece.Color.W));
        placePiece(LEFT_BISHOP, BLACK, new Bishop(Piece.Color.B));
        placePiece(RIGHT_BISHOP, BLACK, new Bishop(Piece.Color.B));
        placePiece(QUEEN, WHITE, new Queen(Piece.Color.W));
        placePiece(QUEEN, BLACK, new Queen(Piece.Color.B));
        placePiece(KING, WHITE, new King(Piece.Color.W));
        placePiece(KING, BLACK, new King(Piece.Color.B));
    }

    @Override
    public final Boolean checkmate(final Piece.Color color) {
        return inCheck(color) && (CountMoves(color) == 0);
    }

    @Override
    public final Boolean stalemate(final Piece.Color color) {
        return (!inCheck(color)) && (CountMoves(color) == 0);
    }

    //returns the amount of moves a player has
    private int CountMoves(final Piece.Color color) {
        int count = 0;
        int y = 0;
        while (y < getHeight()) {
            int x = 0;
            while (x < getWidth()) {
                Piece p = getPiece(new Position(x, y));
                if ((p == null) || (p.getColor() != color)) {
                } else {
                    count += p.generateMoves(true).size();
                }
                x++;
            }
            y++;
        }
        return count;
    }

    @Override
    public final Boolean inCheck(final Piece.Color color) {
        Piece.Color enemyAttack;
        enemyAttack = color == Piece.Color.W ? Piece.Color.B : Piece.Color.W;
        Position kingPos = findKing(color);
        if (kingPos == null) return false;
        int y = 0;
        while (y < getHeight()) {
            int x = 0;
            while (x < getWidth()) {
                Piece p = getPiece(new Position(x, y));
                if ((p == null) || (p.getColor() != enemyAttack) || !p.generateMoves(false).containsDest(kingPos)) {
                    x++;
                } else {
            return true;
        }
            }
            y++;
        }
        return false;
    }
}
