package logic.states;

import logic.game.Player;
import logic.map.BaseRegion;

public abstract interface State{ 
    public abstract State playersAdded();
    public abstract State run();
    public abstract State bet(int playerNumber, int coins);
    public abstract State endTurn();
    public abstract State playMoveArmyCard(int cardNumber, int x);
    public abstract State moveArmy();
    public abstract State playFoundCityCard(int cardNumber);
    public abstract State foundCity();
    public abstract State pickAction(int cardNumber);
    public abstract State pickFirstAction();
    public abstract State playPlaceArmyCard(int cardNumber, int x);
    public abstract State placeArmy();
    public abstract State playRemoveArmyCard(int cardNumber);
    public abstract State removeArmy(Player player, BaseRegion region);
    public abstract State endGame();
    public abstract int getX();
}