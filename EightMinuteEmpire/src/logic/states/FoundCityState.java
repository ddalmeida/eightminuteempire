package logic.states;

import logic.game.Game;
import logic.map.BaseRegion;

public class FoundCityState extends StateAdapter {

      public FoundCityState(Game game) {
            super(game);
      }

      @Override
      public State foundCity(BaseRegion region) {
            // Verficar se jogador ja tem 3 cidades
            if (game.getActivePlayer().getCities().size() >= 3) {
                  result = -1;
                  return endTurn();
            }

            // Verificar se região está fora dos limites do mapa
            if (region == null) {
                  result = -2;
                  return endTurn();
            }

            // Jogador escolheu regiao onde tem um exercito E não é no meio do mar
            if (game.getActivePlayer().haveArmyInRegion(region) && region.isSettleable()) {
                  game.getActivePlayer().addCity(region);
                  result = 1; // Cidade fundada
            } else {
                  result = 0; // Cidade não fundad
            }
            
            return endTurn();
      }

      @Override
      public State endTurn() {
            game.nextPlayer();
            previousStateResult = result;
            return new BuyCardState(game);
      }
}
