package logic.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import logic.cards.*;
import logic.states.*;

public class Game {

    private List<Player> players;
    private State state;
    private Map<BaseCard, Integer> cards;
    private int activePlayer;

    public Game() {
        players = new ArrayList<>();
        cards = new HashMap<>();
        state = new StartGameState(this);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void addPlayer(String name) {
        if (name != null) {
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

        //Não inclui as cartas para jogos de 5+ jogadores
        cards.put(new BaseCard("Jewels", 1, "PlaceArmy"), 1);
        cards.put(new BaseCard("Jewels", 1, "PlaceArmyTwoTimes"), 2);
        cards.put(new BaseCard("Jewels", 1, "PlaceArmyTwoTimes"), 3);
        cards.put(new BaseCard("Jewels", 1, "MoveArmyLand"), 4);
        cards.put(new BaseCard("Food", 1, "FoundCity"), 5);
        cards.put(new BaseCard("Food", 1, "FoundCity"), 6);
        cards.put(new BaseCard("Food", 1, "MoveArmyThreeTimesLand"), 7);
        cards.put(new BaseCard("Food", 2, "MoveArmyThreeTimesLand"), 8);
        cards.put(new BaseCard("Food", 1, "MoveArmyFourTimesLand"), 9);
        cards.put(new BaseCard("Food", 1, "MoveArmyFourTimesLand"), 10);
        cards.put(new BaseCard("Food", 1, "MoveArmyFiveTimesLand"), 11);
        cards.put(new BaseCard("Food", 1, "MoveArmyThreeTimesLandWater"), 12);
        cards.put(new BaseCard("Food", 1, "PlaceArmy AND RemoveArmy"), 13);
        cards.put(new BaseCard("Wood", 1, "FoundCity"), 14);
        cards.put(new BaseCard("Wood", 1, "PlaceArmyThreeTimes"), 15);
        cards.put(new BaseCard("Wood", 1, "MoveArmyThreeTimesLand"), 16);
        cards.put(new BaseCard("Wood", 1, "MoveArmyTwoTimesLand OR MoveArmyThreeTimesLand"), 17);
        cards.put(new BaseCard("Wood", 1, "MoveArmyThreeTimesLandWater"), 18);
        cards.put(new BaseCard("Wood", 1, "MoveArmyFourTimesLandWater"), 19);
        cards.put(new BaseCard("Wood", 1, "RemoveArmy OR FoundCity"), 20);
        cards.put(new BaseCard("Iron", 1, "PlaceArmyTwoTimes"), 21);
        cards.put(new BaseCard("Iron", 1, "PlaceArmyThreeTimes"), 22);
        cards.put(new BaseCard("Iron", 1, "PlaceArmyThreeTimes"), 23);
        cards.put(new BaseCard("Iron", 1, "MoveArmyTwoTimesLand"), 24);
        cards.put(new BaseCard("Iron", 1, "MoveArmyTwoTimesLandWater"), 25);
        cards.put(new BaseCard("Iron", 1, "MoveArmyThreeTimesLandWater"), 26);
        cards.put(new BaseCard("Tools", 1, "FoundCity"), 27);
        cards.put(new BaseCard("Tools", 1, "PlaceArmyThreeTimes"), 28);
        cards.put(new BaseCard("Tools", 1, "PlaceArmyThreeTimes"), 29);
        cards.put(new BaseCard("Tools", 1, "MoveArmyThreeTimesLandWater"), 30);
        cards.put(new BaseCard("Tools", 1, "MoveArmyFourTimesLand"), 31);
        cards.put(new BaseCard("Tools", 1, "PlaceArmyThreeTimes OR MoveArmyThreeTimesLand"), 32);
        cards.put(new BaseCard("Tools", 1, "PlaceArmyThreeTimes OR MoveArmyFourTimesLand"), 33);
        cards.put(new BaseCard("Tools", 1, "MoveArmyFiveTimesLand"), 34);
        cards.put(new BaseCard("Joker", 1, "PlaceArmyTwoTimes"), 35);
        cards.put(new BaseCard("Joker", 1, "MoveArmyTwoTimesLandWater"), 36);
        cards.put(new BaseCard("Joker", 1, "MoveArmyTwoTimesLandWater"), 37);
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
