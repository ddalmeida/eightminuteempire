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
      private Game game;

      Player(String name, int coins, Game game) {
            this.name = name;
            this.coins = coins;
            points = 0;
            initialBet = 0;
            cardsInHand = new ArrayList<>();
            armies = new ArrayList<>();
            cities = new ArrayList<>();
            this.game = game;
      }

      @Override
      public int compareTo(Player o) {
            // valor negativo = este jogador é superior
            // 0 = estao empatados
            // valor positivo = jogador O é superior

            // Verificar se estão empatados em pontos
            if (o.getPoints() == this.points) {
                  // Verificar se estão empatados em moedas
                  if (o.getCoins() == this.coins) {
                        // Verificar se estão empatados em exercitos
                        if (o.getArmies().size() == this.armies.size()) {
                              // Verificar se estão empatados em controlo de regiões
                              if (game.howManyRegionsControls(o) == game.howManyRegionsControls(this))
                                    return 0;
                              else
                                    return (int) game.howManyRegionsControls(o) - game.howManyRegionsControls(this);
                        } else return (int) (o.getArmies().size() - this.armies.size());
                  } else return (int) (o.getCoins() - this.coins);
            } else return (int) (o.getPoints() - this.points);
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

      public void addPoints(int n) {
            points += n;
      }

      public int getPoints() {
            return points;
      }

      public void addCard(RegularCard Card) {
            cardsInHand.add(Card);
      }

      public ArrayList<RegularCard> getCardsInHand() {
            return cardsInHand;
      }
      
            public int getJokerCardsInHandCount()
            {
                  int count = 0;
                  for (int i = 0; i < cardsInHand.size(); ++i)
                        if (cardsInHand.get(i).getResource() == RegularCard.Type.Joker)
                              count++;
                  
                  return count;
            }
            
            public void convertJokersTo(int newType)
            {
                  // verificar se novo tipo de recurso está dentro dos limites.
                  // se não estiver, castigar jogador.
                  if (newType < 0 || newType >= RegularCard.Type.values().length)
                        newType = RegularCard.Type.Food.getCode();
                  
                  // Converter Jokers
                  for (int i = 0; i < cardsInHand.size(); ++i)
                        if (cardsInHand.get(i).getResource() == RegularCard.Type.Joker)
                              cardsInHand.get(i).setResource(RegularCard.Type.values()[newType]);
            }

      public void addCity(City city) {
            cities.add(city);
      }

      public ArrayList<City> getCities() {
            return cities;
      }

      public void addCity(BaseRegion region) {
            cities.add(new City(region));
      }

      public ArrayList<Army> getArmies() {
            return armies;
      }

      public void addArmy(BaseRegion region) {
            armies.add(new Army(region));
      }

      public int removeArmy(BaseRegion region) {
            for (int i = 0; i < armies.size(); ++i) {
                  if (armies.get(i).getRegion().equals(region)) {
                        armies.remove(i);
                        return 1; // Apaga o primeiro exercito encontrado na regiao
                  }
            }
            return 0; // Nenhum exercito encontrado na regiao
      }

      public int moveArmy(BaseRegion from, BaseRegion to) {
            // Procurar um exercito na regiao FROM e move-lo para a regiao TO
            for (int i = 0; i < armies.size(); ++i) {
                  if (armies.get(i).getRegion().equals(from)) {
                        armies.get(i).setRegion(to);
                        return 1;
                  }
            }

            return -1; // Não tem nenhum exercito na região FROM
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
            for (int i = 0; i < cities.size(); ++i) {
                  if (cities.get(i).getRegion() == region) {
                        return true;
                  }
            }

            return false;
      }
}
