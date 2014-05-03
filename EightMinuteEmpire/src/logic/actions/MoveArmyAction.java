package logic.actions;

public class MoveArmyAction extends BaseAction{
    private boolean sea;

    public MoveArmyAction(int times, boolean sea) {
        super(times);
        this.sea = sea;
    }

    @Override
    public void action() {
        
    }

    @Override
    public String toString() {
       return "Move a Army " + times + " time(s)";
    }
    
}
