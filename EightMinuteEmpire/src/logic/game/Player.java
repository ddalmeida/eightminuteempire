package logic.game;

import java.io.Serializable;
import java.util.ArrayList;
import logic.cards.RegularCard;
import logic.map.BaseRegion;

public class Player implements Comparable<Player>, Serializable {

    private String name;
    private int coins;
    private int points;
    private int initialBet;
    private ArrayList<RegularCard> cardsInHand;
    private ArrayList<Army> armies;
    private ArrayList<City> cities;

    Player(String name, int coins) {
        this.name = name;
        this.coins = coins;
        points = 0;
        initialBet = 0;
        cardsInHand = new ArrayList<>();
        armies = new ArrayList<>();
        cities = new ArrayList<>();
    }

    @Override
    public int compareTo(Player o) {
        return (int) (o.getPoints() - this.points);
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

    // inutil?
    public void addPoints(int n) {
        points += n;
    }

    // inutil?
    public int getPoints() {
        return points;
    }

    public void addCard(RegularCard Card) {
        cardsInHand.add(Card);
    }

    public ArrayList<RegularCard> getCardsInHand() {
        return cardsInHand;
    }

    public void addCity(City city) {
        cities.add(city);
    }
    
        public ArrayList<City> getCities() {
        return cities;
    }

    public ArrayList<Army> getArmies() {
        return armies;
    }

    public void addArmy(Army army) {
        armies.add(army);
    }
    
    public boolean removeArmy(BaseRegion region) {

        for (int i = 0; i < armies.size(); i++) {
            if (armies.get(i).getRegion().equals(region)) {
                armies.remove(i);
                return true; // Apaga o primeiro exercito encontrado na regiao
            }
        }
            return false; // Nenhum exercito encontrado na regiao
    }
    
    public boolean haveArmyInRegion(int y, int x)
    {
        for (int i = 0; i < armies.size(); ++i)
        {
            if (armies.get(i).getRegion().getY() == y && armies.get(i).getRegion().getX() == x)
                return true;
        }
        
        return false;
    }
    
     public boolean haveArmyInRegion(BaseRegion region)
    {
        for (int i = 0; i < armies.size(); ++i)
        {
            if (armies.get(i).getRegion() == region)
                return true;
        }
        
        return false;
    }
     
          public boolean haveCityInRegion(BaseRegion region)
    {
        for (int i = 0; i < armies.size(); ++i)
        {
            if (cities.get(i).getRegion() == region)
                return true;
        }
        
        return false;
    }
}
