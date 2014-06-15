package logic.game;

import logic.map.Board;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import logic.actions.*;
import logic.cards.*;
import logic.map.BaseRegion;
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
      
      public Game(Board board) {
            players = new ArrayList<>();
            cards = new ArrayList<>();
            cardsTable = new ArrayList<>();
            this.board = board;
            rnd = new Random();
            state = new StartGameState(this);
            
      }

      // ** ESTADOS
      public State getState() {
            return state;
      }
      
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

            // Guardar referencia para a carta comprada
            RegularCard boughtCard = cardsTable.get(cardNumber);

            // Comprar a carta
            boughtCard(cardNumber);
            
            if (useAction) {
                  // ver se é uma carta OR
                  if (boughtCard.getClass().equals(OrCard.class)) {
                        state = state.pickAction();
                        return state.getResult(); // irrelevante o resultado
                  }

                  // ver se é uma carta AND
                  if (boughtCard.getClass().equals(AndCard.class)) {
                        state = state.pickFirstAction();
                        return state.getResult(); // irrelevante o resultado
                  }

                  // Fazer acção da carta
                  state = boughtCard.getAction().doAction(this);
                  return state.getPreviousStateResult();
            } else {
                  // Não fazer acção da carta
                  state = state.endTurn();
                  return state.getResult(); // irrelevante o resultado
            }
      }
      
      public void placeBet(int playerNumber, int coins) {
            state = state.bet(playerNumber, coins);
      }
      
      public int foundCity(int y, int x) {
            state = state.foundCity(board.getRegion(y, x));
            return state.getPreviousStateResult();
      }
      
      public int placeArmy(int y, int x) {
            state = state.placeArmy(board.getRegion(y, x));
            if (state.getResult() == State.NEW_STATE_CODE) {
                  return state.getPreviousStateResult();
            } else {
                  return state.getResult();
            }
      }
      
      public int moveArmy(int y, int x, int y2, int x2) {
            state = state.moveArmy(board.getRegion(y, x), board.getRegion(y2, x2), getBoughtCard().getAction().getMoveOverSea());
            if (state.getResult() == State.NEW_STATE_CODE) {
                  return state.getPreviousStateResult();
            } else {
                  return state.getResult();
            }
      }
      
      public int removeArmy(Player player, int y, int x) {
            return removeArmy(player, board.getRegion(y, x));
      }
      
      public int removeArmy(Player player, BaseRegion region) {
            state = state.removeArmy(player, region);
            return state.getPreviousStateResult();
            
      }
      
      public void pickAction(int actionChosen) {
            // Verificar se foi uma escolha válida
            if (actionChosen != 1 && actionChosen != 2) {
                  actionChosen = 1;
            }

            // Fazer acção da escolha
            if (actionChosen == 1) {
                  state = getBoughtCard().getAction().doAction(this);
            } else {
                  state = getBoughtCard().getAction2().doAction(this);
            }
      }
      
      public void pickFirstAction(int actionChosen) {
            // Verificar se foi uma escolha válida
            if (actionChosen != 1 && actionChosen != 2) {
                  actionChosen = 1;
            }

            // Fazer acção da escolha
            state = state.chosenAction(actionChosen);
      }

      // ** JOGADORES
      public int getNumberOfPlayers() {
            return players.size();
      }
      
      public Player getPlayer(int i) {
            // Verificar se é um numero valido
            if (i < 0 || i >= players.size()) {
                  return null;
            } else {
                  return players.get(i);
            }
      }
      
      public ArrayList<Player> getPlayers() {
            return players;
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
            ArrayList<Player> highestBidders = new ArrayList<>();
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
      
      public RegularCard getBoughtCard() {
            // Como os objectos são guardados por ordem num ArrayList,
            // sabemos que a ultima carta de um jogador é a ultima carta que comprou.
            return getActivePlayer().getCardsInHand().get(getActivePlayer().getCardsInHand().size() - 1);
      }

      // ** OUTRAS
      public Board getBoard() {
            return board;
      }
      
      public List<Player> getScoreTable() {
            
            class PS //Player Score
            {
                  
                  public Player player;
                  public int score;
                  
                  PS(Player player, int score) {
                        this.player = player;
                        this.score = score;
                  }
            }
            ArrayList<PS> regionWinners = new ArrayList<>();

            // Controlo de regioes
            regionWinners.add(new PS(null, -1));
            for (int y = 0; y < board.getMapSizeY(); ++y) {
                  for (int x = 0; x < board.getMapSizeX(); ++x) {
                        for (int p = 0; p < players.size(); ++p) {
                              
                              int score = board.getRegion(y, x).armiesOfPlayer(players.get(p)).size()
                                      + board.getRegion(y, x).citiesOfPlayer(players.get(p)).size();
                              
                              if (score > regionWinners.get(0).score) {
                                    regionWinners.clear();
                                    regionWinners.add(new PS(players.get(p), score));
                              } else if (score == regionWinners.get(0).score) {
                                    regionWinners.add(new PS(players.get(p), score));
                              }
                        }

                        // Ver quem ganhou a regiao y, x
                        if (regionWinners.size() == 1) {
                              regionWinners.get(0).player.addPoints(1);
                        }
                        regionWinners.clear();
                  }
            }

            // Controlo de continentes
            HashMap<Player, Integer> continentWinners = new HashMap<>();
            ArrayList<PS> winners = new ArrayList<>();
            regionWinners.add(new PS(null, -1));
            for (int c = 0; c < board.getContinents().size(); ++c) {
                  for (int r = 0; r < board.getContinents().get(c).getRegions().size(); ++r) {
                        for (int p = 0; p < players.size(); ++p) {
                              
                              int score = board.getContinents().get(c).getRegions().get(r).armiesOfPlayer(players.get(p)).size()
                                      + board.getContinents().get(c).getRegions().get(r).citiesOfPlayer(players.get(p)).size();
                              
                              if (score > regionWinners.get(0).score) {
                                    regionWinners.clear();
                                    regionWinners.add(new PS(players.get(p), score));
                              } else if (score == regionWinners.get(0).score) {
                                    regionWinners.add(new PS(players.get(p), score));
                              }
                        }

                        // Ver quem ganhou a regiao r
                        if (regionWinners.size() == 1) {
                              int x = continentWinners.get(regionWinners.get(0).player);
                              continentWinners.put(regionWinners.get(0).player, ++x);
                        }
                        regionWinners.clear();
                  }

                  // Ver quem ganhou o continente c
                  winners.add(new PS(null, -1));
                  Iterator it = continentWinners.entrySet().iterator();
                  while (it.hasNext()) {
                        Map.Entry pairs = (Map.Entry) it.next();
                        if ((int) pairs.getValue() > winners.get(0).score) {
                              winners.clear();
                              winners.add(new PS((Player) pairs.getKey(), (int) pairs.getValue()));
                              
                        } else if ((int) pairs.getValue() > winners.get(0).score) {
                              winners.add(new PS((Player) pairs.getKey(), (int) pairs.getValue()));
                        }
                        
                        it.remove(); //proximo continentWinner
                  }
                  
                  if (winners.size() == 1)
                        winners.get(0).player.addPoints(1);
                  continentWinners.clear();
                  winners.clear();
            }

            // Pontos por recursos
            for (int p = 0; p < players.size(); ++p) {
                  // criar mapa onde vai guardar o numero de cada tipo de recurso
                  HashMap<String, Integer> resources = new HashMap<>();
                  resources.put("Jewels", 0);
                  resources.put("Food", 0);
                  resources.put("Wood", 0);
                  resources.put("Iron", 0);
                  resources.put("Tools", 0);

                  // contar os recursos
                  for (int c = 0; c < players.get(p).getCardsInHand().size(); ++c) {
                        int x = resources.get(players.get(p).getCardsInHand().get(c).getResource());
                        resources.put(players.get(p).getCardsInHand().get(c).getResource(), ++x);
                  }

                  // calcular pontuação para cada tipo de recurso
                  int x = resources.get("Jewels");
                  if (x == 1) players.get(p).addCoins(1);
                  if (x == 2) players.get(p).addCoins(2);
                  if (x == 3) players.get(p).addCoins(3);
                  if (x >= 4) players.get(p).addCoins(5);
                  
                  x = resources.get("Iron");
                  if (x == 2 || x == 3) players.get(p).addCoins(1);
                  if (x == 4) players.get(p).addCoins(2);
                  if (x == 5) players.get(p).addCoins(3);
                  if (x >= 6) players.get(p).addCoins(5);
                  
                  x = resources.get("Wood");
                  if (x == 2 || x == 3) players.get(p).addCoins(1);
                  if (x == 4) players.get(p).addCoins(2);
                  if (x == 5) players.get(p).addCoins(3);
                  if (x >= 6) players.get(p).addCoins(5);
                  
                  x = resources.get("Food");
                  if (x == 3 || x == 4) players.get(p).addCoins(1);
                  if (x == 5 || x == 6) players.get(p).addCoins(2);
                  if (x == 7) players.get(p).addCoins(3);
                  if (x >= 8) players.get(p).addCoins(5);
                  
                  x = resources.get("Tools");
                  if (x == 2 || x == 3) players.get(p).addCoins(1);
                  if (x == 4 || x == 5) players.get(p).addCoins(2);
                  if (x == 6) players.get(p).addCoins(3);
                  if (x >= 7) players.get(p).addCoins(5);
            }
            
            Collections.sort(players);
            return players;
      }
      
      public boolean checkForGameOver() {
            int totalCards = 0;

            // Somatorio de todas as cartas dos jogadores
            for (int i = 0; i < players.size(); ++i) {
                  totalCards = players.get(i).getCardsInHand().size();
            }

            // Verificar se somatorio é igual ao de fim de jogo para determinado numero de jogadores
            if ((players.size() == 2 && totalCards >= 13 * 2)
                    || (players.size() == 3 && totalCards >= 10 * 3)
                    || (players.size() == 4 && totalCards >= 8 * 4)
                    || (players.size() >= 5 && totalCards >= 7 * 5)) {
                  return true;
            }
            
            return false;
      }
}
