package logic.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import logic.actions.*;
import logic.cards.*;
import logic.states.*;

public class Game {

    private ArrayList<Player> players;
    private ArrayList<RegularCard> cards;
    private ArrayList<RegularCard> cardsTable;
    private int activePlayer;
    private State state;
    private Random rnd;

    public Game() {
        players = new ArrayList<>();
        cards = new ArrayList<>();
        cardsTable = new ArrayList<>();
        rnd = new Random();

        // Carregar mapa?
        state = new StartGameState(this);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void addPlayer(String name) {
        if (name != "") {
            players.add(new Player(name, 0));
        }
    }

    public int numberOfPlayers() {
        return players.size();
    }

    public Player getPlayer(int i) {
        return players.get(i);
    }
        
    public Player getActivePlayer()
    {
        return players.get(activePlayer);
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

        for (int i = 0; i < numberOfPlayers(); ++i) {
            players.get(i).addCoins(coins);
        }
    }

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
            cardsTable.add(cards.get(rnd.nextInt(cards.size())));
        }
    }
    
    public ArrayList<RegularCard> getCardsTable()
    {
        return cardsTable;
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

        players.get(playerNumber).removeCoins(coins);
        players.get(playerNumber).setInitialBet(coins);
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
    }

    public List<Player> getScoreTable() {
        Collections.sort(players);
        return players;
    }
}
