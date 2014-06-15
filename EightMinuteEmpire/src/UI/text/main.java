package UI.text;

import java.io.Console;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import logic.cards.RegularCard;
import logic.game.Board_Console;
import logic.game.Game;
import logic.game.Player;
import logic.map.BaseRegion;
import logic.map.Board;
import logic.map.LandRegion;

public class main {

      final static int REGION_SIZE = 7; //Não mudar ou explode tudo
      static Game game;
      static Scanner sc;
      static Board board;

      public static void main(String[] args) {
            sc = new Scanner(System.in);

            System.out.println("== EIGHT MINUTE EMPIRE ==");
            System.out.println("  == PA HELL EDITION ==");
            System.out.println();
            System.out.println("by Luís Costa - 21210392");
            System.out.println("and Nuno Aguiar - 21160515");
            System.out.println();
            System.out.println("Warning:");
            System.out.println("This is a professional and serious game. If you or your friends think they are");
            System.out.println("comedians and type some randomness when you are supposed to pick something,");
            System.out.println("the game will pick the worst option for you! Punch them or yourself now!");
            System.out.println();
            System.out.println();

            // Criar novo mapa para jogo em interface consola
            board = new Board_Console();

            // perguntar se quer carregar um jogo ou iniciar um novo
            System.out.println("Load a game? (Enter a file name to load or just press Enter for a New Game):");
            String filename = sc.nextLine();
            if (filename.equals("")) {
                  game = new Game(board);
            } else {
                  game = loadGame(filename);
            }

            while (game.getState() != null) {
                  switch (game.getState().getClass().getSimpleName()) {

                        case "StartGameState":
                              doStartGameState();
                              break;

                        case "AuctionState":
                              doAuctionState();
                              break;

                        case "BuyCardState":
                              doBuyCardState();
                              break;

                        case "PlaceArmyState":
                              doPlaceArmyState();
                              break;

                        case "MoveArmyState":
                              doMoveArmyState();
                              break;

                        case "RemoveArmyState":
                              doRemoveArmyState();
                              break;

                        case "FoundCityState":
                              doFoundCityState();
                              break;

                        case "AndState":
                              doAndState();
                              break;

                        case "OrState":
                              doOrState();
                              break;

                        case "GameOverState":
                              doGameOver();
                              break;

                        default:
                              System.err.println("ERROR! Unknown state! Killing the game!");
                              System.exit(-1);
                  }
            }
      }

      private static void doStartGameState() {
            System.out.println();
            System.out.println("***");
            System.out.println();

            int players = 0;

            // Quantos jogadores sao?
            while (players == 0) {
                  if (game.getNumberOfPlayers() == 0) {
                        System.out.print("How many players? ");
                  } else {
                        System.out.print("There must be atleast 2 players. How many more players? ");
                  }

                  try {
                        players = Integer.parseInt(sc.next());
                  } catch (NumberFormatException e) {
                        players = 0;
                  }
            }

            // Perguntar pelos nomes
            for (int i = 1; i <= players; ++i) {
                  System.out.print("New Player: ");
                  game.addPlayer(sc.next());
            }

            game.addPlayer(null); // não há mais players.
      }

      private static void doAuctionState() {
            System.out.println();
            System.out.println("***");
            System.out.println();

            for (int i = 0; i < game.getNumberOfPlayers(); ++i) {
                  System.out.print(String.format("Player %s, you have %d coins. How many do you want to bet? ",
                          game.getPlayer(i).getName(), game.getPlayer(i).getCoins()));
                  game.placeBet(i, readBet());
            }
      }

      private static void doBuyCardState() {
            System.out.println();
            System.out.println("***");
            System.out.println();

            System.out.println(game.getActivePlayer().getName() + ", it's your turn! Pick a card! (enter 'S' to save the game)");
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
            String input = sc.next();
            try {
                  cardChosen = Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                  if (input.toUpperCase().charAt(0) == 'S') {
                        saveGame();
                        return;
                  } else {
                        cardChosen = 0;
                  }
            }

            System.out.print("Do you want to use it's action? (Y/N): ");
            if (sc.next().toUpperCase().charAt(0) == 'Y') {
                  int errorCode = game.choseCard(cardChosen, true);
                  if (errorCode == -1) {
                        System.err.println("You already have a maximum of 14 Armies");
                        sc.next();
                  } else if (errorCode == -2) {
                        System.err.println("You already have a maximum of 3 Cities");
                        sc.next();
                  }
            } else {
                  game.choseCard(cardChosen, false);
            }
      }

      private static void doPlaceArmyState() {
            System.out.println();
            System.out.println("***");
            System.out.println();
            drawMap();

            System.out.println("[ PLACE ARMY - " + game.getState().getX() + " turns left ]");
            System.out.println("In which region do you want to add an Army?");
            System.out.print("Y: ");
            int y;
            try {
                  y = Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                  y = 0;
            }

            System.out.print("X: ");
            int x;
            try {
                  x = Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                  x = 0;
            }

            switch (game.placeArmy(y, x)) {
                  case 0:
                        System.err.println("You can't add an Army there!");
                        sc.nextLine();
                        break;

                  case -1:
                        System.err.println("You have 14 armies already!");
                        sc.nextLine();
                        break;

                  case -2:
                        System.err.println("That region doesn't exist!");
                        sc.nextLine();
            }
      }

      private static void doMoveArmyState() {
            System.out.println();
            System.out.println("***");
            System.out.println();
            drawMap();

            System.out.println("[ MOVE ARMY - " + game.getState().getX() + " turns left ]");
            System.out.println("Move an Army FROM Region");
            System.out.print("Y: ");
            int y;
            try {
                  y = Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                  y = 0;
            }

            System.out.print("X: ");
            int x;
            try {
                  x = Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                  x = 0;
            }

            System.out.println("TO Region");
            System.out.print("Y: ");
            int y2;
            try {
                  y2 = Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                  y2 = 0;
            }

            System.out.print("X: ");
            int x2;
            try {
                  x2 = Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                  x2 = 0;
            }

            switch (game.moveArmy(y, x, y2, x2)) {
                  case 0:
                        System.err.println("You can't move an Army to that Region!");
                        sc.nextLine();
                        break;

                  case -1:
                        System.err.println("You don't have an Army in that Region!");
                        sc.nextLine();
                        break;

                  case -2:
                        System.err.println("You entered a region that doesn't exist!");
                        sc.nextLine();
            }
      }

      private static void doRemoveArmyState() {
            System.out.println();
            System.out.println("***");
            System.out.println();
            drawMap();

                 System.out.println("[ REMOVE ARMY ]");
            System.out.println("In which region do you want to remove an Army?");
            System.out.print("Y: ");
            int y;
            try {
                  y = Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                  y = 0;
            }

            System.out.print("X: ");
            int x;
            try {
                  x = Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                  x = 0;
            }

            System.out.println("From which player?");
            ArrayList<Player> players = game.getPlayers();
            for (int i = 0; i < players.size(); i++) {
                  System.out.println("[" + (i+1) + "] " + players.get(i).getName());
            }

            System.out.print("Player: ");
            int player;
            try {
                  player = Integer.parseInt(sc.next()) - 1;
            } catch (NumberFormatException e) {
                  player = 0;
            }

            switch (game.removeArmy(game.getPlayer(player), y, x)) {
                  case 0:
                        System.err.println("Didn't find any Army from that Player in that Region!");
                        sc.nextLine();
                        break;
            }
      }

      private static void doFoundCityState() {
            System.out.println();
            System.out.println("***");
            System.out.println();
            drawMap();

                 System.out.println("[ FOUND CITY ] "); 
            System.out.println("In which region do you want to found a City?");
            System.out.print("Y: ");
            int y;
            try {
                  y = Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                  y = 0;
            }

            System.out.print("X: ");
            int x;
            try {
                  x = Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                  x = 0;
            }

            switch (game.foundCity(y, x)) {
                  case 0:
                        System.err.println("You can't found a City there!");
                        sc.nextLine();
                        break;

                  case -1:
                        System.err.println("You have 3 cities already!");
                        sc.nextLine();
                        break;

                  case -2:
                        System.err.println("That region doesn't exist!");
                        sc.nextLine();
            }
      }

      private static void doAndState() {
            System.out.println();
            System.out.println("***");
            System.out.println();

            System.out.println("Which action do you want to first?");
            System.out.println("[1] " + game.getBoughtCard().getAction().toString());
            System.out.println("[2] " + game.getBoughtCard().getAction2().toString());

            int actionChosen;
            String input = sc.next();
            try {
                  actionChosen = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                  actionChosen = 1;
            }

            game.pickFirstAction(actionChosen);
      }

      private static void doOrState() {
            System.out.println();
            System.out.println("***");
            System.out.println();

            System.out.println("Which action do you want to do?");
            System.out.println("[1] " + game.getBoughtCard().getAction().toString());
            System.out.println("[2] " + game.getBoughtCard().getAction2().toString());

            int actionChosen;
            String input = sc.next();
            try {
                  actionChosen = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                  actionChosen = 1;
            }

            game.pickAction(actionChosen);
      }

      private static void doGameOver() {
            System.out.println("=================================");
            System.out.println("POS\tPOINTS\tNAME");

            // Apresentar tabela de pontuação e mensagem de parabens
            List<Player> scoreTable = game.getScoreTable();

            for (int i = 0; i < game.getNumberOfPlayers(); ++i) {
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

      private static Game loadGame(String filename) {
            try {
                  FileInputStream fis = new FileInputStream(filename + ".sav");
                  ObjectInputStream ois = new ObjectInputStream(fis);
                  return (Game) ois.readObject();
            } catch (Exception ex) {
                  System.err.println("Error reading file! Starting a New Game! " + ex.getMessage());
                  return new Game(board);
            }
      }

      private static void saveGame() {
            System.out.print("Save the game with the name: ");
            String filename = sc.next();
            try {
                  FileOutputStream fos = new FileOutputStream(filename + ".sav");
                  ObjectOutputStream oos = new ObjectOutputStream(fos);
                  oos.writeObject(game);
                  oos.close();
                  fos.close();
                  System.err.println("Game saved!");
            } catch (Exception ex) {
                  System.err.println("Failed to save the game! " + ex.getMessage());
            }
      }

      private static int readBet() {
            Console console = System.console();
            String bet;

            if (console != null) {
                  // Se estiver a executar numa consola dá para ocultar os caracteres
                  bet = new String(console.readPassword(""));
            } else {
                  // Não está numa consola por isso é impossivel ocultar caracteres
                  bet = sc.next();
            }

            try {
                  return Integer.parseInt(bet);
            } catch (NumberFormatException e) {
                  return 0;
            }
      }

      private static void drawMap() {
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

            // Mostrar informação sobre ocupação das regiões
            for (int i = 0; i < game.getBoard().getMapSizeY(); ++i) {
                  for (int j = 0; j < game.getBoard().getMapSizeX(); ++j) {
                        StringBuilder info = new StringBuilder();
                        for (int p = 0; p < game.getNumberOfPlayers(); ++p) {
                              // Ver quantos exercitos e cidades o jogador p tem na regiao i, j
                              int a = game.getBoard().getRegion(i, j).armiesOfPlayer(game.getPlayer(p)).size();
                              int c = game.getBoard().getRegion(i, j).citiesOfPlayer(game.getPlayer(p)).size();
                              if (a > 0 || c > 0) {
                                    info.append(String.format("[%s | A: %d | C: %d] ", game.getPlayer(p).getName(), a, c));
                              }
                        }
                        // Ver se há informação sobre a regiao i,j e imprimir
                        if (info.length() > 0) {
                              System.out.println(String.format("[%d,%d] %s", i, j, info));
                        }
                  }
            }
            System.out.println();
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
