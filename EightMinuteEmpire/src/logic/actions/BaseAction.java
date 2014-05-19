package logic.actions;

import java.io.Serializable;
import logic.game.Game;
import logic.states.State;

public abstract class BaseAction implements Serializable{

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

    public abstract State doAction(Game game, int cardNumber);

    public abstract String toString();
}
