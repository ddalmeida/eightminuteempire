package logic.states;

import java.io.Serializable;
import logic.game.Game;
import logic.game.Player;
import logic.map.BaseRegion;

public abstract class State implements Serializable{
    Game game;
    
    State(Game game)
    {
        this.game = game;
    }
    
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
}