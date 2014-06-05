package ia;

import java.util.Observable;
import java.util.Observer;
import logic.game.Model;

public class ia implements Observer{
    private String name;
    private Model model;
    
    public ia(String name, Model model)
    {
        this.name = name;
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
    
    }
}
