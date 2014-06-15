package model;

import java.util.Observable;
import logic.game.Game;

public class Model extends Observable {

    Game game;

    public Model(Game game) {
        this.game = game;
    }

    public void setNumberPlayers() {
    }
}