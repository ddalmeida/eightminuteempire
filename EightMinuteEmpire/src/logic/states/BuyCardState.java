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
      public State playMoveArmyCard( int x) {
            return new MoveArmyState(game, x);
      }

      @Override
      public State playPlaceArmyCard(int x) {
            return new PlaceArmyState(game, x);
      }

      @Override
      public State playRemoveArmyCard() {
            return new RemoveArmyState(game);
      }

      @Override
      public State pickAction() {
            return new OrState(game);
      }

      @Override
      public State endTurn() {
            game.nextPlayer();
            return this;
      }
}
