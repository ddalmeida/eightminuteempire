package logic.states;

import logic.game.Game;

public class BuyCardState extends StateAdapter {

    public BuyCardState(Game game) {
        super(game);
    }

    @Override
    public State playFoundCityCard(int cardNumber) {
        game.boughtCard(cardNumber);
        return new FoundCityState(game);
    }

    @Override
    public State playMoveArmyCard(int cardNumber) {
        return new MoveArmyState(game);
    }

    @Override
    public State playPlaceArmyCard(int cardNumber) {
        return new PlaceArmyState(game);
    }

    @Override
    public State playRemoveArmyCard(int cardNumber) {
        return new RemoveArmyState(game);
    }

    @Override
    public State endTurn() {
        game.nextPlayer();
        return this;
    }
}
