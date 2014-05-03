package logic.states;

import logic.game.Game;

public class BuyCardState extends StateAdapter {

    public BuyCardState(Game game) {
        super(game);
    }

    @Override
    public State playFoundCityCard() {
        return new FoundCityState(game);
    }

    @Override
    public State playMoveArmyCard() {
        return new MoveArmyState(game);
    }

    @Override
    public State playPlaceArmyCard() {
        return new PlaceArmyState(game);
    }

    @Override
    public State playRemoveArmyCard() {
        return new RemoveArmyState(game);
    }

    @Override
    public State endTurn() {
        return this;
    }

}
