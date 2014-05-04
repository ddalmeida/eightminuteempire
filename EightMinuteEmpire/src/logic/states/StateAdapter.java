package logic.states;

import logic.game.Game;
import logic.game.Player;
import logic.map.BaseRegion;

public abstract class StateAdapter extends State {

    public StateAdapter(Game game) {
        super(game);
    }

    @Override
    public State AddPlayer(String name){
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
    public State playMoveArmyCard(int cardNumber,  int x) {
        return this;
    }

    @Override
    public State moveArmy() {
        return this;
    }

    @Override
    public State playFoundCityCard(int cardNumber) {
        return this;
    }

    @Override
    public State foundCity() {
        return this;
    }

    @Override
    public State pickAction(int cardNumber) {
        return this;
    }

    @Override
    public State pickFirstAction() {
        return this;
    }

    @Override
    public State playPlaceArmyCard(int cardNumber,  int x) {
        return this;
    }

    @Override
    public State placeArmy() {
        return this;
    }

    @Override
    public State playRemoveArmyCard(int cardNumber) {
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
}
