package logic.states;

import logic.game.Game;

public abstract class StateAdapter extends State {

    public StateAdapter(Game game) {
        super(game);
    }

    @Override
    State AddPlayer(String name){
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
    public State playMoveArmyCard() {
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
    public State playPlaceArmyCard() {
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
    public State removeArmy() {
        return this;
    }

    @Override
    public State endGame() {
        return this;
    }
}
