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
        int aux = region.removeArmy(player);
        switch (aux) {
            case 0:
                game.nextPlayer();
                return new BuyCardState(game);
            case 1:
                return this;
            case 2:
                return this;
        }
        return this;
    }
}