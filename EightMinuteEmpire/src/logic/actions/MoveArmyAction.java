package logic.actions;

import logic.game.Game;
import logic.states.MoveArmyState;
import logic.states.State;

public class MoveArmyAction extends BaseAction{
    private boolean sea;

    public MoveArmyAction(int times, boolean sea) {
        super(times);
        this.sea = sea;
    }

    @Override
    public State doAction(Game game) {
        return new MoveArmyState(game);
    }

    @Override
    public String toString() {
       return "Move a Army " + times + " time(s)";
    }
    
}
