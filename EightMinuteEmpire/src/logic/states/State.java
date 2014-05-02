package logic.states;

import logic.game.Game;

public abstract class State {
    protected Game game;
    
    State(Game game)
    {
        this.game = game;
    }
    
    abstract State AddPlayer(String name);
    abstract State run();
    abstract State bet();
    abstract State endTurn();
    abstract State playMoveArmyCard();
    abstract State moveArmy();
    abstract State playFoundCityCard();
    abstract State foundCity();
    abstract State pickAction();
    abstract State pickFirstAction();
    abstract State playPlaceArmyCard();
    abstract State placeArmy();
    abstract State playRemoveArmyCard();
    abstract State removeArmy();
    abstract State endGame();
}