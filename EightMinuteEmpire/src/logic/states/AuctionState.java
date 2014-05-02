package logic.states;

import logic.game.Game;

public class AuctionState extends StateAdapter {

    public AuctionState(Game game) {
        super(game);
    }

    public State bet(int playerNumber, int coins) {
        //if (game.getPlayer(playerNumber).getCoins() < coins) {
        //}
        //supostamente já foi testado antes?
        
        game.betCoins(playerNumber, coins);
        
        //retorna para o próximo jogador?
    }
}