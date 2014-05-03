package logic.actions;

import logic.game.Game;
import logic.states.FoundCityState;
import logic.states.State;

public class FoundCityAction extends BaseAction{

    public FoundCityAction(int times) {
        super(times);
    }

    @Override
    public State doAction(Game game) {
        return new FoundCityState(game);
    }

    @Override
    public String toString() {
        return "Found " + times + " City";
    }    
}
