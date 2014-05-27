package logic.states;

import logic.game.Game;

public class PlaceArmyState extends StateAdapter {
    private int x; // numero de vezes

    public PlaceArmyState(Game game, int x) {
        super(game);
        this.x = x;
    }

    @Override
    public State placeArmy() {
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
    public int getX()
    {
        return x;
    }
}