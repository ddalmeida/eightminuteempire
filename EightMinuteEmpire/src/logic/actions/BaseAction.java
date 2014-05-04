package logic.actions;

import logic.game.Game;
import logic.states.State;

public abstract class BaseAction {

    int times;

    public BaseAction(int times) {
        this.times = times;
    }

    public void minusOne() {
        times--;
    }

    public int getTimes() {
        return times;
    }

    public abstract State doAction(Game game);

    public abstract String toString();
}
