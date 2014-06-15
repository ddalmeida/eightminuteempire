package logic.states;

import logic.game.Game;

public class BuyCardState extends StateAdapter {

      public BuyCardState(Game game) {
            super(game);
      }

      @Override
      public State playFoundCityCard() {
            // Verificar se jogador ja atingiu as 3 cidades
            if (game.getActivePlayer().getCities().size() >= 3) {
                  result = -2;
                  return endTurn();
            }

            return new FoundCityState(game);
      }

      @Override
      public State playMoveArmyCard(int x) {
            return new MoveArmyState(game, x);
      }

      @Override
      public State playPlaceArmyCard(int x) {
            // Verificar se jogador ja atingiu os 14 exercitos
            if (game.getActivePlayer().getArmies().size() >= 14) {
                  result = -1;
                  return endTurn();
            }

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
      public State pickFirstAction() {
            return new AndState(game);
      }

      @Override
      public State endTurn() {
            game.nextPlayer();
            previousStateResult = result;
            return this;
      }
}
