package logic.actions;

import logic.game.Game;
import logic.states.State;

public class FoundCityAction extends BaseAction {

    public FoundCityAction(int times) {
        super(times);
    }

    @Override
    public State doAction(Game game, int cardNumber) {
        return game.getState().playFoundCityCard(cardNumber);
    }

    @Override
    public String toString() {
        return "Found " + times + " City";
    }
}
