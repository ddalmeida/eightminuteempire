package logic.states;

import logic.game.Game;

public abstract class State {
    Game game;
    
    State(Game game)
    {
        this.game = game;
    }
    
    public abstract State AddPlayer(String name);
    public abstract State run();
    public abstract State bet(int playerNumber, int coins);
    public abstract State endTurn();
    public abstract State playMoveArmyCard();
    public abstract State moveArmy();
    public abstract State playFoundCityCard();
    public abstract State foundCity();
    public abstract State pickAction();
    public abstract State pickFirstAction();
    public abstract State playPlaceArmyCard();
    public abstract State placeArmy();
    public abstract State playRemoveArmyCard();
    public abstract State removeArmy();
    public abstract State endGame();
}