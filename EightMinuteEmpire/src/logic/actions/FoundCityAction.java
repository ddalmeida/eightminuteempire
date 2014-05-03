package logic.actions;

public class FoundCityAction extends BaseAction{

    public FoundCityAction(int times) {
        super(times);
    }

    @Override
    public void action() {
    }

    @Override
    public String toString() {
        return "Found " + times + " City";
    }
    
}
