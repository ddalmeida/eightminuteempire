package logic.game;

import java.util.Observable;

public class Model extends Observable{
    private Game game;
    
    public Model(Game game)
    {
        this.game = game;
    }
}
