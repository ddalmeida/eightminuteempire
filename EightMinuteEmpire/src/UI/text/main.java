package UI.text;

import java.util.List;
import java.util.Scanner;
import logic.game.Game;
import logic.game.Player;

public class main {

    public static void main(String[] args) {
        Game game = new Game();

        System.out.println("== EIGHT MINUTE EMPIRE ==");
        System.out.println("  == PA HELL EDITION ==");
        System.out.println();
        System.out.println("by Luís Costa - 21210392");
        System.out.println("and Nuno Aguiar - 21160515");
        System.out.println();

        while (game.getState() != null) {
            switch (game.getState().getClass().toString()) {
                case "class logic.states.StartGameState":
                    doStartGameState(game);
                    break;
                case "class logic.states.AuctionState":
                    doAuctionState(game);
                    break;

                case "class logic.states.BuyCardState":
                    doBuyCardState(game);
                    break;

                case "class logic.states.PlaceArmyState":
                    doPlaceArmyState(game);
                    break;

                case "class logic.state.MoveArmyState":
                    doMoveArmyState(game);
                    break;

                case "class logic.state.RemoveArmyState":
                    doRemoveArmyState(game);
                    break;

                case "class logic.state.FoundCityState":
                    doFoundCityState(game);
                    break;

                case "class logic.state.AndState":
                    doAndState(game);
                    break;

                case "class logic.state.OrState":
                    doOrState(game);
                    break;

                case "class logic.states.GameOverState":
                    doGameOver(game);
                    break;

                default:
                    System.out.println("ERROR! Unknown state! Killing the game!");
                    System.exit(-1);
            }
        }
    }

    private static void doStartGameState(Game game) {
        Scanner sc = new Scanner(System.in);
        System.out.println();

        if (game.numberOfPlayers() == 0) {
            System.out.print("How many players? ");
        } else {
            System.out.print("There must be atleast 2 players. How many more players? ");
        }
        int players = sc.nextInt();

        for (int i = 1; i <= players; ++i) {
            System.out.print("New Player: ");
            game.setState(game.getState().AddPlayer(sc.next()));
        }
        game.setState(game.getState().run());
    }

    private static void doAuctionState(Game game) {
        Scanner sc = new Scanner(System.in);
        System.out.println();

        for (int i = 0; i < game.numberOfPlayers(); ++i) {
            System.out.print(String.format("Player %s, you have %d coins. How many do you want to bet? ",
                    game.getPlayer(i).getName(), game.getPlayer(i).getCoins()));
            game.betCoins(i, sc.nextInt());
        }
    }
    
    private static void doBuyCardState(Game game){}
    private static void doPlaceArmyState(Game game){}
    private static void doMoveArmyState(Game game){}
    private static void doRemoveArmyState(Game game){}
    private static void doFoundCityState(Game game){}
    private static void doAndState(Game game){}
    private static void doOrState(Game game){}

    private static void doGameOver(Game game) {
        System.out.println("=================================");
        System.out.println("POS\tPOINTS\tNAME");

        // Apresentar tabela de pontuação e mensagem de parabens
        List<Player> scoreTable = game.getScoreTable();

        for (int i = 0; i < game.numberOfPlayers(); ++i) {
            System.out.println(String.format("%d\t%d\t%s",
                    i + 1, scoreTable.get(i).getPoints(), scoreTable.get(i).getName()));
        }
        System.out.println("=================================");
        System.out.println();
        System.out.println(String.format("CONGRATULATIONS, %s!", scoreTable.get(0).getName()));
        System.out.println("YOU ARE THE STRATEGY MASTER OF THE WORLD!!!");

        // Sai do jogo
        game.setState(null);
    }
}
