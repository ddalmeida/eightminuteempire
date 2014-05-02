package logic.game;

public class Player {

    private String name;
    private int coins;
    private int points;

    Player(String name, int coins) {
        this.name = name;
        this.coins = coins;
        points = 0;
    }

    public String getName() {
        return name;
    }

    public int getCoins() {
        return coins;
    }

    public void removeCoins(int n) {
        coins -= n >= 0 ? coins - n : 0;
    }

    public void addCoins(int n) {
        coins += n;
    }
    
    public void addPoints(int n)
    {
        points += n;
    }
    
    public int getPoints(int n)
    {
        return points;
    }
}
