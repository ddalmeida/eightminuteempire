package logic.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import logic.actions.*;
import logic.cards.*;
import logic.states.*;

public class Game {

    private List<Player> players;
    private List<RegularCard> cards;
    private List<RegularCard> cardsTable;
    private int activePlayer;
    private State state;

    public Game() {
        players = new ArrayList<>();
        cards = new ArrayList<>();
        cardsTable = new ArrayList<>();

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
        cards.add(new RegularCard("Jewels", 1, new MoveArmyAction(2, false)));
        cards.add(new RegularCard("Food", 1, "FoundCity"));
        cards.add(new RegularCard("Food", 1, "MoveArmyThreeTimesLand"));
        cards.add(new RegularCard("Food", 2, "MoveArmyThreeTimesLand"));
        cards.add(new RegularCard("Food", 1, "MoveArmyFourTimesLand"));
        cards.add(new RegularCard("Food", 1, "MoveArmyFourTimesLand"));
        cards.add(new RegularCard("Food", 1, "MoveArmyFiveTimesLand"));
        cards.add(new RegularCard("Food", 1, "MoveArmyThreeTimesLandWater"));
        cards.add(new RegularCard("Food", 1, "PlaceArmy AND RemoveArmy"));
        cards.add(new RegularCard("Wood", 1, "FoundCity"));
        cards.add(new RegularCard("Wood", 1, "PlaceArmyThreeTimes"));
        cards.add(new RegularCard("Wood", 1, "MoveArmyThreeTimesLand"));
        cards.add(new RegularCard("Wood", 1, "MoveArmyTwoTimesLand OR MoveArmyThreeTimesLand"));
        cards.add(new RegularCard("Wood", 1, "MoveArmyThreeTimesLandWater"));
        cards.add(new RegularCard("Wood", 1, "MoveArmyFourTimesLandWater"));
        cards.add(new RegularCard("Wood", 1, "RemoveArmy OR FoundCity"));
        cards.add(new RegularCard("Iron", 1, "PlaceArmyTwoTimes"));
        cards.add(new RegularCard("Iron", 1, "PlaceArmyThreeTimes"));
        cards.add(new RegularCard("Iron", 1, "PlaceArmyThreeTimes"));
        cards.add(new RegularCard("Iron", 1, "MoveArmyTwoTimesLand"));
        cards.add(new RegularCard("Iron", 1, "MoveArmyTwoTimesLandWater"));
        cards.add(new RegularCard("Iron", 1, "MoveArmyThreeTimesLandWater"));
        cards.add(new RegularCard("Tools", 1, "FoundCity"));
        cards.add(new RegularCard("Tools", 1, "PlaceArmyThreeTimes"));
        cards.add(new RegularCard("Tools", 1, "PlaceArmyThreeTimes"));
        cards.add(new RegularCard("Tools", 1, "MoveArmyThreeTimesLandWater"));
        cards.add(new RegularCard("Tools", 1, "MoveArmyFourTimesLand"));
        cards.add(new RegularCard("Tools", 1, "PlaceArmyThreeTimes OR MoveArmyThreeTimesLand"));
        cards.add(new RegularCard("Tools", 1, "PlaceArmyThreeTimes OR MoveArmyFourTimesLand"));
        cards.add(new RegularCard("Tools", 1, "MoveArmyFiveTimesLand"));
        cards.add(new RegularCard("Joker", 1, "PlaceArmyTwoTimes"));
        cards.add(new RegularCard("Joker", 1, "MoveArmyTwoTimesLandWater"));
        cards.add(new RegularCard("Joker", 1, "MoveArmyTwoTimesLandWater"));

        // Cartas apenas disponiveis se houve 5+ jogadores
        if (numberOfPlayers() >= 5) {
            cards.add(new RegularCard("Jewels", 1, new MoveArmyAction(2, false)));
        }
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
            Random r = new Random();
            activePlayer = players.indexOf(highestBidders.get(r.nextInt(highestBidders.size())));
        } else {
            activePlayer = players.indexOf(highestBidders.get(0));
        }
    }

    public List<Player> getScoreTable() {
        Collections.sort(players);
        return players;
    }
}
