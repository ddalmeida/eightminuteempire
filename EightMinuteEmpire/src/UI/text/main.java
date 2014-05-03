package UI.text;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import logic.cards.RegularCard;
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
        System.out.println("Warning: This is a professional and serious game. If you or your friends think they are");
        System.out.println("comedians and type some randomness when you are supposed to pick something,");
        System.out.println("the game with pick the worst option for you! Punch them or yourself now!");
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
        System.out.println("***");
        System.out.println();

        int players = 0;

        // Quantos jogadores sao?
        while (players == 0) {
            if (game.numberOfPlayers() == 0) {
                System.out.print("How many players? ");
            } else {
                System.out.print("There must be atleast 2 players. How many more players? ");
            }

            try {
                players = Integer.parseInt(sc.next());
            } catch (Exception e) {
                players = 0;
            }
        }

        // Perguntar pelos nomes
        for (int i = 1; i <= players; ++i) {
            System.out.print("New Player: ");
            game.S_addPlayer(sc.next());
        }

        game.S_addPlayer(null); // não há mais players.
    }

    private static void doAuctionState(Game game) {
        System.out.println("***");
        System.out.println();

        for (int i = 0; i < game.numberOfPlayers(); ++i) {
            System.out.print(String.format("Player %s, you have %d coins. How many do you want to bet? ",
                    game.getPlayer(i).getName(), game.getPlayer(i).getCoins()));
            game.S_bet(i, readBet());
        }
    }

    private static void doBuyCardState(Game game) {
        Scanner sc = new Scanner(System.in);
        System.out.println("***");
        System.out.println();

        System.out.println(game.getActivePlayer().getName() + ", it's your turn! Pick a card!");
        System.out.println("You have: " + game.getActivePlayer().getCoins() + " coins");
        System.out.println();

        ArrayList<RegularCard> cardsTable = game.getCardsTable();
        for (int i = 0; i < cardsTable.size(); i++) {
            System.out.println(String.format("[CARD %d][%s][Cost: %d]", i + 1, cardsTable.get(i).toString(), ((i + 1) / 2)));
        }

        int cardChosen;
        try {
            cardChosen = Integer.parseInt(sc.next());
        } catch (Exception e) {
            cardChosen = 0;
        }

        System.out.println("Do you want to use it's action? (Y/N): ");
        if (sc.next().toUpperCase() == "Y") {
            game.S_choseCard(cardChosen, true);
        } else {
            game.S_choseCard(cardChosen, false);
        }
    }

    private static void doPlaceArmyState(Game game) {
    }

    private static void doMoveArmyState(Game game) {
    }

    private static void doRemoveArmyState(Game game) {
    }

    private static void doFoundCityState(Game game) {
    }

    private static void doAndState(Game game) {
    }

    private static void doOrState(Game game) {
    }

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

    private static int readBet() {
        Console console = System.console();
        String bet;

        if (console != null) { // Se estiver a executar numa consola dá para ocultar os caracteres
            bet = new String(console.readPassword("f"));
        } else {
            Scanner sc = new Scanner(System.in); // Não está numa consola por isso é impossivel ocultar caracteres
            bet = sc.next();
        }

        try {
            return Integer.parseInt(bet);
        } catch (Exception e) {
            return 0;
        }
    }
}
