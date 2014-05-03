package logic.actions;

public abstract class BaseAction {
    int times;
    
    public BaseAction(int times)
    {
        this.times = times;
    }
    
    public void minusOne()
    {
        times--;
    }
    
    public int getTimes()
    {
        return times;
    }
    
    public abstract void action();
}