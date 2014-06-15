package ia;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import logic.game.Model;
import logic.game.Player;

public class ia implements Observer {

      private final Player me;
      private final int myID;
      private final Model model;
      private final int intelligence;
      private Random rnd;

      public ia(Player me, Model model) {
            this.me = me;
            this.model = model;
            this.myID = model.getPlayers().indexOf(me);

            // Escolher aleoriamente o tipo de inteligencia
            // 0 = Escolhas aleatorias
            // 1 = Anti-jogo
            // 2 = Inteligente (Não implementada ainda)
            intelligence = rnd.nextInt(2);

            output("Running with inteligence of level: " + intelligence);

            model.addObserver(this);
      }

      @Override
      public void update(Observable o, Object arg) {

            // É a minha vez de jogar?
            if (model.getActivePlayer().equals(me)) {
                  output("It's my turn!");

                  switch (model.getState().getClass().getSimpleName()) {

                        case "ActionState":
                              if (intelligence == 0) model.placeBet(myID, rnd.nextInt(me.getCoins()));
                              if (intelligence == 1) model.placeBet(myID, 0);

                        case "BuyCardState":
                              if (intelligence == 0) model.choseCard(rnd.nextInt(6), rnd.nextBoolean());
                              if (intelligence == 1) model.choseCard(0, false);
                              break;

                        case "PlaceArmyState":
                              if (intelligence == 1) {
                                    int y = rnd.nextInt(model.getBoard().getMapSizeY());
                                    int x = rnd.nextInt(model.getBoard().getMapSizeX());

                                    model.placeArmy(y, x);
                              }
                              break;

                        case "MoveArmyState":
                              if (intelligence == 1) {
                                    int y = rnd.nextInt(model.getBoard().getMapSizeY());
                                    int x = rnd.nextInt(model.getBoard().getMapSizeX());

                                    model.moveArmy(y, x, y + rnd.nextInt(3) - 1, x + rnd.nextInt(3) - 1);
                              }
                              break;

                        case "RemoveArmyState":
                              if (intelligence == 1) {
                                    int y = rnd.nextInt(model.getBoard().getMapSizeY());
                                    int x = rnd.nextInt(model.getBoard().getMapSizeX());

                                    model.removeArmy(model.getPlayers().get(rnd.nextInt(model.getPlayers().size())), y, x);
                              }
                              break;

                        case "FoundArmyState":
                              if (intelligence == 1) {
                                    int y = rnd.nextInt(model.getBoard().getMapSizeY());
                                    int x = rnd.nextInt(model.getBoard().getMapSizeX());

                                    model.foundCity(y, x);
                              }
                              break;

                        case "AndState":
                              if (intelligence == 1) model.pickFirstAction(rnd.nextInt(2) + 1);
                              break;

                        case "OrState":
                              if (intelligence == 1) model.pickAction(rnd.nextInt(2) + 1);
                              break;
                  }
            }
      }

      private void output(String text) {
            System.out.println("IA " + me.getName() + " - " + text);
      }
}
