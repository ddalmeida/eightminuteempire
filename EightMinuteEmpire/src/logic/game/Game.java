package logic.game;

import java.util.ArrayList;
import logic.states.*;

public class Game {
    private ArrayList<Player> players;
    State state;

    public Game() {
        players = new ArrayList<Player>();
        state = new StartGameState(this);
    }
    
    public void addPlayer(Player p)
    {
        players.add(p);
    }
}
