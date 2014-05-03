package logic.armies;

import logic.game.*;

public class Army {
    Player owner;
    int x;
    int y;
    
    public Army (Player owner, int x, int y) {
        this.owner = owner;
        this.x = x;
        this.y = y;
    }
    
    public Player getOwner() {
        return owner;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}