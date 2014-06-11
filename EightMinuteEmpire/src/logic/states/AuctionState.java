package logic.states;

import logic.game.Game;

public class AuctionState extends StateAdapter {

    int playersBet; // numero de jogadores que já apostou

    public AuctionState(Game game) {
        super(game);
        playersBet = 0;
    }

    @Override
    public State bet(int playerNumber, int coins) {
        game.betCoins(playerNumber, coins);
        playersBet++;
        if (playersBet == game.getNumberOfPlayers()) {
            game.determinateActivePlayer();
            return new BuyCardState(game);
        } else {
            return this;
        }
    }
}
