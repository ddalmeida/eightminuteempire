package logic.states;

import logic.game.Game;
import logic.map.BaseRegion;

public class MoveArmyState extends StateAdapter {

      private int x;

      public MoveArmyState(Game game, int x) {
            super(game);
            this.x = x;
      }

      @Override
      public State moveArmy(BaseRegion from, BaseRegion to, boolean sea) {
            // verificar se FROM e TO estão dentro dos limites do mapa
            if (from == null || to == null) {
                  result = -2;
            } else {
                  // ver se destino é "passavel" ou se regioes sao adjacentes
                  if (!to.isPassable() || !game.getBoard().areAdjacent(from, to, sea)) {
                        result = 0;
                  } else {
                        // tentar mover um exercito da região FROM para TO
                        int aux = game.getActivePlayer().moveArmy(from, to);
                        result = aux;
                  }
            }

            // Decrementar turnos
            x--;
            if (x == 0) {
                  return endTurn();
            } else {
                  return this;
            }
      }

      @Override
      public State endTurn() {
            game.nextPlayer();
            previousStateResult = result;
            return new BuyCardState(game);
      }

      @Override
      public int getX() {
            return x;
      }
}
