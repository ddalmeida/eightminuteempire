package logic.states;

import logic.game.Game;

public abstract class StateAdapter extends State {

    public StateAdapter(Game game) {
        super(game);
    }

    @Override
    public State pickNumberPlayers(Game game) {
        return this;
    }

    @Override
    public State run(Game game) {
        return this;
    }

    @Override
    public State bet(Game game) {
        return this;
    }

    @Override
    public State endTurn(Game game) {
        return this;
    }

    @Override
    public State playMoveArmyCard(Game game) {
        return this;
    }

    @Override
    public State moveArmy(Game game) {
        return this;
    }

    @Override
    public State playFoundCityCard(Game game) {
        return this;
    }

    @Override
    public State foundCity(Game game) {
        return this;
    }

    @Override
    public State pickAction(Game game) {
        return this;
    }

    @Override
    public State pickFirstAction(Game game) {
        return this;
    }

    @Override
    public State playPlaceArmyCard(Game game) {
        return this;
    }

    @Override
    public State placeArmy(Game game) {
        return this;
    }

    @Override
    public State playRemoveArmyCard(Game game) {
        return this;
    }

    @Override
    public State removeArmy(Game game) {
        return this;
    }

    @Override
    public State endGame(Game game) {
        return this;
    }
}
