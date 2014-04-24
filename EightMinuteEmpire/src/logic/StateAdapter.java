package logic;

public class StateAdapter implements State {

    @Override
    public State pickNumberPlayers() {
        return this;
    }

    @Override
    public State run() {
        return this;
    }

    @Override
    public State bet() {
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