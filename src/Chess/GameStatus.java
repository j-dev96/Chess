package Chess;

public class GameStatus {

    public static final int TURN = 0;

    public static final int STATUS = 1;

    public static final int GAME_END = 2;

    private final int type;

    private final Game game;


    public GameStatus(final Game where, final int eventtype) {
        game = where;
        type = eventtype;
    }

    public final int getType() {
        return type;
    }

    public final Game getGame() {
        return game;
    }
}
