package logic.states;

import logic.game.Game;

public class AndState extends StateAdapter {

      public AndState(Game game) {
            super(game);
      }

      @Override
      public State chosenAction(int actionChosen) {
            switch (actionChosen) {
                  case 1:
                        previousStateResult = RETURN_TO_AND_FIRST_OPTION_DONE;
                        return game.getBoughtCard().getAction().doAction(game);

                  case 2:
                        previousStateResult = RETURN_TO_AND_SECOND_OPTION_DONE;
                        return game.getBoughtCard().getAction2().doAction(game);

                  case RETURN_TO_AND_FIRST_OPTION_DONE:
                        return game.getBoughtCard().getAction2().doAction(game);

                  case RETURN_TO_AND_SECOND_OPTION_DONE:
                        return game.getBoughtCard().getAction().doAction(game);
            }

            return this;
      }

      @Override
      public State playPlaceArmyCard(int x) {
            return new PlaceArmyState(game, x);
      }

      @Override
      public State playRemoveArmyCard() {
            return new RemoveArmyState(game);
      }
}
