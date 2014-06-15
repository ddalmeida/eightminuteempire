package logic.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import logic.cards.RegularCard;
import logic.map.BaseRegion;
import logic.map.Board;
import logic.states.State;

public class Model extends Observable {

      private final Game game;

      public Model(Game game) {
            this.game = game;
      }

      // ** ESTADOS
      public State getState() {
            return game.getState();
      }

      public int getCardTurns() {
            return game.getCardTurns();
      }

      public void addPlayer(String name) {
            game.addPlayer(name);
            setChanged();
            notifyObservers();
      }

      public int choseCard(int cardNumber, boolean useAction) {
            setChanged();
            notifyObservers();
            return game.choseCard(cardNumber, useAction);
      }

      public void placeBet(int playerNumber, int coins) {

            game.placeBet(playerNumber, coins);
            setChanged();
            notifyObservers();
      }

      public int foundCity(int y, int x) {
            setChanged();
            notifyObservers();
            return game.foundCity(y, x);
      }

      public int placeArmy(int y, int x) {
            setChanged();
            notifyObservers();
            return game.placeArmy(y, x);
      }

      public int moveArmy(int y, int x, int y2, int x2) {
            setChanged();
            notifyObservers();
            return game.moveArmy(y, x, y2, x2);
      }

      public int removeArmy(Player player, int y, int x) {
            setChanged();
            notifyObservers();
            return game.removeArmy(player, y, x);
      }

      public int removeArmy(Player player, BaseRegion region) {
            setChanged();
            notifyObservers();
            return game.removeArmy(player, region);
      }

      public void pickAction(int actionChosen) {
            setChanged();
            notifyObservers();
            game.pickAction(actionChosen);
      }

      public void pickFirstAction(int actionChosen) {
            setChanged();
            notifyObservers();
            game.pickFirstAction(actionChosen);
      }

      // ** JOGADORES
      public int getNumberOfPlayers() {
            return game.getNumberOfPlayers();
      }

      public Player getPlayer(int i) {
            return game.getPlayer(i);
      }

      public ArrayList<Player> getPlayers() {
            return game.getPlayers();
      }

      public Player getActivePlayer() {
            return game.getActivePlayer();
      }

      public void betCoins(int playerNumber, int coins) {
            game.betCoins(playerNumber, coins);
      }

      public void addInitialCoins() {
            game.addInitialArmies();
      }

      public void determinateActivePlayer() {
            game.determinateActivePlayer();
      }

      public void boughtCard(int cardNumber) {
            game.boughtCard(cardNumber);
      }

      public void nextPlayer() {
            game.nextPlayer();
      }

      public void addInitialArmies() {
            game.addInitialArmies();
      }

      // ** CARTAS
      public void initializeCards() {
            game.initializeCards();
      }

      public ArrayList<RegularCard> getCardsTable() {
            return game.getCardsTable();
      }

      public RegularCard getBoughtCard() {
            return game.getBoughtCard();
      }

      // ** OUTRAS
      public int getRound() {
            return game.getRound();
      }

      public Board getBoard() {
            return game.getBoard();
      }

      public List<Player> getScoreTable() {
            return game.getScoreTable();
      }

      public boolean checkForGameOver() {
            return game.checkForGameOver();
      }

      public Player whoControlsThisRegion(BaseRegion region) {
            return game.whoControlsThisRegion(region);
      }

      public int howManyRegionsControls(Player p) {
            return game.howManyRegionsControls(p);
      }
}
