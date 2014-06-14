package logic.actions;

import logic.game.Game;
import logic.states.State;

public class MoveArmyAction extends BaseAction {

      private final boolean sea;

      public MoveArmyAction(int times, boolean sea) {
            super(times);
            this.sea = sea;
      }

      @Override
      public State doAction(Game game) {
            return game.getState().playMoveArmyCard(times);
      }
      
      @Override
      public boolean getMoveOverSea()
      {
            return sea;
      }

      @Override
      public String toString() {
            if (sea) {
                  return "Move a Army (land and sea) " + times + " time(s)";
            } else {
                  return "Move a Army (land) " + times + " time(s)";
            }
      }
}
