package logic.game;

import java.util.ArrayList;
import logic.states.*;

public class Game {

    private ArrayList<Player> players;
    private State state;

    public Game() {
        players = new ArrayList<>();
        state = new StartGameState(this);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void addPlayer(String name) {
        players.add(new Player(name, 0));
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

    public void betCoins(int playerNumber, int coins) {
        players.get(playerNumber).removeCoins(coins);
        players.get(playerNumber).setInitialBet(coins);
    }
}
