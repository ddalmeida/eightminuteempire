package logic.states;

import logic.game.Game;

public class OrState extends StateAdapter {

    public OrState(Game game) {
        super(game);
    }

    @Override
    public State playFoundCityCard(int cardNumber) {
        game.boughtCard(cardNumber);
        return new FoundCityState(game);
    }

    @Override
    public State playMoveArmyCard(int cardNumber, int x) {
        game.boughtCard(cardNumber);
        return new MoveArmyState(game, x);
    }

    @Override
    public State playPlaceArmyCard(int cardNumber, int x) {
        game.boughtCard(cardNumber);
        return new PlaceArmyState(game, x);
    }

    @Override
    public State playRemoveArmyCard(int cardNumber) {
        game.boughtCard(cardNumber);
        return new RemoveArmyState(game);
    }
}
