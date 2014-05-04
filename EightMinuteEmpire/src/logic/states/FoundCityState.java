package logic.states;

import logic.game.Game;

public class FoundCityState extends StateAdapter {

    public FoundCityState(Game game) {
        super(game);
    }

    public State endTurn() {
        game.nextPlayer();
        return new BuyCardState(game);
    }
}
