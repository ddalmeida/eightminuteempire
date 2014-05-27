package logic.states;

import logic.game.Game;

public class MoveArmyState extends StateAdapter {

    private int x;

    public MoveArmyState(Game game, int x) {
        super(game);
        this.x = x;
    }

    @Override
    public State moveArmy() {
        x--;
        if (x == 0) {
            return this.endTurn();
        } else {
            return this;
        }
    }

    @Override
    public State endTurn() {
        game.nextPlayer();
        return new BuyCardState(game);
    }

    @Override
    public int getX() {
        return x;
    }
}
