package UI.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import logic.game.Game;
import logic.game.Player;

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

                case "class logic.states.GameOverState":
                    doGameOver(game);
                    break;
            }
        }
    }

    private static void doStartGameState(Game game) {
        Scanner sc = new Scanner(System.in);
        System.out.println();

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
        System.out.println();

        for (int i = 0; i < game.numberOfPlayers(); ++i) {
            System.out.print(String.format("Player %s, you have %d coins. How many do want to bet? ",
                    game.getPlayer(i).getName(), game.getPlayer(i).getCoins()));
            game.betCoins(i, sc.nextInt());
        }
    }
    
    private static void doGameOver(Game game)
    {
        System.out.println("=================");
        System.out.println();
        // Apresentar tabela de pontuação
        
        ArrayList<Player> scoreTable = game.getScoreTable();
        
        for (int i = 0; i < game.numberOfPlayers(); ++i)
        {
            System.out.println(String.format("%d - % s - %d",
                    i+1, scoreTable.get(i).getName(), scoreTable.get(i).getPoints()));
    }
        System.out.println();
        System.out.println("=================");
        System.out.println();
        System.out.println();
        System.out.println(String.format("CONGRATULATIONS, %s!",scoreTable.get(0).getName()));
        System.out.println("YOU ARE THE STRATEGY MASTER OF THE WORLD!!!");
        
        game.setState(null);
    }
}
