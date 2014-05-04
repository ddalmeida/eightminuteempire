package UI.text;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import logic.cards.RegularCard;
import logic.game.Game;
import logic.game.Player;
import logic.map.BaseRegion;
import logic.map.LandRegion;

public class main {

    final static int REGION_SIZE = 7;

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
        System.out.println("the game will pick the worst option for you! Punch them or yourself now!");

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

                case "class logic.states.MoveArmyState":
                    doMoveArmyState(game);
                    break;

                case "class logic.states.RemoveArmyState":
                    doRemoveArmyState(game);
                    break;

                case "class logic.states.FoundCityState":
                    doFoundCityState(game);
                    break;

                case "class logic.states.AndState":
                    doAndState(game);
                    break;

                case "class logic.states.OrState":
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
        System.out.println();
        System.out.println("***");
        System.out.println();

        System.out.println(game.getActivePlayer().getName() + ", it's your turn! Pick a card!");
        System.out.println("You have: " + game.getActivePlayer().getCoins() + " coins, "
                + game.getActivePlayer().getArmies().size() + " armies and "
                + game.getActivePlayer().getCities().size() + " cities");
        System.out.println();

        ArrayList<RegularCard> cardsTable = game.getCardsTable();
        for (int i = 0; i < cardsTable.size(); i++) {
            int cost = ((i + 1) / 2);
            if (cost <= game.getActivePlayer().getCoins()) {
                System.out.println(String.format("[CARD %d][Cost: %d][%s]", i + 1, cost, cardsTable.get(i).toString()));
            } else {
                System.out.println(String.format("[CARD X][Cost: %d][%s] You don't have enough coins for this one!", cost, cardsTable.get(i).toString()));
            }
        }
        System.out.print("Card: ");

        int cardChosen;
        try {
            cardChosen = Integer.parseInt(sc.next()) - 1;
        } catch (Exception e) {
            cardChosen = 0;
        }

        System.out.println("Do you want to use it's action? (Y/N): ");
        if (sc.next().toUpperCase().charAt(0) == 'Y') {
            game.S_boughtCard(cardChosen, true);
        } else {
            game.S_boughtCard(cardChosen, false);
        }
    }

    private static void doPlaceArmyState(Game game) {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("***");
        System.out.println();
        drawMap(game);
    }

    private static void doMoveArmyState(Game game) {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("***");
        System.out.println();
        drawMap(game);
    }

    private static void doRemoveArmyState(Game game) {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("***");
        System.out.println();
        drawMap(game);
    }

    private static void doFoundCityState(Game game) {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("***");
        System.out.println();
        drawMap(game);
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

    private static void drawMap(Game game) {
        // Inicializar buffer
        StringBuilder drawBuffer[] = new StringBuilder[game.getBoard().getMapSizeY() * REGION_SIZE + 2];

        // Criar as "linhas" do mapa e meta-las vazias
        for (int i = 0; i < game.getBoard().getMapSizeY() * REGION_SIZE + 2; ++i) {
            drawBuffer[i] = new StringBuilder();
            for (int j = 0; j < game.getBoard().getMapSizeX() * REGION_SIZE + 2; ++j) {
                drawBuffer[i].append(' ');
            }
        }

        // "Pintar" as regioes
        for (int i = 0; i < game.getBoard().getMapSizeY(); ++i) {
            for (int j = 0; j < game.getBoard().getMapSizeX(); ++j) {
                drawRegion(game.getBoard().getRegion(i, j), drawBuffer);
            }
        }

        // "Desenhar" bordos
        for (int i = 0; i < game.getBoard().getMapSizeX() * REGION_SIZE + 2; ++i) {
            drawBuffer[0].setCharAt(i, '0');
            drawBuffer[game.getBoard().getMapSizeX() * REGION_SIZE + 1].setCharAt(i, '0');
        }
        for (int i = 0; i < game.getBoard().getMapSizeY() * REGION_SIZE + 2; ++i) {
            drawBuffer[i].setCharAt(0, '0');
            drawBuffer[i].setCharAt(game.getBoard().getMapSizeY() * REGION_SIZE + 1, '0');
        }

        // Mostrar na consola
        for (int i = 0; i < drawBuffer.length; ++i) {
            System.out.println(drawBuffer[i].toString());
        }
    }

    private static void drawRegion(BaseRegion region, StringBuilder drawBuffer[]) {
        if (region instanceof LandRegion) {
            for (int i = 0; i < REGION_SIZE; ++i) {
                for (int j = 0; j < REGION_SIZE; ++j) {
                    //System.out.println("Y: " + i + " X: " + j);
                    drawBuffer[i + region.getY() * REGION_SIZE + 1].setCharAt(j + region.getX() * REGION_SIZE + 1, 'X');
                }
            }
        }

        // Adicionar coordenadas
        drawBuffer[region.getY() * REGION_SIZE + 4].setCharAt(region.getX() * REGION_SIZE + 2, '[');
        drawBuffer[region.getY() * REGION_SIZE + 4].setCharAt(region.getX() * REGION_SIZE + 3, (char) (region.getY() + 48));
        drawBuffer[region.getY() * REGION_SIZE + 4].setCharAt(region.getX() * REGION_SIZE + 4, ',');
        drawBuffer[region.getY() * REGION_SIZE + 4].setCharAt(region.getX() * REGION_SIZE + 5, (char) (region.getX() + 48));
        drawBuffer[region.getY() * REGION_SIZE + 4].setCharAt(region.getX() * REGION_SIZE + 6, ']');
    }
}
