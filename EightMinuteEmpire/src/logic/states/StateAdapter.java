package logic.states;

import java.io.Serializable;
import logic.game.Game;
import logic.game.Player;
import logic.map.BaseRegion;

public abstract class StateAdapter implements State, Serializable {
    Game game;

    public StateAdapter(Game game) {
        super();
        this.game = game;
    }

    @Override
    public State playersAdded() {
        return this;
    }

    @Override
    public State run() {
        return this;
    }

    @Override
    public State bet(int playerNumber, int coins) {
        return this;
    }

    @Override
    public State endTurn() {
        return this;
    }

    @Override
    public State playMoveArmyCard(int x) {
        return this;
    }

    @Override
    public State moveArmy() {
        return this;
    }

    @Override
    public State playFoundCityCard() {
        return this;
    }

    @Override
    public State foundCity() {
        return this;
    }

    @Override
    public State pickAction() {
        return this;
    }

    @Override
    public State pickFirstAction() {
        return this;
    }

    @Override
    public State playPlaceArmyCard(int x) {
        return this;
    }

    @Override
    public State placeArmy() {
        return this;
    }

    @Override
    public State playRemoveArmyCard() {
        return this;
    }

    @Override
    public State removeArmy(Player player, BaseRegion region) {
        return this;
    }

    @Override
    public State endGame() {
        return this;
    }

    @Override
    public int getX() {
        return 0;
    }
}
