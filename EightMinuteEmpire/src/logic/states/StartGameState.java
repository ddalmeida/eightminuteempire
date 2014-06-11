package logic.states;

import logic.game.Game;

public class StartGameState extends StateAdapter {

    public StartGameState(Game game) {
        super(game);
    }

    @Override
    public State playersAdded() {
        // Se existirem 2+ jogadores avançar para o proximo estado.
        if (game.getNumberOfPlayers() >= 2) {
            game.addInitialCoins();
            game.addInitialArmies();
            game.initializeCards();
            return new AuctionState(game);
        } else {
            return this;
        }
    }
}
