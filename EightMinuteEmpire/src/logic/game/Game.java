package logic.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import logic.actions.*;
import logic.cards.*;
import logic.states.*;

public class Game implements Serializable{

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

    public void setState(State state) {
        this.state = state;
    }

    public void addPlayer(String name) {
        if (name != null) {
            players.add(new Player(name, 0));
        } else {
            state = state.playersAdded();
        }
    }

    public int S_boughtCard(int cardNumber, boolean useAction) {
        // Se o jogador não tiver moedas para a carta que escolheu, escolher a primeira.
        if ((cardNumber + 1) / 2 > getActivePlayer().getCoins()) {
            cardNumber = 0;
        }
        if (useAction) {
            if (cardsTable.get(cardNumber) instanceof OrCard)
            {
                state = state.pickAction(cardNumber);
            }
            // Ver se é uma carta de colocar excecito e se nao atingiu os 14
            if (cardsTable.get(cardNumber).getAction() instanceof PlaceArmyAction) {
                if (getActivePlayer().getArmies().size() < 14) {
                    state = cardsTable.get(cardNumber).getAction().doAction(this, cardNumber);
                } else {
                    state = state.endTurn();
                    return 1;
                }
            }

            // Ver se é uma carta de fundar cidade e se nao atingiu as 3
            if (cardsTable.get(cardNumber).getAction() instanceof FoundCityAction) {
                if (getActivePlayer().getCities().size() < 3) {
                    state = cardsTable.get(cardNumber).getAction().doAction(this, cardNumber);
                } else {
                    state = state.endTurn();
                    return 2;
                }
            }

        } else {
            state = state.endTurn();
        }

        return 0;
    }

    public void S_bet(int playerNumber, int coins) {
        state = state.bet(playerNumber, coins);
    }

    public boolean S_foundCity(int y, int x) {
        City newCity = board.getRegion(y, x).addCity(y, x, getActivePlayer());
        if (newCity == null) {
            return false;
        } else {
            getActivePlayer().addCity(newCity);
            state = state.endTurn();
            return true;
        }
    }

    public boolean S_PlaceArmy(int y, int x) {
        Army newArmy = board.getRegion(y, x).addArmy(y, x, getActivePlayer());
        if (newArmy == null) {
            return false;
        } else {
            getActivePlayer().addArmy(newArmy);
            if (getActivePlayer().getArmies().size() >= 14) {
                state = state.endTurn();
            } else {
                state = state.placeArmy();
            }
        }

        return true;
    }

    // ** JOGADORES

    public int numberOfPlayers() {
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

        if (numberOfPlayers() == 2) {
            coins = 14;
        } else if (numberOfPlayers() == 3) {
            coins = 11;
        } else if (numberOfPlayers() == 4) {
            coins = 9;
        }

        // Atribuir as moedas a todos os jogadores
        for (int i = 0; i < numberOfPlayers(); ++i) {
            players.get(i).addCoins(coins);
        }
    }

    public void determinateActivePlayer() {
        ArrayList<Player> highestBidders = new ArrayList<Player>();
        highestBidders.add(players.get(0));

        // Ver quem é que apostou mais
        for (int i = 1; i < numberOfPlayers(); ++i) {
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
        activePlayer = (activePlayer + 1) % numberOfPlayers();
    }

    public void addInitialArmies() {
        for (int i = 0; i < numberOfPlayers(); ++i) {
            for (int j = 0; j < 3; ++j) {
                players.get(i).addArmy(board.getInicialRegion().addArmy(players.get(i)));
            }
        }
    }

    // ** CARTAS
    public void initializeCards() {
        cards.add(new RegularCard("Jewels", 1, new PlaceArmyAction(1)));
        cards.add(new RegularCard("Jewels", 1, new PlaceArmyAction(2)));
        cards.add(new RegularCard("Jewels", 1, new PlaceArmyAction(2)));
        cards.add(new RegularCard("Jewels", 1, new MoveArmyAction(2, false)));

        cards.add(new RegularCard("Food", 1, new FoundCityAction(1)));
        cards.add(new RegularCard("Food", 1, new FoundCityAction(1)));
        cards.add(new RegularCard("Food", 1, new MoveArmyAction(3, false)));
        cards.add(new RegularCard("Food", 2, new MoveArmyAction(3, false)));
        cards.add(new RegularCard("Food", 1, new MoveArmyAction(4, false)));
        cards.add(new RegularCard("Food", 1, new MoveArmyAction(4, false)));
        cards.add(new RegularCard("Food", 1, new MoveArmyAction(5, false)));
        cards.add(new RegularCard("Food", 1, new MoveArmyAction(3, true)));
        cards.add(new AndCard("Food", 1, new PlaceArmyAction(1), new RemoveArmyAction(1)));

        cards.add(new RegularCard("Wood", 1, new FoundCityAction(1)));
        cards.add(new RegularCard("Wood", 1, new PlaceArmyAction(3)));
        cards.add(new RegularCard("Wood", 1, new MoveArmyAction(3, false)));
        cards.add(new OrCard("Wood", 1, new MoveArmyAction(2, true), new MoveArmyAction(3, false)));
        cards.add(new RegularCard("Wood", 1, new MoveArmyAction(3, true)));
        cards.add(new RegularCard("Wood", 1, new MoveArmyAction(4, true)));
        cards.add(new OrCard("Wood", 1, new RemoveArmyAction(1), new FoundCityAction(1)));

        cards.add(new RegularCard("Iron", 1, new PlaceArmyAction(2)));
        cards.add(new RegularCard("Iron", 1, new PlaceArmyAction(3)));
        cards.add(new RegularCard("Iron", 1, new PlaceArmyAction(3)));
        cards.add(new RegularCard("Iron", 1, new MoveArmyAction(2, false)));
        cards.add(new RegularCard("Iron", 1, new MoveArmyAction(2, true)));
        cards.add(new RegularCard("Iron", 1, new MoveArmyAction(3, true)));
        ;
        cards.add(new RegularCard("Tools", 1, new FoundCityAction(1)));
        cards.add(new RegularCard("Tools", 1, new PlaceArmyAction(3)));
        cards.add(new RegularCard("Tools", 1, new PlaceArmyAction(3)));
        cards.add(new RegularCard("Tools", 1, new MoveArmyAction(3, true)));
        cards.add(new RegularCard("Tools", 1, new MoveArmyAction(4, false)));
        cards.add(new OrCard("Tools", 1, new PlaceArmyAction(3), new MoveArmyAction(3, false)));
        cards.add(new OrCard("Tools", 1, new PlaceArmyAction(3), new MoveArmyAction(4, false)));
        cards.add(new RegularCard("Tools", 1, new MoveArmyAction(5, false)));

        cards.add(new RegularCard("Joker", 1, new PlaceArmyAction(2)));
        cards.add(new RegularCard("Joker", 1, new MoveArmyAction(2, true)));
        cards.add(new RegularCard("Joker", 1, new MoveArmyAction(2, true)));

        // Cartas apenas disponiveis se houve 5+ jogadores
        if (numberOfPlayers() >= 5) {
            cards.add(new RegularCard("Jewels", 1, new MoveArmyAction(2, false)));
            cards.add(new OrCard("Food", 1, new PlaceArmyAction(4), new MoveArmyAction(2, false)));
            cards.add(new RegularCard("Wood", 1, new MoveArmyAction(6, false)));
            cards.add(new RegularCard("Iron", 1, new MoveArmyAction(2, true)));
            cards.add(new RegularCard("Tools", 2, new MoveArmyAction(4, false)));
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
