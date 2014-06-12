package logic.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import logic.actions.*;
import logic.cards.*;
import logic.states.*;

public class Game implements Serializable {

      private static final long serialVersionUID = 888L;

      private ArrayList<Player> players;
      private ArrayList<RegularCard> cards;
      private ArrayList<RegularCard> cardsTable;
      private int activePlayer;
      private State state;
      private Board board;
      private Random rnd;

      public Game() {
            players = new ArrayList<>();
            cards = new ArrayList<>();
            cardsTable = new ArrayList<>();
            board = new Board();
            rnd = new Random();
            state = new StartGameState(this);
      }

      // ** ESTADOS
      public State getState() {
            return state;
      }

      // apenas para debug
      public void setState(State state) {
            this.state = state;
      }

      public int getCardTurns() {
            return state.getX();
      }

      public void addPlayer(String name) {
            if (name != null) {
                  players.add(new Player(name, 0));
            } else {
                  state = state.playersAdded();
            }
      }

      public int choseCard(int cardNumber, boolean useAction) {
            // Ver se a carta escolhida está entre 0 e 5 (6 cartas na mesa).
            if (cardNumber < 0 || cardNumber >= 6) {
                  cardNumber = 0;
            }

            // Se o jogador não tiver moedas para a carta que escolheu, escolher a primeira.
            if ((cardNumber + 1) / 2 > getActivePlayer().getCoins()) {
                  cardNumber = 0;
            }

            if (useAction) {
                  if (cardsTable.get(cardNumber) instanceof OrCard) {
                        state = state.pickAction(cardNumber);
                  }
                  // Ver se é uma carta de colocar exercito e se atingiu os 14
                  if (cardsTable.get(cardNumber).getAction() instanceof PlaceArmyAction && getActivePlayer().getArmies().size() >= 14) {
                        state = state.endTurn();
                        return 1;
                  }

                  // Ver se é uma carta de fundar cidade e se atingiu as 3
                  if (cardsTable.get(cardNumber).getAction() instanceof FoundCityAction && getActivePlayer().getCities().size() >= 3) {
                        state = state.endTurn();
                        return 2;
                  }

                  // Fazer acção da carta
                  state = cardsTable.get(cardNumber).getAction().doAction(this, cardNumber);
            } else {
                  // Não fazer acção da carta
                  boughtCard(cardNumber);
                  state = state.endTurn();
            }

            return 0;
      }

      public void placeBet(int playerNumber, int coins) {
            state = state.bet(playerNumber, coins);
      }

      public int foundCity(int y, int x) {
            int aux = getActivePlayer().addCity(board.getRegion(y, x));
            state = state.endTurn();
            return aux;
      }

      public int placeArmy(int y, int x) {
            int aux = getActivePlayer().addArmy(board.getRegion(y, x));

            if (aux < 2) {
                  state = state.placeArmy();
            } else {
                  state = state.endTurn();
            }

            return aux;
      }

      public int moveArmy(int y, int x, int y2, int x2) {
            int aux = getActivePlayer().moveArmy(board.getRegion(y, x), board.getRegion(y2, x2));
            state = state.moveArmy();
            return aux;
      }

      // ** JOGADORES
      public int getNumberOfPlayers() {
            return players.size();
      }

      public Player getPlayer(int i) {
            return players.get(i);
      }

      public Player getActivePlayer() {
            return players.get(activePlayer);
      }

      public void betCoins(int playerNumber, int coins) {
            // Jogador apostou mais do que o tem.
            if (coins > players.get(playerNumber).getCoins()) {
                  coins = players.get(playerNumber).getCoins();
            }

            // Jogador engraçadinho aposto um valor negativo.
            if (coins < 0) {
                  coins = 0;
            }

            players.get(playerNumber).setInitialBet(coins);
      }

      public void addInitialCoins() {
            int coins = 8; // 5+ players

            if (getNumberOfPlayers() == 2) {
                  coins = 14;
            } else if (getNumberOfPlayers() == 3) {
                  coins = 11;
            } else if (getNumberOfPlayers() == 4) {
                  coins = 9;
            }

            // Atribuir as moedas a todos os jogadores
            for (int i = 0; i < getNumberOfPlayers(); ++i) {
                  players.get(i).addCoins(coins);
            }
      }

      public void determinateActivePlayer() {
            ArrayList<Player> highestBidders = new ArrayList<Player>();
            highestBidders.add(players.get(0));

            // Ver quem é que apostou mais
            for (int i = 1; i < getNumberOfPlayers(); ++i) {
                  if (players.get(i).getInitialBet() > highestBidders.get(0).getInitialBet()) // Maior que o(s) maior(s) actual(s)
                  {
                        highestBidders.clear();
                        highestBidders.add(players.get(i));
                  } else if (players.get(i).getInitialBet() == highestBidders.get(0).getInitialBet()) // Empate com o(s) maior(s)
                  {
                        highestBidders.add(players.get(i));
                  }
            }

            // Ver se houve empate
            if (highestBidders.size() > 1) {
                  activePlayer = players.indexOf(highestBidders.get(rnd.nextInt(highestBidders.size())));
            } else {
                  activePlayer = players.indexOf(highestBidders.get(0));
            }

            // Retirar-lhe as moedas que licitou
            getActivePlayer().removeCoins(getActivePlayer().getInitialBet());
      }

      public void boughtCard(int cardNumber) {
            // remover moedas ao jogador
            getActivePlayer().removeCoins((cardNumber + 1) / 2);

            // remover carta da mesa e adicionar ao jogador
            getActivePlayer().addCard(cardsTable.get(cardNumber));
            cardsTable.remove(cardNumber);

            // adicionar uma nova carta à "mesa"
            cardToTable();
      }

      public void nextPlayer() {
            activePlayer = (activePlayer + 1) % getNumberOfPlayers();
      }

      public void addInitialArmies() {
            for (int i = 0; i < getNumberOfPlayers(); ++i) {
                  for (int j = 0; j < 3; ++j) {
                        players.get(i).addArmy(board.getInicialRegion());
                  }
            }
      }

      // ** CARTAS
      public void initializeCards() {
            // Jewels (0-3)
            cards.add(new RegularCard("Jewels", 1, new PlaceArmyAction(1), 0));
            cards.add(new RegularCard("Jewels", 1, new PlaceArmyAction(2), 1));
            cards.add(new RegularCard("Jewels", 1, new PlaceArmyAction(2), 1));
            cards.add(new RegularCard("Jewels", 1, new MoveArmyAction(2, false), 2));
            // Food (4-11)
            cards.add(new RegularCard("Food", 1, new FoundCityAction(1), 4));
            cards.add(new RegularCard("Food", 1, new FoundCityAction(1), 4));
            cards.add(new RegularCard("Food", 1, new MoveArmyAction(3, false), 5));
            cards.add(new RegularCard("Food", 2, new MoveArmyAction(3, false), 6));
            cards.add(new RegularCard("Food", 1, new MoveArmyAction(4, false), 7));
            cards.add(new RegularCard("Food", 1, new MoveArmyAction(4, false), 7));
            cards.add(new RegularCard("Food", 1, new MoveArmyAction(5, false), 8));
            cards.add(new RegularCard("Food", 1, new MoveArmyAction(3, true), 9));
            cards.add(new AndCard("Food", 1, new PlaceArmyAction(1), new RemoveArmyAction(1), 11));
            // Wood (12-19)
            cards.add(new RegularCard("Wood", 1, new FoundCityAction(1), 12));
            cards.add(new RegularCard("Wood", 1, new PlaceArmyAction(3), 13));
            cards.add(new RegularCard("Wood", 1, new MoveArmyAction(3, false), 14));
            cards.add(new OrCard("Wood", 1, new MoveArmyAction(2, true), new MoveArmyAction(3, false), 15));
            cards.add(new RegularCard("Wood", 1, new MoveArmyAction(3, true), 16));
            cards.add(new RegularCard("Wood", 1, new MoveArmyAction(4, true), 17));
            cards.add(new OrCard("Wood", 1, new RemoveArmyAction(1), new FoundCityAction(1), 19));
            // Iron (20-25)
            cards.add(new RegularCard("Iron", 1, new PlaceArmyAction(2), 20));
            cards.add(new RegularCard("Iron", 1, new PlaceArmyAction(3), 21));
            cards.add(new RegularCard("Iron", 1, new PlaceArmyAction(3), 21));
            cards.add(new RegularCard("Iron", 1, new MoveArmyAction(2, false), 22));
            cards.add(new RegularCard("Iron", 1, new MoveArmyAction(2, true), 23));
            cards.add(new RegularCard("Iron", 1, new MoveArmyAction(3, true), 25));
            // Tools (26-33)
            cards.add(new RegularCard("Tools", 1, new FoundCityAction(1), 26));
            cards.add(new RegularCard("Tools", 1, new PlaceArmyAction(3), 27));
            cards.add(new RegularCard("Tools", 1, new PlaceArmyAction(3), 27));
            cards.add(new RegularCard("Tools", 1, new MoveArmyAction(3, true), 28));
            cards.add(new RegularCard("Tools", 1, new MoveArmyAction(4, false), 29));
            cards.add(new OrCard("Tools", 1, new PlaceArmyAction(3), new MoveArmyAction(3, false), 30));
            cards.add(new OrCard("Tools", 1, new PlaceArmyAction(3), new MoveArmyAction(4, false), 31));
            cards.add(new RegularCard("Tools", 1, new MoveArmyAction(5, false), 33));
            // Joker (34-35)
            cards.add(new RegularCard("Joker", 1, new PlaceArmyAction(2), 34));
            cards.add(new RegularCard("Joker", 1, new MoveArmyAction(2, true), 35));
            cards.add(new RegularCard("Joker", 1, new MoveArmyAction(2, true), 35));

            // Cartas apenas disponiveis se houve 5+ jogadores
            if (getNumberOfPlayers() >= 5) {
                  cards.add(new RegularCard("Jewels", 1, new MoveArmyAction(2, false), 3));
                  cards.add(new OrCard("Food", 1, new PlaceArmyAction(4), new MoveArmyAction(2, false), 10));
                  cards.add(new RegularCard("Wood", 1, new MoveArmyAction(6, false), 18));
                  cards.add(new RegularCard("Iron", 1, new MoveArmyAction(2, true), 24));
                  cards.add(new RegularCard("Tools", 2, new MoveArmyAction(4, false), 32));
            }

            // Escolher 6 e mete-las na "mesa"
            for (int i = 1; i <= 6; ++i) {
                  cardToTable();
            }
      }

      public ArrayList<RegularCard> getCardsTable() {
            return cardsTable;
      }

      private void cardToTable() {
            // Retira uma carta do barulho e mete-a na mesa
            if (cards.size() >= 1) {
                  int randomCard = rnd.nextInt(cards.size());
                  cardsTable.add(cards.get(randomCard));
                  cards.remove(randomCard);
            }
      }

      // ** OUTRAS
      public Board getBoard() {
            return board;
      }

      public List<Player> getScoreTable() {
            // muito imcompleto
            Collections.sort(players);
            return players;
      }
}
