package logic.states;

import logic.game.Player;
import logic.map.BaseRegion;

public abstract interface State{ 
    // Estado não fez nada ainda / não alterou o result / indicar novo estado inicioado, ir buscar o ultimo valor
    public static final int NEW_STATE_CODE = -99;
    // Para o estado saber que tem de voltar para o AND em vez de BuyCardState;
    public static final int RETURN_TO_AND_FIRST_OPTION_DONE = -98;
    public static final int RETURN_TO_AND_SECOND_OPTION_DONE = -97;
    
    public abstract State playersAdded();
    public abstract State run();
    public abstract State bet(int playerNumber, int coins);
    public abstract State endTurn();
    public abstract State playMoveArmyCard(int x);
    public abstract State moveArmy(BaseRegion from, BaseRegion to, boolean sea);
    public abstract State playFoundCityCard();
    public abstract State foundCity(BaseRegion region);
    public abstract State pickAction();
    public abstract State pickFirstAction();
    public abstract State playPlaceArmyCard(int x);
    public abstract State placeArmy(BaseRegion region);
    public abstract State playRemoveArmyCard();
    public abstract State removeArmy(Player player, BaseRegion region);
    public abstract State chosenAction(int firstAction);
    public abstract int getX();
    public abstract int getResult();
    public abstract int getPreviousStateResult();
}