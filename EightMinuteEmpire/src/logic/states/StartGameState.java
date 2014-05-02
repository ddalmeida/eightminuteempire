package logic.states;

import logic.game.Game;

public class StartGameState extends StateAdapter {

    public StartGameState(Game game) {
        super(game);
    }

    @Override
    public State AddPlayer(String name) {
        if (name != null) {
            game.addPlayer(name);
        }

        return this;
    }

    public State run() {
        if (game.numberOfPlayers() >= 2) {
            game.addInitialCoins();
            return new AuctionState(game);
        } else {
            return this;
        }
    }
}
