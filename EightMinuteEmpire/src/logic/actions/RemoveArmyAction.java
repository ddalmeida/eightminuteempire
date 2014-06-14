package logic.actions;

import logic.game.Game;
import logic.states.State;

public class RemoveArmyAction extends BaseAction {

    public RemoveArmyAction(int times) {
        super(times);
    }

    @Override
    public State doAction(Game game) {
        return game.getState().playRemoveArmyCard();
    }

    @Override
    public String toString() {
        return "Remove an Army " + times + " time(s)";
    }

}
