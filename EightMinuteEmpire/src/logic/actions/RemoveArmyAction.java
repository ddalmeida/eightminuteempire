package logic.actions;

import logic.game.Game;
import logic.states.RemoveArmyState;
import logic.states.State;

public class RemoveArmyAction extends BaseAction{

    public RemoveArmyAction(int times) {
        super(times);
    }

    @Override
    public State doAction(Game game) {
        return new RemoveArmyState(game);
    }

    @Override
    public String toString() {
       return "Remove an Army" + times + " time(s)";
    }

}
