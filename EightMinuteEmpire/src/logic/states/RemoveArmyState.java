package logic.states;

import logic.game.Game;
import logic.game.Player;
import logic.map.BaseRegion;

public class RemoveArmyState extends StateAdapter {

      public RemoveArmyState(Game game) {
            super(game);
      }

      @Override
      public State removeArmy(Player player, BaseRegion region) {
            result = player.removeArmy(region);
            return endTurn();
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

                  // Se game over se verificar, ir para o estado de jogo terminou.
                  if (game.checkForGameOver()) {
                        return new GameOverState(game);
                  } else {
                        return new BuyCardState(game);
                  }
            }
      }
}
