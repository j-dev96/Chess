package gui;

import Chess.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.*;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

public class Panel extends JComponent implements MouseListener, PlayerInterface, GameInterfaceListener {

    private static final int BOARD_SIZE = 100;

    private static final double WIDTH = 200, HEIGHT = 200;

    private static final Shape TILE =
            new Rectangle2D.Double(0, 0, WIDTH, HEIGHT);

    private static final Shape HIGHLIGHT = TILE;

    private Board board;

    private Position isSelected = null;

    private Moves moves = null;

    private Interact interact = Interact.HALT;

    private Piece.Color color;

    private CountDownLatch latch;


    private Move selectedMove;


    public Panel(final Board display) {
        board = display;
        addMouseListener(this);
    }

    @Override
    public final Dimension getPreferredSize() {
        return new Dimension(BOARD_SIZE * board.getWidth(),
                BOARD_SIZE * board.getHeight());
    }

    public final void setPanel(final Board b) {
        board = b;
        repaint();
    }
    //scales drawing and work panel
    private AffineTransform getTransform() {
        AffineTransform at = new AffineTransform();
        at.scale(getHeight() / (HEIGHT * board.getHeight()), getWidth() / (WIDTH * board.getWidth()));
        return at;
    }

    public final void paintComponent(final Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        int h = board.getHeight();
        int w = board.getWidth();
        g.transform(getTransform());

        AffineTransform at = new AffineTransform();

        //Draws board
        for (int y = 0; y < h; y++) {
            int x = 0;
            while (x < w) {
                if ((x + y) % 2 == 0) {
                    g.setColor(Color.white);//light tiles
                } else {
                    g.setColor(Color.black);//dark tiles
                }
                at.setToTranslation(x * WIDTH, y * HEIGHT);
                g.fill(at.createTransformedShape(TILE));
                x++;
            }
        }

        //Places the pieces
        int y = 0;
        while (y < h) {
            int x = 0;
            while (x < w) {
                Piece piece = board.getPiece(new Position(x, y));
                if (piece != null) {
                    Image tileImage = piece.getImage();
                    int y2;
                    y2 = board.getHeight() - 1 - y;
                    at.setToTranslation(x * WIDTH, y2 * HEIGHT);
                    g.drawImage(tileImage, at, null);
                }
                x++;
            }
            y++;
        }

        //Show previous move
        Move last = board.previous();
        if (last != null) {
            g.setColor(Color.pink);
            highlight(g, last.getOrigin());
            g.setColor(Color.red);
            highlight(g, last.getDest());
        }

        //Show square you clicked on
        if (isSelected != null) {
            g.setColor(Color.cyan);
            highlight(g, isSelected);

            //Draw move
            if (moves != null) {
                for (Iterator<Move> iterator = moves.iterator(); iterator.hasNext(); ) {
                    Move move = iterator.next();
                    highlight(g, move.getDest());
                }
            }
        }
    }

    private void highlight(final Graphics2D g, final Position pos) {
        int x = pos.getX();
        int y = board.getHeight() - 1 - pos.getY();
        g.setStroke(new BasicStroke(15));
        AffineTransform at = new AffineTransform();
        at.translate(x * WIDTH, y * HEIGHT);
        g.draw(at.createTransformedShape(HIGHLIGHT));
    }

    @Override
    public final void mouseReleased(final MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) leftClick(e);
        repaint();
    }


    private void leftClick(final MouseEvent e) {
        if (interact == Interact.HALT) {//prevents player from clicking when computers turn
            return;
        }

        Position pos = getPixelPosition(e.getPoint()); //get where they clicked
        if (!board.inRange(pos)) {
            return;
        }
        if (pos != null) {
            if (pos.equals(isSelected)) {
                isSelected = null;
                moves = null;
            } else if (moves != null && moves.containsDest(pos)) {//Move Piece
                interact = Interact.HALT;
                Move move = moves.getMoveByDest(pos);
                isSelected = null;
                moves = null;
                selectedMove = move;
                latch.countDown();
            } else {
                Piece p;
                p = board.getPiece(pos);
                if (!(p == null || p.getColor() != color)) {
                    isSelected = pos;//select piece
                    moves = p.generateMoves(true);
                }
            }
        }
    }


    private Position getPixelPosition(final Point2D p) {
        Point2D pout=null;
        try {
            pout = getTransform().inverseTransform(p,null);
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }
        int x = (int) (pout.getX() / WIDTH);
        int y = (int) (pout.getY() / HEIGHT);
            y = board.getHeight() - 1 - y;
        return new Position(x, y);
    }

    public final Move takeTurn(final Board turnPanel,
                               final Piece.Color currentColor) {
        latch = new CountDownLatch(1);
        board = turnPanel;
        color = currentColor;
        repaint();
        interact = Interact.PLAYER;
        try {
            latch.await();
        } catch (InterruptedException e) {

        }
        return selectedMove;
    }

    @Override
    public final void gameEvent(final GameStatus e) {
        board = e.getGame().getBoard();
        if (e.getType() != GameStatus.STATUS) {
            repaint();
        }
    }

    @Override
    public void mouseExited(final MouseEvent e) {

    }

    @Override
    public void mouseEntered(final MouseEvent e) {

    }

    @Override
    public void mouseClicked(final MouseEvent e) {

    }

    @Override
    public void mousePressed(final MouseEvent e) {

    }

    private enum Interact {

        HALT,//wait for player

        PLAYER//players turn!
    }
}
