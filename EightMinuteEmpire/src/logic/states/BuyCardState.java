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
    
    @Override
    public State pickAction(int cardNumber)
    {
        game.boughtCard(cardNumber);
        return new OrState(game);
    }

    @Override
    public State endTurn() {
        game.nextPlayer();
        return this;
    }
}
