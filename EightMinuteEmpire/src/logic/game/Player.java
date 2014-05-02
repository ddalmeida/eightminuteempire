package logic.game;

import java.util.ArrayList;
import java.util.List;
import logic.cards.*;

public class Player implements Comparable<Player> {
    private String name;
    private int coins;
    private int points;
    private int initialBet;
    private List<BaseCard> cardsInHand;

    Player(String name, int coins) {
        this.name = name;
        this.coins = coins;
        points = 0;
        initialBet = 0;
        cardsInHand = new ArrayList<>();
    }
    
    @Override
    public int compareTo(Player o) {
        return (int)(o.getPoints()- this.points);
    }

    public String getName() {
        return name;
    }

    public int getCoins() {
        return coins;
    }

    public int getInitialBet() {
        return initialBet;
    }

    public void setInitialBet(int initialBet) {
        this.initialBet = initialBet;
    }

    public void removeCoins(int n) {
        coins -= n >= 0 ? n : 0;
    }

    public void addCoins(int n) {
        coins += n;
    }
    
    public void addPoints(int n)
    {
        points += n;
    }
    
    public int getPoints()
    {
        return points;
    }
}
