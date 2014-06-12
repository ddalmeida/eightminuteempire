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

    public int addCity(BaseRegion region) {
        if (cities.size() >= 3) {
            return 2; // jogador ja tem 3 cidades
        }
        // Jogador escolheu regiao onde tem um exercito E não é no meio do mar
        if (haveArmyInRegion(region) && region.isSettleable()) {
            cities.add(new City(region));
            return 1; // Cidade fundada
        }

        return 0; // cidade não fundada porque regiao nao é validade para tal
    }

    public ArrayList<Army> getArmies() {
        return armies;
    }

    public void addArmy(BaseRegion region) {
            armies.add(new Army(region));
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

    public int moveArmy(BaseRegion from, BaseRegion to) {                
        // Procurar um exercito na regiao FROM e move-lo para a regiao TO
        for (int i = 0; i < armies.size(); ++i) {
            if (armies.get(i).getRegion().equals(from)) {
                armies.get(i).setRegion(to);
                return 1;
            }
        }
        
        return 2; // Não tem nenhum exercito na região FROM
    }

    public boolean haveArmyInRegion(BaseRegion region) {
        for (int i = 0; i < armies.size(); ++i) {
            if (armies.get(i).getRegion().equals(region)) {
                return true;
            }
        }

        return false;
    }

    public boolean haveCityInRegion(BaseRegion region) {
        for (int i = 0; i < armies.size(); ++i) {
            if (cities.get(i).getRegion() == region) {
                return true;
            }
        }

        return false;
    }
}
