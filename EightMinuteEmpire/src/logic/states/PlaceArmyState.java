package logic.states;

import logic.game.Game;
import logic.map.BaseRegion;

public class PlaceArmyState extends StateAdapter {

      private int x; // numero de vezes

      public PlaceArmyState(Game game, int x) {
            super(game);
            this.x = x;
      }

      @Override
      public State placeArmy(BaseRegion region) {
            // verificar se jogador ja tem 14 exercitos
            if (game.getActivePlayer().getArmies().size() >= 14) {
                  result = -1;
                  return endTurn();
            }

            // Região fora dos limites do mapa
            if (region == null) {
                  result = -2;
            } else {
                  // jogador escolheu regiao inicial ou jogador escolheu regiao onde tem uma cidade
                  if (region.isInitialRegion() || game.getActivePlayer().haveCityInRegion(region)) {
                        game.getActivePlayer().addArmy(region);
                        result = 1; // novo exercito adicionado
                  } else {
                        result = 0; // escolheu uma regiao onde não pode adicionar um novo exercito
                  }
            }

            // Decrementar turnos (e verficiar se agora ja tem 14 exercitos)
            x--;
            if (x == 0 || game.getActivePlayer().getArmies().size() >= 14) {
                  return endTurn();
            } else {
                  return this;
            }
      }

      @Override
      public State endTurn() {
            // Verificar se veio de um AndState
            if (previousStateResult == RETURN_TO_AND_FIRST_OPTION_DONE
                    || previousStateResult == RETURN_TO_AND_SECOND_OPTION_DONE) {
                  int aux = previousStateResult;
                  previousStateResult = result;
                  game.setState(new AndState(game));
                  return new AndState(game).chosenAction(aux);
            } else {
                  game.nextPlayer();
                  previousStateResult = result;
                  return new BuyCardState(game);
            }
      }

      @Override
      public int getX() {
            return x;
      }
}
