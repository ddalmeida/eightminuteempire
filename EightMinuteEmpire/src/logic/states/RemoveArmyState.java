package logic.states;

import logic.game.Game;
import logic.game.Player;
import logic.map.BaseRegion;

public class RemoveArmyState extends StateAdapter {

    public RemoveArmyState(Game game) {
        super(game);
    }

    @Override
    public State removeArmy(Player player, BaseRegion region) {
        boolean deleted = player.removeArmy(region);
        if (deleted) {
            game.nextPlayer();
            return new BuyCardState(game);
        }

        return this;
    }

    public State endTurn() {
        game.nextPlayer();
        return new BuyCardState(game);
    }
}
