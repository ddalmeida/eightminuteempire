package logic.map;

import java.io.Serializable;
import java.util.ArrayList;
import logic.game.Player;

public abstract class BaseRegion implements Serializable{

    int x;
    int y;
    boolean passable;
    boolean settleable; //Se é possível fundar cidades
    boolean initialRegion;

    public BaseRegion(int y, int x) {
        this.x = x;
        this.y = y;
        this.initialRegion = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isPassable() {
        return passable;
    }

    public boolean isSettleable() {
        return settleable;
    }

    public boolean isInitialRegion() {
        return initialRegion;
    }
    
    public int countArmiesOfPlayer(Player player) {
        int number = 0;
        for (int i = 0; i < player.getArmies().size(); ++i) {
            if (player.getArmies().get(i).getRegion().equals(this)) {
                number++;
            }
        }
        return number;
    }

    public void setInicialRegion(boolean b) {
        initialRegion = b;
    }
}
