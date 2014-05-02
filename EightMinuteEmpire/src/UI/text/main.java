package UI.text;

import java.util.Scanner;
import logic.game.Game;

public class main {

    public static void main(String[] args) {
        Game game = new Game();

        System.out.println("== EIGHT MINUTE EMPIRE ==");
        System.out.println("  == PA HELL EDITION ==");
        System.out.println("");
        System.out.println("by Luís Costa - 21210392");
        System.out.println("and Nuno Aguiar - 21160515");
        System.out.println("");

        while (game.getState() != null) {
            switch (game.getState().getClass().toString()) {
                case "class logic.states.StartGameState":
                    doStartGameState(game);
                    break;
                case "class logic.states.AuctionState":
                    doAuctionState(game);
                    break;
            }
        }
    }

    private static void doStartGameState(Game game) {
        Scanner sc = new Scanner(System.in);

        if (game.numberOfPlayers() == 0) {
            System.out.print("How many Players? ");
        } else {
            System.out.print("There must be atleast 2 players. How many more Players? ");
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
    }
}
