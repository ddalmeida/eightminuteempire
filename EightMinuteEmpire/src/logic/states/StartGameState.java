package logic.states;

import logic.game.Game;

public class StartGameState extends StateAdapter {

    public StartGameState(Game game) {
        super(game);
    }
    
    @Override
    public State AddPlayer(String name){
        if (name != null || game.numberOfPlayers() < 2)
        { // new player
           game.addPlayer(name);
           return this;
        }
        else
        { // no more players
            game.addInitialCoins();
            return new AuctionState(game);
        }
    }
}
