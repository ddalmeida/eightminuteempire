package logic.actions;

import logic.game.Game;
import logic.states.State;

public class FoundCityAction extends BaseAction{

    public FoundCityAction(int times) {
        super(times);
    }

    @Override
    public State doAction(Game game) {
        return game.getState().playFoundCityCard();
    }

    @Override
    public String toString() {
        return "Found " + times + " City";
    }    
}
