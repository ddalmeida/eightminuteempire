package logic.actions;

import logic.game.Game;
import logic.states.State;

public class RemoveArmyAction extends BaseAction {

    public RemoveArmyAction(int times) {
        super(times);
    }

    @Override
    public State doAction(Game game, int cardNumber) {
        return game.getState().playRemoveArmyCard(cardNumber);
    }

    @Override
    public String toString() {
        return "Remove an Army " + times + " time(s)";
    }

}
