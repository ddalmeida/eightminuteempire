package logic.actions;

import logic.game.Game;
import logic.states.PlaceArmyState;
import logic.states.State;

public class PlaceArmyAction extends BaseAction{

    public PlaceArmyAction(int times) {
        super(times);
    }

    @Override
    public State doAction(Game game) {
        return new PlaceArmyState(game);
    }

    @Override
    public String toString() {
        return "Place an Army"  + times + " time(s)";
    }
    
}
