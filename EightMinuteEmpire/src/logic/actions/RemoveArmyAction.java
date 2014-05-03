package logic.actions;

public class RemoveArmyAction extends BaseAction{

    public RemoveArmyAction(int times) {
        super(times);
    }

    @Override
    public void action() {
    }

    @Override
    public String toString() {
       return "Remove an Army" + times + " time(s)";
    }

}
