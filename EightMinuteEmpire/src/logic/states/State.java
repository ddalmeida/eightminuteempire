package logic.states;

import logic.game.Game;

public abstract class State {
    private Game game;
    
    State(Game game)
    {
        this.game = game;
    }
    
    abstract State pickNumberPlayers(Game game);
    abstract State run(Game game);
    abstract State bet(Game game);
    abstract State endTurn(Game game);
    abstract State playMoveArmyCard(Game game);
    abstract State moveArmy(Game game);
    abstract State playFoundCityCard(Game game);
    abstract State foundCity(Game game);
    abstract State pickAction(Game game);
    abstract State pickFirstAction(Game game);
    abstract State playPlaceArmyCard(Game game);
    abstract State placeArmy(Game game);
    abstract State playRemoveArmyCard(Game game);
    abstract State removeArmy(Game game);
    abstract State endGame(Game game);
}