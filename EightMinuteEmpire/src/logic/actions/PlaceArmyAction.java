package logic.actions;

public class PlaceArmyAction extends BaseAction{

    public PlaceArmyAction(int times) {
        super(times);
    }

    @Override
    public void action() {
        
    }

    @Override
    public String toString() {
        return "Place an Army"  + times + " time(s)";
    }
    
}
