package ia;

import java.util.Observable;
import java.util.Observer;
import logic.game.Model;
import logic.game.Player;

public class ia implements Observer{
    private final Player me;
    private final Model model;
    
    public ia(Player me, Model model)
    {
        this.me = me;
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
   switch(model.getState().getClass().getSimpleName())
   {
         case "BuyCardState":
               break;
   }
    }
}
