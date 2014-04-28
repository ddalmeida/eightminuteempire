package logic.states;

public interface State {
    State pickNumberPlayers();
    State run();
    State bet();
    State endTurn();
    State playMoveArmyCard();
    State moveArmy();
    State playFoundCityCard();
    State foundCity();
    State pickAction();
    State pickFirstAction();
    State playPlaceArmyCard();
    State placeArmy();
    State playRemoveArmyCard();
    State removeArmy();
    State endGame();
}